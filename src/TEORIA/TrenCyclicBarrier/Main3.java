package TEORIA.TrenCyclicBarrier;
import TP5.Ejercicio3.*;
import Utiles.TextoAColor;

public class Main3 {
    
    public static void main(String[] args) {
        
        int i, cantLugares = 5, cantPasajeros = 14;
        TextoAColor color = new TextoAColor();
        
        // Hilo controlTren.
        ControlTren control = new ControlTren();
        
        // Recursos compartidos.
        Tren trenActual = new Tren(cantLugares, control);
        control.setTren(trenActual);
        
        Boleteria tickets = new Boleteria();
        
        //Hilo vendedor.
        Vendedor vendedor = new Vendedor(tickets);
        Thread vendedorTickets = new Thread(vendedor, color.cambiarColorTexto(4, "VendedorTickets"));

        // Hilos pasajeros.
        Thread[] pasajero = new Thread[cantPasajeros];
        for (i = 0; i < cantPasajeros; i++) {
            pasajero[i] = new Thread (new Pasajero(trenActual, tickets), color.cambiarColorTexto(i, "Pasajero " + (i+1)));
        }
        
        vendedorTickets.start();
        
        for (i = 0; i < cantPasajeros; i++) {
            pasajero[i].start();
        }
        
    }
    
}
