package com.hospital.modelo;

// POO: Herencia - Administrador extiende Usuario para reutilizar atributos y comportamiento.
public class Administrador extends Usuario {


    private String nivelAcceso;

    public Administrador() {}

    //Este es el constructor donde llamamos a los atributos del padre
    public Administrador(int idUsuario, String nombre, String username, String password, String rol, String nivelAcceso)
    {
        super(idUsuario, nombre, username, password, rol);
        this.nivelAcceso = nivelAcceso;
    }

    public String getNivelAcceso() {
        return nivelAcceso;
    }

    public void setNivelAcceso(String nivelAcceso) {}

    // POO: Polimorfismo - redefine permisos segun el rol.
    @Override
    public String getPermisos() {
        return "Gestionar usuarios, configurar sistema, generar reportes";
    }
}
