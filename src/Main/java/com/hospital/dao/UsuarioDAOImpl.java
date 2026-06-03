package com.hospital.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;


import com.hospital.modelo.*;

public class UsuarioDAOImpl {

    // POO: Encapsulacion - la conexion se maneja internamente.
    private Conexion conexion = new Conexion();

    // Si la contraseña es incorrecta devuelve un texto vacío
    public String validarLogin(String username, String password) {
        String rol = "";

        // Buscamos el usuario y validamos la contraseña en la BD
        String sql = "SELECT Rol FROM Usuarios WHERE Usuario = ? AND Contrasena = ?";
        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            // Si encontro una fila, significa que la contraseña es correcta
            if (rs.next()) {
                rol = rs.getString("Rol");
            }

        } catch (Exception e) {
            System.err.println("Error en el Login: " + e.getMessage());
        }

        return rol;
    }

    // Método para insertar un nuevo usuario Para quitar el primer error en rojo
    public void insertar(Usuario usuario) {
    // Cambiamos los nombres de las columnas en el INSERT
        String sql = "INSERT INTO Usuarios (Nombre, Usuario, Contrasena, Rol) VALUES (?, ?, ?, ?)";        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getUsername());
            ps.setString(3, usuario.getPassword());
            ps.setString(4, usuario.getRol());

            ps.executeUpdate();
        } catch (Exception e) {
            System.err.println("Error al insertar usuario: " + e.getMessage());
        }
    }

     // Metodo para obtener todos los usuarios (Para quitar el segundo error en rojo)
    public List<Usuario> obtenerTodos() {
        List<Usuario> lista = new java.util.ArrayList<>();
        String sql = "SELECT * FROM Usuarios";

        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                // POO: Polimorfismo - implementacion concreta via clase anonima.
                Usuario u = new Usuario() {
                    @Override
                    public String getPermisos() {
                        return this.getRol();
                    }
                };

                u.setIdUsuario(rs.getInt("ID"));
                u.setNombre(rs.getString("Nombre"));
                u.setUsername(rs.getString("Usuario"));
                u.setPassword(rs.getString("Contrasena"));
                u.setRol(rs.getString("Rol"));

                lista.add(u);
            }
        } catch (Exception e) {
            System.err.println("Error al obtener usuarios: " + e.getMessage());
        }
        return lista;
    }
}