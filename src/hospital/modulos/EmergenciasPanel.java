package hospital.modulos;
import hospital.estilos.Estilos;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EmergenciasPanel extends JPanel {

    // Campos del formulario de registro
    private JTextField  campoNombre;
    private JTextField  campoCedula;
    private JTextArea   campoMotivo;
    private JComboBox<String> comboCamilla;

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
        panelCentro.add(crearFilaEstadisticas());   // 4 tarjetas de numeros
        panelCentro.add(Box.createVerticalStrut(15));
        panelCentro.add(crearFilaPrincipal());       // Camillas + Formulario

        JScrollPane scroll = new JScrollPane(panelCentro);
        scroll.setBorder(null);
        scroll.setBackground(Estilos.FONDO_APP);
        scroll.getVerticalScrollBar().setUnitIncrement(12);
        add(scroll, BorderLayout.CENTER);
    }

    //fila titulo

    // Titulo "Emergencias" y boton "Nuevo Paciente"
    private JPanel crearFilaTitulo() {
        JPanel fila = new JPanel(new BorderLayout());
        fila.setBackground(Estilos.FONDO_APP);

        JLabel titulo = new JLabel("Emergencias");
        titulo.setFont(Estilos.TITULO);
        titulo.setForeground(Estilos.TEXTO_NORMAL);

        btnNuevoPaciente = crearBotonOutline("Nuevo Paciente");
        btnNuevoPaciente.addActionListener(e -> {
            //Limpiar formulario y preparar nuevo registro
        });

        fila.add(titulo,           BorderLayout.WEST);
        fila.add(btnNuevoPaciente, BorderLayout.EAST);
        return fila;
    }

    //filas estadisticas

    // Cuatro tarjetas con contadores en tiempo real.
    private JPanel crearFilaEstadisticas() {
        JPanel fila = new JPanel(new GridLayout(1, 4, 10, 0));
        fila.setBackground(Estilos.FONDO_APP);
        fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 75));

        // Cada tarjeta muestra etiqueta + valor "X" (a conectar con BD)
        fila.add(crearTarjetaStat("Pacientes de Hoy",  "x", Estilos.TEXTO_NORMAL));
        fila.add(crearTarjetaStat("Camillas Libres",   "x", Estilos.VERDE_TEXTO));
        fila.add(crearTarjetaStat("En espera",         "x", Estilos.AMBAR_TEXTO));
        fila.add(crearTarjetaStat("Ocupadas",          "x", Estilos.ROJO_TEXTO));
        return fila;
    }

    // fila principal (Camillas + Formulario) 
    // Dos columnas: Estado de Camillas y Registro Rapido.
    private JPanel crearFilaPrincipal() {
        JPanel fila = new JPanel(new GridLayout(1, 2, 12, 0));
        fila.setBackground(Estilos.FONDO_APP);

        fila.add(crearTarjetaCamillas());
        fila.add(crearTarjetaRegistro());
        return fila;
    }

    //camillas

    // Grilla 4x2 de botones de camilla con colores de estado.
    private JPanel crearTarjetaCamillas() {
        JPanel tarjeta = crearTarjeta("Estado de Camillas");

        JPanel cuerpo = new JPanel();
        cuerpo.setLayout(new BoxLayout(cuerpo, BoxLayout.Y_AXIS));
        cuerpo.setBackground(Estilos.FONDO_BLANCO);
        cuerpo.setBorder(new EmptyBorder(12, 14, 12, 14));

        // Nombres de las 8 camillas
        String[] nombresCamillas = {"C - 01","C - 02","C - 03","C - 04",
                                    "C - 05","C - 06","C - 07","C - 08"};
        // Color de fondo de cada camilla (verde/rojo/ambar)
        Color[] fondos = {
            Estilos.VERDE_FONDO, Estilos.ROJO_FONDO,  Estilos.ROJO_FONDO,  Estilos.AMBAR_FONDO,
            Estilos.ROJO_FONDO,  Estilos.VERDE_FONDO, Estilos.AMBAR_FONDO, Estilos.ROJO_FONDO
        };
        // Color del texto de cada camilla
        Color[] textos = {
            Estilos.VERDE_TEXTO, Estilos.ROJO_TEXTO, Estilos.ROJO_TEXTO, Estilos.AMBAR_TEXTO,
            Estilos.ROJO_TEXTO,  Estilos.VERDE_TEXTO, Estilos.AMBAR_TEXTO, Estilos.ROJO_TEXTO
        };

        // Grilla 4 columnas x 2 filas
        JPanel grilla = new JPanel(new GridLayout(8, 6, 8, 6));
        grilla.setBackground(Estilos.FONDO_BLANCO);

        botonesCamilla = new JButton[8]; // Guardar referencia a cada camilla

        for (int i = 0; i < 8; i++) {
            JButton btn = new JButton(nombresCamillas[i]);
            btn.setFont(Estilos.ETIQUETA);
            btn.setBackground(fondos[i]);
            btn.setForeground(textos[i]);
            btn.setFocusPainted(false);
            btn.setOpaque(true);
            btn.setBorderPainted(false);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.setBorder(BorderFactory.createLineBorder(
                fondos[i].darker(), 1));

            final int indice = i; // necesario para lambda
            // Clic en camilla: ver/asignar paciente
            btn.addActionListener(e -> {
                // Mostrar info del paciente en esa camilla
            });

            botonesCamilla[i] = btn;
            grilla.add(btn);
        }

        // Leyenda de colores
        JPanel leyenda = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        leyenda.setBackground(Estilos.FONDO_BLANCO);
        leyenda.add(crearItemLeyenda("Libre",    Estilos.VERDE_TEXTO));
        leyenda.add(crearItemLeyenda("Ocupado",  Estilos.ROJO_TEXTO));
        leyenda.add(crearItemLeyenda("Limpieza", Estilos.AMBAR_TEXTO));

        cuerpo.add(grilla);
        cuerpo.add(Box.createVerticalStrut(10));
        cuerpo.add(leyenda);

        tarjeta.add(cuerpo, BorderLayout.CENTER);
        return tarjeta;
    }

    //registro rapido

    // Formulario nombre, cedula, motivo y asignacion de camilla.
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

    	    // Campo nombre
    	    campoNombre = new JTextField();
    	    JPanel pNombre = crearCampoFormulario("NOMBRE", campoNombre, false);

    	    gbc.gridy = 0;
    	    cuerpo.add(pNombre, gbc);

    	    // Campo cedula
    	    campoCedula = new JTextField();
    	    campoCedula = new JTextField();
    	    campoCedula.addKeyListener(new java.awt.event.KeyAdapter() {
    	        @Override
    	        public void keyTyped(java.awt.event.KeyEvent e) {
    	            char c = e.getKeyChar();

    	            if (!Character.isDigit(c)) {
    	                e.consume();
    	            }
    	            if (campoCedula.getText().length() >= 11) {
    	                e.consume();
    	            }
    	        }
    	    });
    	    JPanel pCedula = crearCampoFormulario("CEDULA", campoCedula, false);

    	    gbc.gridy = 1;
    	    cuerpo.add(pCedula, gbc);

    	    // Area de texto
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

    	    // Reset para lo demás
    	    gbc.weighty = 0;
    	    gbc.fill = GridBagConstraints.HORIZONTAL;

    	    // Combo camilla
    	    String[] camillaLibres = {
    	        "C - 01 --- Libre", "C - 02 --- Ocupado", "C - 03 --- Ocupado", "C - 04 --- Limpieza",
    	        "C - 05 --- Ocupado", "C - 06 --- Libre", "C - 07 --- Limpieza", "C - 08 --- Ocupado"
    	    };

    	    comboCamilla = new JComboBox<>(camillaLibres);
    	    comboCamilla.setFont(Estilos.NORMAL);
    	    comboCamilla.setBackground(Estilos.FONDO_BLANCO);

    	    JPanel pCamilla = crearPanelEtiqueta("ASIGNAR CAMILLA", comboCamilla);

    	    gbc.gridy = 3;
    	    cuerpo.add(pCamilla, gbc);

    	    // Botones
    	    JPanel filaBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
    	    filaBotones.setBackground(Estilos.FONDO_BLANCO);

    	    btnRegistrar = crearBotonOutline("Registrar y asignar");
    	    btnLimpiar   = crearBotonOutline("Limpiar");

    	    filaBotones.add(btnRegistrar);
    	    filaBotones.add(btnLimpiar);

    	    gbc.gridy = 4;
    	    cuerpo.add(filaBotones, gbc);

    	    tarjeta.add(cuerpo, BorderLayout.CENTER);
    	    return tarjeta;
    	}
    	//utilidades
    
    // Tarjeta blanca con titulo en la parte superior. 
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

    // Tarjeta gris para estadisticas con etiqueta y valor. 
    private JPanel crearTarjetaStat(String etiqueta, String valor, Color colorValor) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Estilos.FONDO_STAT);
        panel.setBorder(new EmptyBorder(10, 14, 10, 14));

        JLabel lblEtiqueta = new JLabel(etiqueta);
        lblEtiqueta.setFont(Estilos.SB_SMALL);
        lblEtiqueta.setForeground(Estilos.TEXTO_GRIS);

        JLabel lblValor = new JLabel(valor);
        lblValor.setFont(Estilos.STAT_VAL);
        lblValor.setForeground(colorValor);

        panel.add(lblEtiqueta);
        panel.add(Box.createVerticalStrut(4));
        panel.add(lblValor);
        return panel;
    }

    // Panel con etiqueta en mayusculas y campo de formulario debajo. 
    private JPanel crearCampoFormulario(String etiqueta, JTextField campo, boolean soloLectura) {
        campo.setFont(Estilos.NORMAL);
        campo.setBackground(soloLectura ? Estilos.FONDO_CAMPO : Estilos.FONDO_CAMPO);
        campo.setEditable(!soloLectura);
        campo.setBorder(new EmptyBorder(6, 8, 6, 8));
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));
        return crearPanelEtiqueta(etiqueta, campo);
    }

    // Envuelve un componente con su etiqueta en mayusculas.
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

    // Item de leyenda con punto de color y texto.
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

    // Boton con borde visible y fondo blanco (estilo outline)
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
}
