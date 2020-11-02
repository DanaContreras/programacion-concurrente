package TP4.Ejercicio8;

import java.util.concurrent.Semaphore;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Testigo {

    // Texto de color.
    public static final String AZUL = "\u001B[34m";
    public static final String FINALIZAR_COLOR = "\u001B[0m";

    private Semaphore semTestigo;
    private int tiempoTotal;

    public Testigo() {
        semTestigo = new Semaphore(1);
        tiempoTotal = 0;
    }

    public int getTiempoTotal() {
        return tiempoTotal;
    }

    public void correr() {

        try {
            this.semTestigo.acquire(); // adquiero el semaforo.

        } catch (InterruptedException ex) {
            Logger.getLogger(Testigo.class.getName()).log(Level.SEVERE, null, ex);
        }

        procesoCorrer();

        this.semTestigo.release(); // libero el semaforo.
    }

    private void procesoCorrer() {
        // Metodo que simula el tiempo que demora el atleta en llegar a su destino.

        System.out.println("Comienza a correr: " + AZUL + Thread.currentThread().getName() + FINALIZAR_COLOR + ".");

        long ini = System.currentTimeMillis(), fin, total;
        Random tiempo = new Random();
        int tiempoCorriendo = tiempo.nextInt(3) + 9;

        try {
            Thread.sleep(tiempoCorriendo * 1000); // simulo tiempo de carrera.

            System.out.println(Thread.currentThread().getName() + " pasa el testigo.");

            fin = System.currentTimeMillis();
            total = (fin - ini) / 1000;
            System.out.println("Tiempo Corriendo: " + total + " segundos.");
            System.out.println("");

            this.tiempoTotal += total;

        } catch (InterruptedException ex) {
            Logger.getLogger(Testigo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
