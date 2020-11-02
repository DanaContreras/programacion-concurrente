package TEORIA.BarberoMonitor;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BarberiaConMonitor {

    private int sillasEspera;
    private ReentrantLock cerrojo;
    private Semaphore semSillon;
    private Semaphore semBarbero;
    private Semaphore semCliente;
    
    public BarberiaConMonitor (int cantSillas){
        this.sillasEspera = cantSillas;
        this.cerrojo = new ReentrantLock();
        this.semSillon = new Semaphore (1,true);
        this.semBarbero = new Semaphore (0);
        this.semCliente = new Semaphore (0,true);
    }
    
    // Metedos utilizados por Barbero.
    
    public void esperarCliente(){
      
        try {
            System.out.println(Thread.currentThread().getName() + " esta esperando a algun cliente.");
            this.semBarbero.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(BarberiaConMonitor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void terminarCorte(){
        
        System.out.println(Thread.currentThread().getName() + " termino el corte.");
        this.semCliente.release();
    }
    
    // Metodos utilizados por Cliente.
    public boolean entrarABarberia(){
        
        boolean exito = false;
        this.cerrojo.lock();
        if (this.sillasEspera > 0){
            this.sillasEspera--;
            exito = true;
            System.out.println(Thread.currentThread().getName() + " encontro una silla de espera.");
        }
        this.cerrojo.unlock();
        return exito;
    }
       
    public synchronized void solicitarCorte(){

        try {
            if (!this.semSillon.tryAcquire())
                this.wait();
            avisarClienteEnEspera();
            System.out.println(Thread.currentThread().getName() + " se sienta en el sillon.");
            this.semBarbero.release(); // El barbero se libera para hacer su trabajo.
            this.semCliente.acquire();
       
        } catch (InterruptedException ex) {
            Logger.getLogger(BarberiaConMonitor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private synchronized void avisarClienteEnEspera(){
        this.notify();
        this.sillasEspera++;
    }
    
    public void salirDeBarberia(){
        
        System.out.println(Thread.currentThread().getName() + " sale de la barberia.");
        this.semSillon.release();
    }
    
}
