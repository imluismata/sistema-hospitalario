package com.hospital.modelo;

//Esta clase es hija de la clase Usuario, representa a un paciente en el sistema del hospital.
// Hereda los atributos y métodos de la clase Usuario, como idUsuario, nombre, username, password y rol.

public class Paciente extends Usuario {


    //Este es el constructor donde llamamos a los atributos del padre
    public Paciente(int idUsuario, String nombre, String username, String password, String rol)
    {
        super(idUsuario, nombre, username, password, rol);
    }

    // Este metodo es para especificar los permisos que tendra el paciente en el sistema,
    // en este caso no tiene permisos especiales, por lo que devuelve una cadena vacía.
    @Override
    public String getPermisos() {
        return "";
    }
}
