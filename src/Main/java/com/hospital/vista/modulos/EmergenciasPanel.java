package com.hospital.vista.modulos;

import com.hospital.modelo.Paciente;
import com.hospital.servicio.EmergenciasServicioImpl;
import com.hospital.servicio.IEmergenciasServicio;
import com.hospital.vista.estilos.Estilos;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class EmergenciasPanel extends JPanel {

    // Campos del formulario de registro
    private JTextField campoNombre;
    private JTextField campoCedula;
    private JTextArea campoMotivo;
    private JComboBox<String> comboCamilla;

    // Etiquetas dinámicas para estadísticas
    private JLabel lblStatPacientesHoy;
    private JLabel lblStatCamillasLibres;
    private JLabel lblStatEnEspera;
    private JLabel lblStatOcupadas;

    // Tu motor lógico conectado a MySQL
    private IEmergenciasServicio servicioEmergencias = new EmergenciasServicioImpl();

    // Botones de accion
    private JButton btnNuevoPaciente;
    private JButton btnRegistrar;
    private JButton btnLimpiar;

    // Botones de camilla en la grilla
    private JButton[] botonesCamilla;

    public EmergenciasPanel() {
        setLayout(new BorderLayout());
        setBackground(Estilos.FONDO_APP);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        //Fila superior titulo + boton
        add(crearFilaTitulo(), BorderLayout.NORTH);

        //Contenido central con scroll
        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));
        panelCentro.setBackground(Estilos.FONDO_APP);

        panelCentro.add(Box.createVerticalStrut(15));
        panelCentro.add(crearFilaEstadisticas());
        panelCentro.add(Box.createVerticalStrut(15));
        panelCentro.add(crearFilaPrincipal());

        JScrollPane scroll = new JScrollPane(panelCentro);
        scroll.setBorder(null);
        scroll.setBackground(Estilos.FONDO_APP);
        scroll.getVerticalScrollBar().setUnitIncrement(12);
        add(scroll, BorderLayout.CENTER);

        // Gatillo automático: Cada vez que esta pantalla se hace visible, recarga todo.
        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                refrescarDatos();
            }
        });

        // Llamada inicial para que cargue la primera vez
        refrescarDatos();
    }

    // Titulo "Emergencias" y boton "Nuevo Paciente"
    private JPanel crearFilaTitulo() {
        JPanel fila = new JPanel(new BorderLayout());
        fila.setBackground(Estilos.FONDO_APP);

        JLabel titulo = new JLabel("Emergencias");
        titulo.setFont(Estilos.TITULO);
        titulo.setForeground(Estilos.TEXTO_NORMAL);

        btnNuevoPaciente = crearBotonOutline("Nuevo Paciente");
        btnNuevoPaciente.addActionListener(e -> {
            if (campoNombre != null) campoNombre.setText("");
            if (campoCedula != null) campoCedula.setText("");
            if (campoMotivo != null) campoMotivo.setText("");
            if (comboCamilla != null) comboCamilla.setSelectedIndex(0);
            if (campoNombre != null) campoNombre.requestFocus();
        });

        fila.add(titulo, BorderLayout.WEST);
        fila.add(btnNuevoPaciente, BorderLayout.EAST);
        return fila;
    }

    // Cuatro tarjetas con contadores en tiempo real.
    private JPanel crearFilaEstadisticas() {
        JPanel fila = new JPanel(new GridLayout(1, 4, 10, 0));
        fila.setBackground(Estilos.FONDO_APP);
        fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 75));

        int[] stats = servicioEmergencias.obtenerEstadisticasEmergencia();

        lblStatPacientesHoy = new JLabel(String.valueOf(stats[0]));
        lblStatCamillasLibres = new JLabel(String.valueOf(stats[1]));
        lblStatEnEspera = new JLabel(String.valueOf(stats[2]));
        lblStatOcupadas = new JLabel(String.valueOf(stats[3]));

        fila.add(crearTarjetaStatDinamica("Pacientes de Hoy", lblStatPacientesHoy, Estilos.TEXTO_NORMAL));
        fila.add(crearTarjetaStatDinamica("Camillas Libres", lblStatCamillasLibres, Estilos.VERDE_TEXTO));
        fila.add(crearTarjetaStatDinamica("En espera", lblStatEnEspera, Estilos.AMBAR_TEXTO));
        fila.add(crearTarjetaStatDinamica("Ocupadas", lblStatOcupadas, Estilos.ROJO_TEXTO));

        return fila;
    }

    // fila principal (Camillas + Formulario)
    private JPanel crearFilaPrincipal() {
        JPanel fila = new JPanel(new GridLayout(1, 2, 12, 0));
        fila.setBackground(Estilos.FONDO_APP);
        fila.add(crearTarjetaCamillas());
        fila.add(crearTarjetaRegistro());
        return fila;
    }

    // Grilla de botones de camilla con colores de BD
    private JPanel crearTarjetaCamillas() {
        JPanel tarjeta = crearTarjeta("Estado de Camillas");

        JPanel cuerpo = new JPanel();
        cuerpo.setLayout(new BoxLayout(cuerpo, BoxLayout.Y_AXIS));
        cuerpo.setBackground(Estilos.FONDO_BLANCO);
        cuerpo.setBorder(new EmptyBorder(12, 14, 12, 14));

        String[] nombresCamillas = {"C - 01", "C - 02", "C - 03", "C - 04",
                "C - 05", "C - 06", "C - 07", "C - 08"};

        boolean[] camillasLibres = servicioEmergencias.obtenerEstadoCamillas();

        JPanel grilla = new JPanel(new GridLayout(8, 1, 0, 6));
        grilla.setBackground(Estilos.FONDO_BLANCO);

        botonesCamilla = new JButton[8];

        for (int i = 0; i < 8; i++) {
            JButton btn = new JButton(nombresCamillas[i]);
            btn.setFont(Estilos.ETIQUETA);

            if (camillasLibres[i]) {
                btn.setBackground(Estilos.VERDE_FONDO);
                btn.setForeground(Estilos.VERDE_TEXTO);
            } else {
                btn.setBackground(Estilos.ROJO_FONDO);
                btn.setForeground(Estilos.ROJO_TEXTO);
            }

            btn.setFocusPainted(false);
            btn.setOpaque(true);
            btn.setBorderPainted(false);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.setBorder(BorderFactory.createLineBorder(btn.getBackground().darker(), 1));

            botonesCamilla[i] = btn;
            grilla.add(btn);
        }

        JPanel leyenda = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        leyenda.setBackground(Estilos.FONDO_BLANCO);
        leyenda.add(crearItemLeyenda("Libre", Estilos.VERDE_TEXTO));
        leyenda.add(crearItemLeyenda("Ocupado", Estilos.ROJO_TEXTO));
        leyenda.add(crearItemLeyenda("Limpieza", Estilos.AMBAR_TEXTO));

        cuerpo.add(grilla);
        cuerpo.add(Box.createVerticalStrut(10));
        cuerpo.add(leyenda);

        tarjeta.add(cuerpo, BorderLayout.CENTER);
        return tarjeta;
    }

    // Formulario nombre, cedula, motivo y asignacion
    private JPanel crearTarjetaRegistro() {
        JPanel tarjeta = crearTarjeta("Registro Rapido");

        JPanel cuerpo = new JPanel(new GridBagLayout());
        cuerpo.setBackground(Estilos.FONDO_BLANCO);
        cuerpo.setBorder(new EmptyBorder(12, 14, 12, 14));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);

        campoNombre = new JTextField();
        JPanel pNombre = crearCampoFormulario("NOMBRE", campoNombre, false);
        gbc.gridy = 0;
        cuerpo.add(pNombre, gbc);

        campoCedula = new JTextField();
        campoCedula.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar()) || campoCedula.getText().length() >= 11) {
                    e.consume();
                }
            }
        });
        JPanel pCedula = crearCampoFormulario("CEDULA", campoCedula, false);
        gbc.gridy = 1;
        cuerpo.add(pCedula, gbc);

        campoMotivo = new JTextArea(9, 30);
        campoMotivo.setLineWrap(true);
        campoMotivo.setWrapStyleWord(true);
        campoMotivo.setBackground(Estilos.FONDO_CAMPO);
        campoMotivo.setFont(Estilos.NORMAL);
        campoMotivo.setBorder(new EmptyBorder(6, 8, 6, 8));

        JScrollPane scrollMotivo = new JScrollPane(campoMotivo);
        scrollMotivo.setBorder(BorderFactory.createLineBorder(Estilos.BORDE));
        JPanel pMotivo = crearPanelEtiqueta("MOTIVO DE CONSULTA", scrollMotivo);

        gbc.gridy = 2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        cuerpo.add(pMotivo, gbc);

        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        comboCamilla = new JComboBox<>();


        comboCamilla = new JComboBox<>();
        comboCamilla.setFont(Estilos.NORMAL);
        comboCamilla.setBackground(Estilos.FONDO_BLANCO);
        JPanel pCamilla = crearPanelEtiqueta("ASIGNAR CAMILLA", comboCamilla);

        gbc.gridy = 3;
        cuerpo.add(pCamilla, gbc);

        JPanel filaBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        filaBotones.setBackground(Estilos.FONDO_BLANCO);

        btnRegistrar = crearBotonOutline("Registrar y asignar");
        btnLimpiar = crearBotonOutline("Limpiar");

        btnRegistrar.addActionListener(e -> {
            String nombre = campoNombre.getText();
            String cedula = campoCedula.getText();
            String camillaSeleccionada = (String) comboCamilla.getSelectedItem();

            if (nombre.trim().isEmpty() || cedula.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre y la cédula son obligatorios.",
                        "Error de Registro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Paciente nuevoPaciente = new Paciente();
            nuevoPaciente.setNombre(nombre);
            nuevoPaciente.setCedula(cedula);

            servicioEmergencias.registrarPacienteEmergencia(nuevoPaciente);

            if (camillaSeleccionada != null && camillaSeleccionada.contains("Libre")) {
                String idCamilla = camillaSeleccionada.split("---")[0].trim();
                servicioEmergencias.internarPaciente(cedula, idCamilla);
                JOptionPane.showMessageDialog(this, "Paciente " + nombre + " internado en " + idCamilla);
            } else {
                JOptionPane.showMessageDialog(this, "Paciente " + nombre + " en sala de espera.");
            }
            // 5. Limpiamos el formulario automáticamente
            btnLimpiar.doClick();

            // 6. ¡MAGIA! Recargamos toda la pantalla instantáneamente
            refrescarDatos();

        });

        btnLimpiar.addActionListener(e -> {
            campoNombre.setText("");
            campoCedula.setText("");
            campoMotivo.setText("");
            comboCamilla.setSelectedIndex(0);
        });

        filaBotones.add(btnRegistrar);
        filaBotones.add(btnLimpiar);

        gbc.gridy = 4;
        cuerpo.add(filaBotones, gbc);

        tarjeta.add(cuerpo, BorderLayout.CENTER);
        return tarjeta;
    }

    // --- MÉTODOS DE UTILIDAD ---

    private JPanel crearTarjeta(String titulo) {
        JPanel tarjeta = new JPanel(new BorderLayout());
        tarjeta.setBackground(Estilos.FONDO_BLANCO);
        tarjeta.setBorder(BorderFactory.createLineBorder(Estilos.BORDE, 1));
        JLabel lblTitulo = new JLabel("  " + titulo);
        lblTitulo.setFont(Estilos.SUBTITULO);
        lblTitulo.setForeground(Estilos.TEXTO_NORMAL);
        lblTitulo.setBorder(new MatteBorder(0, 0, 1, 0, Estilos.BORDE));
        lblTitulo.setPreferredSize(new Dimension(0, 36));
        tarjeta.add(lblTitulo, BorderLayout.NORTH);
        return tarjeta;
    }

    private JPanel crearTarjetaStatDinamica(String etiqueta, JLabel lblValor, Color colorValor) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Estilos.FONDO_STAT);
        panel.setBorder(new EmptyBorder(10, 14, 10, 14));
        JLabel lblEtiqueta = new JLabel(etiqueta);
        lblEtiqueta.setFont(Estilos.SB_SMALL);
        lblEtiqueta.setForeground(Estilos.TEXTO_GRIS);
        lblValor.setFont(Estilos.STAT_VAL);
        lblValor.setForeground(colorValor);
        panel.add(lblEtiqueta);
        panel.add(Box.createVerticalStrut(4));
        panel.add(lblValor);
        return panel;
    }

    private JPanel crearCampoFormulario(String etiqueta, JTextField campo, boolean soloLectura) {
        campo.setFont(Estilos.NORMAL);
        campo.setBackground(soloLectura ? Estilos.FONDO_CAMPO : Estilos.FONDO_CAMPO);
        campo.setEditable(!soloLectura);
        campo.setBorder(new EmptyBorder(6, 8, 6, 8));
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));
        return crearPanelEtiqueta(etiqueta, campo);
    }

    private JPanel crearPanelEtiqueta(String etiqueta, JComponent componente) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Estilos.FONDO_BLANCO);
        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(Estilos.ETIQUETA);
        lbl.setForeground(Estilos.TEXTO_GRIS);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        componente.setMaximumSize(new Dimension(Integer.MAX_VALUE, componente.getPreferredSize().height));
        componente.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(lbl);
        panel.add(Box.createVerticalStrut(4));
        panel.add(componente);
        return panel;
    }

    private JPanel crearItemLeyenda(String texto, Color color) {
        JPanel item = new JPanel(new FlowLayout(FlowLayout.LEFT, 3, 0));
        item.setBackground(Estilos.FONDO_BLANCO);
        JLabel punto = new JLabel("•");
        punto.setFont(new Font("SansSerif", Font.BOLD, 14));
        punto.setForeground(color);
        JLabel lblTexto = new JLabel(texto);
        lblTexto.setFont(Estilos.SB_SMALL);
        lblTexto.setForeground(color);
        item.add(punto);
        item.add(lblTexto);
        return item;
    }

    public static JButton crearBotonOutline(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(Estilos.NORMAL);
        btn.setBackground(Estilos.FONDO_BLANCO);
        btn.setForeground(Estilos.TEXTO_NORMAL);
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Estilos.BORDE, 1),
                new EmptyBorder(6, 14, 6, 14)
        ));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // --- MOTOR DE ACTUALIZACIÓN EN TIEMPO REAL ---
    // --- MOTOR DE ACTUALIZACIÓN EN TIEMPO REAL ---
    public void refrescarDatos() {
        // Envolvemos todo en el hilo gráfico para evitar que Swing se congele
        SwingUtilities.invokeLater(() -> {
            // 1. Recargar las 4 estadísticas de arriba
            int[] stats = servicioEmergencias.obtenerEstadisticasEmergencia();
            if (lblStatPacientesHoy != null) lblStatPacientesHoy.setText(String.valueOf(stats[0]));
            if (lblStatCamillasLibres != null) lblStatCamillasLibres.setText(String.valueOf(stats[1]));
            if (lblStatEnEspera != null) lblStatEnEspera.setText(String.valueOf(stats[2]));
            if (lblStatOcupadas != null) lblStatOcupadas.setText(String.valueOf(stats[3]));

            // 2. Recargar colores de camillas y armar el ComboBox
            boolean[] camillasLibres = servicioEmergencias.obtenerEstadoCamillas();

            if (comboCamilla != null) {
                comboCamilla.removeAllItems(); // Vaciamos la lista vieja
            }

            for (int i = 0; i < 8; i++) {
                String numeroCamilla = "C - 0" + (i + 1);

                if (camillasLibres[i]) {
                    // Camilla libre: Botón Verde y texto "Libre" en el combo
                    if (botonesCamilla != null && botonesCamilla[i] != null) {
                        botonesCamilla[i].setBackground(Estilos.VERDE_FONDO);
                        botonesCamilla[i].setForeground(Estilos.VERDE_TEXTO);
                    }
                    if (comboCamilla != null) comboCamilla.addItem(numeroCamilla + " --- Libre");
                } else {
                    // Camilla ocupada: Botón Rojo y texto "Ocupado" en el combo
                    if (botonesCamilla != null && botonesCamilla[i] != null) {
                        botonesCamilla[i].setBackground(Estilos.ROJO_FONDO);
                        botonesCamilla[i].setForeground(Estilos.ROJO_TEXTO);
                    }
                    if (comboCamilla != null) comboCamilla.addItem(numeroCamilla + " --- Ocupado");
                }
            }

            // 3. EL LÁTIGO PARA QUE JAVA PINTE LOS CAMBIOS INMEDIATAMENTE 💥
            this.revalidate();
            this.repaint();
        });
    }
}