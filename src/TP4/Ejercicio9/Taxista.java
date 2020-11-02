package TP4.Ejercicio9;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Taxista implements Runnable {

    // Texto de color.
    public static final String CYAN = "\u001B[36m";
    public static final String FINALIZAR_COLOR = "\u001B[0m";
    
    private Taxi auto;

    public Taxista(Taxi auto) {
        this.auto = auto;
    }

    public void run() {

        while (true) { 
            this.auto.esperarACliente();
            manejarTaxi();
            this.auto.pararTaxi();
        }
    }

    private void manejarTaxi() {
        // Metodo que simula el tiempo que toma manejar el taxi hasta destino.

        Random tiempo = new Random();

        try {
            System.out.println(Thread.currentThread().getName() + " se encuentra manejando.\n");
            Thread.sleep((tiempo.nextInt(11) + 1) * 100);
        } catch (InterruptedException ex) {
            Logger.getLogger(Taxista.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
