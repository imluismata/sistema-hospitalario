package hospital.modulos;
import hospital.estilos.Estilos;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class HospitalizacionPanel extends JPanel {

    // Combo para asignar habitacion
    private JComboBox<String> comboHabitacion;

    // Boton de confirmacion de ingreso
    private JButton btnConfirmarIngreso;

    public HospitalizacionPanel() {
        setLayout(new BorderLayout());
        setBackground(Estilos.FONDO_APP);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        add(crearFilaTitulo(),       BorderLayout.NORTH);

        JPanel centro = new JPanel();
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
        centro.setBackground(Estilos.FONDO_APP);
        centro.add(Box.createVerticalStrut(15));
        centro.add(crearFilaEstadisticas());
        centro.add(Box.createVerticalStrut(15));
        centro.add(crearFilaPrincipal());

        JScrollPane scroll = new JScrollPane(centro);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(12);
        add(scroll, BorderLayout.CENTER);
    }

    // titulo del modulo sin  
    private JPanel crearFilaTitulo() {
        JPanel fila = new JPanel(new BorderLayout());
        fila.setBackground(Estilos.FONDO_APP);

        JLabel titulo = new JLabel("Hospitalizacion");
        titulo.setFont(Estilos.TITULO);
        titulo.setForeground(Estilos.TEXTO_NORMAL);
        fila.add(titulo, BorderLayout.WEST);
        return fila;
    }

    // estadisticas de hospitalizacion.
    private JPanel crearFilaEstadisticas() {
        JPanel fila = new JPanel(new GridLayout(1, 4, 10, 0));
        fila.setBackground(Estilos.FONDO_APP);
        fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 75));

        fila.add(crearStat("Pacientes ingresados",  "x", Estilos.TEXTO_NORMAL));
        fila.add(crearStat("Habitaciones Libres",   "x", Estilos.VERDE_TEXTO));
        fila.add(crearStat("Habitaciones Ocupadas", "x", Estilos.ROJO_TEXTO));
        fila.add(crearStat("Traslados externos",    "x", Estilos.AMBAR_TEXTO));
        return fila;
    }

    // Dos columnas: validacion e ingreso + mapa de habitaciones.
    private JPanel crearFilaPrincipal() {
        JPanel fila = new JPanel(new GridLayout(1, 2, 12, 0));
        fila.setBackground(Estilos.FONDO_APP);
        fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 340));

        fila.add(crearTarjetaValidacion());
        fila.add(crearTarjetaMapa());
        return fila;
    }

    //validacion de ingresos
    
    // Card con banner del paciente, checks de validacion y combo de habitacion.
    private JPanel crearTarjetaValidacion() {
        JPanel tarjeta = crearTarjeta("Validacion de Ingresos");

        JPanel cuerpo = new JPanel();
        cuerpo.setLayout(new BoxLayout(cuerpo, BoxLayout.Y_AXIS));
        cuerpo.setBackground(Estilos.FONDO_BLANCO);
        cuerpo.setBorder(new EmptyBorder(12, 14, 14, 14));

        // Banner verde con nombre y especialidad del paciente
        JPanel bannerPaciente = new JPanel(new BorderLayout());
        bannerPaciente.setBackground(Estilos.VERDE_FONDO);
        bannerPaciente.setBorder(new EmptyBorder(8, 10, 8, 10));
        JLabel lblPaciente = new JLabel("Paciente: Nombre – Especialidad: x");
        lblPaciente.setFont(Estilos.NORMAL);
        lblPaciente.setForeground(Estilos.VERDE_TEXTO);
        bannerPaciente.add(lblPaciente, BorderLayout.CENTER);
        bannerPaciente.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));

        cuerpo.add(bannerPaciente);
        cuerpo.add(Box.createVerticalStrut(12));

        // Fila de validacion: habitacion disponible
        cuerpo.add(crearFilaValidacion("Habitacion Disponible:", "x", null));
        cuerpo.add(new JSeparator());
        cuerpo.add(Box.createVerticalStrut(4));

        // Fila de validacion: especialidad disponible
        cuerpo.add(crearFilaValidacion("Especialidad Disponible:", "x", null));
        cuerpo.add(Box.createVerticalStrut(12));

        // Combo para elegir habitacion
        JLabel lblAsignar = new JLabel("ASIGNAR HABITACION");
        lblAsignar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
        lblAsignar.setFont(Estilos.ETIQUETA);
        lblAsignar.setForeground(Estilos.TEXTO_GRIS);
        lblAsignar.setAlignmentX(Component.RIGHT_ALIGNMENT);

        String[] habitaciones = {"Hab. 01 - Cardiología","Hab. 02 - Traumatologia","Hab. 03 - Pediatria",
        		"Hab. 04 - Cirugia General", "Hab. 05 - Cardiología", "Hab. 06 - Med. Interna",
        		"Hab. 07 - Cirugia General", "Hab. 08 - Pediatria"};
        comboHabitacion = new JComboBox<>(habitaciones);
        comboHabitacion.setFont(Estilos.NORMAL);
        comboHabitacion.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        comboHabitacion.setPreferredSize(new Dimension(300, 36));
        comboHabitacion.setAlignmentX(Component.CENTER_ALIGNMENT);

        cuerpo.add(lblAsignar);
        cuerpo.add(Box.createVerticalStrut(5));
        cuerpo.add(comboHabitacion);
        cuerpo.add(Box.createVerticalStrut(14));

        // Boton confirmar ingreso ancho completo
        btnConfirmarIngreso = new JButton("Confirmar ingreso");
        btnConfirmarIngreso.setFont(Estilos.SUBTITULO);
        btnConfirmarIngreso.setBackground(Estilos.FONDO_BLANCO);
        btnConfirmarIngreso.setFocusPainted(false);
        btnConfirmarIngreso.setOpaque(true);
        btnConfirmarIngreso.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnConfirmarIngreso.setPreferredSize(new Dimension(300, 40));
        btnConfirmarIngreso.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnConfirmarIngreso.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnConfirmarIngreso.addActionListener(e -> {
          //Registrar ingreso del paciente en la BD
        });

        cuerpo.add(btnConfirmarIngreso);

        tarjeta.add(cuerpo, BorderLayout.CENTER);
        return tarjeta;
    }

    // Fila con texto descriptivo y badge de estado a la derecha
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

    //habitaciones

    //Grilla 4x2 de habitaciones coloreadas por disponibilidad.
    private JPanel crearTarjetaMapa() {
        JPanel tarjeta = crearTarjeta("Mapa de Habitaciones");

        JPanel cuerpo = new JPanel();
        cuerpo.setLayout(new BoxLayout(cuerpo, BoxLayout.Y_AXIS));
        cuerpo.setBackground(Estilos.FONDO_BLANCO);
        cuerpo.setBorder(new EmptyBorder(12, 14, 12, 14));

        // Datos de habitaciones: {numero, especialidad, libre?}
        String[][] habitaciones = {
            {"01","Cardiología","libre"},  {"02","Traumatologia","ocupada"},
            {"03","Pediatria","ocupada"},{"04","Cirugia General","ocupada"},
            {"05","Cardiología","libre"},  {"06","Med. Interna","libre"},
            {"07","Cirugia General","libre"},  {"08","Pediatria","libre"}
        };

        // Grilla 4 columnas x 2 filas
        JPanel grilla = new JPanel(new GridLayout(2, 4, 8, 8));
        grilla.setBackground(Estilos.FONDO_BLANCO);

        for (String[] hab : habitaciones) {
            grilla.add(crearCeldaHabitacion(hab[0], hab[1], hab[2].equals("libre")));
        }

        // Leyenda de colores
        JPanel leyenda = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        leyenda.setBackground(Estilos.FONDO_BLANCO);
        leyenda.add(crearItemLeyenda("Libre",   Estilos.VERDE_TEXTO));
        leyenda.add(crearItemLeyenda("Ocupado", Estilos.ROJO_TEXTO));

        cuerpo.add(grilla);
        cuerpo.add(Box.createVerticalStrut(10));
        cuerpo.add(leyenda);

        tarjeta.add(cuerpo, BorderLayout.CENTER);
        return tarjeta;
    }

    // Celda individual del mapa con numero y especialidad.
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

    //utilidades

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
        JLabel punto = new JLabel("•"); punto.setFont(new Font("SansSerif",Font.BOLD,14)); punto.setForeground(color);
        JLabel lbl   = new JLabel(texto); lbl.setFont(Estilos.SB_SMALL); lbl.setForeground(color);
        item.add(punto); item.add(lbl);
        return item;
    }
}

