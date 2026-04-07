package com.hospital.modelo;

import java.util.Date;



public class Emergencia {
    private int idEmergencia;
    private Date fechaIngreso;
    private Date fechaSalida;
    private String prioridad;
    private String motivoConsulta;
    private String Estado;


    public Emergencia(int idEmergencia, Date fechaIngreso, Date fechaSalida, String prioridad, String motivoConsulta, String estado) {
        this.idEmergencia = idEmergencia;
        this.fechaIngreso = fechaIngreso;
        this.fechaSalida = fechaSalida;
        this.prioridad = prioridad;
        this.motivoConsulta = motivoConsulta;
        Estado = estado;
    }

    //Getters
    public int getIdEmergencia() {
        return idEmergencia;
    }
    public Date getFechaSalida() {
        return fechaSalida;
    }
    public String getPrioridad() {
        return prioridad;
    }
    public String getMotivoConsulta() {
        return motivoConsulta;
    }
    public String getEstado() {
        return Estado;
    }
    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    //Setters
    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }
    public void setIdEmergencia(int idEmergencia) {
        this.idEmergencia = idEmergencia;
    }
    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }
    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }
    public void setMotivoConsulta(String motivoConsulta) {this.motivoConsulta = motivoConsulta;}
    public void setEstado(String estado) {Estado = estado;}
}
