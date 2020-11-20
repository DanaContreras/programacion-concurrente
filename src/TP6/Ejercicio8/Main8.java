package TP6.Ejercicio8;
import Utiles.TextoAColor;

/*
 * @author Dana ~
 */

public class Main8 {

    public static void main(String[] args) {
        
        TextoAColor color = new TextoAColor();
        
        int cantCamas = 4, cantPersonas = 20, cantSillas = 6, cantRevistas = 9;
        
        // Recurso compartido.
        CentroHemoterapia centro = new CentroHemoterapia (cantCamas,cantSillas,cantRevistas);
        
        // Creacion de hilos.
        Thread[] persona = new Thread[cantPersonas];
        
        for (int i = 0; i < cantPersonas; i++) {
            persona[i] = new Thread (new Persona(centro), color.cambiarColorTexto(i, "Persona " + (i+1)));
        }
        
        for (int i = 0; i < cantPersonas; i++) {
            persona[i].start();
        }
        
    }
    

    
}
