package bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PacienteDAO {

    Conexion conexion = new Conexion();

    // 🔹 INSERT
    public void guardarPaciente(String nombre, String cedula, String contacto, int edad) {

        try {
            Connection conn = conexion.conectar();

            String sql = "INSERT INTO Pacientes (Nombre, Cedula, Contacto, Edad) VALUES (?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, nombre);
            ps.setString(2, cedula);
            ps.setString(3, contacto);
            ps.setInt(4, edad);

            ps.executeUpdate();

            System.out.println("Paciente guardado correctamente");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔹 SELECT
    public void listarPacientes() {

        try {
            Connection conn = conexion.conectar();

            String sql = "SELECT * FROM Pacientes";

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println(
                        rs.getInt("ID") + " - " +
                                rs.getString("Nombre") + " - " +
                                rs.getString("Cedula") + " - " +
                                rs.getString("Contacto") + " - " +
                                rs.getInt("Edad")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔹 UPDATE
    public void actualizarPaciente(int id, String nombre) {

        try {
            Connection conn = conexion.conectar();

            String sql = "UPDATE Pacientes SET Nombre = ? WHERE ID = ?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, nombre);
            ps.setInt(2, id);

            ps.executeUpdate();

            System.out.println("Paciente actualizado");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔹 DELETE
    public void eliminarPaciente(int id) {

        try {
            Connection conn = conexion.conectar();

            String sql = "DELETE FROM Pacientes WHERE ID = ?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, id);

            ps.executeUpdate();

            System.out.println("Paciente eliminado");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}