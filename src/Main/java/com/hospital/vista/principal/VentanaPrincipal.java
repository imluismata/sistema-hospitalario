package com.hospital.vista.principal;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;

import com.hospital.vista.modulos.AdminPanel;
import com.hospital.vista.modulos.CajeraPanel;
import com.hospital.vista.modulos.EmergenciasPanel;
import com.hospital.vista.modulos.EvaluacionPanel;
import com.hospital.vista.modulos.FacturacionPanel;
import com.hospital.vista.modulos.HospitalizacionPanel;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

//Ventana principal del sistema hospitalario.
//Contiene el sidebar y todos los modulos en un CardLayout.

public class VentanaPrincipal extends JFrame {

  // CardLayout maneja cual modulo esta visible
  private final CardLayout cardLayout     = new CardLayout();
  private final JPanel     panelModulos   = new JPanel(cardLayout);

  public VentanaPrincipal() {
      configurarVentana();
      construirUI();
  }

  // Configuracion basica de la ventana.
  private void configurarVentana() {
      setTitle("LPA Hospital");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setSize(960, 640);
      setMinimumSize(new Dimension(800, 580));
      setLocationRelativeTo(null); // Centrar en pantalla

      // Icono de la ventana
      ImageIcon icono = new ImageIcon("recursos/logo_jpg.jpeg");
      setIconImage(icono.getImage());
  }

  // Arma el layout: sidebar a la izquierda, modulos a la derecha.
  private void construirUI() {
      setLayout(new BorderLayout());

      // Registrar cada modulo con su nombre en el CardLayout
      panelModulos.add(new EmergenciasPanel(),     "emergencias");
      panelModulos.add(new HospitalizacionPanel(), "hospitalizacion");
      panelModulos.add(new EvaluacionPanel(),      "evaluacion");
      panelModulos.add(new FacturacionPanel(),     "facturacion");
      panelModulos.add(new CajeraPanel(),          "cajera");
      panelModulos.add(new AdminPanel(),           "admin");

      // Mostrar emergencias al inicio
      cardLayout.show(panelModulos, "emergencias");
   
      // Sidebar recibe referencia al CardLayout para poder cambiar pantallas
      Sidebar sidebar = new Sidebar(cardLayout, panelModulos);

      add(sidebar,       BorderLayout.WEST);
      add(panelModulos,  BorderLayout.CENTER);
  }
}
