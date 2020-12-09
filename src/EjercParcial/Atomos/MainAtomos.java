package EjercParcial.Atomos;

import Utiles.TextoAColor;
/*
 * @author Dana ~
 */

public class MainAtomos {

    public static void main(String[] args) {
        
        TextoAColor color = new TextoAColor();
        
        int capacidadRecipiente = 5;
        char[] tipoAtomo = { 'O', 'H'};
                
        // Recurso compartido.
        Agua agua = new Agua(capacidadRecipiente);
        
        // Hilo creador.
        Thread generador = new Thread (new GeneradorAtomo (agua, tipoAtomo), color.colorCYAN("Generador de Atomos"));
        generador.start();
        
    }
    
}
