package Utiles;
import java.util.HashMap;
import java.awt.Color;

public class TextoAColor {
    
    private HashMap <Integer,String> hash;
    private static final String NEGRO = "\u001B[30m";
    private static final String BLANCO = "\u001B[37m"; 
    private static final String FINALIZAR_COLOR = "\u001B[0m";
    private static final String ROJO = "\u001B[31m";
    private static final String VERDE = "\u001B[32m";
    private static final String AMARILLO = "\u001B[33m";
    private static final String AZUL = "\u001B[34m";
    private static final String VIOLETA = "\u001B[35m";
    private static final String CYAN = "\u001B[36m";
    

    public TextoAColor(){
        
        this.hash = new HashMap <Integer,String>();
        this.llenarHash();
    }
    
    private void llenarHash(){
        
        this.hash.put(0, ROJO);
        this.hash.put(1, VERDE);
        this.hash.put(2, AMARILLO);
        this.hash.put(3, AZUL);
        this.hash.put(4, VIOLETA);
        this.hash.put(5, CYAN);
        
    }
    
    public String colorNEGRO(String texto) {
        return NEGRO + texto + FINALIZAR_COLOR;
    }

    public String colorROJO(String texto) {
        return ROJO + texto + FINALIZAR_COLOR;
    }

    public String colorVERDE(String texto) {
        return VERDE + texto + FINALIZAR_COLOR;
    }

    public String colorAMARILLO(String texto) {
        return AMARILLO + texto + FINALIZAR_COLOR;
    }

    public String colorAZUL(String texto) {
        return AZUL + texto + FINALIZAR_COLOR;
    }

    public String colorPURPLE(String texto) {
        return VIOLETA + texto + FINALIZAR_COLOR;
    }

    public String colorCYAN(String texto) {
        return CYAN + texto + FINALIZAR_COLOR;
    }

    public String colorBLANCO(String texto) {
        return BLANCO + texto + FINALIZAR_COLOR;
    }
    
    public String cambiarColorTexto(int tipo, String texto){
        
        tipo = tipo % 6;
        return (this.hash.get(tipo) + texto + this.FINALIZAR_COLOR);
    }
    
}
