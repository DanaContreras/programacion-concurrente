package Main;

import Hilos.Visitante;
import RecursosCompartidos.Shop;
import Utiles.TextoAColor;

public class MainShop {

    public static void main(String[] args) {
        TextoAColor txtColor = new TextoAColor();
        int maxCajas = 2;
        int maxVisitantes = 5;
        
        // recurso compartido.
        Shop shop = new Shop(maxCajas);
        
        // creacion de hilos
        Thread[] visitante = new Thread[maxVisitantes];
        for (int i = 0; i < maxVisitantes; i++)
            visitante[i] = new Thread(new Visitante(shop), txtColor.cambiarColor("Rojo", "Visitante ") + txtColor.cambiarColorTexto(i, "" +(i + 1) + ""));
        
        // estado start
        for (int i = 0; i < maxVisitantes; i++)
            visitante[i].start();
    }
 
}
