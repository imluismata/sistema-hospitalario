package com.hospital;

import com.hospital.modelo.Paciente;
import com.hospital.modelo.Usuario;
import com.hospital.servicio.IUsuarioServicio;
import com.hospital.servicio.UsuarioServicioImpl;

public class Main {
    public static void main(String[] args) {

        IUsuarioServicio servicio = new UsuarioServicioImpl();

        System.out.println("CREAR PACIENTE");
        Paciente p1 = new Paciente();
        p1.setNombre("Juan Soto");
        p1.setCedula("001-1234567-8");
        servicio.crearUsuario(p1);

        System.out.println("\nLEER USUARIOS");
        for (Usuario u : servicio.obtenerTodosLosUsuarios()) {
            System.out.println("ID: " + u.getIdUsuario() + " | Nombre: " + u.getNombre());
        }

        System.out.println("\nACTUALIZAR PACIENTE");
        p1.setNombre("Juan Soto (Con 700 Millones)");
        servicio.actualizarUsuario(p1);

        System.out.println("\nVOLVER A LEER USUARIOS");
        for (Usuario u : servicio.obtenerTodosLosUsuarios()) {
            System.out.println("ID: " + u.getIdUsuario() + " | Nombre: " + u.getNombre());
        }

        System.out.println("\nELIMINAR PACIENTE");
        servicio.eliminarUsuario(p1.getIdUsuario()); // Borramos el ID 1
        // debe estar vacio
        System.out.println("\nLEER FINAL");
        System.out.println("Usuarios registrados: " + servicio.obtenerTodosLosUsuarios().size());
    }
}