package com.hospital.modelo;


//Esta clase hereda de la clase usuario, el cual hereda todos sus atributos
// y métodos, como idUsuario, nombre, username, password y rol.
public class Administrador extends Usuario {


    private String nivelAcceso;

    public Administrador() {}

    //Este es el constructor donde llamamos a los atributos del padre
    public Administrador(int idUsuario, String nombre, String username, String password, String rol, String nivelAcceso)
    {
        super(idUsuario, nombre, username, password, rol);
        this.nivelAcceso = nivelAcceso;
    }

    public String getNivelAcceso() {
        return nivelAcceso;
    }

    public void setNivelAcceso(String nivelAcceso) {}

//Este metodo es para especificar los permisos que tendra el administrador en el sistema

    @Override
    public String getPermisos() {
        return "Gestionar usuarios, configurar sistema, generar reportes";
    }
}
