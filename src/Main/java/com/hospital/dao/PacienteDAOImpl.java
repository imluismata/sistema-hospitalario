package com.hospital.dao;

import com.hospital.modelo.Paciente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PacienteDAOImpl {

    // Nuestra llave maestra para MySQL
    private Conexion conexion = new Conexion();

    // 1. Cuando llega un paciente nuevo a la sala de espera
    public void registrarPaciente(Paciente paciente) {
        // Por defecto, cuando entra por la puerta su estado es 'En espera'
        String sql = "INSERT INTO Pacientes (Nombre, Cedula, Estado) VALUES (?, ?, 'En espera')";

        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, paciente.getNombre());
            ps.setString(2, paciente.getCedula());

            ps.executeUpdate();
            System.out.println("DAO: Paciente " + paciente.getNombre() + " guardado en MySQL (En espera).");

        } catch (Exception e) {
            System.err.println("Error al registrar paciente: " + e.getMessage());
        }
    }

    // 2. Cuando el médico decide ingresarlo a una camilla
// 2. Cuando el médico decide ingresarlo a una camilla
    public void internarPaciente(String cedula, String idCamilla) {
        String sqlPaciente = "UPDATE Pacientes SET Estado = 'Internado' WHERE Cedula = ?";
        String sqlCamilla = "UPDATE Habitaciones SET Disponible = false WHERE Numero = ?";

        try (Connection conn = conexion.conectar()) {
            conn.setAutoCommit(false); // TRUCO SENIOR: Todo o nada

            try (PreparedStatement psP = conn.prepareStatement(sqlPaciente);
                 PreparedStatement psC = conn.prepareStatement(sqlCamilla)) {

                // Ejecución 1: Cambiar estado del paciente a Internado
                psP.setString(1, cedula);
                psP.executeUpdate();

                // Ejecución 2: Marcar la cama como Ocupada (false)
                if (idCamilla != null && idCamilla.contains("-")) {
                    String numStr = idCamilla.split("-")[1].trim();
                    int numHab = Integer.parseInt(numStr);
                    psC.setInt(1, numHab);
                    psC.executeUpdate();
                }

                conn.commit(); // Guardamos ambos cambios
                System.out.println("DAO: Transacción exitosa. Paciente internado en " + idCamilla);

            } catch (Exception e) {
                conn.rollback(); // Si algo falla, deshacemos
                System.err.println("Error en transacción de ingreso: " + e.getMessage());
            }
        } catch (Exception e) {
            System.err.println("Error de conexión: " + e.getMessage());
        }
    }
    // 3. Método para leer las estadísticas en tiempo real
    public int[] obtenerEstadisticas() {
        // Arreglo mágico: [0]=Total, [1]=Libres, [2]=Espera, [3]=Ocupadas
        int[] stats = new int[4];
        stats[1] = 8; // Tu hospital tiene 8 camillas en total

        String sql = "SELECT Estado, COUNT(*) as cantidad FROM Pacientes GROUP BY Estado";

        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String estado = rs.getString("Estado");
                int cantidad = rs.getInt("cantidad");

                stats[0] += cantidad; // Sumamos al gran total

                if (estado.equalsIgnoreCase("En espera")) {
                    stats[2] = cantidad;
                } else if (estado.equalsIgnoreCase("Internado")) {
                    stats[3] = cantidad;
                    stats[1] -= cantidad; // Si hay internados, hay menos camillas libres
                }
            }
        } catch (Exception e) {
            System.err.println("Error al cargar estadísticas: " + e.getMessage());
        }
        return stats;
    }

    // 4. Método para leer qué camillas están libres u ocupadas
    public boolean[] obtenerEstadoCamillas() {
        // Un arreglo de 8 espacios (true = Libre, false = Ocupada)
        boolean[] estado = new boolean[8];
        String sql = "SELECT Numero, Disponible FROM Habitaciones ORDER BY Numero ASC LIMIT 8";

        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int numero = rs.getInt("Numero");
                boolean disponible = rs.getBoolean("Disponible");
                // Los arreglos en Java empiezan en 0, así que la camilla 1 va en la posición 0
                estado[numero - 1] = disponible;
            }
        } catch (Exception e) {
            System.err.println("Error al cargar camillas: " + e.getMessage());
        }
        return estado;
    }



}