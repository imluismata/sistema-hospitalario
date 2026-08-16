package com.hospital.servicio;

import com.hospital.modelo.Usuario;

import java.util.List;

// POO: Abstraccion - define el contrato de servicios de usuario.
public interface IUsuarioServicio {
    void crearUsuario(Usuario usuario);
    void actualizarUsuario(Usuario usuario);
    void eliminarUsuario(int idUsuario);

    //  Método para ver quién está registrado
    List<Usuario> obtenerTodosLosUsuarios();
}