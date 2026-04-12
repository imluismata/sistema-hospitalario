package hospital.principal;

import hospital.estilos.Estilos;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


 //Barra lateral de navegacion.
 //Cambia el modulo visible usando CardLayout.
 
public class Sidebar extends JPanel {

    // CardLayout para cambiar entre modulos
    private final CardLayout cardLayout;
    private final JPanel     panelContenido;

    // Labels de usuario (se actualizan al cambiar modulo)
    private final JLabel lblRol;
    private final JLabel lblNombre;

    // Item del menu actualmente seleccionado
    private JPanel itemActivo;

    private static final Color COLOR_HOVER  = new Color(50,  80, 105); // opaco, ligeramente más claro
    private static final Color COLOR_ACTIVO = new Color(42,  95, 143); // opaco, azul activo

    public Sidebar(CardLayout cardLayout, JPanel panelContenido) {
        this.cardLayout     = cardLayout;
        this.panelContenido = panelContenido;

        setPreferredSize(new Dimension(230, 0));
        setBackground(Estilos.SIDEBAR_FONDO);
        setLayout(new BorderLayout());
        setOpaque(true); // El sidebar mismo debe ser opaco

        // Panel superior: logo + info de usuario
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS));
        panelSuperior.setBackground(Estilos.SIDEBAR_FONDO);
        panelSuperior.setOpaque(true); // Opaco para que no filtre nada

        panelSuperior.add(crearPanelLogo());
        panelSuperior.add(crearSeparador());

     JPanel panelUsuario = new JPanel(new GridLayout(2, 1, 0, 2));
     panelUsuario.setBackground(Estilos.SIDEBAR_FONDO);
     panelUsuario.setOpaque(true);
     panelUsuario.setBorder(new EmptyBorder(10, 15, 10, 15));

     lblRol    = new JLabel("Enfermeria / Admision");
     lblNombre = new JLabel("Nombre");
     lblRol.setFont(Estilos.SB_SMALL);
     lblNombre.setFont(Estilos.SB_NORMAL);
     lblRol.setForeground(Estilos.SIDEBAR_SUBTXT);
     lblNombre.setForeground(Estilos.SIDEBAR_TEXTO);
     lblRol.setHorizontalAlignment(SwingConstants.LEFT);
     lblNombre.setHorizontalAlignment(SwingConstants.LEFT);

     panelUsuario.add(lblRol);
     panelUsuario.add(lblNombre);

        panelSuperior.add(panelUsuario);
        panelSuperior.add(crearSeparador());

        // Panel de navegacion con los items del menu
        JPanel panelNav = new JPanel();
        panelNav.setLayout(new BoxLayout(panelNav, BoxLayout.Y_AXIS));
        panelNav.setBackground(Estilos.SIDEBAR_FONDO);
        panelNav.setOpaque(true); //opaco para evitar filtrado
        panelNav.setBorder(new EmptyBorder(8, 0, 8, 0));

        // Crear cada item del menu con su pantalla y datos del usuario
        JPanel itemEmergencias     = crearItemMenu("Emergencias",       "emergencias",
                "Enfermeria / Admision", "Nombre");
        JPanel itemHospitalizacion = crearItemMenu("Hospitalizacion",   "hospitalizacion",
                "Medico / Admision",     "Dr. Nombre");
        JPanel itemEvaluacion      = crearItemMenu("Evaluacion Medica", "evaluacion",
                "Medico",                "Dr. Nombre");
        JPanel itemFacturacion     = crearItemMenu("Facturación",       "facturacion",
                "Financiero",            "Nombre");
        JPanel itemCajera          = crearItemMenu("Cajera",            "cajera",
                "Cajera",                "Cajera Nombre");
        JPanel itemAdmin           = crearItemMenu("Administración",    "admin",
                "Administrador",         "Admin. Nombre");

        panelNav.add(itemEmergencias);
        panelNav.add(itemHospitalizacion);
        panelNav.add(itemEvaluacion);
        panelNav.add(itemFacturacion);
        panelNav.add(itemCajera);
        panelNav.add(itemAdmin);

        // El primer item empieza activo
        activarItem(itemEmergencias);

        add(panelSuperior, BorderLayout.NORTH);
        add(panelNav,      BorderLayout.CENTER);
    }

    // panel logo

    // Logo del hospital y texto "LPA Hospital" en la parte superior
    private JPanel crearPanelLogo() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 14));
        panel.setBackground(Estilos.SIDEBAR_FONDO);
        panel.setOpaque(true);

        ImageIcon iconoOriginal = new ImageIcon("recursos/logo_jpg.jpeg");
        Image iconoEscalado = iconoOriginal.getImage()
                .getScaledInstance(28, 28, Image.SCALE_SMOOTH);
        JLabel lblIcono  = new JLabel(new ImageIcon(iconoEscalado));

        JLabel lblTitulo = new JLabel("LPA Hospital");
        lblTitulo.setFont(Estilos.SB_TITULO);
        lblTitulo.setForeground(Color.WHITE);

        panel.add(lblIcono);
        panel.add(lblTitulo);
        return panel;
    }



    // Linea separadora fina entre secciones del sidebar. 
    private JPanel crearSeparador() {
        JPanel sep = new JPanel();
        sep.setBackground(Estilos.SIDEBAR_FONDO);
        sep.setOpaque(true);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep.setBorder(new MatteBorder(1, 0, 0, 0, new Color(80, 110, 135)));
        return sep;
    }

    // item del menu
    
     //Crea un item del menu lateral.
     //Al hacer clic cambia el modulo visible.
     
    private JPanel crearItemMenu(String texto, String pantalla, String rol,   String nombre) {
        JPanel item = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        item.setBackground(Estilos.SIDEBAR_FONDO);
        item.setOpaque(true);       
        item.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        item.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Punto decorativo del item
        JLabel punto = new JLabel("•");
        punto.setFont(new Font("SansSerif", Font.BOLD, 14));
        punto.setForeground(Estilos.SIDEBAR_SUBTXT);

        // Texto visible del item
        JLabel lblTexto = new JLabel(texto);
        lblTexto.setFont(Estilos.SB_NORMAL);
        lblTexto.setForeground(Estilos.SIDEBAR_SUBTXT);

        item.add(punto);
        item.add(lblTexto);

        // MouseListener: hover y clic con colores opacos
        item.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(panelContenido, pantalla); // Cambiar modulo
                actualizarUsuario(rol, nombre);            // Actualizar info
                activarItem(item);                         // Resaltar item
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // Solo aplicar hover si este item NO esta activo
                if (item != itemActivo) {
                    item.setBackground(COLOR_HOVER);
                    item.repaint(); // Forzar repintado inmediato
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Restaurar fondo solo si no es el activo
                if (item != itemActivo) {
                    item.setBackground(Estilos.SIDEBAR_FONDO);
                    item.repaint();
                }
            }
        });

        return item;
    }

    //activar item

    // Resalta el item seleccionado y quita el estilo del anterior
    private void activarItem(JPanel nuevoActivo) {
        // Restaurar el item anterior a su estado normal
        if (itemActivo != null) {
            itemActivo.setBackground(Estilos.SIDEBAR_FONDO);
            itemActivo.setBorder(null);
            for (Component c : itemActivo.getComponents()) {
                if (c instanceof JLabel) {
                    c.setForeground(Estilos.SIDEBAR_SUBTXT);
                }
            }
            itemActivo.repaint();
        }

        // Aplicar estilo al nuevo item activo
        nuevoActivo.setBackground(COLOR_ACTIVO);
        // Borde izquierdo azul claro como indicador visual
        nuevoActivo.setBorder(new MatteBorder(0, 3, 0, 0, new Color(100, 180, 230)));
        for (Component c : nuevoActivo.getComponents()) {
            if (c instanceof JLabel) {
                c.setForeground(Color.WHITE);
            }
        }
        nuevoActivo.repaint();

        itemActivo = nuevoActivo;
    }

    // actualizar usuario

    //Cambia el rol y nombre mostrado en el sidebar
    private void actualizarUsuario(String rol, String nombre) {
        lblRol.setText(rol);
        lblNombre.setText(nombre);
    }
}

