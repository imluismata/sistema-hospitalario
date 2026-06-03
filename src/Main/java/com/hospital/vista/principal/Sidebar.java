package com.hospital.vista.principal;

import com.hospital.vista.estilos.Estilos;
import com.hospital.vista.login.LoginFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// POO: Herencia - Sidebar extiende JPanel para reutilizar comportamiento Swing.
public class Sidebar extends JPanel {

    // POO: Encapsulacion - estado interno del componente.
    // CardLayout para cambiar entre modulos
    private final CardLayout cardLayout;
    private final JPanel     panelContenido;

    // Labels de usuario
    private final JLabel lblRol;
    private final JLabel lblNombre;

    // Item del menu actualmente seleccionado
    private JPanel itemActivo;

    private static final Color COLOR_HOVER  = new Color(50,  80, 105);
    private static final Color COLOR_ACTIVO = new Color(42,  95, 143);

    public Sidebar(CardLayout cardLayout, JPanel panelModulos, String nombreUsuario, String rolUsuario) {
        this.cardLayout     = cardLayout;
        this.panelContenido = panelModulos;

        setPreferredSize(new Dimension(230, 0));
        setBackground(Estilos.SIDEBAR_FONDO);
        setLayout(new BorderLayout());
        setOpaque(true);

        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS));
        panelSuperior.setBackground(Estilos.SIDEBAR_FONDO);
        panelSuperior.setOpaque(true);

        panelSuperior.add(crearPanelLogo());
        panelSuperior.add(crearSeparador());

        JPanel panelUsuario = new JPanel(new GridLayout(2, 1, 0, 2));
        panelUsuario.setBackground(Estilos.SIDEBAR_FONDO);
        panelUsuario.setOpaque(true);
        panelUsuario.setBorder(new EmptyBorder(10, 15, 10, 15));

        lblRol    = new JLabel(rolUsuario);
        lblNombre = new JLabel(nombreUsuario);
        lblRol.setFont(Estilos.SB_SMALL);
        lblNombre.setFont(Estilos.SB_NORMAL);
        lblRol.setForeground(Estilos.SIDEBAR_SUBTXT);
        lblNombre.setForeground(Estilos.SIDEBAR_TEXTO);

        panelUsuario.add(lblRol);
        panelUsuario.add(lblNombre);

        panelSuperior.add(panelUsuario);
        panelSuperior.add(crearSeparador());

        JPanel panelNav = new JPanel();
        panelNav.setLayout(new BoxLayout(panelNav, BoxLayout.Y_AXIS));
        panelNav.setBackground(Estilos.SIDEBAR_FONDO);
        panelNav.setOpaque(true);
        panelNav.setBorder(new EmptyBorder(8, 0, 8, 0));

        JPanel itemEmergencias     = crearItemMenu("Emergencias",       "emergencias");
        JPanel itemHospitalizacion = crearItemMenu("Hospitalizacion",   "hospitalizacion");
        JPanel itemEvaluacion      = crearItemMenu("Evaluacion Medica", "evaluacion");
        JPanel itemCajera          = crearItemMenu("Cajera",            "cajera");
        JPanel itemAdmin           = crearItemMenu("Administración",    "admin");

        panelNav.add(itemEmergencias);
        panelNav.add(itemHospitalizacion);
        panelNav.add(itemEvaluacion);
        panelNav.add(itemCajera);
        panelNav.add(itemAdmin);

        //  PERMISOS Y ROLES
        String rolLimpio = rolUsuario.toLowerCase();

        if (rolLimpio.contains("cajera") || rolLimpio.contains("cajero")) {
            // Si es cajera, apagamos todo menos la caja
            itemEmergencias.setVisible(false);
            itemHospitalizacion.setVisible(false);
            itemEvaluacion.setVisible(false);
            itemAdmin.setVisible(false);
            activarItem(itemCajera);
            cardLayout.show(panelContenido, "cajera"); // La mandamos directo a su pantalla

        } else if (rolLimpio.contains("medico") || rolLimpio.contains("doctor")) {
            // El médico no ve emergencias ni la caja
            itemEmergencias.setVisible(false);
            itemCajera.setVisible(false);
            itemAdmin.setVisible(false);
            activarItem(itemEvaluacion);
            cardLayout.show(panelContenido, "evaluacion");

        } else if (rolLimpio.contains("enfermer") || rolLimpio.contains("admision") || rolLimpio.contains("recepcion")) {
            // Enfermería no diagnostica ni cobra
            itemEvaluacion.setVisible(false);
            itemCajera.setVisible(false);
            itemAdmin.setVisible(false);
            activarItem(itemEmergencias);
            cardLayout.show(panelContenido, "emergencias");

        } else {
            // Admin lo ve todo. Empieza en Emergencias
            activarItem(itemEmergencias);
            cardLayout.show(panelContenido, "emergencias");
        }

        //(Cerrar Sesión)
        JPanel panelLogout = new JPanel(new BorderLayout());
        panelLogout.setBackground(Estilos.SIDEBAR_FONDO);
        panelLogout.setOpaque(true);
        panelLogout.setBorder(new EmptyBorder(15, 15, 20, 15)); // Espacio abajo

        JButton btnLogout = new JButton("Cerrar Sesión");
        btnLogout.setFont(Estilos.SB_NORMAL);
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setBackground(new Color(190, 50, 50)); // Un rojito suave corporativo
        btnLogout.setFocusPainted(false);
        btnLogout.setOpaque(true);
        btnLogout.setBorderPainted(false);
        btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogout.setPreferredSize(new Dimension(0, 36));

        btnLogout.addActionListener(e -> {
            //  Matamos la ventana actual
            Window ventanaActual = SwingUtilities.getWindowAncestor(this);
            if (ventanaActual != null) {
                ventanaActual.dispose();
            }
            // Invocamos al LoginFrame
            new LoginFrame().setVisible(true);
        });

        panelLogout.add(btnLogout, BorderLayout.CENTER);

        add(panelSuperior, BorderLayout.NORTH);
        add(panelNav,      BorderLayout.CENTER);
        add(panelLogout,   BorderLayout.SOUTH); // El botón se queda pegado abajo
    }


    private JPanel crearPanelLogo() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 14));
        panel.setBackground(Estilos.SIDEBAR_FONDO);
        panel.setOpaque(true);

        try {
            ImageIcon iconoOriginal = new ImageIcon("/Users/luismata/sistemahospitalario/src/main/resources/Copia de Logo_LPA.png");
            Image iconoEscalado = iconoOriginal.getImage().getScaledInstance(28, 28, Image.SCALE_SMOOTH);
            JLabel lblIcono  = new JLabel(new ImageIcon(iconoEscalado));
            panel.add(lblIcono);
        } catch (Exception e) {}

        JLabel lblTitulo = new JLabel("LPA Hospital");
        lblTitulo.setFont(Estilos.SB_TITULO);
        lblTitulo.setForeground(Color.WHITE);
        panel.add(lblTitulo);

        return panel;
    }

    private JPanel crearSeparador() {
        JPanel sep = new JPanel();
        sep.setBackground(Estilos.SIDEBAR_FONDO);
        sep.setOpaque(true);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep.setBorder(new MatteBorder(1, 0, 0, 0, new Color(80, 110, 135)));
        return sep;
    }

    private JPanel crearItemMenu(String texto, String pantalla) {
        JPanel item = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        item.setBackground(Estilos.SIDEBAR_FONDO);
        item.setOpaque(true);
        item.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        item.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel punto = new JLabel("•");
        punto.setFont(new Font("SansSerif", Font.BOLD, 14));
        punto.setForeground(Estilos.SIDEBAR_SUBTXT);

        JLabel lblTexto = new JLabel(texto);
        lblTexto.setFont(Estilos.SB_NORMAL);
        lblTexto.setForeground(Estilos.SIDEBAR_SUBTXT);

        item.add(punto);
        item.add(lblTexto);

        item.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(panelContenido, pantalla);
                activarItem(item);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (item != itemActivo) {
                    item.setBackground(COLOR_HOVER);
                    item.repaint();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (item != itemActivo) {
                    item.setBackground(Estilos.SIDEBAR_FONDO);
                    item.repaint();
                }
            }
        });

        return item;
    }

    private void activarItem(JPanel nuevoActivo) {
        if (itemActivo != null) {
            itemActivo.setBackground(Estilos.SIDEBAR_FONDO);
            itemActivo.setBorder(null);
            for (Component c : itemActivo.getComponents()) {
                if (c instanceof JLabel) {
                    c.setForeground(Estilos.SIDEBAR_SUBTXT);
                }
            }
            itemActivo.repaint();
        }

        nuevoActivo.setBackground(COLOR_ACTIVO);
        nuevoActivo.setBorder(new MatteBorder(0, 3, 0, 0, new Color(100, 180, 230)));
        for (Component c : nuevoActivo.getComponents()) {
            if (c instanceof JLabel) {
                c.setForeground(Color.WHITE);
            }
        }
        nuevoActivo.repaint();

        itemActivo = nuevoActivo;
    }
}