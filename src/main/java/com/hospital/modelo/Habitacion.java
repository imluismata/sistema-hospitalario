package com.hospital.modelo;

public class Habitacion {
    // POO: Encapsulacion - estado de la habitacion accesible via getters/setters.
    private int idHabitacion;
    private String numero;
    private String tipo;
    private String estado;


    // Constructor
    public Habitacion(String numero, String tipo, String estado) {
        this.numero = numero;
        this.tipo = tipo;
        this.estado = estado;
    }



    // Getters
    public String getNumero() {
        return numero;
    }
    public int getIdHabitacion() {
        return idHabitacion;
    }

    public String getTipo() {
        return tipo;
    }
    public String getEstado() {
        return estado;
    }

    // Setters
    public void setIdHabitacion(int idHabitacion) {
        this.idHabitacion = idHabitacion;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public void setNumero(String numero) {
        this.numero = numero;
    }
}
