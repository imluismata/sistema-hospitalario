package hospital.modulos;

import hospital.estilos.Estilos;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class FacturacionPanel extends JPanel {

    private JButton btnGenerarFactura;
    private JButton btnImprimir;
    private JButton btnNuevaFactura;

    public FacturacionPanel() {
        setLayout(new BorderLayout());
        setBackground(Estilos.FONDO_APP);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        add(crearFilaTitulo(), BorderLayout.NORTH);

        // panel central con la tarjeta y el boton nueva factura
        JPanel centro = new JPanel(new BorderLayout(0, 10));
        centro.setBackground(Estilos.FONDO_APP);
        centro.setBorder(new EmptyBorder(15, 0, 0, 0));

        // tarjeta ocupa todo el espacio central
        centro.add(crearTarjetaFactura(), BorderLayout.CENTER);

        // boton nueva factura 
        centro.add(crearBotonNuevaFactura(), BorderLayout.SOUTH);

        add(centro, BorderLayout.CENTER);
    }

    // titulo del modulo
    private JPanel crearFilaTitulo() {
        JPanel fila = new JPanel(new BorderLayout());
        fila.setBackground(Estilos.FONDO_APP);

        JLabel titulo = new JLabel("Facturacion");
        titulo.setFont(Estilos.TITULO);
        titulo.setForeground(Estilos.TEXTO_NORMAL);
        fila.add(titulo, BorderLayout.WEST);
        return fila;
    }

    // tarjeta principal con info del paciente, costos y botones
    private JPanel crearTarjetaFactura() {
        JPanel tarjeta = new JPanel(new BorderLayout());
        tarjeta.setBackground(Estilos.FONDO_BLANCO);
        tarjeta.setBorder(BorderFactory.createLineBorder(Estilos.BORDE, 1));

        JPanel cuerpo = new JPanel();
        cuerpo.setLayout(new BoxLayout(cuerpo, BoxLayout.Y_AXIS));
        cuerpo.setBackground(Estilos.FONDO_BLANCO);
        cuerpo.setBorder(new EmptyBorder(14, 18, 14, 18));

        // banner gris con info del paciente
        JPanel bannerPaciente = new JPanel();
        bannerPaciente.setLayout(new BoxLayout(bannerPaciente, BoxLayout.Y_AXIS));
        bannerPaciente.setBackground(Estilos.FONDO_CAMPO);
        bannerPaciente.setBorder(new EmptyBorder(10, 12, 10, 12));
        bannerPaciente.setMaximumSize(new Dimension(Integer.MAX_VALUE, 58));

        JLabel lblLinea1 = new JLabel("Cajera - Nombre - 111-222-3333");
        JLabel lblLinea2 = new JLabel("Especialidad - Hab. 00 - Ingreso: xx/xx/xxxx");
        lblLinea1.setFont(Estilos.NORMAL);
        lblLinea2.setFont(Estilos.NORMAL);
        lblLinea1.setForeground(Estilos.TEXTO_NORMAL);
        lblLinea2.setForeground(Estilos.TEXTO_NORMAL);
        bannerPaciente.add(lblLinea1);
        bannerPaciente.add(lblLinea2);

        // etiqueta de seccion
        JLabel lblDesgloso = new JLabel("DESGLOSO DE COSTOS");
        lblDesgloso.setFont(Estilos.ETIQUETA);
        lblDesgloso.setForeground(Estilos.TEXTO_GRIS);
        lblDesgloso.setBorder(new EmptyBorder(12, 0, 6, 0));
        lblDesgloso.setAlignmentX(Component.LEFT_ALIGNMENT);

        // filas de costos individuales
        JPanel filaCostos = new JPanel();
        filaCostos.setLayout(new BoxLayout(filaCostos, BoxLayout.Y_AXIS));
        filaCostos.setBackground(Estilos.FONDO_BLANCO);
        filaCostos.setAlignmentX(Component.LEFT_ALIGNMENT);
        filaCostos.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));

        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));

        filaCostos.add(crearFilaCosto("Atención de emergencia", "RD$ 00.00", false));
        filaCostos.add(crearFilaCosto("Hospitalizacion",        "RD$ 00.00", false));
        filaCostos.add(crearFilaCosto("Servicio X",             "RD$ 00.00", false));
        filaCostos.add(crearFilaCosto("Servicio X",             "RD$ 00.00", false));
        filaCostos.add(sep);
        filaCostos.add(crearFilaCosto("Total",                  "RD$ 00.00", true));

        // fila de botones generar + imprimir
        JPanel filaBotones = new JPanel(new GridLayout(1, 2, 10, 0));
        filaBotones.setBackground(Estilos.FONDO_BLANCO);
        filaBotones.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60)); 
        filaBotones.setAlignmentX(Component.LEFT_ALIGNMENT);

        btnGenerarFactura = crearBotonGrande("Generar factura");
        btnGenerarFactura.addActionListener(e -> {
            // generar y guardar la factura en la bd
        });

        btnImprimir = crearBotonGrande("Imprimir");
        btnImprimir.addActionListener(e -> {
            // enviar factura a impresora o exportar pdf
        });

        filaBotones.add(btnGenerarFactura);
        filaBotones.add(btnImprimir);

        cuerpo.add(bannerPaciente);
        cuerpo.add(lblDesgloso);
        cuerpo.add(filaCostos);

        // espacio flexible para empujar los botones hacia abajo
        cuerpo.add(Box.createVerticalGlue());

        cuerpo.add(filaBotones);

        tarjeta.add(cuerpo, BorderLayout.CENTER);
        return tarjeta;
    }

    // boton nueva factura alineado al ancho de la tarjeta
    private JButton crearBotonNuevaFactura() {
        btnNuevaFactura = crearBotonGrande("Nueva Factura");
        btnNuevaFactura.setPreferredSize(new Dimension(0, 60)); // más grueso
        btnNuevaFactura.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        btnNuevaFactura.addActionListener(e -> {
            // limpiar formulario para nueva factura
        });
        return btnNuevaFactura;
    }

    // fila con concepto a la izquierda y monto a la derecha
    private JPanel crearFilaCosto(String concepto, String monto, boolean esTotal) {
        JPanel fila = new JPanel(new BorderLayout());
        fila.setBackground(Estilos.FONDO_BLANCO);
        fila.setBorder(new EmptyBorder(5, 0, 5, 0));
        fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        fila.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblConcepto = new JLabel(concepto);
        JLabel lblMonto    = new JLabel(monto);

        Font fuente = esTotal ? Estilos.SUBTITULO : Estilos.NORMAL;
        lblConcepto.setFont(fuente);
        lblMonto.setFont(fuente);
        lblConcepto.setForeground(Estilos.TEXTO_NORMAL);
        lblMonto.setForeground(esTotal ? Estilos.TOTAL_AZUL : Estilos.TEXTO_NORMAL);

        fila.add(lblConcepto, BorderLayout.WEST);
        fila.add(lblMonto,    BorderLayout.EAST);
        return fila;
    }

    // boton grande 
    private JButton crearBotonGrande(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(Estilos.SUBTITULO);
        btn.setBackground(Estilos.FONDO_BLANCO);
        btn.setForeground(Estilos.TEXTO_NORMAL);
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setBorder(BorderFactory.createLineBorder(Estilos.BORDE, 0));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(0, 60));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        return btn;
    }
}