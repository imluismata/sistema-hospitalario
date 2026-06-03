package com.hospital.modelo;

import java.util.Date;

// Clase que representa un ingreso hospitalario de un paciente
public class Ingreso {

    // POO: Encapsulacion - atributos privados del ingreso hospitalario.
    // Atributos privados del ingreso
    private int idIngreso;
    private Date fecha;
    private String motivo;
    private String estado;
    private int idPaciente;

    // Constructor para inicializar todos los atributos
    public Ingreso(int idIngreso, Date fecha, String motivo, String estado, int idPaciente) {
        this.idIngreso = idIngreso;
        this.fecha = fecha;
        this.motivo = motivo;
        this.estado = estado;
        this.idPaciente = idPaciente;
    }

    // Getters
    public int getIdIngreso() { return idIngreso; }
    public Date getFecha() { return fecha; }
    public String getMotivo() { return motivo; }
    public String getEstado() { return estado; }
    public int getIdPaciente() { return idPaciente; }

    // Setters
    public void setIdIngreso(int idIngreso) { this.idIngreso = idIngreso; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    public void setEstado(String estado) { this.estado = estado; }
    public void setIdPaciente(int idPaciente) { this.idPaciente = idPaciente; }
}