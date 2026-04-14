package com.hospital.vista.modulos;
import com.hospital.modelo.Paciente;
import com.hospital.vista.estilos.Estilos;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class EvaluacionPanel extends JPanel {

    // Campos del lado izquierdo
    private JTextField campoNombre;       // Solo lectura
    private JTextField campoCedula;       // Solo lectura
    private JTextField campoCamilla;      // Editable
    private JTextField campoHoraLlegada;  // Editable
    private JTextArea  campoMotivo;       // Editable

    // Campos del lado derecho
    private JTextArea         campoDiagnostico;
    private JComboBox<String> comboEspecialidad;
    private JComboBox<String> comboDecision;

    // Botones
    private JButton btnSiguientePaciente;
    private JButton btnConfirmarEvaluacion;

    // Checkboxes de procedimientos
    private JCheckBox chkElectrocardiograma;
    private JCheckBox chkOximetria;
    private JCheckBox chkAnalisisSangre;
    private JCheckBox chkRadiografia;
    private JCheckBox chkSueroIV;
    // Nuestro motor de base de datos para el doctor
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
        centro.add(Box.createVerticalStrut(15));
        centro.add(crearTarjetaProcedimientos());

        JScrollPane scroll = new JScrollPane(centro);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(12);
        add(scroll, BorderLayout.CENTER);
    }

    //titulo

    private JPanel crearFilaTitulo() {
        JPanel fila = new JPanel(new BorderLayout());
        fila.setBackground(Estilos.FONDO_APP);

        JLabel titulo = new JLabel("Evaluacion Medica");
        titulo.setFont(Estilos.TITULO);
        titulo.setForeground(Estilos.TEXTO_NORMAL);

        btnSiguientePaciente = EmergenciasPanel.crearBotonOutline("Siguiente Paciente");
        btnSiguientePaciente.addActionListener(e -> {
            // 1. Vamos a MySQL a buscar al paciente
            Paciente p = evaluacionDAO.obtenerSiguientePaciente();

            if (p != null) {
                // 2. Lo ponemos en pantalla
                campoNombre.setText(p.getNombre());
                campoCedula.setText(p.getCedula());
                campoMotivo.setText("Motivo en registro central...");
                JOptionPane.showMessageDialog(this, "Paciente " + p.getNombre() + " listo para evaluación.");
            } else {
                JOptionPane.showMessageDialog(this, "Excelente trabajo Doc. No hay pacientes en espera.", "Sala Vacía", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        fila.add(titulo,               BorderLayout.WEST);
        fila.add(btnSiguientePaciente, BorderLayout.EAST);
        return fila;
    }

    //fila superior

    private JPanel crearFilaSuperior() {
        JPanel fila = new JPanel(new GridLayout(1, 2, 12, 0));
        fila.setBackground(Estilos.FONDO_APP);
        fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 430));

        fila.add(crearTarjetaDatosPaciente());
        fila.add(crearTarjetaEvaluacion());
        return fila;
    }

    // datos del paciente

    private JPanel crearTarjetaDatosPaciente() {
        JPanel tarjeta = crearTarjeta("Datos del Paciente");

        // GridBagLayout garantiza que TODOS los campos tengan
        // exactamente el mismo ancho, incluyendo el motivo de consulta
        JPanel cuerpo = new JPanel(new GridBagLayout());
        cuerpo.setBackground(Estilos.FONDO_BLANCO);
        cuerpo.setBorder(new EmptyBorder(12, 14, 12, 14));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx   = 0;
        gbc.weightx = 1.0;                          // Ocupa todo el ancho
        gbc.fill    = GridBagConstraints.HORIZONTAL; // Estira horizontalmente
        gbc.insets  = new Insets(0, 0, 8, 0);       // Espacio entre campos

        //nombre (solo lectura) 
        campoNombre = crearCampoTexto(false);
        gbc.gridy = 0;
        cuerpo.add(crearPanelCampo("NOMBRE", campoNombre), gbc);

        // cedula (solo lectura)
        campoCedula = crearCampoTexto(false);
        gbc.gridy = 1;
        cuerpo.add(crearPanelCampo("CEDULA", campoCedula), gbc);

        // camilla (editable)
        campoCamilla = crearCampoTexto(true);
        gbc.gridy = 2;
        cuerpo.add(crearPanelCampo("CAMILLA", campoCamilla), gbc);

        // ── HORA DE LLEGADA (editable, formato 00:00)
        campoHoraLlegada = crearCampoTexto(true);
        campoHoraLlegada.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {
                char c = e.getKeyChar();
                String texto = campoHoraLlegada.getText();
                // Solo permite digitos y los dos puntos, maximo 5 caracteres
                if (!Character.isDigit(c) && c != ':') { e.consume(); return; }
                if (texto.length() >= 5)               { e.consume(); return; }
                //coloca dos puntos en la posicion 2
                if (texto.length() == 2 && c != ':')
                    campoHoraLlegada.setText(texto + ":");
            }
        });
        gbc.gridy = 3;
        cuerpo.add(crearPanelCampo("HORA DE LLEGADA", campoHoraLlegada), gbc);

        // motivo de consulta(editable)
        campoMotivo = new JTextArea(4, 0);
        campoMotivo.setEditable(true);              // Editable
        campoMotivo.setLineWrap(true);
        campoMotivo.setWrapStyleWord(true);
        campoMotivo.setBackground(Estilos.FONDO_CAMPO);
        campoMotivo.setFont(Estilos.NORMAL);
        campoMotivo.setBorder(new EmptyBorder(6, 8, 6, 8));

        JScrollPane scrollMotivo = new JScrollPane(campoMotivo);
        scrollMotivo.setBorder(BorderFactory.createLineBorder(Estilos.BORDE, 1));

        gbc.gridy   = 4;
        gbc.weighty = 1.0;
        gbc.fill    = GridBagConstraints.BOTH;
        gbc.insets  = new Insets(0, 0, 0, 0);
        cuerpo.add(crearPanelCampoArea("MOTIVO DE CONSULTA", scrollMotivo), gbc);

        tarjeta.add(cuerpo, BorderLayout.CENTER);
        return tarjeta;
    }

    //evaluacion medica

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

        // 1. ¡PRIMERO CREAMOS EL BOTÓN EN MEMORIA! (Esto es lo que se había borrado)
        btnConfirmarEvaluacion = new JButton("Confirmar Evaluacion");
        btnConfirmarEvaluacion.setFont(Estilos.SUBTITULO);
        btnConfirmarEvaluacion.setBackground(Estilos.FONDO_BLANCO);
        btnConfirmarEvaluacion.setFocusPainted(false);
        btnConfirmarEvaluacion.setOpaque(true);
        btnConfirmarEvaluacion.setBorder(BorderFactory.createLineBorder(Estilos.BORDE));
        btnConfirmarEvaluacion.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnConfirmarEvaluacion.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnConfirmarEvaluacion.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // 2. AHORA SÍ, LE DAMOS LA ACCIÓN MÁGICA DE MYSQL
        btnConfirmarEvaluacion.addActionListener(e -> {
            String cedula = campoCedula.getText();

            // Validamos que haya un paciente cargado
            if (cedula.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Primero llame al siguiente paciente.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String diag = campoDiagnostico.getText();

            // --- TRUCO SENIOR: RECOLECTAR PROCEDIMIENTOS ---
            String procedimientos = "";
            if (chkElectrocardiograma.isSelected()) procedimientos += "- Electrocardiograma\n";
            if (chkOximetria.isSelected())          procedimientos += "- Oximetría de pulso\n";
            if (chkAnalisisSangre.isSelected())     procedimientos += "- Análisis de Sangre\n";
            if (chkRadiografia.isSelected())        procedimientos += "- Radiografía de Tórax\n";
            if (chkSueroIV.isSelected())            procedimientos += "- Suero Intravenoso\n";

            // Si marcó al menos uno, lo pegamos al final del diagnóstico
            if (!procedimientos.isEmpty()) {
                diag = diag + "\n\nProcedimientos Aplicados:\n" + procedimientos;
            }
            // -----------------------------------------------

            String esp = (String) comboEspecialidad.getSelectedItem();
            String dec = (String) comboDecision.getSelectedItem();

            // Guardamos el reporte médico (ahora incluye los procedimientos)
            evaluacionDAO.guardarEvaluacion(cedula, diag, esp, dec);

            // Cambiamos su estado si se va de alta
            evaluacionDAO.procesarDecision(cedula, dec, campoCamilla.getText());

            JOptionPane.showMessageDialog(this, "Evaluación médica guardada en el sistema.");

            // Limpiamos la pantalla para el siguiente
            // Limpiamos la pantalla para el siguiente
            campoNombre.setText("");
            campoCedula.setText("");
            campoDiagnostico.setText("");
            comboDecision.setSelectedIndex(0);

            chkElectrocardiograma.setSelected(false);
            chkOximetria.setSelected(false);
            chkAnalisisSangre.setSelected(false);
            chkRadiografia.setSelected(false);
            chkSueroIV.setSelected(false);
        });

        // 3. Y FINALMENTE LO PEGAMOS A LA PANTALLA
        cuerpo.add(btnConfirmarEvaluacion);

        tarjeta.add(cuerpo, BorderLayout.CENTER);
        return tarjeta;
    } // <- Esta es la llave que cierra el método crearTarjetaEvaluacion()

    //procedimientos

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

    // utilidades

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

    // Panel con etiqueta arriba y JTextField abajo (para campos de una linea).
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

    // Panel con etiqueta arriba y area de texto (JScrollPane) abajo
    private JPanel crearPanelCampoArea(String etiqueta, JScrollPane areaScroll) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Estilos.FONDO_BLANCO);

        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 0; g.weightx = 1.0; g.fill = GridBagConstraints.HORIZONTAL;

        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(Estilos.ETIQUETA);
        lbl.setForeground(Estilos.TEXTO_GRIS);

        g.gridy = 0; g.insets = new Insets(0, 0, 3, 0);
        panel.add(lbl, g);

        g.gridy = 1; g.weighty = 1.0;
        g.fill  = GridBagConstraints.BOTH;
        g.insets = new Insets(0, 0, 0, 0);
        panel.add(areaScroll, g);

        return panel;
    }

    // Panel con etiqueta y combo box debajo.
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

    // Checkbox estilizado
    private JCheckBox crearCheckBox(String texto) {
        JCheckBox check = new JCheckBox(texto);
        check.setFont(Estilos.NORMAL);
        check.setBackground(Estilos.FONDO_BLANCO);
        check.setForeground(Estilos.TEXTO_NORMAL);
        check.setBorder(new EmptyBorder(6, 0, 6, 0));
        check.addActionListener(e -> {
            //Agregar/quitar procedimiento de cobros
        });
        return check;
    }

    // Tarjeta blanca con titulo en la parte superior.
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

