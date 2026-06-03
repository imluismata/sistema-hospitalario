package com.hospital.dao;

import com.hospital.modelo.Paciente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

// POO: Polimorfismo - implementa el contrato IPacienteDAO.
public class PacienteDAO implements IPacienteDAO {

    Conexion conexion = new Conexion();

    @Override
    public void insertar(Paciente objeto) {
        try {
            Connection conn = conexion.conectar();
            String sql = "INSERT INTO Pacientes (Nombre, Cedula, Contacto, Edad) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            // Usamos modelo Paciente para obtener los datos
            ps.setString(1, objeto.getNombre());
            ps.setString(2, objeto.getCedula());
            ps.setString(3, objeto.getContacto());
            ps.setInt(4, objeto.getEdad());

            ps.executeUpdate();
            System.out.println("Paciente guardado correctamente");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(Paciente objeto) {
        try {
            Connection conn = conexion.conectar();
            // Actualizamos todos los campos
            String sql = "UPDATE Pacientes SET Nombre = ?, Cedula = ?, Contacto = ?, Edad = ? WHERE ID = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, objeto.getNombre());
            ps.setString(2, objeto.getCedula());
            ps.setString(3, objeto.getContacto());
            ps.setInt(4, objeto.getEdad());
            ps.setInt(5, objeto.getIdUsuario());

            ps.executeUpdate();
            System.out.println("Paciente actualizado correctamente");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(int id) {
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

    @Override
    public List<Paciente> obtenerTodos() {
        List<Paciente> listaPacientes = new ArrayList<>();
        try {
            Connection conn = conexion.conectar();
            String sql = "SELECT * FROM Pacientes";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // En lugar de imprimir, creamos objetos Paciente y los llenamos
                Paciente p = new Paciente();
                p.setIdUsuario(rs.getInt("ID"));
                p.setNombre(rs.getString("Nombre"));
                p.setCedula(rs.getString("Cedula"));
                p.setContacto(rs.getString("Contacto"));
                p.setEdad(rs.getInt("Edad"));

                listaPacientes.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaPacientes;
    }

    @Override
    public Paciente obtenerPorId(int id) {
        Paciente paciente = null;
        try {
            Connection conn = conexion.conectar();
            String sql = "SELECT * FROM Pacientes WHERE ID = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                paciente = new Paciente();
                paciente.setIdUsuario(rs.getInt("ID"));
                paciente.setNombre(rs.getString("Nombre"));
                paciente.setCedula(rs.getString("Cedula"));
                paciente.setContacto(rs.getString("Contacto"));
                paciente.setEdad(rs.getInt("Edad"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return paciente;
    }

    @Override
    public List<Paciente> buscarPorNombre(String nombre) {
        List<Paciente> listaPacientes = new ArrayList<>();
        try {
            Connection conn = conexion.conectar();
            //  LIKE para coincidencias parciales
            String sql = "SELECT * FROM Pacientes WHERE Nombre LIKE ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + nombre + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Paciente p = new Paciente();
                p.setIdUsuario(rs.getInt("ID"));
                p.setNombre(rs.getString("Nombre"));
                p.setCedula(rs.getString("Cedula"));
                p.setContacto(rs.getString("Contacto"));
                p.setEdad(rs.getInt("Edad"));

                listaPacientes.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaPacientes;
    }

    @Override
    public Paciente buscarPorUsername(String username) {
        Paciente paciente = null;
        try {
            Connection conn = conexion.conectar();
            String sql = "SELECT * FROM Pacientes WHERE Username = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                paciente = new Paciente();
                paciente.setIdUsuario(rs.getInt("ID"));
                paciente.setNombre(rs.getString("Nombre"));
                paciente.setCedula(rs.getString("Cedula"));
                paciente.setContacto(rs.getString("Contacto"));
                paciente.setEdad(rs.getInt("Edad"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return paciente;
    }
}
