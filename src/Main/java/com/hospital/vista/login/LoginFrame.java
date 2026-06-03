package com.hospital.vista.login;

import com.hospital.vista.estilos.Estilos;
import com.hospital.dao.UsuarioDAOImpl;
import com.hospital.vista.principal.VentanaPrincipal;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

// POO: Herencia - LoginFrame extiende JFrame.
public class LoginFrame extends JFrame {

    // POO: Encapsulacion - campos de UI privados.
    // Campos que el usuario llena
    private JTextField campoUsuario;
    private JPasswordField campoContrasena;
    private JButton btnIngresar;
    private UsuarioDAOImpl usuarioDAO = new UsuarioDAOImpl();

    public LoginFrame() {
        configurarVentana();
        construirUI();
    }

    // Propiedades básicas de la ventana
    private void configurarVentana() {
        setTitle("LPA - Sistema Hospitalario");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(760, 500);
        setResizable(false);
        setLocationRelativeTo(null); // Centrar en pantalla

        try {
            java.net.URL urlIcono = getClass().getResource("/Logo_LPA.png");
            if (urlIcono != null) {
                Image icono = Toolkit.getDefaultToolkit().getImage(urlIcono);
                setIconImage(icono);
            }
        } catch (Exception e) {
            System.out.println("No se encontró el icono.");
        }
    }

    private void construirUI() {
        // Panel principal fondo gris claro
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(new Color(245, 245, 245));
        setContentPane(panelPrincipal);

        // Panel central con todo el contenido
        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));
        panelCentro.setBackground(new Color(245, 245, 245));
        panelCentro.setBorder(new EmptyBorder(50, 0, 0, 0));

        // Logo principaln
        JLabel labelLogo = crearLogo();

        // Espacio entre logo y campos
        Component espacioLogo = Box.createVerticalStrut(35);

        // Campo Usuario
        campoUsuario = new JTextField();
        estilizarCampo(campoUsuario, "Usuario", 22);

        // Espacio entre campos
        Component espacioCampos = Box.createVerticalStrut(20);

        // Campo Contraseña
        campoContrasena = new JPasswordField();
        estilizarCampo(campoContrasena, "Contraseña", 22);

        // Espacio antes del botón
        Component espacioBoton = Box.createVerticalStrut(25);

        // Botón Ingresar
        btnIngresar = new JButton("Ingresar");
        btnIngresar.setFont(Estilos.SUBTITULO);
        btnIngresar.setBackground(Estilos.SIDEBAR_FONDO);
        btnIngresar.setForeground(Color.WHITE);
        btnIngresar.setFocusPainted(false);
        btnIngresar.setOpaque(true);
        btnIngresar.setPreferredSize(new Dimension(250, 38));
        btnIngresar.setMaximumSize(new Dimension(250, 38));
        btnIngresar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnIngresar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Lógica de acceso
        btnIngresar.addActionListener(e -> {
            String user = campoUsuario.getText();
            String pass = new String(campoContrasena.getPassword());

            if (user.isEmpty() || user.equals("Usuario") || pass.isEmpty() || pass.equals("Contraseña")) {
                JOptionPane.showMessageDialog(this, "Por favor, llene todos los campos.");
                return;
            }

            // Validación en la Base de Datos
            String rolUsuario = usuarioDAO.validarLogin(user, pass);

            if (!rolUsuario.isEmpty()) {
                JOptionPane.showMessageDialog(this, "¡Bienvenido al Sistema, " + user + "!");
                abrirVentanaPrincipal(user, rolUsuario);
            } else {
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.", "Error de Acceso", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Evento para ingresar al presionar Enter
        campoContrasena.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    btnIngresar.doClick();
                }
            }
        });

        // Ensamblar el panel
        panelCentro.add(labelLogo);
        panelCentro.add(espacioLogo);
        panelCentro.add(campoUsuario);
        panelCentro.add(espacioCampos);
        panelCentro.add(campoContrasena);
        panelCentro.add(espacioBoton);
        panelCentro.add(btnIngresar);

        // Texto de derechos reservados
        JLabel lblDerechos = new JLabel("Todos los derechos reservados. LPA Hospital", SwingConstants.CENTER);
        lblDerechos.setFont(Estilos.NORMAL);
        lblDerechos.setForeground(Estilos.TEXTO_GRIS);
        lblDerechos.setBorder(new EmptyBorder(0, 0, 14, 0));

        panelPrincipal.add(panelCentro, BorderLayout.CENTER);
        panelPrincipal.add(lblDerechos, BorderLayout.SOUTH);
    }

    private JLabel crearLogo() {
        JLabel labelLogo = new JLabel();
        labelLogo.setAlignmentX(Component.CENTER_ALIGNMENT);

        try {
            java.net.URL urlLogo = getClass().getResource("/logo_entero.jpg");
            if (urlLogo != null) {
                ImageIcon logoOriginal = new ImageIcon(urlLogo);
                Image imagenEscalada = logoOriginal.getImage().getScaledInstance(300, 150, Image.SCALE_SMOOTH);
                labelLogo.setIcon(new ImageIcon(imagenEscalada));
            } else {
                labelLogo.setText("LPA HOSPITAL");
                labelLogo.setFont(Estilos.TITULO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return labelLogo;
    }

    private void estilizarCampo(JTextField campo, String placeholder, int columnas) {
        campo.setColumns(columnas);
        campo.setFont(Estilos.NORMAL);
        campo.setForeground(Estilos.TEXTO_GRIS);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180), 1),
                new EmptyBorder(8, 12, 8, 12)
        ));
        campo.setBackground(Color.WHITE);
        campo.setText(placeholder);

        campo.setMaximumSize(new Dimension(280, 42));
        campo.setAlignmentX(Component.CENTER_ALIGNMENT);

        campo.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (campo.getText().equals(placeholder)) {
                    campo.setText("");
                    campo.setForeground(Estilos.TEXTO_NORMAL);
                }
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (campo.getText().isEmpty()) {
                    campo.setText(placeholder);
                    campo.setForeground(Estilos.TEXTO_GRIS);
                }
            }
        });
    }

    private void abrirVentanaPrincipal(String usuario, String rol) {
        VentanaPrincipal ventana = new VentanaPrincipal(usuario, rol);
        ventana.setVisible(true);
        this.dispose();
    }
}