package com.hospital.modelo;

public class Especialidad {

    private int idEspecialidad;
    private String nombre;
    private String descripcion;


    public Especialidad(int idEspecialidad, String nombre, String descripcion) {
        this.idEspecialidad = idEspecialidad;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }



    // Getters
    public int getIdEspecialidad() {
        return idEspecialidad;
    }
    public String getNombre() {
        return nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }



    // Setters
    public void setIdEspecialidad(int idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
