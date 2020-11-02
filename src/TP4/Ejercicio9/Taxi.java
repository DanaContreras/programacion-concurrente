package TP4.Ejercicio9;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Taxi {

    // Texto de color.
    public static final String VERDE = "\u001B[32m";
    public static final String CYAN = "\u001B[36m";
    public static final String FINALIZAR_COLOR = "\u001B[0m";

    private Semaphore semTaxista;
    private Semaphore semCliente;
    private Semaphore semAsiento;

    public Taxi() {
        this.semAsiento = new Semaphore(1, true);
        this.semTaxista = new Semaphore(0, true);
        this.semCliente = new Semaphore(0, true);
    }

    
    // Metodos ejecutados por el hilo Cliente.
    
    public boolean verificarDisponibilidad() {
        // Verifica si el taxi está disponible para transportar a un nuevo cliente.
        return this.semAsiento.tryAcquire();
    }

    public void esperarTaxi() {

        try { System.out.println(VERDE + Thread.currentThread().getName() + FINALIZAR_COLOR + " no encuentra un taxi disponible, por lo tanto espera.\n");
            this.semAsiento.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(Taxi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ocuparTaxi() {
        
        System.out.println(VERDE + Thread.currentThread().getName() + FINALIZAR_COLOR + " sube al taxi disponible.\n");
        
        this.semTaxista.release(); // El taxista se libera para realizar su tarea.
        try {
            this.semCliente.acquire(); // El cliente se bloquea
        } catch (InterruptedException ex) {
            Logger.getLogger(Taxi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void salirDelTaxi() {
        this.semAsiento.release(); // Se encuentra el asiento desocupado.
    }

    
    // Metodos ejecutados por el hilo Taxista.
    
    public void esperarACliente() {

        try {
            System.out.println(CYAN + Thread.currentThread().getName() +FINALIZAR_COLOR + " esta esperando a que aparezca un cliente.\n");
            this.semTaxista.acquire(); // El taxista duerme en el auto, se bloquea.
        } catch (InterruptedException ex) {
            Logger.getLogger(Taxi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void pararTaxi() {
        System.out.println("El cliente llego a su destino, por lo tanto se baja del taxi.\n");
        this.semCliente.release(); // El cliente se libera, para seguir su ejecucion.
    }

}
