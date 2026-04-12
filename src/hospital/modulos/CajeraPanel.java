package hospital.modulos;

import hospital.estilos.Estilos;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CajeraPanel extends JPanel {

    private JTextField        campoMontoRecibido;
    private JLabel            lblCambioValor;
    private JComboBox<String> comboMetodoPago;
    private JTable            tablaFacturas;
    private DefaultTableModel modeloTabla;
    private JButton           btnCobrosDelDia;
    private JButton           btnConfirmarCobro;
    private JButton           btnAgregarMedicina;

    public CajeraPanel() {
        setLayout(new BorderLayout());
        setBackground(Estilos.FONDO_APP);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        add(crearFilaTitulo(), BorderLayout.NORTH);

        // panel central con las dos tarjetas y boton agregar medicina
        JPanel centro = new JPanel(new BorderLayout(0, 10));
        centro.setBackground(Estilos.FONDO_APP);
        centro.setBorder(new EmptyBorder(15, 0, 0, 0));

        centro.add(crearFilaPrincipal(),        BorderLayout.CENTER);
        centro.add(crearBotonAgregarMedicina(), BorderLayout.SOUTH);

        add(centro, BorderLayout.CENTER);
    }

    // titulo con boton cobros del dia
    private JPanel crearFilaTitulo() {
        JPanel fila = new JPanel(new BorderLayout());
        fila.setBackground(Estilos.FONDO_APP);

        JLabel titulo = new JLabel("Modulo de Cajera");
        titulo.setFont(Estilos.TITULO);
        titulo.setForeground(Estilos.TEXTO_NORMAL);

        btnCobrosDelDia = EmergenciasPanel.crearBotonOutline("Cobros del Dia");
        btnCobrosDelDia.addActionListener(e -> {
            // mostrar reporte de cobros del dia
        });

        fila.add(titulo,          BorderLayout.WEST);
        fila.add(btnCobrosDelDia, BorderLayout.EAST);
        return fila;
    }

    // dos columnas: cobro de factura y facturas pendientes
    private JPanel crearFilaPrincipal() {
        JPanel fila = new JPanel(new GridLayout(1, 2, 12, 0));
        fila.setBackground(Estilos.FONDO_APP);

        fila.add(crearTarjetaCobro());
        fila.add(crearTarjetaPendientes());
        return fila;
    }

    // tarjeta con resumen de factura y formulario de pago
    private JPanel crearTarjetaCobro() {
        JPanel tarjeta = crearTarjeta("Cobro de Factura");

        JPanel cuerpo = new JPanel();
        cuerpo.setLayout(new BoxLayout(cuerpo, BoxLayout.Y_AXIS));
        cuerpo.setBackground(Estilos.FONDO_BLANCO);
        cuerpo.setBorder(new EmptyBorder(12, 14, 14, 14));

        // banner gris con nombre del paciente y numero de factura
        JPanel banner = new JPanel();
        banner.setLayout(new BoxLayout(banner, BoxLayout.Y_AXIS));
        banner.setBackground(Estilos.FONDO_CAMPO);
        banner.setBorder(new EmptyBorder(8, 10, 8, 10));
        banner.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        JLabel lblNombre  = new JLabel("Cajera - Nombre");
        JLabel lblFactura = new JLabel("Factura #0000 - xx/xx/xxxx");
        lblNombre.setFont(Estilos.NORMAL);
        lblFactura.setFont(Estilos.NORMAL);
        lblNombre.setForeground(Estilos.TEXTO_NORMAL);
        lblFactura.setForeground(Estilos.TEXTO_NORMAL);
        banner.add(lblNombre);
        banner.add(lblFactura);

        cuerpo.add(banner);
        cuerpo.add(Box.createVerticalStrut(10));

        // filas de montos
        cuerpo.add(crearFilaMonto("Subtotal Servicios:", "RD$ 00.00", false));
        cuerpo.add(crearFilaMonto("ITBIS (x%)",          "RD$ 00.00", false));
        cuerpo.add(new JSeparator());
        cuerpo.add(crearFilaMonto("Total a Cobrar:",     "RD$ 00.00", true));
        cuerpo.add(Box.createVerticalStrut(12));

        // combo metodo de pago
        JLabel lblMetodo = new JLabel("METODO DE PAGO");
        lblMetodo.setFont(Estilos.ETIQUETA);
        lblMetodo.setForeground(Estilos.TEXTO_GRIS);
        lblMetodo.setAlignmentX(Component.LEFT_ALIGNMENT);

        String[] metodos = {"Efectivo", "Tarjeta de crédito", "Tarjeta de débito", "Seguro médico"};
        comboMetodoPago = new JComboBox<>(metodos);
        comboMetodoPago.setFont(Estilos.NORMAL);
        comboMetodoPago.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));
        comboMetodoPago.setAlignmentX(Component.LEFT_ALIGNMENT);

        cuerpo.add(lblMetodo);
        cuerpo.add(Box.createVerticalStrut(4));
        cuerpo.add(comboMetodoPago);
        cuerpo.add(Box.createVerticalStrut(10));

        // campo monto recibido
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

        cuerpo.add(lblMontoRec);
        cuerpo.add(Box.createVerticalStrut(4));
        cuerpo.add(campoMontoRecibido);
        cuerpo.add(Box.createVerticalStrut(8));

        // fila de cambio calculado
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

        // boton confirmar cobro mas grueso con setPreferredSize
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
            // marcar factura como pagada y calcular cambio final
        });

        cuerpo.add(btnConfirmarCobro);

        tarjeta.add(cuerpo, BorderLayout.CENTER);
        return tarjeta;
    }

    // tarjeta derecha con tabla de facturas pendientes
    private JPanel crearTarjetaPendientes() {
        JPanel tarjeta = crearTarjeta("Facturas Pendiente de Cobro");

        // columnas de la tabla, la tercera es para los botones cobrar
        String[] columnas = {"Paciente", "Total", ""};

        Object[][] datos = {
            {"Nombre", "RD$ 00.00", "Cobrar"},
            {"Nombre", "RD$ 00.00", "Cobrar"},
        };

        modeloTabla = new DefaultTableModel(datos, columnas) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false; // ninguna celda es editable directamente
            }
        };

        tablaFacturas = new JTable(modeloTabla);
        tablaFacturas.setFont(Estilos.NORMAL);
        tablaFacturas.setRowHeight(40);
        tablaFacturas.setBackground(Estilos.FONDO_BLANCO);
        tablaFacturas.setGridColor(Estilos.BORDE);
        tablaFacturas.setShowHorizontalLines(true);
        tablaFacturas.setShowVerticalLines(false);
        tablaFacturas.getTableHeader().setFont(Estilos.ETIQUETA);
        tablaFacturas.getTableHeader().setBackground(Estilos.FONDO_STAT);

        // renderer para dibujar botones reales en la columna cobrar
        tablaFacturas.getColumnModel().getColumn(2).setCellRenderer(new BotonCobrarRenderer());

        // clic en columna cobrar activa el boton
        tablaFacturas.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int fila    = tablaFacturas.rowAtPoint(e.getPoint());
                int columna = tablaFacturas.columnAtPoint(e.getPoint());
                if (fila >= 0 && columna == 2) {
                    // cargar la factura de esa fila en el panel de cobro
                }
            }
        });

        JScrollPane scroll = new JScrollPane(tablaFacturas);
        scroll.setBorder(null);

        tarjeta.add(scroll, BorderLayout.CENTER);
        return tarjeta;
    }

    // boton agregar medicina 
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
            // abrir dialogo para agregar medicina a la factura
        });
        return btnAgregarMedicina;
    }

    // fila con etiqueta izquierda y monto derecho
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

    // tarjeta blanca con titulo
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

    // renderer que dibuja un jbutton real dentro de la celda de la tabla
    private class BotonCobrarRenderer extends DefaultTableCellRenderer {
        // boton reutilizado para todas las celdas de la columna
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

        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {
            // cambia el fondo del boton si la fila esta seleccionada
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
}