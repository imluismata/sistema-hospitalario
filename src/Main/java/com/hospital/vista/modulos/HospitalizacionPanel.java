package com.hospital.vista.modulos;
import com.hospital.vista.estilos.Estilos;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class HospitalizacionPanel extends JPanel {

    private JComboBox<String> comboHabitacion;
    private JButton           btnConfirmarIngreso;

    public HospitalizacionPanel() {
        setLayout(new BorderLayout());
        setBackground(Estilos.FONDO_APP);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        add(crearFilaTitulo(), BorderLayout.NORTH);

        // borderlayout en el centro: estadisticas arriba
        JPanel centro = new JPanel(new BorderLayout(0, 15));
        centro.setBackground(Estilos.FONDO_APP);
        centro.setBorder(new EmptyBorder(15, 0, 0, 0));
        centro.add(crearFilaEstadisticas(), BorderLayout.NORTH);
        centro.add(crearFilaPrincipal(),    BorderLayout.CENTER);

        add(centro, BorderLayout.CENTER);
    }

    private JPanel crearFilaTitulo() {
        JPanel fila = new JPanel(new BorderLayout());
        fila.setBackground(Estilos.FONDO_APP);
        JLabel titulo = new JLabel("Hospitalizacion");
        titulo.setFont(Estilos.TITULO);
        titulo.setForeground(Estilos.TEXTO_NORMAL);
        fila.add(titulo, BorderLayout.WEST);
        return fila;
    }

    private JPanel crearFilaEstadisticas() {
        JPanel fila = new JPanel(new GridLayout(1, 4, 10, 0));
        fila.setBackground(Estilos.FONDO_APP);
        fila.setPreferredSize(new Dimension(0, 75));
        fila.add(crearStat("Pacientes ingresados",  "x", Estilos.TEXTO_NORMAL));
        fila.add(crearStat("Habitaciones Libres",   "x", Estilos.VERDE_TEXTO));
        fila.add(crearStat("Habitaciones Ocupadas", "x", Estilos.ROJO_TEXTO));
        fila.add(crearStat("Traslados externos",    "x", Estilos.AMBAR_TEXTO));
        return fila;
    }

    // dos columnas en borderlayout center
    private JPanel crearFilaPrincipal() {
        JPanel fila = new JPanel(new GridLayout(1, 2, 12, 0));
        fila.setBackground(Estilos.FONDO_APP);
        fila.add(crearTarjetaValidacion());
        fila.add(crearTarjetaMapa());
        return fila;
    }

    // tarjeta validacion 
    private JPanel crearTarjetaValidacion() {
        JPanel tarjeta = crearTarjeta("Validacion de Ingresos");

        // panel superior: banner y filas de validacion
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS));
        panelSuperior.setBackground(Estilos.FONDO_BLANCO);
        panelSuperior.setBorder(new EmptyBorder(12, 14, 8, 14));

        // banner verde con nombre y especialidad
        JPanel bannerPaciente = new JPanel(new BorderLayout());
        bannerPaciente.setBackground(Estilos.VERDE_FONDO);
        bannerPaciente.setBorder(new EmptyBorder(8, 10, 8, 10));
        bannerPaciente.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        JLabel lblPaciente = new JLabel("Paciente: Nombre – Especialidad: x");
        lblPaciente.setFont(Estilos.NORMAL);
        lblPaciente.setForeground(Estilos.VERDE_TEXTO);
        bannerPaciente.add(lblPaciente, BorderLayout.CENTER);

        panelSuperior.add(bannerPaciente);
        panelSuperior.add(Box.createVerticalStrut(12));

        // habitacion disponible, 
        panelSuperior.add(crearFilaValidacion("Habitacion Disponible:",  "si/no", null));
        panelSuperior.add(new JSeparator());
        
        // especialidad disponible
        panelSuperior.add(crearFilaValidacion("Especialidad Disponible:", "si/no", null));

        // panel inferior: combo y boton 
        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new BoxLayout(panelInferior, BoxLayout.Y_AXIS));
        panelInferior.setBackground(Estilos.FONDO_BLANCO);
        panelInferior.setBorder(new EmptyBorder(8, 14, 14, 14));

        JLabel lblAsignar = new JLabel("ASIGNAR HABITACION");
        lblAsignar.setFont(Estilos.ETIQUETA);
        lblAsignar.setForeground(Estilos.TEXTO_GRIS);
        lblAsignar.setAlignmentX(Component.LEFT_ALIGNMENT);

        String[] habitaciones = {
            "Hab. 01 - Cardiología", "Hab. 02 - Traumatologia", "Hab. 03 - Pediatria",
            "Hab. 04 - Cirugia General", "Hab. 05 - Cardiología",
            "Hab. 06 - Med. Interna", "Hab. 07 - Cirugia General", "Hab. 08 - Pediatria"
        };
        comboHabitacion = new JComboBox<>(habitaciones);
        comboHabitacion.setFont(Estilos.NORMAL);
        comboHabitacion.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        comboHabitacion.setAlignmentX(Component.LEFT_ALIGNMENT);

        btnConfirmarIngreso = new JButton("Confirmar ingreso");
        btnConfirmarIngreso.setFont(Estilos.SUBTITULO);
        btnConfirmarIngreso.setBackground(Estilos.FONDO_BLANCO);
        btnConfirmarIngreso.setFocusPainted(false);
        btnConfirmarIngreso.setOpaque(true);
        btnConfirmarIngreso.setBorder(BorderFactory.createLineBorder(Estilos.BORDE, 1));
        btnConfirmarIngreso.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        btnConfirmarIngreso.setPreferredSize(new Dimension(0, 44));
        btnConfirmarIngreso.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnConfirmarIngreso.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnConfirmarIngreso.addActionListener(e -> {
            // registrar ingreso del paciente en la bd
        });

        panelInferior.add(lblAsignar);
        panelInferior.add(Box.createVerticalStrut(5));
        panelInferior.add(comboHabitacion);
        panelInferior.add(Box.createVerticalStrut(10));
        panelInferior.add(btnConfirmarIngreso);

        // center se expande, south queda siempre en la parte inferior
        tarjeta.add(panelSuperior, BorderLayout.CENTER);
        tarjeta.add(panelInferior, BorderLayout.SOUTH);
        return tarjeta;
    }

    private JPanel crearFilaValidacion(String descripcion, String estado, Color colorEstado) {
        JPanel fila = new JPanel(new BorderLayout());
        fila.setBackground(Estilos.FONDO_BLANCO);
        fila.setBorder(new EmptyBorder(4, 0, 4, 0));
        fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));

        JLabel lblDesc   = new JLabel(descripcion);
        lblDesc.setFont(Estilos.NORMAL);
        lblDesc.setForeground(Estilos.TEXTO_GRIS);

        JLabel lblEstado = new JLabel(estado);
        lblEstado.setFont(Estilos.ETIQUETA);
        lblEstado.setForeground(colorEstado);

        fila.add(lblDesc,   BorderLayout.WEST);
        fila.add(lblEstado, BorderLayout.EAST);
        return fila;
    }

    // mapa de habitaciones
    private JPanel crearTarjetaMapa() {
        JPanel tarjeta = crearTarjeta("Mapa de Habitaciones");

        String[][] habitaciones = {
            {"01","Cardiología",    "libre"},   {"02","Traumatologia","ocupada"},
            {"03","Pediatria",      "ocupada"}, {"04","Cirugia General","ocupada"},
            {"05","Cardiología",    "libre"},   {"06","Med. Interna",  "libre"},
            {"07","Cirugia General","libre"},   {"08","Pediatria",     "libre"}
        };

        // grilla 
        JPanel grilla = new JPanel(new GridLayout(2, 4, 8, 8));
        grilla.setBackground(Estilos.FONDO_BLANCO);
        for (String[] hab : habitaciones) {
            grilla.add(crearCeldaHabitacion(hab[0], hab[1], hab[2].equals("libre")));
        }

        // leyenda 
        JPanel leyenda = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        leyenda.setBackground(Estilos.FONDO_BLANCO);
        leyenda.add(crearItemLeyenda("Libre",   Estilos.VERDE_TEXTO));
        leyenda.add(crearItemLeyenda("Ocupado", Estilos.ROJO_TEXTO));

        // panel que agrupa grilla y leyenda
        JPanel panelGrillaYLeyenda = new JPanel();
        panelGrillaYLeyenda.setLayout(new BoxLayout(panelGrillaYLeyenda, BoxLayout.Y_AXIS));
        panelGrillaYLeyenda.setBackground(Estilos.FONDO_BLANCO);
        panelGrillaYLeyenda.setBorder(new EmptyBorder(12, 14, 10, 14));
        panelGrillaYLeyenda.add(grilla);
        panelGrillaYLeyenda.add(Box.createVerticalStrut(10));
        panelGrillaYLeyenda.add(leyenda);

        JPanel panelRelleno = new JPanel();
        panelRelleno.setBackground(Estilos.FONDO_BLANCO);

        JPanel contenido = new JPanel(new BorderLayout());
        contenido.setBackground(Estilos.FONDO_BLANCO);
        contenido.add(panelGrillaYLeyenda, BorderLayout.NORTH);
        contenido.add(panelRelleno,        BorderLayout.CENTER);

        tarjeta.add(contenido, BorderLayout.CENTER);
        return tarjeta;
    }

    private JPanel crearCeldaHabitacion(String numero, String especialidad, boolean libre) {
        JPanel celda = new JPanel();
        celda.setLayout(new BoxLayout(celda, BoxLayout.Y_AXIS));
        celda.setBackground(libre ? Estilos.VERDE_FONDO : Estilos.ROJO_FONDO);
        celda.setBorder(BorderFactory.createLineBorder(
            libre ? Estilos.VERDE_BORDE : Estilos.ROJO_BORDE, 1));

        JLabel lblNum = new JLabel(numero);
        lblNum.setFont(Estilos.SUBTITULO);
        lblNum.setForeground(libre ? Estilos.VERDE_TEXTO : Estilos.ROJO_TEXTO);
        lblNum.setBorder(new EmptyBorder(6, 8, 0, 8));

        JLabel lblEsp = new JLabel(especialidad);
        lblEsp.setFont(new Font("SansSerif", Font.PLAIN, 10));
        lblEsp.setForeground(libre ? Estilos.VERDE_TEXTO : Estilos.ROJO_TEXTO);
        lblEsp.setBorder(new EmptyBorder(0, 8, 6, 8));

        celda.add(lblNum);
        celda.add(lblEsp);
        return celda;
    }

    private JPanel crearTarjeta(String titulo) {
        JPanel tarjeta = new JPanel(new BorderLayout());
        tarjeta.setBackground(Estilos.FONDO_BLANCO);
        tarjeta.setBorder(BorderFactory.createLineBorder(Estilos.BORDE, 1));
        JLabel lbl = new JLabel("  " + titulo);
        lbl.setFont(Estilos.SUBTITULO);
        lbl.setForeground(Estilos.TEXTO_NORMAL);
        lbl.setPreferredSize(new Dimension(0, 36));
        lbl.setBorder(new MatteBorder(0, 0, 1, 0, Estilos.BORDE));
        tarjeta.add(lbl, BorderLayout.NORTH);
        return tarjeta;
    }

    private JPanel crearStat(String etiqueta, String valor, Color color) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(Estilos.FONDO_STAT);
        p.setBorder(new EmptyBorder(10, 14, 10, 14));
        JLabel e = new JLabel(etiqueta); e.setFont(Estilos.SB_SMALL); e.setForeground(Estilos.TEXTO_GRIS);
        JLabel v = new JLabel(valor);    v.setFont(Estilos.STAT_VAL); v.setForeground(color);
        p.add(e); p.add(Box.createVerticalStrut(4)); p.add(v);
        return p;
    }

    private JPanel crearItemLeyenda(String texto, Color color) {
        JPanel item = new JPanel(new FlowLayout(FlowLayout.LEFT, 3, 0));
        item.setBackground(Estilos.FONDO_BLANCO);
        JLabel punto = new JLabel("•"); punto.setFont(new Font("SansSerif", Font.BOLD, 14)); punto.setForeground(color);
        JLabel lbl   = new JLabel(texto); lbl.setFont(Estilos.SB_SMALL); lbl.setForeground(color);
        item.add(punto); item.add(lbl);
        return item;
    }
}