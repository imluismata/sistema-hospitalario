package com.hospital.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EvaluacionDAOImpl {

    // POO: Encapsulacion - la conexion se maneja internamente.
    private Conexion conexion = new Conexion();

    public String[] obtenerSiguientePaciente() {
        String[] datosPaciente = null;

        //LEFT JOIN para unir al paciente con su camilla
        String sql = "SELECT p.Nombre, p.Cedula, c.Numero as Camilla " +
                "FROM Pacientes p " +
                "LEFT JOIN Camillas c ON p.Cedula = c.Cedula_Paciente " +
                "WHERE p.Estado IN ('Internado', 'En espera') LIMIT 1";

        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            //
            if (rs.next()) {
                String nombre = rs.getString("Nombre");
                String cedula = rs.getString("Cedula");
                String numCamilla = rs.getString("Camilla");

                // Si el paciente estaba "En espera" y no tenía camilla asignada
                if (numCamilla == null) {
                    numCamilla = "N/A (En Sala)";
                } else {
                    numCamilla = "C - 0" + numCamilla; // Formato visual
                }

                datosPaciente = new String[]{nombre, cedula, numCamilla};
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return datosPaciente;
    }

    public void guardarEvaluacion(String cedula, String diagnostico, String especialidad, String decision) {
        String sql = "INSERT INTO Evaluaciones (Cedula_Paciente, Diagnostico, Especialidad, Decision) VALUES (?, ?, ?, ?)";
        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cedula);
            ps.setString(2, diagnostico);
            ps.setString(3, especialidad);
            ps.setString(4, decision);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    // Procesamos la decisión del médico
    public void procesarDecision(String cedula, String decision, String idCamilla) {
        String nuevoEstado = "";
        boolean liberarCamilla = false;

        // LÓGICA DE NEGOCIO
        if (decision.equals("Alta médica")) {
            nuevoEstado = "Alta";
            liberarCamilla = true; // Se va a su casa, soltamos la camilla
        }
        else if (decision.equals("Traslado externo")) {
            nuevoEstado = "Trasladado";
            liberarCamilla = true; // Se va a otro hospital, soltamos la camilla
        }
        else if (decision.equals("Hospitalizar")) {
            nuevoEstado = "Para Ingreso";
            liberarCamilla = false; // aqui se termina de liberar cuando la enfermera le asigne habi
        }

        if (!nuevoEstado.isEmpty()) {
            String sqlPaciente = "UPDATE Pacientes SET Estado = ? WHERE Cedula = ?";
            String sqlCamilla  = "UPDATE Camillas SET Disponible = true, Cedula_Paciente = NULL WHERE Numero = ?";

            try (Connection conn = conexion.conectar()) {
                conn.setAutoCommit(false);

                try (PreparedStatement psP = conn.prepareStatement(sqlPaciente);
                     PreparedStatement psC = conn.prepareStatement(sqlCamilla)) {

                    // Actualizamos el estado del paciente para que no vuelva a salir en la consulta
                    psP.setString(1, nuevoEstado);
                    psP.setString(2, cedula);
                    psP.executeUpdate();

                    // Solo liberamos la camilla si la decisión lo amerita
                    if (liberarCamilla && idCamilla != null && idCamilla.contains("-")) {
                        String numStr = idCamilla.split("-")[1].trim();
                        int numHab = Integer.parseInt(numStr);
                        psC.setInt(1, numHab);
                        psC.executeUpdate();
                    }

                    conn.commit();
                } catch (Exception e) {
                    conn.rollback();
                    throw e;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
