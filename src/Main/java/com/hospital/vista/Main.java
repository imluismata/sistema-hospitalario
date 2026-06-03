package com.hospital.vista;
import com.hospital.vista.principal.VentanaPrincipal;
import com.hospital.vista.login.LoginFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {

    public static void main(String[] args) {

		com.hospital.dao.Conexion testConexion = new com.hospital.dao.Conexion();
		testConexion.conectar();
    	try {

    		for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {

    		if ("Nimbus".equals(info.getName())) {

    		UIManager.setLookAndFeel(info.getClassName());

    		break;

    		}

    		}

    		} catch (Exception e) {

    		// Si Nimbus no está disponible, puedes establecer otro Look and Feel.

    		try {

    		UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());

    		} catch (Exception ex) {

    		// Manejo de excepción

    		}

    		}

        SwingUtilities.invokeLater(() -> {
            LoginFrame login = new LoginFrame();
            login.setVisible(true); // Mostrar ventana de login
        });
    }
}
