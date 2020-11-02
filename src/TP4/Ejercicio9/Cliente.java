package TP4.Ejercicio9;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente implements Runnable {

    // Texto de color.
    public static final String VERDE = "\u001B[32m";
    public static final String FINALIZAR_COLOR = "\u001B[0m";

    private Taxi auto;

    public Cliente(Taxi auto) {
        this.auto = auto;
    }

    public void run() {

        caminar();
        
        if (!this.auto.verificarDisponibilidad()) 
            this.auto.esperarTaxi();
        
        this.auto.ocuparTaxi();
        this.auto.salirDelTaxi();
    }

    private void caminar() {
        // Simula el tiempo que tarda el cliente hasta llegar el taxi.
        
        Random tiempo = new Random();
        
        try {
            System.out.println(VERDE + Thread.currentThread().getName() + FINALIZAR_COLOR + " se encuentra caminando hasta el taxi.\n");
            Thread.sleep((tiempo.nextInt(11) + 1) * 1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
