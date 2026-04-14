package com.hospital.vista.modulos;
import com.hospital.modelo.*;
import com.hospital.servicio.IUsuarioServicio;
import com.hospital.servicio.UsuarioServicioImpl;
import com.hospital.vista.estilos.Estilos;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AdminPanel extends JPanel {

    // Tabla de usuarios del sistema
    private JTable          tablaUsuarios;
    private DefaultTableModel modeloUsuarios;
    // Agrega esta línea junto a las demás variables de la clase
    private IUsuarioServicio servicio = new UsuarioServicioImpl();

    // Botones
    private JButton btnNuevoUsuario;
    private JButton btnAnadir;
    private JButton btnPacientesAtendidos;

    public AdminPanel() {
        setLayout(new BorderLayout());
        setBackground(Estilos.FONDO_APP);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        add(crearFilaTitulo(), BorderLayout.NORTH);

        // cambio a borderlayout para que ocupe todo el espacio
        JPanel centro = new JPanel(new BorderLayout());
        centro.setBackground(Estilos.FONDO_APP);
        centro.setBorder(new EmptyBorder(15, 0, 0, 0));

        centro.add(crearFilaEstadisticas(), BorderLayout.NORTH);
        centro.add(crearFilaPrincipal(), BorderLayout.CENTER);

        add(centro, BorderLayout.CENTER);
    }

    //titulo

    private JPanel crearFilaTitulo() {
        JPanel fila = new JPanel(new BorderLayout());
        fila.setBackground(Estilos.FONDO_APP);

        JLabel titulo = new JLabel("Administracion");
        titulo.setFont(Estilos.TITULO);
        titulo.setForeground(Estilos.TEXTO_NORMAL);

     //   btnNuevoUsuario = EmergenciasPanel.crearBotonOutline("+ Nuevo Usuario");
      //  btnNuevoUsuario.addActionListener(e -> {
            //Abrir dialogo para crear nuevo usuario con su rol
       // });

        fila.add(titulo,          BorderLayout.WEST);
       // fila.add(btnNuevoUsuario, BorderLayout.EAST);
        return fila;
    }

    //estadistica

    // Cuatro tarjetas con contadores del sistema.
    private JPanel crearFilaEstadisticas() {
        JPanel fila = new JPanel(new GridLayout(1, 4, 10, 0));
        fila.setBackground(Estilos.FONDO_APP);
        fila.setPreferredSize(new Dimension(0, 75));

        fila.add(crearStat("Usuarios Activos",  "x", Estilos.TEXTO_NORMAL));
        fila.add(crearStat("Especialidades",    "x", Estilos.TEXTO_NORMAL));
        fila.add(crearStat("Habitaciones Total","x", Estilos.TEXTO_NORMAL));
        fila.add(crearStat("Pacientes del mes", "x", Estilos.TEXTO_NORMAL));
        return fila;
    }

    //fila principal
    private JPanel crearFilaPrincipal() {
        JPanel fila = new JPanel(new GridLayout(1, 2, 12, 0));
        fila.setBackground(Estilos.FONDO_APP);

        fila.add(crearTarjetaUsuarios());
        fila.add(crearTarjetaEspecialidades());
        return fila;
    }

    // gestion de usuarios
    private JPanel crearTarjetaUsuarios() {
        // Encabezado personalizado con boton "+ Añadir"
        JPanel encabezado = new JPanel(new BorderLayout());
        encabezado.setBackground(Estilos.FONDO_BLANCO);
        encabezado.setBorder(new CompoundBorder(
            new MatteBorder(0, 0, 1, 0, Estilos.BORDE),
            new EmptyBorder(5, 12, 5, 8)
        ));

        JLabel lblTitulo = new JLabel("Gestion de Usuarios");
        lblTitulo.setFont(Estilos.SUBTITULO);
        lblTitulo.setForeground(Estilos.TEXTO_NORMAL);

        btnAnadir = EmergenciasPanel.crearBotonOutline("+ Añadir");
        btnAnadir.setFont(Estilos.ETIQUETA);
        btnAnadir.addActionListener(e -> {
            String nombre = JOptionPane.showInputDialog("Nombre del nuevo empleado:");
            String rol = JOptionPane.showInputDialog("Rol (Ej: Medico, Cajero, Enfermera):");

            if (nombre != null && rol != null && !nombre.trim().isEmpty()) {

                // 1. Declaramos la variable padre (vacia por ahora)
                Usuario nuevoEmpleado = null;

                // 2. Evaluamos qué rol escribieron y creamos el HIJO correspondiente
                String rolLimpio = rol.toLowerCase();

                if (rolLimpio.contains("medico") || rolLimpio.contains("médico")) {
                    nuevoEmpleado = new Medico();
                } else if (rolLimpio.contains("enfermer@")) {
                    nuevoEmpleado = new Enfermera();
                } else if (rolLimpio.contains("cajer@")) {
                    nuevoEmpleado = new Cajero();
                } else {
                    nuevoEmpleado = new Administrador(); // Por defecto si escribe otra cosa
                }

                // 3. Le ponemos el nombre (todos los hijos heredan este método del padre)
                nuevoEmpleado.setNombre(nombre);

                // 4. Tu lógica entra en acción (¡El servicio acepta cualquier hijo!)
                servicio.crearUsuario(nuevoEmpleado);

                // 5. Actualizamos la tabla
                actualizarTablaVisual();
            }
        });


        encabezado.add(lblTitulo, BorderLayout.WEST);
        encabezado.add(btnAnadir, BorderLayout.EAST);

        // Columnas de la tabla
        String[] columnas = {"Usuario", "Rol", "Estado"};

        // Datos de ejemplo con placeholders
        Object[][] datos = {
            {"Nombre", "Medico",     "Activo"},
            {"Nombre", "Admision",   "Activo"},
            {"Nombre", "Enfermeria", "Inactivo"},
            {"Nombre", "Financiero", "Inactivo"},
            {"Nombre", "Medico",     "Inactivo"},
        };

        modeloUsuarios = new DefaultTableModel(datos, columnas) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false; // Tabla de solo lectura
            }
        };

        tablaUsuarios = new JTable(modeloUsuarios);
        tablaUsuarios.setFont(Estilos.NORMAL);
        tablaUsuarios.setRowHeight(35);
        tablaUsuarios.setBackground(Estilos.FONDO_BLANCO);
        tablaUsuarios.setGridColor(Estilos.BORDE);
        tablaUsuarios.setShowHorizontalLines(true);
        tablaUsuarios.setShowVerticalLines(false);
        tablaUsuarios.getTableHeader().setFont(Estilos.ETIQUETA);
        tablaUsuarios.getTableHeader().setBackground(Estilos.FONDO_STAT);

        JScrollPane scroll = new JScrollPane(tablaUsuarios);
        scroll.setBorder(null);

        JPanel tarjeta = new JPanel(new BorderLayout());
        tarjeta.setBackground(Estilos.FONDO_BLANCO);
        tarjeta.setBorder(BorderFactory.createLineBorder(Estilos.BORDE, 1));
        tarjeta.add(encabezado, BorderLayout.NORTH);
        tarjeta.add(scroll,     BorderLayout.CENTER);
        return tarjeta;
    }

    //especialidades y habitaciones

    // Muestra badges de especialidades activas y boton de reporte.
    private JPanel crearTarjetaEspecialidades() {
        JPanel tarjeta = crearTarjeta("Especialidades y Habitaciones");

        JPanel cuerpo = new JPanel();
        cuerpo.setLayout(new BoxLayout(cuerpo, BoxLayout.Y_AXIS));
        cuerpo.setBackground(Estilos.FONDO_BLANCO);
        cuerpo.setBorder(new EmptyBorder(12, 14, 14, 14));

        // Subtitulo de especialidades
        JLabel lblSubtitulo = new JLabel("Especialidades Activas");
        lblSubtitulo.setFont(Estilos.SUBTITULO);
        lblSubtitulo.setForeground(Estilos.TEXTO_NORMAL);
        lblSubtitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        cuerpo.add(lblSubtitulo);
        cuerpo.add(Box.createVerticalStrut(10));

        // Badges de especialidades en dos columnas
        String[] especialidades = {
            "Cardiologia", "Med. Interna",
            "Pediatría",
            "Cirugia General",
            "Traumatologia"
        };

        JPanel panelBadges = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 4));
        panelBadges.setBackground(Estilos.FONDO_BLANCO);
        panelBadges.setAlignmentX(Component.LEFT_ALIGNMENT);

        for (String esp : especialidades) {
            panelBadges.add(crearBadge(esp, Estilos.AZUL_FONDO, Estilos.AZUL_TEXTO));
        }

        cuerpo.add(panelBadges);
        cuerpo.add(Box.createVerticalGlue());

        // Boton de reporte en la parte inferior
        btnPacientesAtendidos = new JButton("Pacientes Atendidos");
        btnPacientesAtendidos.setFont(Estilos.SUBTITULO);
        btnPacientesAtendidos.setBackground(Estilos.FONDO_BLANCO);
        btnPacientesAtendidos.setFocusPainted(false);
        btnPacientesAtendidos.setOpaque(true);
        btnPacientesAtendidos.setBorder(BorderFactory.createLineBorder(Estilos.BORDE, 1));
        btnPacientesAtendidos.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnPacientesAtendidos.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnPacientesAtendidos.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnPacientesAtendidos.addActionListener(e -> {
            //Generar reporte de pacientes atendidos
        });
        cuerpo.add(btnPacientesAtendidos);

        tarjeta.add(cuerpo, BorderLayout.CENTER);
        return tarjeta;
    }

    // utilidades

    //Etiqueta con fondo de color para mostrar rol o estado.
    private JLabel crearBadge(String texto, Color fondo, Color textoColor) {
        JLabel badge = new JLabel(texto);
        badge.setFont(Estilos.ETIQUETA);
        badge.setForeground(textoColor);
        badge.setBackground(fondo);
        badge.setOpaque(true);
        badge.setBorder(new EmptyBorder(4, 8, 4, 8));
        return badge;
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

    private JPanel crearStat(String etiqueta, String valor, Color color) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(Estilos.FONDO_STAT);
        p.setBorder(new EmptyBorder(10, 14, 10, 14));
        JLabel e = new JLabel(etiqueta); e.setFont(Estilos.SB_SMALL); e.setForeground(Estilos.TEXTO_GRIS);
        JLabel v = new JLabel(valor);    v.setFont(Estilos.STAT_VAL); v.setForeground(color);
        p.add(e); p.add(Box.createVerticalStrut(4)); p.add(v);
        return p;
    }

// Método para que la tabla lea de tu UsuarioServicioImpl
// Método para que la tabla lea de tu UsuarioServicioImpl
private void actualizarTablaVisual() {
    // 1. Limpiamos las filas viejas o de mentira
    modeloUsuarios.setRowCount(0);

    // 2. Le pedimos a tu servicio la lista real que está en la RAM
    List<Usuario> empleados = servicio.obtenerTodosLosUsuarios();

    // 3. Llenamos la tabla fila por fila
    for (Usuario emp : empleados) {

        // EL TRUCO MAGICO: Le pedimos a Java el nombre exacto de la clase hija
        String rolReal = emp.getClass().getSimpleName();

        modeloUsuarios.addRow(new Object[]{
                emp.getNombre(),
                rolReal,   // ¡Aquí metemos la variable mágica!
                "Activo"
        });
    }
}

}