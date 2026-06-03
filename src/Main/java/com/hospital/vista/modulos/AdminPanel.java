package com.hospital.vista.modulos;

import com.hospital.vista.estilos.Estilos;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

// POO: Herencia - AdminPanel extiende JPanel.
public class AdminPanel extends JPanel {

    // POO: Encapsulacion - estado interno del panel.
    private JTable tablaUsuarios;
    private DefaultTableModel modeloUsuarios;
    private JButton btnPacientesAtendidos;

    public AdminPanel() {
        setLayout(new BorderLayout());
        setBackground(Estilos.FONDO_APP);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        add(crearFilaTitulo(), BorderLayout.NORTH);

        JPanel centro = new JPanel(new BorderLayout());
        centro.setBackground(Estilos.FONDO_APP);
        centro.setBorder(new EmptyBorder(15, 0, 0, 0));

        centro.add(crearFilaPrincipal(), BorderLayout.CENTER);

        add(centro, BorderLayout.CENTER);
    }

    private JPanel crearFilaTitulo() {
        JPanel fila = new JPanel(new BorderLayout());
        fila.setBackground(Estilos.FONDO_APP);

        JLabel titulo = new JLabel("Administracion");
        titulo.setFont(Estilos.TITULO);
        titulo.setForeground(Estilos.TEXTO_NORMAL);

        fila.add(titulo, BorderLayout.WEST);
        return fila;
    }

    private JPanel crearFilaPrincipal() {
        JPanel fila = new JPanel(new GridLayout(1, 2, 12, 0));
        fila.setBackground(Estilos.FONDO_APP);

        fila.add(crearTarjetaUsuarios());
        fila.add(crearTarjetaEspecialidades());
        return fila;
    }

    private JPanel crearTarjetaUsuarios() {
        JPanel encabezado = new JPanel(new BorderLayout());
        encabezado.setBackground(Estilos.FONDO_BLANCO);
        encabezado.setBorder(new CompoundBorder(
                new MatteBorder(0, 0, 1, 0, Estilos.BORDE),
                new EmptyBorder(5, 12, 5, 8)
        ));

        JLabel lblTitulo = new JLabel("Gestion de Usuarios (v1.0)");
        lblTitulo.setFont(Estilos.SUBTITULO);
        lblTitulo.setForeground(Estilos.TEXTO_NORMAL);
        encabezado.add(lblTitulo, BorderLayout.WEST);

        String[] columnas = {"Nombre Completo", "Usuario", "Rol", "Estado"};

        Object[][] datos = {
                {"Administrador General", "admin",     "Administrador",     "Activo"},
                {"María González",        "enfermera", "Enfermera Titular", "Activo"},
                {"Dr. Roberto Sánchez",   "doctor",    "Medico Internista", "Activo"},
                {"Ana López",             "cajera",    "Cajera Principal",  "Activo"}
        };

        modeloUsuarios = new DefaultTableModel(datos, columnas) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };

        tablaUsuarios = new JTable(modeloUsuarios);
        tablaUsuarios.setFont(Estilos.NORMAL);
        tablaUsuarios.setRowHeight(40);
        tablaUsuarios.setBackground(Estilos.FONDO_BLANCO);
        tablaUsuarios.setGridColor(Estilos.BORDE);
        tablaUsuarios.setShowHorizontalLines(true);
        tablaUsuarios.setShowVerticalLines(false);
        tablaUsuarios.getTableHeader().setFont(Estilos.ETIQUETA);
        tablaUsuarios.getTableHeader().setBackground(Estilos.FONDO_STAT);

        JScrollPane scroll = new JScrollPane(tablaUsuarios);
        scroll.setBorder(null);

        JPanel tarjeta = new JPanel(new BorderLayout());
        tarjeta.setBackground(Estilos.FONDO_BLANCO);
        tarjeta.setBorder(BorderFactory.createLineBorder(Estilos.BORDE, 1));
        tarjeta.add(encabezado, BorderLayout.NORTH);
        tarjeta.add(scroll,     BorderLayout.CENTER);
        return tarjeta;
    }

    private JPanel crearTarjetaEspecialidades() {
        JPanel tarjeta = crearTarjeta("Especialidades y Habitaciones");

        JPanel cuerpo = new JPanel();
        cuerpo.setLayout(new BoxLayout(cuerpo, BoxLayout.Y_AXIS));
        cuerpo.setBackground(Estilos.FONDO_BLANCO);
        cuerpo.setBorder(new EmptyBorder(12, 14, 14, 14));

        JLabel lblSubtitulo = new JLabel("Especialidades Activas");
        lblSubtitulo.setFont(Estilos.SUBTITULO);
        lblSubtitulo.setForeground(Estilos.TEXTO_NORMAL);
        lblSubtitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        cuerpo.add(lblSubtitulo);
        cuerpo.add(Box.createVerticalStrut(10));

        String[] especialidades = {
                "Cardiologia", "Med. Interna", "Pediatría", "Cirugia General", "Traumatologia"
        };

        JPanel panelBadges = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 4));
        panelBadges.setBackground(Estilos.FONDO_BLANCO);
        panelBadges.setAlignmentX(Component.LEFT_ALIGNMENT);

        for (String esp : especialidades) {
            panelBadges.add(crearBadge(esp, Estilos.AZUL_FONDO, Estilos.AZUL_TEXTO));
        }

        cuerpo.add(panelBadges);
        cuerpo.add(Box.createVerticalGlue());

        btnPacientesAtendidos = new JButton("");
        btnPacientesAtendidos.setFont(Estilos.SUBTITULO);
        btnPacientesAtendidos.setBackground(Estilos.FONDO_BLANCO);
        btnPacientesAtendidos.setFocusPainted(false);
        btnPacientesAtendidos.setOpaque(true);
        btnPacientesAtendidos.setBorder(BorderFactory.createLineBorder(Estilos.BORDE, 1));
        btnPacientesAtendidos.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnPacientesAtendidos.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnPacientesAtendidos.setCursor(new Cursor(Cursor.HAND_CURSOR));

        cuerpo.add(btnPacientesAtendidos);
        tarjeta.add(cuerpo, BorderLayout.CENTER);
        return tarjeta;
    }

    private JLabel crearBadge(String texto, Color fondo, Color textoColor) {
        JLabel badge = new JLabel(texto);
        badge.setFont(Estilos.ETIQUETA);
        badge.setForeground(textoColor);
        badge.setBackground(fondo);
        badge.setOpaque(true);
        badge.setBorder(new EmptyBorder(4, 8, 4, 8));
        return badge;
    }

    private JPanel crearTarjeta(String titulo) {
        JPanel t = new JPanel(new BorderLayout());
        t.setBackground(Estilos.FONDO_BLANCO);
        t.setBorder(BorderFactory.createLineBorder(Estilos.BORDE, 1));
        JLabel lbl = new JLabel("  " + titulo);
        lbl.setFont(Estilos.SUBTITULO);
        lbl.setForeground(Estilos.TEXTO_NORMAL);
        lbl.setPreferredSize(new Dimension(0, 36));
        lbl.setBorder(new MatteBorder(0, 0, 1, 0, Estilos.BORDE));
        t.add(lbl, BorderLayout.NORTH);
        return t;
    }
}