package com.hospital.servicio;

import com.hospital.dao.IPacienteDAO;
import com.hospital.dao.PacienteDAOMemoria;
import com.hospital.modelo.Paciente;
import com.hospital.modelo.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioServicioImpl implements IUsuarioServicio {

    //  Instanciamos el DAO
    //  private IPacienteDAO pacienteDAO = new PacienteDAO();
    // este objeto es para pruebas
    private IPacienteDAO pacienteDAO = new PacienteDAOMemoria();

    @Override
    public void crearUsuario(Usuario usuario) {

        //Verificamos qué tipo de usuario me tan mandando
        if (usuario instanceof Paciente) {
            System.out.println(" Validando que el paciente tenga datos correctos...");

            // Convertimos el cast  el Usuario generico a un Paciente específico
            Paciente pacienteNuevo = (Paciente) usuario;

            // se llama al dao para el  INSERT en SQL
            pacienteDAO.insertar(pacienteNuevo);

            System.out.println("Servicio: Paciente procesado y enviado a la base de datos.");

        } else {
            // En el futuro aquí pondremos la lógica para crear Médicos, Enfermeras, etc.
            System.out.println("Error: Por ahora el sistema solo sabe crear Pacientes.");
        }
    }

    @Override
    public void actualizarUsuario(Usuario usuario) {
        if (usuario instanceof Paciente) {
            System.out.println("Verificando permisos para actualizar...");
            // Aquí en el futuro podrías validar: "Si el nombre está vacío, no actualices"
            pacienteDAO.actualizar((Paciente) usuario);
        }
    }

    @Override
    public void eliminarUsuario(int idUsuario) {
        System.out.println("Verificando si el usuario tiene facturas pendientes...");
        // Regla de negocio: Solo le decimos al DAO que lo borre si pasa nuestras validaciones
        pacienteDAO.eliminar(idUsuario);
    }

    @Override
    public List<Usuario> obtenerTodosLosUsuarios() {
        System.out.println("Obteniendo lista de usuarios activos...");
        // El DAO nos devuelve Pacientes, los metemos en una lista genérica de Usuarios
        return new ArrayList<>(pacienteDAO.obtenerTodos());
    }
}