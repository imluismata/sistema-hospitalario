package com.hospital.dao;

import com.hospital.modelo.Paciente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PacienteDAOImpl {

    // POO: Encapsulacion - la conexion se maneja internamente.
    private Conexion conexion = new Conexion();

    //  Cuando llega un paciente nuevo a la sala de espera
    public void registrarPaciente(Paciente paciente) {
        String sql = "INSERT INTO Pacientes (Cedula, Nombre, Contacto, Edad, Estado, Seguro, NSS) VALUES (?, ?, ?, ?, 'En espera', ?, ?)";

        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, paciente.getCedula());
            ps.setString(2, paciente.getNombre());
            ps.setString(3, paciente.getContacto());
            ps.setInt(4, paciente.getEdad());
            ps.setString(5, paciente.getSeguro());
            ps.setString(6, paciente.getNss());

            ps.executeUpdate();
        } catch (Exception e) {
            System.err.println("Error al registrar paciente: " + e.getMessage());
        }
    }

    // Cuando el médico decide ingresarlo a una camilla
    public void internarPaciente(String cedula, String idCamilla) {
        String sqlPaciente = "UPDATE Pacientes SET Estado = 'Internado' WHERE Cedula = ?";

        String sqlCamilla = "UPDATE Camillas SET Disponible = false, Cedula_Paciente = ? WHERE Numero = ?";

        try (Connection conn = conexion.conectar()) {
            conn.setAutoCommit(false);

            try (PreparedStatement psP = conn.prepareStatement(sqlPaciente);
                 PreparedStatement psC = conn.prepareStatement(sqlCamilla)) {

                psP.setString(1, cedula);
                psP.executeUpdate();

                if (idCamilla != null && idCamilla.contains("-")) {
                    String numStr = idCamilla.split("-")[1].trim();
                    int numHab = Integer.parseInt(numStr);

                    psC.setString(1, cedula); // Guardamos quien ta en la camilla
                    psC.setInt(2, numHab);
                    psC.executeUpdate();
                }
                conn.commit();
            } catch (Exception e) {
                conn.rollback();
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
    // para leer las estadísticas en tiempo real
    public int[] obtenerEstadisticas() {
        // Arreglo [0]=Total, [1]=Libres, [2]=Espera, [3]=Ocupadas
        int[] stats = new int[4];
        stats[1] = 8; // 8 camillas

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

    // Leer que camillas están libres u ocupadas
    public boolean[] obtenerEstadoCamillas() {
        boolean[] estado = new boolean[8];
        // AHORA BUSCAMOS EN LA TABLA CAMILLAS
        String sql = "SELECT Numero, Disponible FROM Camillas ORDER BY Numero ASC LIMIT 8";

        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                estado[rs.getInt("Numero") - 1] = rs.getBoolean("Disponible");
            }
        } catch (Exception e) { e.printStackTrace(); }
        return estado;
    }

}