package com.hospital.modelo;

import java.util.Date;

public class Hospitalizacion {

    // POO: Encapsulacion - datos de hospitalizacion con acceso controlado.
    private int idHospitalizacion;
    private Date fechaIngreso;
    private Date fechaSalida;
    private String estado;
    private String observaciones;
    private int idMedico;


    public void validarIngreso(){
    }

    public void registrarHospitalizacion(Hospitalizacion hospitalizacion){

    }

    public void validarSalida(){
    }

    public Hospitalizacion(int idHospitalizacion, Date fechaIngreso, Date fechaSalida, String estado, String observaciones, int idMedico) {
        this.idHospitalizacion = idHospitalizacion;
        this.fechaIngreso = fechaIngreso;
        this.fechaSalida = fechaSalida;
        this.estado = estado;
        this.observaciones = observaciones;
        this.idMedico = idMedico;
    }


    // Getters
    public int getIdHospitalizacion() {
        return idHospitalizacion;
    }
    public Date getFechaIngreso() {
        return fechaIngreso;
    }
    public Date getFechaSalida() {
        return fechaSalida;
    }
    public String getEstado() {
        return estado;
    }
    public String getObservaciones() {
        return observaciones;
    }
    public int getIdMedico() {
        return idMedico;
    }


    // Setters
    public void setIdHospitalizacion(int idHospitalizacion) {
        this.idHospitalizacion = idHospitalizacion;
    }
    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }
    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    public void setIdMedico(int idMedico) {
        this.idMedico = idMedico;
    }
}
