package TEORIA.BarberoSemGenerales;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Barberia {

    private Semaphore semSillasEspera;
    private Semaphore semSillon;
    private Semaphore semBarbero;
    private Semaphore semCliente;
    
    public Barberia (int cantSillas){
        this.semSillasEspera = new Semaphore (cantSillas);
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
            Logger.getLogger(Barberia.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void terminarCorte(){
        
        System.out.println(Thread.currentThread().getName() + "termino el corte.");
        this.semCliente.release();
    }
    
    // Metodos utilizados por Cliente.
    public boolean entrarABarberia(){
        return (this.semSillasEspera.tryAcquire());
    }
    
    public void solicitarCorte(){

        try {
            System.out.println(Thread.currentThread().getName() + " esta esperando a que se desocupe el sillon.");
            this.semSillon.acquire();
            this.semSillasEspera.release();
            System.out.println(Thread.currentThread().getName() + " se sienta en el sillon.");
            this.semBarbero.release(); // El barbero se libera para hacer su trabajo.
            this.semCliente.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(Barberia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void salirDeBarberia(){
        
        System.out.println(Thread.currentThread().getName() + " sale de la barberia.");
        this.semSillon.release();
    }
    
}
