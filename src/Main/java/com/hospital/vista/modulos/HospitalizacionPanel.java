package com.hospital.vista.modulos;

import com.hospital.dao.HospitalizacionDAOImpl;
import com.hospital.vista.estilos.Estilos;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

// POO: Herencia - HospitalizacionPanel extiende JPanel.
public class HospitalizacionPanel extends JPanel {

    // POO: Encapsulacion - estado interno del panel.
    private JComboBox<String> comboHabitacion;
    private JButton           btnConfirmarIngreso;
    private JTextField campoCedulaBusqueda;
    private JLabel lblPaciente;
    private String cedulaActiva = "";

    // Para controlar los colores del mapa
    private JPanel[] panelesCeldas = new JPanel[8];
    private JLabel[] labelsNumeros = new JLabel[8];
    private JLabel[] labelsEspecialidades = new JLabel[8];

    private JLabel lblIngresadosVal = new JLabel("0");
    private JLabel lblLibresVal = new JLabel("0");
    private JLabel lblOcupadasVal = new JLabel("0");

    private HospitalizacionDAOImpl hospitalizacionDAO = new HospitalizacionDAOImpl();

    public HospitalizacionPanel() {
        setLayout(new BorderLayout());
        setBackground(Estilos.FONDO_APP);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        add(crearFilaTitulo(), BorderLayout.NORTH);

        JPanel centro = new JPanel(new BorderLayout(0, 15));
        centro.setBackground(Estilos.FONDO_APP);
        centro.setBorder(new EmptyBorder(15, 0, 0, 0));
        centro.add(crearFilaEstadisticas(), BorderLayout.NORTH);
        centro.add(crearFilaPrincipal(),    BorderLayout.CENTER);

        add(centro, BorderLayout.CENTER);
        refrescarMapa();

        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                refrescarMapa();
            }
        });
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

        fila.add(crearStat("Pacientes ingresados",  lblIngresadosVal, Estilos.TEXTO_NORMAL));
        fila.add(crearStat("Habitaciones Libres",   lblLibresVal, Estilos.VERDE_TEXTO));
        fila.add(crearStat("Habitaciones Ocupadas", lblOcupadasVal, Estilos.ROJO_TEXTO));
        fila.add(crearStat("Traslados externos",    new JLabel("0"), Estilos.AMBAR_TEXTO)); // Fijo por ahora
        return fila;
    }

    private JPanel crearFilaPrincipal() {
        JPanel fila = new JPanel(new GridLayout(1, 2, 12, 0));
        fila.setBackground(Estilos.FONDO_APP);
        fila.add(crearTarjetaValidacion());
        fila.add(crearTarjetaMapa());
        return fila;
    }

    private JPanel crearTarjetaValidacion() {
        JPanel tarjeta = crearTarjeta("Validacion de Ingresos");

        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS));
        panelSuperior.setBackground(Estilos.FONDO_BLANCO);
        panelSuperior.setBorder(new EmptyBorder(12, 14, 8, 14));

        JPanel panelBuscador = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelBuscador.setBackground(Estilos.FONDO_BLANCO);
        panelBuscador.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblCedulaTexto = new JLabel("Cédula: ");
        lblCedulaTexto.setFont(Estilos.NORMAL);
        campoCedulaBusqueda = new JTextField(12);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBackground(Estilos.VERDE_FONDO);
        btnBuscar.setForeground(Estilos.VERDE_TEXTO);
        btnBuscar.setFocusPainted(false);
        btnBuscar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnBuscar.addActionListener(e -> {
            cedulaActiva = campoCedulaBusqueda.getText();
            if (!cedulaActiva.isEmpty()) {
                String nombreEncontrado = hospitalizacionDAO.buscarPaciente(cedulaActiva);
                if(nombreEncontrado != null) {
                    lblPaciente.setText("Paciente: " + nombreEncontrado + " | Cédula: " + cedulaActiva);
                } else {
                    JOptionPane.showMessageDialog(this, "Paciente no encontrado en el sistema.");
                    cedulaActiva = "";
                }
            }
        });

        panelBuscador.add(lblCedulaTexto);
        panelBuscador.add(campoCedulaBusqueda);
        panelBuscador.add(btnBuscar);

        panelSuperior.add(panelBuscador);
        panelSuperior.add(Box.createVerticalStrut(10));

        JPanel bannerPaciente = new JPanel(new BorderLayout());
        bannerPaciente.setBackground(Estilos.VERDE_FONDO);
        bannerPaciente.setBorder(new EmptyBorder(8, 10, 8, 10));
        bannerPaciente.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));

        lblPaciente = new JLabel("Esperando paciente...");
        lblPaciente.setFont(Estilos.NORMAL);
        lblPaciente.setForeground(Estilos.VERDE_TEXTO);
        bannerPaciente.add(lblPaciente, BorderLayout.CENTER);

        panelSuperior.add(bannerPaciente);
        panelSuperior.add(Box.createVerticalStrut(12));

        panelSuperior.add(crearFilaValidacion("Habitacion Disponible:",  "SI", Estilos.VERDE_TEXTO));
        panelSuperior.add(new JSeparator());
        panelSuperior.add(crearFilaValidacion("Especialidad Disponible:", "SI", Estilos.VERDE_TEXTO));

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
            if (cedulaActiva.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe buscar un paciente primero.");
                return;
            }

            String habitacionSeleccionada = (String) comboHabitacion.getSelectedItem();
            String numeroHab = habitacionSeleccionada.substring(5, 7);

            boolean exito = hospitalizacionDAO.registrarIngreso(cedulaActiva, numeroHab);

            if(exito) {
                JOptionPane.showMessageDialog(this, "¡Ingreso Confirmado!\nPaciente asignado a la Habitación " + numeroHab);
                cedulaActiva = "";
                campoCedulaBusqueda.setText("");
                lblPaciente.setText("Esperando paciente...");
                refrescarMapa();
            } else {
                JOptionPane.showMessageDialog(this, "Error al confirmar el ingreso.");
            }
        });

        JButton btnDarDeAlta = new JButton("Dar de Alta Médica");
        btnDarDeAlta.setFont(Estilos.SUBTITULO);
        btnDarDeAlta.setBackground(Estilos.ROJO_FONDO);
        btnDarDeAlta.setForeground(Estilos.ROJO_TEXTO);
        btnDarDeAlta.setFocusPainted(false);
        btnDarDeAlta.setOpaque(true);
        btnDarDeAlta.setBorder(BorderFactory.createLineBorder(Estilos.ROJO_BORDE, 1));
        btnDarDeAlta.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        btnDarDeAlta.setPreferredSize(new Dimension(0, 44));
        btnDarDeAlta.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnDarDeAlta.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnDarDeAlta.addActionListener(e -> {
            if (cedulaActiva.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe buscar un paciente primero para darle de alta.");
                return;
            }

            String inputDias = JOptionPane.showInputDialog(this,
                    "El paciente será dado de alta.\n\n" +
                            "NOTA: Recuerde que si el paciente sale después de las 12:00 PM, se factura un día adicional.\n\n" +
                            "¿Cuántos días completos se le facturarán?",
                    "Confirmar Alta y Días", JOptionPane.QUESTION_MESSAGE);

            if (inputDias != null && !inputDias.trim().isEmpty()) {
                try {
                    int dias = Integer.parseInt(inputDias.trim());

                    //  DAO para que acepte los días
                    boolean exito = hospitalizacionDAO.darDeAlta(cedulaActiva, dias);

                    if (exito) {
                        JOptionPane.showMessageDialog(this, "¡Paciente dado de alta!\nDías reportados a Cajera: " + dias);
                        cedulaActiva = "";
                        campoCedulaBusqueda.setText("");
                        lblPaciente.setText("Esperando paciente...");
                        refrescarMapa();
                    } else {
                        JOptionPane.showMessageDialog(this, "Error: El paciente no estaba ingresado.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Por favor, introduzca un número de días válido.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        panelInferior.add(lblAsignar);
        panelInferior.add(Box.createVerticalStrut(5));
        panelInferior.add(comboHabitacion);
        panelInferior.add(Box.createVerticalStrut(10));
        panelInferior.add(btnConfirmarIngreso);
        panelInferior.add(Box.createVerticalStrut(10));
        panelInferior.add(btnDarDeAlta);

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

    private JPanel crearTarjetaMapa() {
        JPanel tarjeta = crearTarjeta("Mapa de Habitaciones");

        String[][] datosHab = {
                {"01","Cardiología"}, {"02","Traumatologia"}, {"03","Pediatria"}, {"04","Cirugia General"},
                {"05","Cardiología"}, {"06","Med. Interna"},  {"07","Cirugia General"}, {"08","Pediatria"}
        };

        JPanel grilla = new JPanel(new GridLayout(2, 4, 8, 8));
        grilla.setBackground(Estilos.FONDO_BLANCO);

        for (int i = 0; i < 8; i++) {
            grilla.add(crearCeldaHabitacion(i, datosHab[i][0], datosHab[i][1]));
        }

        JPanel leyenda = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        leyenda.setBackground(Estilos.FONDO_BLANCO);
        leyenda.add(crearItemLeyenda("Libre",   Estilos.VERDE_TEXTO));
        leyenda.add(crearItemLeyenda("Ocupado", Estilos.ROJO_TEXTO));

        JPanel panelGrillaYLeyenda = new JPanel();
        panelGrillaYLeyenda.setLayout(new BoxLayout(panelGrillaYLeyenda, BoxLayout.Y_AXIS));
        panelGrillaYLeyenda.setBackground(Estilos.FONDO_BLANCO);
        panelGrillaYLeyenda.setBorder(new EmptyBorder(12, 14, 10, 14));
        panelGrillaYLeyenda.add(grilla);
        panelGrillaYLeyenda.add(Box.createVerticalStrut(10));
        panelGrillaYLeyenda.add(leyenda);

        JPanel contenido = new JPanel(new BorderLayout());
        contenido.setBackground(Estilos.FONDO_BLANCO);
        contenido.add(panelGrillaYLeyenda, BorderLayout.NORTH);

        tarjeta.add(contenido, BorderLayout.CENTER);
        return tarjeta;
    }

    private JPanel crearCeldaHabitacion(int index, String numero, String especialidad) {
        JPanel celda = new JPanel();
        celda.setLayout(new BoxLayout(celda, BoxLayout.Y_AXIS));
        celda.setBackground(Estilos.VERDE_FONDO);
        celda.setBorder(BorderFactory.createLineBorder(Estilos.VERDE_BORDE, 1));

        JLabel lblNum = new JLabel(numero);
        lblNum.setFont(Estilos.SUBTITULO);
        lblNum.setForeground(Estilos.VERDE_TEXTO);
        lblNum.setBorder(new EmptyBorder(6, 8, 0, 8));

        JLabel lblEsp = new JLabel(especialidad);
        lblEsp.setFont(new Font("SansSerif", Font.PLAIN, 10));
        lblEsp.setForeground(Estilos.VERDE_TEXTO);
        lblEsp.setBorder(new EmptyBorder(0, 8, 6, 8));

        celda.add(lblNum);
        celda.add(lblEsp);

        panelesCeldas[index] = celda;
        labelsNumeros[index] = lblNum;
        labelsEspecialidades[index] = lblEsp;

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

    private JPanel crearStat(String etiqueta, JLabel v, Color color) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(Estilos.FONDO_STAT);
        p.setBorder(new EmptyBorder(10, 14, 10, 14));
        JLabel e = new JLabel(etiqueta);
        e.setFont(Estilos.SB_SMALL);
        e.setForeground(Estilos.TEXTO_GRIS);

        v.setFont(Estilos.STAT_VAL);
        v.setForeground(color);

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

    public void refrescarMapa() {
        boolean[] camasLibres = hospitalizacionDAO.obtenerEstadoHabitaciones();

        int contLibres = 0;
        int contOcupadas = 0;

        for (int i = 0; i < 8; i++) {
            if (camasLibres[i]) {
                contLibres++;
                panelesCeldas[i].setBackground(Estilos.VERDE_FONDO);
                panelesCeldas[i].setBorder(BorderFactory.createLineBorder(Estilos.VERDE_BORDE, 1));
                labelsNumeros[i].setForeground(Estilos.VERDE_TEXTO);
                labelsEspecialidades[i].setForeground(Estilos.VERDE_TEXTO);
            } else {
                contOcupadas++;
                panelesCeldas[i].setBackground(Estilos.ROJO_FONDO);
                panelesCeldas[i].setBorder(BorderFactory.createLineBorder(Estilos.ROJO_BORDE, 1));
                labelsNumeros[i].setForeground(Estilos.ROJO_TEXTO);
                labelsEspecialidades[i].setForeground(Estilos.ROJO_TEXTO);
            }
        }

        lblLibresVal.setText(String.valueOf(contLibres));
        lblOcupadasVal.setText(String.valueOf(contOcupadas));
        lblIngresadosVal.setText(String.valueOf(contOcupadas));

        this.revalidate();
        this.repaint();
    }
}