package com.hospital.modelo;

// POO: Herencia - Cajero extiende Usuario para compartir la base comun.
public class Cajero extends Usuario {

    // POO: Encapsulacion - atributo propio manejado via getter/setter.
    // Atributo propio del cajero
    private String turno; // turno en el que trabaja (mañana, tarde, noche)

    //constructor vacio
    public Cajero() {}

    // Constructor que inicializa los atributos del padre y el turno del cajero
    public Cajero(int idUsuario, String nombre, String username, String password, String rol, String turno) {
        super(idUsuario, nombre, username, password, rol);
        this.turno = turno;
    }

    // Getter y setter del turno
    public String getTurno() { return turno; }
    public void setTurno(String turno) { this.turno = turno; }

    // POO: Polimorfismo - permisos especificos del cajero.
    @Override
    public String getPermisos() {
        return "Generar facturas, procesar pagos, consultar historial de pagos";
    }
}