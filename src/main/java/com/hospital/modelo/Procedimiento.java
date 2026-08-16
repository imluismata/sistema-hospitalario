package com.hospital.modelo;

public class Procedimiento {

    // POO: Encapsulacion - datos del procedimiento protegidos por getters/setters.
    private int idProcedimiento;
    private String nombre;
    private double costo;
    private String descripcion;


    public void registrarProcedimiento() {
        // Lógica para registrar un procedimiento en la base de datos
    }

    public void calcularCosto() {
    }
    // Constructor para inicializar todos los atributos
    public Procedimiento(int idProcedimiento, String nombre, double costo, String descripcion) {
        this.idProcedimiento = idProcedimiento;
        this.nombre = nombre;
        this.costo = costo;
        this.descripcion = descripcion;
    }


    // Getters
    public int getIdProcedimiento() {
        return idProcedimiento;
    }
    public String getNombre() {
        return nombre;
    }
    public double getCosto() {
        return costo;
    }
    public String getDescripcion() {
        return descripcion;
    }



    // Setters
    public void setIdProcedimiento(int idProcedimiento) {
        this.idProcedimiento = idProcedimiento;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setCosto(double costo) {
        this.costo = costo;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
