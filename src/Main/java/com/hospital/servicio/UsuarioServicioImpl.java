package com.hospital.servicio;

import com.hospital.dao.UsuarioDAOImpl;
import com.hospital.modelo.Usuario;
import java.util.List;

// POO: Polimorfismo - implementa el contrato IUsuarioServicio.
public class UsuarioServicioImpl implements IUsuarioServicio {

    // POO: Encapsulacion - el acceso a datos queda oculto en el servicio.
    // Instanciamos el DAO
    private UsuarioDAOImpl usuarioDAO = new UsuarioDAOImpl();

    @Override
    public void crearUsuario(Usuario usuario) {
        usuarioDAO.insertar(usuario);
    }

    @Override
    public void actualizarUsuario(Usuario usuario) {
    }

    @Override
    public void eliminarUsuario(int idUsuario) {
    }

    @Override
    public List<Usuario> obtenerTodosLosUsuarios() {
        // Le pedimos al DAO que traiga la lista real desde MySQL
        return usuarioDAO.obtenerTodos();
    }
}