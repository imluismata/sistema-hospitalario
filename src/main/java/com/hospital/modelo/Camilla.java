package com.hospital.modelo;

public class Camilla {

    private int idCamilla;
    private String numero;
    private String estado;
    private String ubicacion;


    public Camilla(String numero, String estado, String ubicacion) {
        this.numero = numero;
        this.estado = estado;
        this.ubicacion = ubicacion;

    }



    //Getters
    public int getIdCamilla() {
        return idCamilla;
    }
    public String getNumero() {
        return numero;
    }
    public String getEstado() {
        return estado;
    }
    public String getUbicacion() {
        return ubicacion;
    }

    //Setters
    public void setIdCamilla(int idCamilla) {
        this.idCamilla = idCamilla;
    }
    public void setNumero(String numero) {
        this.numero = numero;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
}
