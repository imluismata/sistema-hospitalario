package com.hospital.vista.modulos;

import com.hospital.vista.estilos.Estilos;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

// POO: Herencia - CajeraPanel extiende JPanel.
public class CajeraPanel extends JPanel {

    // POO: Encapsulacion - estado interno del panel.
    private JTextField        campoMontoRecibido;
    private JLabel            lblCambioValor;
    private JComboBox<String> comboMetodoPago;
    private JTable            tablaFacturas;
    private DefaultTableModel modeloTabla;
    private JButton           btnCobrosDelDia;
    private JButton           btnConfirmarCobro;
    private JButton           btnAgregarMedicina;
    private com.hospital.dao.FacturacionDAOImpl facturacionDAO = new com.hospital.dao.FacturacionDAOImpl();
    private com.hospital.servicio.PdfService pdfService = new com.hospital.servicio.PdfService();
    private JLabel lblNombreFactura;
    private JLabel lblSubtotalValor;
    private JLabel lblItbisValor;
    private JLabel lblTotalValor;
    private String cedulaPacienteCobrando = "";
    private String seguroPacienteCobrando = "";
    private double coberturaARS = 0.0;
    private double subtotalOriginal = 0.0; // Lo que viene de la BD
    private double subtotalMedicinas = 0.0; //  extra
    private String diasFacturados = "1";
    private java.util.List<String[]> listaMedicinas = new java.util.ArrayList<>();

    public CajeraPanel() {
        setLayout(new BorderLayout());
        setBackground(Estilos.FONDO_APP);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        add(crearFilaTitulo(), BorderLayout.NORTH);

        JPanel centro = new JPanel(new BorderLayout(0, 10));
        centro.setBackground(Estilos.FONDO_APP);
        centro.setBorder(new EmptyBorder(15, 0, 0, 0));

        centro.add(crearFilaPrincipal(),        BorderLayout.CENTER);
        centro.add(crearBotonAgregarMedicina(), BorderLayout.SOUTH);

        add(centro, BorderLayout.CENTER);

        //detecta cuando la pantalla se hace visible
        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                //  pacientes nuevos a la base de datos
                actualizarTablaPendientes();
                limpiarPantallaCobro(); // Limpiamos
            }
        });
    }

    private JPanel crearFilaTitulo() {
        JPanel fila = new JPanel(new BorderLayout());
        fila.setBackground(Estilos.FONDO_APP);

        JLabel titulo = new JLabel("Modulo de Cajera");
        titulo.setFont(Estilos.TITULO);
        titulo.setForeground(Estilos.TEXTO_NORMAL);

       btnCobrosDelDia = EmergenciasPanel.crearBotonOutline("Cobros del Dia");
        btnCobrosDelDia.setVisible(false);
        btnCobrosDelDia.addActionListener(e -> {
            // mostrar reporte de cobros del dia
        });

        fila.add(titulo,          BorderLayout.WEST);
        fila.add(btnCobrosDelDia, BorderLayout.EAST);
        return fila;
    }

    private JPanel crearFilaPrincipal() {
        JPanel fila = new JPanel(new GridLayout(1, 2, 12, 0));
        fila.setBackground(Estilos.FONDO_APP);

        fila.add(crearTarjetaCobro());
        fila.add(crearTarjetaPendientes());
        return fila;
    }

    private JPanel crearTarjetaCobro() {
        JPanel tarjeta = crearTarjeta("Cobro de Factura");

        JPanel cuerpo = new JPanel();
        cuerpo.setLayout(new BoxLayout(cuerpo, BoxLayout.Y_AXIS));
        cuerpo.setBackground(Estilos.FONDO_BLANCO);
        cuerpo.setBorder(new EmptyBorder(12, 14, 14, 14));

        JPanel banner = new JPanel();
        banner.setLayout(new BoxLayout(banner, BoxLayout.Y_AXIS));
        banner.setBackground(Estilos.FONDO_CAMPO);
        banner.setBorder(new EmptyBorder(8, 10, 8, 10));
        banner.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        lblNombreFactura = new JLabel("Seleccione una factura...");
        JLabel lblFactura = new JLabel("Factura Pendiente");
        lblNombreFactura.setFont(Estilos.NORMAL);
        lblFactura.setFont(Estilos.NORMAL);
        lblNombreFactura.setForeground(Estilos.TEXTO_NORMAL);
        lblFactura.setForeground(Estilos.TEXTO_NORMAL);
        banner.add(lblNombreFactura);
        banner.add(lblFactura);

        cuerpo.add(banner);
        cuerpo.add(Box.createVerticalStrut(10));

        JPanel filaSub = crearFilaMonto("Subtotal Servicios:", "RD$ 00.00", false);
        lblSubtotalValor = (JLabel) filaSub.getComponent(1);
        cuerpo.add(filaSub);

        JPanel filaItbis = crearFilaMonto("ITBIS (18%)", "RD$ 00.00", false);
        lblItbisValor = (JLabel) filaItbis.getComponent(1);
        cuerpo.add(filaItbis);

        cuerpo.add(new JSeparator());

        JPanel filaTotal = crearFilaMonto("Total a Cobrar:", "RD$ 00.00", true);
        lblTotalValor = (JLabel) filaTotal.getComponent(1);
        cuerpo.add(filaTotal);

        JLabel lblMetodo = new JLabel("METODO DE PAGO");
        lblMetodo.setFont(Estilos.ETIQUETA);
        lblMetodo.setForeground(Estilos.TEXTO_GRIS);
        lblMetodo.setAlignmentX(Component.LEFT_ALIGNMENT);

        String[] metodos = {"Efectivo", "Tarjeta de crédito", "Tarjeta de débito"};
        comboMetodoPago = new JComboBox<>(metodos);
        comboMetodoPago.setFont(Estilos.NORMAL);
        comboMetodoPago.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));
        comboMetodoPago.setAlignmentX(Component.LEFT_ALIGNMENT);

        comboMetodoPago.addActionListener(e -> {
            String metodoSel = (String) comboMetodoPago.getSelectedItem();
            if (metodoSel.equals("Efectivo")) {
                campoMontoRecibido.setEditable(true);
                campoMontoRecibido.setBackground(Estilos.FONDO_BLANCO);
                campoMontoRecibido.setText("00.00");
            } else {
                campoMontoRecibido.setEditable(false);
                campoMontoRecibido.setBackground(Estilos.FONDO_APP);
                String totalTxt = lblTotalValor.getText().replace("RD$ ", "").replace(",", "");
                if (!totalTxt.equals("00.00")) {
                    campoMontoRecibido.setText(totalTxt);
                }
            }
        });

        cuerpo.add(lblMetodo);
        cuerpo.add(Box.createVerticalStrut(4));
        cuerpo.add(comboMetodoPago);
        cuerpo.add(Box.createVerticalStrut(10));

        JLabel lblMontoRec = new JLabel("MONTO RECIBIDO");
        lblMontoRec.setFont(Estilos.ETIQUETA);
        lblMontoRec.setForeground(Estilos.TEXTO_GRIS);
        lblMontoRec.setAlignmentX(Component.LEFT_ALIGNMENT);

        campoMontoRecibido = new JTextField("00.00");
        campoMontoRecibido.setFont(Estilos.NORMAL);
        campoMontoRecibido.setBackground(Estilos.FONDO_BLANCO);
        campoMontoRecibido.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Estilos.BORDE, 1),
                new EmptyBorder(6, 8, 6, 8)
        ));
        campoMontoRecibido.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        campoMontoRecibido.setAlignmentX(Component.LEFT_ALIGNMENT);

        campoMontoRecibido.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { calcularCambioEnVivo(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { calcularCambioEnVivo(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { calcularCambioEnVivo(); }
        });

        cuerpo.add(lblMontoRec);
        cuerpo.add(Box.createVerticalStrut(4));
        cuerpo.add(campoMontoRecibido);
        cuerpo.add(Box.createVerticalStrut(8));

        JPanel filaCambio = new JPanel(new BorderLayout());
        filaCambio.setBackground(Estilos.FONDO_BLANCO);
        filaCambio.setMaximumSize(new Dimension(Integer.MAX_VALUE, 26));
        filaCambio.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblCambioEtiqueta = new JLabel("Cambio");
        lblCambioEtiqueta.setFont(Estilos.NORMAL);
        lblCambioEtiqueta.setForeground(Estilos.TEXTO_GRIS);

        lblCambioValor = new JLabel("RD$ 00.00");
        lblCambioValor.setFont(Estilos.SUBTITULO);
        lblCambioValor.setForeground(Estilos.CAMBIO_VERDE);

        filaCambio.add(lblCambioEtiqueta, BorderLayout.WEST);
        filaCambio.add(lblCambioValor,    BorderLayout.EAST);

        cuerpo.add(filaCambio);
        cuerpo.add(Box.createVerticalStrut(12));

        btnConfirmarCobro = new JButton("Confirmar Cobro");
        btnConfirmarCobro.setFont(Estilos.SUBTITULO);
        btnConfirmarCobro.setBackground(Estilos.FONDO_BLANCO);
        btnConfirmarCobro.setFocusPainted(false);
        btnConfirmarCobro.setOpaque(true);
        btnConfirmarCobro.setBorder(BorderFactory.createLineBorder(Estilos.BORDE, 1));
        btnConfirmarCobro.setMaximumSize(new Dimension(Integer.MAX_VALUE, 52));
        btnConfirmarCobro.setPreferredSize(new Dimension(0, 52));
        btnConfirmarCobro.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnConfirmarCobro.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnConfirmarCobro.addActionListener(e -> {
            if (cedulaPacienteCobrando.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Seleccione un paciente primero.");
                return;
            }

            String metodoFinal = comboMetodoPago.getSelectedItem().toString();
            if (metodoFinal.equals("Efectivo") && lblCambioValor.getText().contains("Faltan")) {
                JOptionPane.showMessageDialog(this, "Monto insuficiente.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String nombrePaciente = lblNombreFactura.getText().split("\\|")[0].replace("Paciente: ", "").trim();
            String itbis = lblItbisValor.getText();
            String total = lblTotalValor.getText();


            List<String[]> itemsFactura = new java.util.ArrayList<>();

            String descServicio = diasFacturados.equals("0") ? "Consulta de Emergencia" : "Habitacion (" + diasFacturados + " dias) + Emer.";            itemsFactura.add(new String[]{descServicio, String.format("RD$ %.2f", subtotalOriginal)});

            // Agregamos todas las medicinas sueltas
            for (String[] med : listaMedicinas) {
                itemsFactura.add(new String[]{med[0], String.format("RD$ %.2f", Double.parseDouble(med[1]))});
            }

            //restamos la cobertura ARS si existe (20% del total)
            if (coberturaARS > 0) {
                itemsFactura.add(new String[]{"Cobertura " + seguroPacienteCobrando + " (20%)", String.format("-RD$ %.2f", coberturaARS)});
            }

            String rutaLogo  = "/Users/luismata/sistemahospitalario/src/main/resources/logo_jpg.jpeg";
            String rutaSello = "/Users/luismata/sistemahospitalario/src/main/resources/Sello_PNG_LPA.png";
            String rutaEscritorio = System.getProperty("user.home") + "/Desktop/Facturas_LPA";

            java.io.File carpeta = new java.io.File(rutaEscritorio);

            // Si la carpeta no existe, la creamos de una vez
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }

            // Definimos la ruta final del archivo dentro de esa carpeta
            String rutaSalida = rutaEscritorio + "/Factura_" + cedulaPacienteCobrando + ".pdf";


            boolean cobrado = facturacionDAO.cobrarFactura(cedulaPacienteCobrando);

            if (cobrado) {
                pdfService.generarFacturaPdf(
                        nombrePaciente,
                        cedulaPacienteCobrando,
                        itemsFactura,
                        itbis,
                        total,
                        metodoFinal,
                        rutaLogo,
                        rutaSello,
                        rutaSalida
                );

                JOptionPane.showMessageDialog(this, "¡Cobro Exitoso!\nFactura guardada en tu Escritorio: Facturas_LPA");

                limpiarPantallaCobro();
                actualizarTablaPendientes();
            }
        });

        cuerpo.add(btnConfirmarCobro);

        tarjeta.add(cuerpo, BorderLayout.CENTER);
        return tarjeta;
    }

    private JPanel crearTarjetaPendientes() {
        JPanel tarjeta = crearTarjeta("Facturas Pendiente de Cobro");

        // Añadimos la columna "Seguro" oculta
        String[] columnas = {"Cédula", "Paciente", "Total", "Dias", "Seguro", ""};
        modeloTabla = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };

        List<String[]> pendientes = facturacionDAO.obtenerFacturasPendientes();
        for (String[] p : pendientes) {
            modeloTabla.addRow(new Object[]{p[0], p[1], "RD$ " + p[2], p[3], p[4], "Cobrar"});
        }

        tablaFacturas = new JTable(modeloTabla);
        tablaFacturas.setFont(Estilos.NORMAL);
        tablaFacturas.setRowHeight(40);
        tablaFacturas.setBackground(Estilos.FONDO_BLANCO);
        tablaFacturas.setGridColor(Estilos.BORDE);
        tablaFacturas.setShowHorizontalLines(true);
        tablaFacturas.setShowVerticalLines(false);
        tablaFacturas.getTableHeader().setFont(Estilos.ETIQUETA);
        tablaFacturas.getTableHeader().setBackground(Estilos.FONDO_STAT);

        // Esconder columnas que no queremos que se vean, solo que guarden data
        tablaFacturas.getColumnModel().getColumn(0).setMinWidth(0); tablaFacturas.getColumnModel().getColumn(0).setMaxWidth(0); // Cedula
        tablaFacturas.getColumnModel().getColumn(3).setMinWidth(0); tablaFacturas.getColumnModel().getColumn(3).setMaxWidth(0); // Dias
        tablaFacturas.getColumnModel().getColumn(4).setMinWidth(0); tablaFacturas.getColumnModel().getColumn(4).setMaxWidth(0); // Seguro

        // El botón ahora está en la columna 5
        tablaFacturas.getColumnModel().getColumn(5).setCellRenderer(new BotonCobrarRenderer());

        tablaFacturas.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int fila    = tablaFacturas.rowAtPoint(e.getPoint());
                int columna = tablaFacturas.columnAtPoint(e.getPoint());
                if (fila >= 0 && columna == 5) {
                    cedulaPacienteCobrando = (String) tablaFacturas.getValueAt(fila, 0);
                    String nombre = (String) tablaFacturas.getValueAt(fila, 1);
                    String totalString = (String) tablaFacturas.getValueAt(fila, 2);
                    diasFacturados = (String) tablaFacturas.getValueAt(fila, 3);
                    seguroPacienteCobrando = (String) tablaFacturas.getValueAt(fila, 4);

                    if (seguroPacienteCobrando == null || seguroPacienteCobrando.trim().isEmpty()) {
                        seguroPacienteCobrando = "Privado (Sin Seguro)";
                    }

                    listaMedicinas.clear();
                    subtotalMedicinas = 0.0;

                    // Extraemos el monto original de la BD
                    subtotalOriginal = Double.parseDouble(totalString.replace("RD$ ", "").replace(",", ""));

                    lblNombreFactura.setText("Paciente: " + nombre + " | Cédula: " + cedulaPacienteCobrando);


                    recalcularTotales();

                    if(coberturaARS > 0) {
                        JOptionPane.showMessageDialog(null, "Paciente con " + seguroPacienteCobrando + "\nSe aplicará 20% de descuento a toda la factura.");
                    }
                }
            }
        });

        JScrollPane scroll = new JScrollPane(tablaFacturas);
        scroll.setBorder(null);
        tarjeta.add(scroll, BorderLayout.CENTER);
        return tarjeta;
    }

    private JButton crearBotonAgregarMedicina() {
        btnAgregarMedicina = new JButton("Agregar Medicina");
        btnAgregarMedicina.setFont(Estilos.SUBTITULO);
        btnAgregarMedicina.setBackground(Estilos.FONDO_BLANCO);
        btnAgregarMedicina.setFocusPainted(false);
        btnAgregarMedicina.setOpaque(true);
        btnAgregarMedicina.setBorder(BorderFactory.createLineBorder(Estilos.BORDE, 1));
        btnAgregarMedicina.setPreferredSize(new Dimension(0, 48));
        btnAgregarMedicina.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnAgregarMedicina.addActionListener(e -> {
            if (cedulaPacienteCobrando.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Seleccione un paciente de la lista derecha primero.");
                return;
            }

            Window parent = SwingUtilities.getWindowAncestor(this);
            DialogoMedicinas dialogo = new DialogoMedicinas(parent);
            dialogo.setVisible(true);

            if (dialogo.isConfirmado()) {
                listaMedicinas.add(new String[]{dialogo.getNombre(), String.valueOf(dialogo.getPrecio())});
                subtotalMedicinas += dialogo.getPrecio();

                recalcularTotales();

                JOptionPane.showMessageDialog(this, "Insumo agregado a la cuenta.");
            }
        });
        return btnAgregarMedicina;
    }

    private JPanel crearFilaMonto(String etiqueta, String monto, boolean esTotal) {
        JPanel fila = new JPanel(new BorderLayout());
        fila.setBackground(Estilos.FONDO_BLANCO);
        fila.setBorder(new EmptyBorder(4, 0, 4, 0));
        fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
        fila.setAlignmentX(Component.LEFT_ALIGNMENT);

        Font fuente = esTotal ? Estilos.SUBTITULO : Estilos.NORMAL;
        JLabel lblEtiqueta = new JLabel(etiqueta);
        JLabel lblMonto    = new JLabel(monto);
        lblEtiqueta.setFont(fuente);
        lblMonto.setFont(fuente);
        lblEtiqueta.setForeground(Estilos.TEXTO_NORMAL);
        lblMonto.setForeground(esTotal ? Estilos.TOTAL_AZUL : Estilos.TEXTO_NORMAL);

        fila.add(lblEtiqueta, BorderLayout.WEST);
        fila.add(lblMonto,    BorderLayout.EAST);
        return fila;
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

    private class BotonCobrarRenderer extends DefaultTableCellRenderer {
        private final JButton boton = new JButton("Cobrar");

        public BotonCobrarRenderer() {
            boton.setFont(Estilos.NORMAL);
            boton.setBackground(Estilos.FONDO_BLANCO);
            boton.setFocusPainted(false);
            boton.setOpaque(true);
            boton.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Estilos.BORDE, 1),
                    new EmptyBorder(2, 10, 2, 10)
            ));
        }

        // POO: Polimorfismo - redefinicion del renderizado de celdas.
        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {
            if (isSelected) {
                boton.setBackground(table.getSelectionBackground());
                boton.setForeground(table.getSelectionForeground());
            } else {
                boton.setBackground(Estilos.FONDO_BLANCO);
                boton.setForeground(Estilos.TEXTO_NORMAL);
            }
            return boton;
        }
    }

    //  cambio mientras escriben
    private void calcularCambioEnVivo() {
        try {
            String totalTxt = lblTotalValor.getText().replace("RD$ ", "").replace(",", "");
            if (totalTxt.equals("00.00")) return; // Si no hay factura, no hace nada

            double total = Double.parseDouble(totalTxt);
            double recibido = Double.parseDouble(campoMontoRecibido.getText());
            double cambio = recibido - total;

            if (cambio >= 0) {
                lblCambioValor.setText(String.format("RD$ %.2f", cambio));
                lblCambioValor.setForeground(Estilos.CAMBIO_VERDE); // Verde si hay que devolver
            } else {
                lblCambioValor.setText(String.format("Faltan RD$ %.2f", Math.abs(cambio)));
                lblCambioValor.setForeground(Estilos.ROJO_TEXTO); // Rojo si el cliente dio menos cuartos
            }
        } catch (NumberFormatException ex) {
            lblCambioValor.setText("RD$ 00.00");
            lblCambioValor.setForeground(Estilos.TEXTO_GRIS);
        }
    }

    // Método para limpiar los textos después de cobrar
    private void limpiarPantallaCobro() {
        cedulaPacienteCobrando = "";
        lblNombreFactura.setText("Seleccione una factura...");
        lblSubtotalValor.setText("RD$ 00.00");
        lblItbisValor.setText("RD$ 00.00");
        lblTotalValor.setText("RD$ 00.00");
        campoMontoRecibido.setText("00.00");
        lblCambioValor.setText("RD$ 00.00");
        lblCambioValor.setForeground(Estilos.CAMBIO_VERDE);
    }

    // Método para recargar la tabla al entrar a la pestaña
    private void recalcularTotales() {
        if (cedulaPacienteCobrando.isEmpty()) return;

        // Sumamos todo Base + Extras de la cajera
        double subtotalBruto = subtotalOriginal + subtotalMedicinas;
        coberturaARS = 0.0;

        // Le sacamos el 20% de descuento al TOTAL COMPLETO si tiene ARS
        if (seguroPacienteCobrando != null && seguroPacienteCobrando.contains("ARS")) {
            coberturaARS = subtotalBruto * 0.20;
        }

        // Calculamos lo que realmente va a pagar y su ITBIS
        double subtotalNeto = subtotalBruto - coberturaARS;
        double itbis = subtotalNeto * 0.18;
        double totalFinal = subtotalNeto + itbis;

        //  Pintamos en pantalla
        lblSubtotalValor.setText(String.format("RD$ %.2f", subtotalBruto)); // Mostramos lo que cuesta realmente
        lblItbisValor.setText(String.format("RD$ %.2f", itbis));
        lblTotalValor.setText(String.format("RD$ %.2f", totalFinal));

        calcularCambioEnVivo(); // Si ya habían puesto dinero, recalcula el vuelto


    }

    // Método para recargar la tabla al entrar a la pestaña
    private void actualizarTablaPendientes() {
        modeloTabla.setRowCount(0); // Borra las filas actuales
        List<String[]> pendientes = facturacionDAO.obtenerFacturasPendientes();

        for (String[] p : pendientes) {
            modeloTabla.addRow(new Object[]{p[0], p[1], "RD$ " + p[2], p[3], p[4], "Cobrar"});
        }
    }

}