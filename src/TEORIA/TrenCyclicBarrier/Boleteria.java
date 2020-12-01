package TEORIA.TrenCyclicBarrier;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * @author Dana ~
 */

public class Boleteria {

    private final Semaphore semVendedor;
    private final Semaphore semPasajero;
    private final Semaphore semAtencionVendedor;
    
    public Boleteria(){
        this.semVendedor = new Semaphore(0);
        this.semPasajero = new Semaphore(0);
        this.semAtencionVendedor = new Semaphore(1);
    }
    
    // Metodos utilizados por Vendedor.
    public void esperarPasajero(){
        
        try {
            System.out.println(Thread.currentThread().getName() + " esta esperando a algun cliente.");
            this.semVendedor.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(Tren.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void entregarTicket(){
        System.out.println(Thread.currentThread().getName() + " le entrego el ticket al cliente.");
        this.semPasajero.release();
        this.semAtencionVendedor.release(); // El vendedor esta nuevamente disponible.
    }
    
    // Metodos utilizados por Pasajero.
    public void comprarTicket(){
       
        try {
            this.semAtencionVendedor.acquire(); // El vendedor solo esta disponible para un cliente.
            System.out.println(Thread.currentThread().getName() + " pide un ticket para el tren.");
            this.semVendedor.release();
            this.semPasajero.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(Tren.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
