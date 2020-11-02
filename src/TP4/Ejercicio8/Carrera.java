package TP4.Ejercicio8;

public class Carrera {

    public static void main(String[] args) {

        int i;
        Testigo testigo = new Testigo();
        Thread[] atleta = new Thread[4];

        // Creacion de hilos.
        for (i = 0; i < 4; i++) {
            atleta[i] = new Thread(new Atleta(testigo), "Atleta " + (i + 1));
        }

        System.out.println("3...2...1...");
        System.out.println("Comienza la CARRERA");
        System.out.println("");

        for (i = 0; i < 4; i++) {
            atleta[i].start();
        }

        for (i = 0; i < 4; i++) {
            try {
                atleta[i].join();
            } catch (InterruptedException exc) {
            }
        }

        System.out.println("La carrera ha finalizado.");
        System.out.println("\033[32mDuracion total: " + testigo.getTiempoTotal() + " segundos.");
        System.out.println("");
    }
}
