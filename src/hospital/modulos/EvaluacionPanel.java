package hospital.modulos;
import hospital.estilos.Estilos;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

// modulo de evaluacion medica
public class EvaluacionPanel extends JPanel {

    // campos del lado izquierdo
    private JTextField campoNombre;
    private JTextField campoCedula;

    // campos editables
    private JTextField campoCamilla;
    private JTextField campoHoraLlegada;
    private JTextArea  campoMotivo;

    // campos del lado derecho
    private JTextArea         campoDiagnostico;
    private JComboBox<String> comboEspecialidad;
    private JComboBox<String> comboDecision;

    // botones
    private JButton btnSiguientePaciente;
    private JButton btnConfirmarEvaluacion;

    // checkboxes de procedimientos
    private JCheckBox chkElectrocardiograma;
    private JCheckBox chkOximetria;
    private JCheckBox chkAnalisisSangre;
    private JCheckBox chkRadiografia;
    private JCheckBox chkSueroIV;

    public EvaluacionPanel() {
        setLayout(new BorderLayout());
        setBackground(Estilos.FONDO_APP);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        add(crearFilaTitulo(), BorderLayout.NORTH);

        JPanel centro = new JPanel();
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
        centro.setBackground(Estilos.FONDO_APP);
        centro.add(Box.createVerticalStrut(15));
        centro.add(crearFilaSuperior());
        centro.add(Box.createVerticalStrut(15));
        centro.add(crearTarjetaProcedimientos());

        JScrollPane scroll = new JScrollPane(centro);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(12);
        add(scroll, BorderLayout.CENTER);
    }

    // titulo con boton siguiente paciente
    private JPanel crearFilaTitulo() {
        JPanel fila = new JPanel(new BorderLayout());
        fila.setBackground(Estilos.FONDO_APP);

        JLabel titulo = new JLabel("Evaluacion Medica");
        titulo.setFont(Estilos.TITULO);
        titulo.setForeground(Estilos.TEXTO_NORMAL);

        btnSiguientePaciente = EmergenciasPanel.crearBotonOutline("Siguiente Paciente");
        btnSiguientePaciente.addActionListener(e -> {
            // cargar el proximo paciente en espera de evaluacion
        });

        fila.add(titulo,               BorderLayout.WEST);
        fila.add(btnSiguientePaciente, BorderLayout.EAST);
        return fila;
    }

    // fila superior con tarjeta de datos y tarjeta de evaluacion
    private JPanel crearFilaSuperior() {
        JPanel fila = new JPanel(new GridLayout(1, 2, 12, 0));
        fila.setBackground(Estilos.FONDO_APP);
        fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 430));

        fila.add(crearTarjetaDatosPaciente());
        fila.add(crearTarjetaEvaluacion());
        return fila;
    }
//prueba git
 // datos del paciente
    private JPanel crearTarjetaDatosPaciente() {
        JPanel tarjeta = crearTarjeta("Datos del Paciente");

        JPanel cuerpo = new JPanel();
        cuerpo.setLayout(new BoxLayout(cuerpo, BoxLayout.Y_AXIS));
        cuerpo.setBackground(Estilos.FONDO_BLANCO);
        cuerpo.setBorder(new EmptyBorder(12, 14, 12, 14));

        campoNombre      = new JTextField(); // solo lectura
        campoCedula      = new JTextField(); // solo lectura
        campoCamilla     = new JTextField(); // editable
        campoHoraLlegada = new JTextField(); // editable

        cuerpo.add(crearCampoSoloLectura("NOMBRE",          campoNombre,      false));
        cuerpo.add(Box.createVerticalStrut(8));
        cuerpo.add(crearCampoSoloLectura("CEDULA",          campoCedula,      false));
        cuerpo.add(Box.createVerticalStrut(8));
        cuerpo.add(crearCampoSoloLectura("CAMILLA",         campoCamilla,     true));
        cuerpo.add(Box.createVerticalStrut(8));
        cuerpo.add(crearCampoSoloLectura("HORA DE LLEGADA", campoHoraLlegada, true));
        cuerpo.add(Box.createVerticalStrut(8));

        // area de texto para motivo de consulta (editable)
        campoMotivo = new JTextArea(3, 0);
        campoMotivo.setEditable(true);
        campoMotivo.setLineWrap(true);
        campoMotivo.setWrapStyleWord(true);
        campoMotivo.setBackground(Estilos.FONDO_CAMPO);
        campoMotivo.setFont(Estilos.NORMAL);
        campoMotivo.setBorder(new EmptyBorder(6, 8, 6, 8));
        JScrollPane scrollMotivo = new JScrollPane(campoMotivo);
        scrollMotivo.setBorder(BorderFactory.createLineBorder(Estilos.BORDE));
        scrollMotivo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel panelMotivo = new JPanel();
        panelMotivo.setLayout(new BoxLayout(panelMotivo, BoxLayout.Y_AXIS));
        panelMotivo.setBackground(Estilos.FONDO_BLANCO);
        JLabel lblMotivo = new JLabel("MOTIVO DE CONSULTA");
        lblMotivo.setFont(Estilos.ETIQUETA);
        lblMotivo.setForeground(Estilos.TEXTO_GRIS);
        lblMotivo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelMotivo.add(lblMotivo);
        panelMotivo.add(Box.createVerticalStrut(4));
        panelMotivo.add(scrollMotivo);
        panelMotivo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));
        panelMotivo.setAlignmentX(Component.LEFT_ALIGNMENT);

        cuerpo.add(panelMotivo);

        tarjeta.add(cuerpo, BorderLayout.CENTER);
        return tarjeta;
    }

    // campo con etiqueta, fondo gris 
    private JPanel crearCampoSoloLectura(String etiqueta, JTextField campo, boolean editable) {
        campo.setEditable(editable);
        campo.setBackground(Estilos.FONDO_CAMPO);
        campo.setFont(Estilos.NORMAL);
        campo.setBorder(new EmptyBorder(6, 8, 6, 8));
        

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Estilos.FONDO_BLANCO);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(Estilos.ETIQUETA);
        lbl.setForeground(Estilos.TEXTO_GRIS);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        campo.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(lbl);
        panel.add(Box.createVerticalStrut(4));
        panel.add(campo);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 58));
        return panel;
    }
    // tarjeta de evaluacion medica
    // incluye diagnostico, especialidad, decision y boton de confirmar
    private JPanel crearTarjetaEvaluacion() {
        JPanel tarjeta = crearTarjeta("Evaluacion");

        JPanel cuerpo = new JPanel();
        cuerpo.setLayout(new BoxLayout(cuerpo, BoxLayout.Y_AXIS));
        cuerpo.setBackground(Estilos.FONDO_BLANCO);
        cuerpo.setBorder(new EmptyBorder(12, 14, 12, 14));

        // area de texto para el diagnostico del medico
        JLabel lblDiag = new JLabel("DIAGNOSTICO");
        lblDiag.setFont(Estilos.ETIQUETA);
        lblDiag.setForeground(Estilos.TEXTO_GRIS);
        lblDiag.setAlignmentX(Component.LEFT_ALIGNMENT);

        campoDiagnostico = new JTextArea(4, 0);
        campoDiagnostico.setLineWrap(true);
        campoDiagnostico.setWrapStyleWord(true);
        campoDiagnostico.setBackground(Estilos.FONDO_CAMPO);
        campoDiagnostico.setFont(Estilos.NORMAL);
        campoDiagnostico.setBorder(new EmptyBorder(6, 8, 6, 8));

        JScrollPane scrollDiag = new JScrollPane(campoDiagnostico);
        scrollDiag.setBorder(BorderFactory.createLineBorder(Estilos.BORDE));
        scrollDiag.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollDiag.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));

        cuerpo.add(lblDiag);
        cuerpo.add(Box.createVerticalStrut(4));
        cuerpo.add(scrollDiag);
        cuerpo.add(Box.createVerticalStrut(10));

        // combo de especialidad requerida
        String[] especialidades = {
            "Cardiologia", "Pediatría", "Medicina Interna",
            "Cirugía General", "Traumatología"
        };
        comboEspecialidad = new JComboBox<>(especialidades);
        comboEspecialidad.setFont(Estilos.NORMAL);
        cuerpo.add(crearPanelCombo("ESPECIALIDAD REQUERIDA", comboEspecialidad));
        cuerpo.add(Box.createVerticalStrut(10));

        // combo de decision medica
        String[] decisiones = {"Hospitalizar", "Alta médica", "Traslado externo"};
        comboDecision = new JComboBox<>(decisiones);
        comboDecision.setFont(Estilos.NORMAL);
        cuerpo.add(crearPanelCombo("DECISION", comboDecision));
        cuerpo.add(Box.createVerticalStrut(14));

        // boton confirmar evaluacion a ancho completo
        btnConfirmarEvaluacion = new JButton("Confirmar Evaluacion");
        btnConfirmarEvaluacion.setFont(Estilos.SUBTITULO);
        btnConfirmarEvaluacion.setBackground(Estilos.FONDO_BLANCO);
        btnConfirmarEvaluacion.setFocusPainted(false);
        btnConfirmarEvaluacion.setOpaque(true);
        btnConfirmarEvaluacion.setBorder(BorderFactory.createLineBorder(Estilos.BORDE));
        btnConfirmarEvaluacion.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnConfirmarEvaluacion.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnConfirmarEvaluacion.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnConfirmarEvaluacion.addActionListener(e -> {
            // guardar evaluacion y decidir flujo segun decision seleccionada
        });
        cuerpo.add(btnConfirmarEvaluacion);

        tarjeta.add(cuerpo, BorderLayout.CENTER);
        return tarjeta;
    }

    // tarjeta de procedimientos medicos aplicados
    private JPanel crearTarjetaProcedimientos() {
        JPanel tarjeta = crearTarjeta("Procedimientos aplicados");

        JPanel cuerpo = new JPanel();
        cuerpo.setLayout(new BoxLayout(cuerpo, BoxLayout.Y_AXIS));
        cuerpo.setBackground(Estilos.FONDO_BLANCO);
        cuerpo.setBorder(new EmptyBorder(10, 14, 14, 14));

        chkElectrocardiograma = crearCheckBox("Electrocardiograma");
        chkOximetria          = crearCheckBox("Oximetria de pulso");
        chkAnalisisSangre     = crearCheckBox("Analisis de Sangre");
        chkRadiografia        = crearCheckBox("Radiografia de Torax");
        chkSueroIV            = crearCheckBox("Suero Intravenoso");

        cuerpo.add(chkElectrocardiograma);
        cuerpo.add(new JSeparator());
        cuerpo.add(chkOximetria);
        cuerpo.add(new JSeparator());
        cuerpo.add(chkAnalisisSangre);
        cuerpo.add(new JSeparator());
        cuerpo.add(chkRadiografia);
        cuerpo.add(new JSeparator());
        cuerpo.add(chkSueroIV);

        tarjeta.add(cuerpo, BorderLayout.CENTER);
        tarjeta.setMaximumSize(new Dimension(Integer.MAX_VALUE, 230));
        return tarjeta;
    }

    // campo de texto con etiqueta, recibe si es editable o no
    private JPanel crearCampoConEtiqueta(String etiqueta, JTextField campo, boolean editable) {
        campo.setEditable(editable);
        campo.setBackground(Estilos.FONDO_CAMPO);
        campo.setFont(Estilos.NORMAL);
        campo.setBorder(new EmptyBorder(6, 8, 6, 8));
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Estilos.FONDO_BLANCO);

        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(Estilos.ETIQUETA);
        lbl.setForeground(Estilos.TEXTO_GRIS);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        campo.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(lbl);
        panel.add(Box.createVerticalStrut(4));
        panel.add(campo);
        return panel;
    }

    // panel con etiqueta y combo box debajo
    private JPanel crearPanelCombo(String etiqueta, JComboBox<String> combo) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Estilos.FONDO_BLANCO);

        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(Estilos.ETIQUETA);
        lbl.setForeground(Estilos.TEXTO_GRIS);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        combo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));
        combo.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(lbl);
        panel.add(Box.createVerticalStrut(4));
        panel.add(combo);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 58));
        return panel;
    }

    // checkbox estilizado con fuente del sistema
    private JCheckBox crearCheckBox(String texto) {
        JCheckBox check = new JCheckBox(texto);
        check.setFont(Estilos.NORMAL);
        check.setBackground(Estilos.FONDO_BLANCO);
        check.setForeground(Estilos.TEXTO_NORMAL);
        check.setBorder(new EmptyBorder(6, 0, 6, 0));
        check.addActionListener(e -> {
            // agregar o quitar procedimiento de la lista de cobros
        });
        return check;
    }

    // tarjeta contenedor con titulo y borde
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
