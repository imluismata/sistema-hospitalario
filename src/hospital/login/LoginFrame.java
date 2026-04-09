package hospital.login;
import hospital.estilos.Estilos;
import hospital.principal.VentanaPrincipal;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class LoginFrame extends JFrame {

    // Campos que el usuario llena
    private JTextField  campoUsuario;
    private JPasswordField campoContrasena;
    private JButton     btnIngresar;

    public LoginFrame() {
        configurarVentana();
        construirUI();
    }

    //Propiedades basicas de la ventana
    private void configurarVentana() {
        setTitle("LPA - Sistema Hospitalario");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(760, 490);
        setResizable(false);
        setLocationRelativeTo(null); // Centrar en pantalla

        // Icono pequeño de la ventana (logo.png)
        ImageIcon icono = new ImageIcon("recursos/logo_jpg.jpeg");
        setIconImage(icono.getImage());
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

        //Logo
        JLabel labelLogo = crearLogo();

        //Espacio entre logo y campos
        Component espacioLogo = Box.createVerticalStrut(35);

        //Campo Usuario
        campoUsuario = new JTextField();
        estilizarCampo(campoUsuario, "Usuario", 22);

        //Espacio entre campos
        Component espacioCampos = Box.createVerticalStrut(20);

        //Campo Contraseña
        campoContrasena = new JPasswordField();
        estilizarCampo(campoContrasena, "Contraseña", 22);

        //Espacio antes del boton
        Component espacioBoton = Box.createVerticalStrut(25);

        //Boton Ingresar
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

        // Listener del boton ingresar (logica pendiente)
        btnIngresar.addActionListener(e -> {
            // Validar usuario y contraseña con la BD
            // Si es valido, abrir VentanaPrincipal y cerrar login
            abrirVentanaPrincipal();
        });

        // Tambien ingresar al presionar Enter en cualquier campo
        campoContrasena.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    btnIngresar.doClick(); // Simula clic en boton
                }
            }
        });

        // Agregar todo al panel central
        panelCentro.add(labelLogo);
        panelCentro.add(espacioLogo);
        panelCentro.add(campoUsuario);
        panelCentro.add(espacioCampos);
        panelCentro.add(campoContrasena);
        panelCentro.add(espacioBoton);
        panelCentro.add(btnIngresar);

        //Texto de derechos reservados (abajo)
        JLabel lblDerechos = new JLabel("Todos los derechos reservados.", SwingConstants.CENTER);
        lblDerechos.setFont(Estilos.NORMAL);
        lblDerechos.setForeground(Estilos.TEXTO_GRIS);
        lblDerechos.setBorder(new EmptyBorder(0, 0, 14, 0));

        panelPrincipal.add(panelCentro,  BorderLayout.CENTER);
        panelPrincipal.add(lblDerechos,  BorderLayout.SOUTH);
    }

    // Crea el label con el logo completo del hospital.
    private JLabel crearLogo() {
        // Cargar imagen del logo completo
        ImageIcon logoOriginal = new ImageIcon("recursos/logo_entero.jpg");

        // Escalar logo a un tamaño adecuado
        Image imagenEscalada = logoOriginal.getImage()
                .getScaledInstance(260, 150, Image.SCALE_SMOOTH);
        ImageIcon logoEscalado = new ImageIcon(imagenEscalada);

        JLabel labelLogo = new JLabel(logoEscalado);
        labelLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        return labelLogo;
    }

    //Estiliza un campo de texto con apariencia del diseño.
    
    private void estilizarCampo(JTextField campo, String placeholder, int columnas) {
        campo.setColumns(columnas);
        campo.setFont(Estilos.NORMAL);
        campo.setForeground(Estilos.TEXTO_GRIS);
        campo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 180), 1),
            new EmptyBorder(8, 12, 8, 12)
        ));
        campo.setBackground(Color.WHITE);
        campo.setText(placeholder); // Texto inicial como placeholder

        // Maximo ancho del campo centrado
        campo.setMaximumSize(new Dimension(280, 42));
        campo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Limpiar placeholder al hacer foco
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

    // Abre la ventana principal y cierra el login.
    private void abrirVentanaPrincipal() {
        VentanaPrincipal ventana = new VentanaPrincipal();
        ventana.setVisible(true);
        dispose(); // Cierra esta ventana
    }
}

