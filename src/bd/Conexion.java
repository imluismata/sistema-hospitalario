package bd;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {

    public Connection conectar() {
        Connection conn = null;

        try {
            // 👇 ESTA LÍNEA ES LA CLAVE
            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/Sistema_Hospitalario",
                    "root",
                    "admin1234"
            );

            System.out.println("Conectado!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return conn;
    }
}
