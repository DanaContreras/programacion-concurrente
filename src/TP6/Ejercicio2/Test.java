package TP6.Ejercicio2;
import Utiles.TextoAColor;

/**
 *
 * @author Dana^^
 */

public class Test {

    public static void main(String[] args) {
        
        TextoAColor color = new TextoAColor();
        int cantPersonas = 10, cantNormal = 5, cantAnormal= 2, umbral = 30;
                
        // Recurso compartido.
        Sala sala = new Sala(cantNormal, cantAnormal, umbral);
        
        // Creacion de hilos.
        Thread medidor = new Thread( new Medidor(sala), color.cambiarColorTexto(5,"Medidor"));
        Thread[] persona = new Thread[cantPersonas];
        
        for (int i = 0; i < cantPersonas; i++) {
            
            if (i>5)
                persona[i] = new Thread(new Jubilado(sala), color.cambiarColorTexto(i, "Jubilado "+ (i+1)));
            else
                persona[i] = new Thread(new Persona(sala), color.cambiarColorTexto(i, "Persona " + (i+1)));
        }
        
        medidor.start();
        for (int i = 0; i < cantPersonas; i++) {
            persona[i].start();
        }
  
    }
    
}
