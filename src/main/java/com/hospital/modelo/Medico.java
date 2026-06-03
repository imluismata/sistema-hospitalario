package com.hospital.modelo;

// POO: Herencia - Medico extiende Usuario.
public class Medico extends Usuario {

    // POO: Encapsulacion - atributos propios del medico.
    private String especialidad;
    private String codigoMedico;

    //  EL CONSTRUCTOR VACÍO ES OBLIGATORIO PARA QUE EL DAO PUEDA CREAR INSTANCIAS DE MEDICO
    //  CUANDO LEA DE LA BASE DE DATOS
    public Medico() {
    }

    //Este es el constructor de la clase Medico, donde llamamos a los atributos del padre
    public Medico(int idUsuario, String nombre, String username, String password, String rol,  String especialidad, String codigoMedico)
    {
        super(idUsuario, nombre, username, password, rol);
        this.especialidad = especialidad;
        this.codigoMedico = codigoMedico;
    }


    //Estos son los getters para obtener los atributos del medico
    public String getEspecialidad() {
        return especialidad;
    }
    public String getCodigoMedico() {
        return codigoMedico;
    }

    //Estos son los setters para modificar los atributos del medico
    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
    public void setCodigoMedico(String codigoMedico) {
        this.codigoMedico = codigoMedico;
    }

    // POO: Polimorfismo - permisos especificos del medico.
    @Override
    public String getPermisos() {
        return "diagnóstico, decisiones de ingreso o alta";
    }
}
