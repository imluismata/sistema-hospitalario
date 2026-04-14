package com.hospital.dao;

import com.hospital.modelo.Paciente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EvaluacionDAOImpl {

    private Conexion conexion = new Conexion();

    // 1. Botón "Siguiente Paciente": Busca al primero que esté Internado o En espera
    public Paciente obtenerSiguientePaciente() {
        Paciente p = null;
        String sql = "SELECT Nombre, Cedula FROM Pacientes WHERE Estado IN ('Internado', 'En espera') LIMIT 1";

        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                p = new Paciente();
                p.setNombre(rs.getString("Nombre"));
                p.setCedula(rs.getString("Cedula"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }

    // 2. Botón "Confirmar": Guarda el texto del doctor
    public void guardarEvaluacion(String cedula, String diagnostico, String especialidad, String decision) {
        String sql = "INSERT INTO Evaluaciones (Cedula_Paciente, Diagnostico, Especialidad, Decision) VALUES (?, ?, ?, ?)";

        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cedula);
            ps.setString(2, diagnostico);
            ps.setString(3, especialidad);
            ps.setString(4, decision);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 3. Si el doctor elige "Alta médica", lo sacamos del hospital
    public void procesarDecision(String cedula, String decision, String idCamilla) {
        if (decision.equals("Alta médica")) {
            // 1. Preparamos las dos órdenes para MySQL
            String sqlPaciente = "UPDATE Pacientes SET Estado = 'Alta' WHERE Cedula = ?";
            String sqlCamilla  = "UPDATE Habitaciones SET Disponible = true WHERE Numero = ?";

            try (Connection conn = conexion.conectar()) {
                // TRUCO SENIOR: Desactivamos el auto-guardado para manejar la transacción nosotros
                conn.setAutoCommit(false);

                try (PreparedStatement psP = conn.prepareStatement(sqlPaciente);
                     PreparedStatement psC = conn.prepareStatement(sqlCamilla)) {

                    // Ejecución 1: Liberar Paciente
                    psP.setString(1, cedula);
                    psP.executeUpdate();

                    // Ejecución 2: Liberar Camilla (convertimos "C - 01" a 1)
                    if (idCamilla != null && idCamilla.contains("-")) {
                        String numStr = idCamilla.split("-")[1].trim();
                        int numHab = Integer.parseInt(numStr);
                        psC.setInt(1, numHab);
                        psC.executeUpdate();
                    }

                    // Si todo salió bien, guardamos ambos cambios permanentemente
                    conn.commit();
                    System.out.println("DAO: Transacción completada. Paciente libre y camilla " + idCamilla + " disponible.");

                } catch (Exception e) {
                    conn.rollback(); // Si algo falla, cancelamos todo para no dejar datos corruptos
                    throw e;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}