package com.hospital.servicio;

import com.hospital.dao.UsuarioDAOImpl;
import com.hospital.modelo.Usuario;
import java.util.List;

public class UsuarioServicioImpl implements IUsuarioServicio {

    // 1. Instanciamos el DAO (nuestro nuevo puente a la base de datos)
    private UsuarioDAOImpl usuarioDAO = new UsuarioDAOImpl();

    @Override
    public void crearUsuario(Usuario usuario) {
        // Ya no guardamos en la lista 'empleadosDB', ahora usamos el DAO
        usuarioDAO.insertar(usuario);
    }

    @Override
    public void actualizarUsuario(Usuario usuario) {
        // Próximamente: usuarioDAO.actualizar(usuario);
    }

    @Override
    public void eliminarUsuario(int idUsuario) {
        // Próximamente: usuarioDAO.eliminar(idUsuario);
    }

    @Override
    public List<Usuario> obtenerTodosLosUsuarios() {
        // Le pedimos al DAO que traiga la lista real desde MySQL
        return usuarioDAO.obtenerTodos();
    }
}