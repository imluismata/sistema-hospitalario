package com.hospital.modelo;

// POO: Herencia - Admision extiende Usuario.
public class Admision extends Usuario {

    // POO: Encapsulacion - atributo propio del personal de admision.
    private String turno;

    //Este es el constructor donde llamamos a los atributos del padre

    public Admision(int idUsuario, String nombre, String username, String password, String rol, String turno)
    {
        super(idUsuario, nombre, username, password, rol);
        this.turno = turno;
    }

    public String getTurno() {
        return turno;
    }
    public void setTurno(String turno) {}

    // POO: Polimorfismo - permisos especificos de admision.
    @Override
    public String getPermisos() {
        return "registro de pacientes e ingresos";
    }
}
