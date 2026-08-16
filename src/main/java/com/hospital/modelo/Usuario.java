package com.hospital.modelo;

// POO: Abstraccion - define una base comun para todos los usuarios.
public abstract class Usuario {


    // POO: Encapsulacion - atributos privados accesibles solo via getters/setters.
    // Atributos privados
    private int idUsuario;
    private String nombre;
    private String username;
    private String password;
    private String rol;

    //Constructor para inicializar los atributos de la clase Usuario
    public Usuario(int idUsuario, String nombre, String username, String password, String rol){
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.username = username;
        this.password = password;
        this.rol = rol;
    }
    // este metodo se utiliza para cuando se necesite crear un usuario
    // sin especificar sus atributos, por ejemplo, al momento de iniciar sesión
    protected Usuario() {
    }

    // POO: Abstraccion - contrato que cada subclase debe implementar.
    public abstract String getPermisos();


    // Getters
    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRol() {
        return rol;
    }

    //Setters

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    // Métodos de sesión que todos los usuarios comparten

    public void iniciarSesion(){
        System.out.println("Usuario " + username + " ha iniciado sesión.");
    }

    public void cerrarSesion(){
        System.out.println("Usuario " + username + " ha cerrado sesión.");
    }
}
