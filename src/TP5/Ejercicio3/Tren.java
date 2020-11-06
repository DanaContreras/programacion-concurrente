
package TP5.Ejercicio3;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tren {
    
    // Recurso compartido.
    
    private int cantLugares;
    private int cantEnTren;
    private Semaphore semVendedor;
    private Semaphore semPasajero;
    private Semaphore semAtencionVendedor;
    private Semaphore semControl;
    private Semaphore semAsiento;
    private Semaphore semPasajeroEnTren;
    private ReentrantLock cerrojo;
    
    public Tren (int c){
        this.cantLugares = c;
        this.cantEnTren = 0;
        this.semVendedor = new Semaphore(0);
        this.semPasajero = new Semaphore(0);
        this.semAtencionVendedor = new Semaphore(1);
        this.semControl = new Semaphore(0);
        this.semAsiento = new Semaphore(c,true);
        this.semPasajeroEnTren = new Semaphore(0,true);
        this.cerrojo = new ReentrantLock();
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
        System.out.println(Thread.currentThread().getName() + " le entro el ticket al cliente.");
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
    
    public void subirseAlTren(){
        
        try {
            this.semAsiento.acquire();
            
            this.cerrojo.lock();
            this.cantEnTren++;
            System.out.println(Thread.currentThread().getName() + " encontro un asiento disponible en el tren.");
            if (this.cantEnTren == this.cantLugares)
                this.semControl.release(); 
            this.cerrojo.unlock();
            
            this.semPasajeroEnTren.acquire();
          
        } catch (InterruptedException ex) {
                Logger.getLogger(Tren.class.getName()).log(Level.SEVERE, null, ex);
        }
      
    }
    
    public void bajarseDelTren(){
        
        try{
            this.cerrojo.lock();
            cantEnTren --; 
            System.out.println(Thread.currentThread().getName() + " se bajo del tren.");
            this.semAsiento.release();
        }
        finally{
           this.cerrojo.unlock(); 
        }
    }
    
    // Metodos utilizados por control del tren.
    
    public void esperarATrenCompleto(){
        
        try {
            this.semControl.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(Tren.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void terminarRecorrido(){
        System.out.println("El recorrido del tren termino, los pasajeros se bajan.");
        this.semPasajeroEnTren.release(this.cantLugares);
        
    }
    
}
