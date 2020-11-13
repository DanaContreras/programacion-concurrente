package TP6.Ejercicio1;
import Utiles.TextoAColor;

/**
 *
 * @author Dana^^
 */

public class DisparaSala {

    public static void main(String[] args) {
        
        TextoAColor color = new TextoAColor();
        
        int cantFumadores = 3;
        SalaFumadores sala = new SalaFumadores();
        
        Thread agente = new Thread(new Agente(sala),color.cambiarColorTexto(5,"Agente"));
        Thread[] fumador = new Thread[cantFumadores];
        
        for (int i = 0; i < cantFumadores; i++) {
            fumador[i] = new Thread(new Fumador((i), sala), color.cambiarColorTexto(i, "Fumador " + (i+1)));
        }
        
        agente.start();
        for (int i = 0; i < cantFumadores; i++) {
            fumador[i].start();
        }
    }
} 
