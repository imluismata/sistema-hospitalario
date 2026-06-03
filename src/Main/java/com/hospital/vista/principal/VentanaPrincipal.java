package com.hospital.vista.principal;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Taskbar;
import java.awt.Toolkit;

import com.hospital.vista.modulos.AdminPanel;
import com.hospital.vista.modulos.CajeraPanel;
import com.hospital.vista.modulos.EmergenciasPanel;
import com.hospital.vista.modulos.EvaluacionPanel;
import com.hospital.vista.modulos.HospitalizacionPanel;

import javax.swing.JFrame;
import javax.swing.JPanel;

// POO: Herencia - la ventana principal extiende JFrame.
public class VentanaPrincipal extends JFrame {

    // POO: Encapsulacion - estado interno de la UI.
    private final CardLayout cardLayout     = new CardLayout();
    private final JPanel     panelModulos   = new JPanel(cardLayout);

    public VentanaPrincipal(String nombreUsuario, String rolUsuario) {
        configurarVentana();
        construirUI(nombreUsuario, rolUsuario);

        try {
            Image icono = Toolkit.getDefaultToolkit().getImage("/Users/luismata/sistemahospitalario/src/main/resources/Copia de Logo_LPA.png");

            this.setIconImage(icono);

            if (Taskbar.isTaskbarSupported()) {
                Taskbar taskbar = Taskbar.getTaskbar();
                if (taskbar.isSupported(Taskbar.Feature.ICON_IMAGE)) {
                    taskbar.setIconImage(icono);
                }
            }
        } catch (Exception e) {
            System.out.println("No se encontro el logo para el icono.");
        }
    }

    private void configurarVentana() {
        setTitle("LPA Hospital");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 850); // Tamaño panorámico nivel Dios
        setMinimumSize(new Dimension(800, 580));
        setLocationRelativeTo(null);
    }

    private void construirUI(String nombreUsuario, String rolUsuario) {
        setLayout(new BorderLayout());

        panelModulos.add(new EmergenciasPanel(),     "emergencias");
        panelModulos.add(new HospitalizacionPanel(), "hospitalizacion");
        panelModulos.add(new EvaluacionPanel(),      "evaluacion");
        panelModulos.add(new CajeraPanel(),          "cajera");
        panelModulos.add(new AdminPanel(),           "admin");

        cardLayout.show(panelModulos, "emergencias");

        Sidebar sidebar = new Sidebar(cardLayout, panelModulos, nombreUsuario, rolUsuario);

        add(sidebar,       BorderLayout.WEST);
        add(panelModulos,  BorderLayout.CENTER);
    }
}