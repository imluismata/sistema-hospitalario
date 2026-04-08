package com.hospital.modelo;


//Esta clase hereda de la clase usuario, el cual hereda todos sus atributos
// y métodos, como idUsuario, nombre, username, password y rol.
public class Admision extends Usuario {

    private String turno;
    //Este es el constructor donde llamamos a los atributos del padre

    public Admision(int idUsuario, String nombre, String username, String password, String rol, String turno)
    {
        super(idUsuario, nombre, username, password, rol);
        this.turno = turno;
    }

    public String getTurno() {
        return turno;
    }
    public void setTurno(String turno) {}



    //Este metodo es para especificar los permisos que tendra el personal de admision  en el sistema

    @Override
    public String getPermisos() {
        return "registro de pacientes e ingresos";
    }
}
