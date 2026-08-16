package com.hospital.modelo;

// POO: Herencia - Paciente extiende Usuario.
public class Paciente extends Usuario {


    // POO: Encapsulacion - datos propios del paciente con acceso controlado.
    private String cedula;
    private String contacto;
    private int edad;
    private String seguro;
    private String nss;

    // Este es el constructor donde llamamos a los atributos del padre
    public Paciente(int idUsuario, String nombre, String username, String password, String rol) {
        super(idUsuario, nombre, username, password, rol);
    }

    // Constructor vacío
    public Paciente() {
    }

    // POO: Polimorfismo - permisos especificos del paciente.
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

    // Getters y Setters
    public String getSeguro() { return seguro; }
    public void setSeguro(String seguro) { this.seguro = seguro; }
    public String getNss() { return nss; }
    public void setNss(String nss) { this.nss = nss; }
}