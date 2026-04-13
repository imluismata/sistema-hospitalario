package com.hospital.modelo;

// Esta clase es hija de la clase Usuario, representa a un paciente en el sistema del hospital.
// Hereda los atributos y métodos de la clase Usuario, como idUsuario, nombre, username, password y rol.
public class Paciente extends Usuario {


    private String cedula;
    private String contacto;
    private int edad;

    // Este es el constructor donde llamamos a los atributos del padre
    public Paciente(int idUsuario, String nombre, String username, String password, String rol) {
        super(idUsuario, nombre, username, password, rol);
    }

    // Constructor vacío
    public Paciente() {
    }

    // Este metodo es para especificar los permisos que tendra el paciente en el sistema
    @Override
    public String getPermisos() {
        return "";
    }

    // --- GETTERS Y SETTERS ---

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
}