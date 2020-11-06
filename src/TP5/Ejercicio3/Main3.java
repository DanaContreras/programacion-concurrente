package TP5.Ejercicio3;
import Utiles.TextoAColor;

public class Main3 {
    
    public static void main(String[] args) {
        
        int i, cantLugares = 5, cantPasajeros = 14;
        TextoAColor color = new TextoAColor();
        
        // Recurso compartido: tren.
        Tren trenActual = new Tren(cantLugares);
        
        // Creación de hilos.
        
        //Hilo vendedor.
        Vendedor vendedor = new Vendedor(trenActual);
        Thread vendedorTickets = new Thread(vendedor, color.cambiarColorTexto(4, "VendedorTickets"));
        
        // Hilo controlTren.
        ControlTren control = new ControlTren(trenActual);
        Thread controlTren = new Thread (control, color.cambiarColorTexto (5, "ControlTren"));
        
        // Hilos pasajeros.
        Thread[] pasajero = new Thread[cantPasajeros];
        for (i = 0; i < cantPasajeros; i++) {
            pasajero[i] = new Thread (new Pasajero(trenActual), color.cambiarColorTexto(i, "Pasajero " + (i+1)));
        }
        
        vendedorTickets.start();
        controlTren.start();
        for (i = 0; i < cantPasajeros; i++) {
            pasajero[i].start();
        }
        
    }
    
}
