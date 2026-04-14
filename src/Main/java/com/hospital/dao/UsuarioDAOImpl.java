package com.hospital.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.hospital.modelo.*;

public class UsuarioDAOImpl {

    // ¡Aquí está la variable mágica que Java no encontraba!
    private Conexion conexion = new Conexion();

    // 1. Método para INSERTA en MySQL (Lo que hace el botón Guardar)
    public void insertar(Usuario usuario) {
        String sql = "INSERT INTO Usuarios (Nombre, Rol, Estado) VALUES (?, ?, ?)";

        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getClass().getSimpleName()); // Truco del nombre de clase
            ps.setString(3, "Activo");

            ps.executeUpdate();
            System.out.println("DAO: Usuario guardado en MySQL con éxito.");

        } catch (Exception e) {
            System.err.println("Error al insertar usuario: " + e.getMessage());
        }
    }

    // 2. Método para LEER de MySQL (Lo que llena la tabla de Alan)
    public List<Usuario> obtenerTodos() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM Usuarios";

        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String rol = rs.getString("Rol");
                Usuario u;

                // Polimorfismo: Creamos el objeto correcto según el rol en la BD
                if (rol.equalsIgnoreCase("Medico")) u = new Medico();
                else if (rol.equalsIgnoreCase("Enfermera")) u = new Enfermera();
                else if (rol.equalsIgnoreCase("Cajero")) u = new Cajero();
                else u = new Administrador();

                u.setNombre(rs.getString("Nombre"));
                // u.setId(rs.getInt("ID")); // Descomenta esto si agregaste el ID a tus clases hijas

                lista.add(u);
            }
        } catch (Exception e) {
            System.err.println("Error al obtener usuarios: " + e.getMessage());        }
        return lista;
    }
}