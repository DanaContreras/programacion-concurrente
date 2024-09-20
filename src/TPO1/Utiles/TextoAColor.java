package Utiles;
import java.util.HashMap;

public class TextoAColor {
    
    // Clase encargada de gestionar los colores que se le pueden asignar a un texto.
    
    private final HashMap <Integer,String> hash;
    private static final String NEGRO = "\u001B[30m";
    private static final String BLANCO = "\u001B[37m"; 
    private static final String FINALIZAR_COLOR = "\u001B[0m";
    private static final String ROJO = "\u001B[31m";
    private static final String VERDE = "\u001B[32m";
    private static final String AMARILLO = "\u001B[33m";
    private static final String AZUL = "\u001B[34m";
    private static final String VIOLETA = "\u001B[35m";
    private static final String CYAN = "\u001B[36m";
    
    // Constructor.
    public TextoAColor(){
        
        this.hash = new HashMap <Integer,String>();
        this.llenarHash();
    }
    
    private void llenarHash(){
        // Metodo encargado de llenar hash con los colores existentes para acceso mas rapido.
        
        this.hash.put(0, ROJO);
        this.hash.put(1, VERDE);
        this.hash.put(2, AMARILLO);
        this.hash.put(3, AZUL);
        this.hash.put(4, VIOLETA);
        this.hash.put(5, CYAN);    
    }
    
    // Retorna el color pedido.
    public static String getNEGRO() {
        return NEGRO;
    }

    public static String getROJO() {
        return ROJO;
    }

    public static String getVERDE() {
        return VERDE;
    }

    public static String getAMARILLO() {
        return AMARILLO;
    }

    public static String getAZUL() {
        return AZUL;
    }

    public static String getPURPLE() {
        return VIOLETA;
    }

    public static String getCYAN() {
        return CYAN;
    }

    public static String getBLANCO() {
        return BLANCO;
    }

    public static String getFINALIZAR_COLOR() {
        return FINALIZAR_COLOR;
    }
    
    public String cambiarColorTexto(int tipo, String texto){
        // Metodo encargado de cambiar el color de un texto dependiendo del numero(tipo) pasado por parametro.
        
        tipo = tipo % 6;
        
        return (this.hash.get(tipo) + texto + this.FINALIZAR_COLOR);
    }
    
    public String cambiarColor(String nombreColor, String texto){
        // Metodo encargado de cambiar el color de un texto a eleccion.
        
        String colorEleccion;
        
        switch (nombreColor){
            case "Rojo": colorEleccion = this.ROJO; break;
            case "Verde": colorEleccion = this.VERDE; break;
            case "Amarillo": colorEleccion = this.AMARILLO; break;
            case "Azul": colorEleccion = this.AZUL; break;
            case "Violeta": colorEleccion = this.VIOLETA; break;
            case "Cyan": colorEleccion = this.CYAN; break;
            case "Blanco": colorEleccion = this.BLANCO; break;
            default: colorEleccion = "";
        }
        
        return colorEleccion + texto + this.FINALIZAR_COLOR;
    }
    
}
