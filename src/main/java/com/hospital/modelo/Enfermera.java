package com.hospital.modelo;

// POO: Herencia - Enfermera extiende Usuario.
public class Enfermera extends Usuario {

    // POO: Encapsulacion - estado propio protegido por getters/setters.
    private String turno;
    private String area;

    //Este es el constructor donde llamamos a los atributos del padre

    //constructor vacio
    public Enfermera() {}

    public Enfermera(int idUsuario, String nombre, String username, String password, String rol)
    {
        super(idUsuario, nombre, username, password, rol);
        this.turno = turno;
        this.area = area;
    }
    //Getters
    public String getTurno() {
        return turno;
    }
    public String getArea() {
        return area;
    }

    //Setters
    public void setTurno(String turno) {
        this.turno = turno;
    }
    public void setArea(String area) {}
    // POO: Polimorfismo - permisos especificos de enfermeria.
    @Override
    public String getPermisos() {
        return "control de camillas y estado del paciente";
    }
}
