package com.hospital.modelo;


//Esta clase hereda de la clase usuario, el cual hereda todos sus atributos
// y métodos, como idUsuario, nombre, username, password y rol.
public class Enfermera extends Usuario {

    private String turno;
    private String area;
    //Este es el constructor donde llamamos a los atributos del padre

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
    public void setArea(String area) { this.area = area; }
    //Este metodo es para especificar los permisos que tendra la enfermera  en el sistema

    @Override
    public String getPermisos() {
        return "control de camillas y estado del paciente";
    }
}
