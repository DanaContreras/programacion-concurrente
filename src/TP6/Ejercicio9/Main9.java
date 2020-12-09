package TP6.Ejercicio9;
import Utiles.TextoAColor;

/*
 * @author Dana ~
 */

public class Main9 {

    public static void main(String[] args) {
        
        TextoAColor color = new TextoAColor();
        
        int numEnanitos = 7, cantSillas = 4;
        Casa hogar = new Casa(cantSillas);
        
        // Creacion de hilos.
        Thread blancanieves = new Thread (new Blancanieves(hogar), color.colorROJO("Blancanieves"));
        Thread[] enano = new Thread[numEnanitos];
        
        for (int i = 0; i < numEnanitos; i++) {
            enano[i] = new Thread(new Enanito(hogar), color.colorAMARILLO("Enanito")+ color.cambiarColorTexto(i, ""+(i+1)+""));
        }
        
        blancanieves.start();
        for (int i = 0; i < numEnanitos; i++) {
            enano[i].start();
        }
    }
    
}
