package hospital.estilos;

import java.awt.Color;
import java.awt.Font;

//Colores y fuentes usados en toda la app.
//Si cambia un color, solo se edita aqui.
public class Estilos {

  //Sidebar
  public static final Color SIDEBAR_FONDO   = new Color(26, 47, 61);   // Azul oscuro del sidebar
  public static final Color SIDEBAR_ACTIVO  = new Color(42, 95, 143);  // Fondo item activo
  public static final Color SIDEBAR_TEXTO   = new Color(180, 205, 225);// Texto del sidebar
  public static final Color SIDEBAR_SUBTXT  = new Color(120, 150, 175);// Texto secundario sidebar

  //Fondo
  public static final Color FONDO_APP    = new Color(238, 238, 238); // Fondo gris de la app
  public static final Color FONDO_BLANCO = Color.WHITE;              // Fondo de tarjetas
  public static final Color FONDO_STAT   = new Color(215, 215, 215); // Fondo tarjeta estadistica
  public static final Color FONDO_CAMPO  = new Color(225, 225, 225); // Fondo de campos readonly

  //colores de estado (camillas, badges, etc.)
  public static final Color VERDE_FONDO  = new Color(200, 235, 200); // Fondo verde (libre)
  public static final Color VERDE_TEXTO  = new Color(30, 100, 30);   // Texto verde
  public static final Color VERDE_BORDE  = new Color(100, 180, 100); // Borde verde

  public static final Color ROJO_FONDO   = new Color(250, 205, 205); // Fondo rojo (ocupado)
  public static final Color ROJO_TEXTO   = new Color(140, 30, 30);   // Texto rojo
  public static final Color ROJO_BORDE   = new Color(210, 100, 100); // Borde rojo

  public static final Color AMBAR_FONDO  = new Color(252, 228, 188); // Fondo ambar (limpieza)
  public static final Color AMBAR_TEXTO  = new Color(140, 78, 10);   // Texto ambar

  public static final Color AZUL_FONDO   = new Color(210, 230, 255); // Fondo azul (especialidad)
  public static final Color AZUL_TEXTO   = new Color(20, 60, 140);   // Texto azul

  //texto especial
  public static final Color TOTAL_AZUL   = new Color(0, 80, 200);   // Total en factura
  public static final Color CAMBIO_VERDE = new Color(0, 130, 0);    // Cambio en cajera

  // bordes y texto en general
  public static final Color BORDE        = new Color(195, 195, 195); // Borde de tarjetas
  public static final Color TEXTO_NORMAL = new Color(30, 30, 30);   // Texto principal
  public static final Color TEXTO_GRIS   = new Color(100, 100, 100);// Texto secundario

  //fuentes
  public static final Font TITULO     = new Font("SansSerif", Font.BOLD,  20); // Titulo modulo
  public static final Font SUBTITULO  = new Font("SansSerif", Font.BOLD,  14); // Titulo tarjeta
  public static final Font ETIQUETA   = new Font("SansSerif", Font.BOLD,  11); // Label de campo
  public static final Font NORMAL     = new Font("SansSerif", Font.PLAIN, 13); // Texto normal
  public static final Font STAT_VAL   = new Font("SansSerif", Font.BOLD,  22); // Valor estadistica
  public static final Font SB_TITULO  = new Font("SansSerif", Font.BOLD,  14); // Titulo sidebar
  public static final Font SB_NORMAL  = new Font("SansSerif", Font.PLAIN, 13); // Item sidebar
  public static final Font SB_SMALL   = new Font("SansSerif", Font.PLAIN, 11); // Texto pequeño sidebar
}
