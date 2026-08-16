package com.hospital.vista.modulos;

import com.hospital.vista.estilos.Estilos;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

// POO: Herencia - EvaluacionPanel extiende JPanel.
public class EvaluacionPanel extends JPanel {

    // POO: Encapsulacion - campos internos de evaluacion.
    private JTextField campoNombre;
    private JTextField campoCedula;
    private JTextField campoCamilla;

    private JTextArea         campoDiagnostico;
    private JComboBox<String> comboEspecialidad;
    private JComboBox<String> comboDecision;

    private JButton btnSiguientePaciente;
    private JButton btnConfirmarEvaluacion;

    private JCheckBox chkElectrocardiograma;
    private JCheckBox chkOximetria;
    private JCheckBox chkAnalisisSangre;
    private JCheckBox chkRadiografia;
    private JCheckBox chkSueroIV;

    private com.hospital.dao.EvaluacionDAOImpl evaluacionDAO = new com.hospital.dao.EvaluacionDAOImpl();

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

        JScrollPane scroll = new JScrollPane(centro);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(12);
        add(scroll, BorderLayout.CENTER);
    }

    private JPanel crearFilaTitulo() {
        JPanel fila = new JPanel(new BorderLayout());
        fila.setBackground(Estilos.FONDO_APP);

        JLabel titulo = new JLabel("Evaluacion Medica");
        titulo.setFont(Estilos.TITULO);
        titulo.setForeground(Estilos.TEXTO_NORMAL);

        btnSiguientePaciente = EmergenciasPanel.crearBotonOutline("Siguiente Paciente");
        btnSiguientePaciente.addActionListener(e -> {

            String[] datos = evaluacionDAO.obtenerSiguientePaciente();

            if (datos != null) {
                campoNombre.setText(datos[0]);
                campoCedula.setText(datos[1]);
                campoCamilla.setText(datos[2]);
                JOptionPane.showMessageDialog(this, "Paciente " + datos[0] + " listo para evaluación.");
            } else {
                JOptionPane.showMessageDialog(this, "No hay pacientes en espera.", "Sala Vacía", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        fila.add(titulo,               BorderLayout.WEST);
        fila.add(btnSiguientePaciente, BorderLayout.EAST);
        return fila;
    }

    private JPanel crearFilaSuperior() {
        JPanel fila = new JPanel(new GridLayout(1, 2, 12, 0));
        fila.setBackground(Estilos.FONDO_APP);
        fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 430));

        fila.add(crearTarjetaDatosPaciente());
        fila.add(crearTarjetaEvaluacion());
        return fila;
    }

    private JPanel crearTarjetaDatosPaciente() {
        JPanel tarjeta = crearTarjeta("Datos del Paciente");

        JPanel cuerpo = new JPanel(new GridBagLayout());
        cuerpo.setBackground(Estilos.FONDO_BLANCO);
        cuerpo.setBorder(new EmptyBorder(12, 14, 12, 14));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx   = 0;
        gbc.weightx = 1.0;
        gbc.fill    = GridBagConstraints.HORIZONTAL;
        gbc.insets  = new Insets(0, 0, 15, 0);

        campoNombre = crearCampoTexto(false);
        gbc.gridy = 0;
        cuerpo.add(crearPanelCampo("NOMBRE", campoNombre), gbc);

        campoCedula = crearCampoTexto(false);
        gbc.gridy = 1;
        cuerpo.add(crearPanelCampo("CEDULA", campoCedula), gbc);

        campoCamilla = crearCampoTexto(false);
        gbc.gridy = 2;
        cuerpo.add(crearPanelCampo("CAMILLA", campoCamilla), gbc);

        gbc.gridy = 3;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        JPanel filler = new JPanel();
        filler.setBackground(Estilos.FONDO_BLANCO);
        cuerpo.add(filler, gbc);

        tarjeta.add(cuerpo, BorderLayout.CENTER);
        return tarjeta;
    }

    private JPanel crearTarjetaEvaluacion() {
        JPanel tarjeta = crearTarjeta("Evaluacion");

        JPanel cuerpo = new JPanel();
        cuerpo.setLayout(new BoxLayout(cuerpo, BoxLayout.Y_AXIS));
        cuerpo.setBackground(Estilos.FONDO_BLANCO);
        cuerpo.setBorder(new EmptyBorder(12, 14, 12, 14));

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

        String[] especialidades = {
                "Cardiologia", "Pediatría", "Medicina Interna",
                "Cirugía General", "Traumatología"
        };
        comboEspecialidad = new JComboBox<>(especialidades);
        comboEspecialidad.setFont(Estilos.NORMAL);
        cuerpo.add(crearPanelCombo("ESPECIALIDAD REQUERIDA", comboEspecialidad));
        cuerpo.add(Box.createVerticalStrut(10));

        String[] decisiones = {"Hospitalizar", "Alta médica", "Traslado externo"};
        comboDecision = new JComboBox<>(decisiones);
        comboDecision.setFont(Estilos.NORMAL);
        cuerpo.add(crearPanelCombo("DECISION", comboDecision));
        cuerpo.add(Box.createVerticalStrut(14));

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
            String cedula = campoCedula.getText();
            if (cedula.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Primero llame al siguiente paciente.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String diag = campoDiagnostico.getText();
            String esp = (String) comboEspecialidad.getSelectedItem();
            String dec = (String) comboDecision.getSelectedItem();

            if (dec.equals("Traslado externo")) {
                String motivoTraslado = JOptionPane.showInputDialog(this,
                        "Por favor, ingrese el motivo del traslado y el hospital de destino:",
                        "Justificación de Traslado", JOptionPane.WARNING_MESSAGE);

                if (motivoTraslado == null || motivoTraslado.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Es obligatorio especificar el motivo para realizar un traslado externo.");
                    return;
                }

                diag = diag + "\n\n*** TRASLADO EXTERNO ***\nMotivo/Destino: " + motivoTraslado;
            }


            // Guardamos en la base de datos
            evaluacionDAO.guardarEvaluacion(cedula, diag, esp, dec);
            evaluacionDAO.procesarDecision(cedula, dec, campoCamilla.getText());

            JOptionPane.showMessageDialog(this, "Evaluación médica confirmada y procesada.");

            // Limpiamos pantalla
            campoNombre.setText("");
            campoCedula.setText("");
            campoCamilla.setText("");
            campoDiagnostico.setText("");
            comboDecision.setSelectedIndex(0);
            comboEspecialidad.setSelectedIndex(0);

        });

        cuerpo.add(btnConfirmarEvaluacion);
        tarjeta.add(cuerpo, BorderLayout.CENTER);
        return tarjeta;
    }

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

    private JTextField crearCampoTexto(boolean editable) {
        JTextField campo = new JTextField();
        campo.setFont(Estilos.NORMAL);
        campo.setEditable(editable);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Estilos.BORDE, 1),
                new EmptyBorder(5, 8, 5, 8)
        ));
        campo.setBackground(editable ? Estilos.FONDO_BLANCO : Estilos.FONDO_CAMPO);
        return campo;
    }

    private JPanel crearPanelCampo(String etiqueta, JTextField campo) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Estilos.FONDO_BLANCO);

        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 0; g.weightx = 1.0; g.fill = GridBagConstraints.HORIZONTAL;

        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(Estilos.ETIQUETA);
        lbl.setForeground(Estilos.TEXTO_GRIS);

        g.gridy = 0; g.insets = new Insets(0, 0, 3, 0);
        panel.add(lbl, g);

        g.gridy = 1; g.insets = new Insets(0, 0, 0, 0);
        panel.add(campo, g);

        return panel;
    }

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

    private JCheckBox crearCheckBox(String texto) {
        JCheckBox check = new JCheckBox(texto);
        check.setFont(Estilos.NORMAL);
        check.setBackground(Estilos.FONDO_BLANCO);
        check.setForeground(Estilos.TEXTO_NORMAL);
        check.setBorder(new EmptyBorder(6, 0, 6, 0));
        return check;
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