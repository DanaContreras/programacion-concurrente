package Main;

import Hilos.Visitante;
import RecursosCompartidos.Restaurantes;
import Utiles.TextoAColor;

public class MainRestaurantes {
    
    public static void main(String[] args) {
        
        TextoAColor txtColor = new TextoAColor();
        int maxVisitantes = 5;
        int maxRest1 = 4;
        int maxRest2 = 2;
        int maxRest3 = 1;
        
        // recurso compartido
        Restaurantes restaurantes = new Restaurantes(maxRest1, maxRest2, maxRest3);
        
        // creacion de hilos
        Thread[] visitante = new Thread[maxVisitantes];
        
        for (int i = 0; i < maxVisitantes; i++)
            visitante[i] = new Thread(new Visitante(restaurantes), txtColor.cambiarColor("Rojo", "Visitante ") + txtColor.cambiarColorTexto(i, "" +(i + 1) + ""));
        
        // estado start
        for (int i = 0; i < maxVisitantes; i++)
            visitante[i].start();
        
    }
    
}
