package com.hospital.modelo;

import java.util.Date;

public class Factura {

    // POO: Encapsulacion - datos de la factura protegidos por getters/setters.
    private int idFactura;
    private Date fecha;
    private double montoTotal;
    private String estadoPago;
    private int idHospitalizacion;
    private int idProcedimiento;


    public Factura(int idFactura, Date fecha, double montoTotal, String estadoPago, int idHospitalizacion, int idProcedimiento) {
        this.idFactura = idFactura;
        this.fecha = fecha;
        this.montoTotal = montoTotal;
        this.estadoPago = estadoPago;
        this.idHospitalizacion = idHospitalizacion;
        this.idProcedimiento = idProcedimiento;
    }



    // Getters
    public int getIdFactura() {
        return idFactura;
    }
    public double getMontoTotal() {
        return montoTotal;
    }
    public String getEstadoPago() {
        return estadoPago;
    }
    public int getIdHospitalizacion() {
        return idHospitalizacion;
    }
    public int getIdProcedimiento() {
        return idProcedimiento;
    }
    public Date getFecha() {
        return fecha;
    }



    // Setters
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }
    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }
    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
    }
    public void setIdHospitalizacion(int idHospitalizacion) {
        this.idHospitalizacion = idHospitalizacion;
    }
    public void setIdProcedimiento(int idProcedimiento) {
        this.idProcedimiento = idProcedimiento;
    }
}
