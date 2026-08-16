package com.hospital.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {
// esta clase se encarga de establecer la conexión con la base de datos
    public Connection conectar() {
        Connection conn = null;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/Sistema_Hospitalario",
                    "root",
                    ""
            );

            System.out.println("Conectado!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return conn;
    }
}
