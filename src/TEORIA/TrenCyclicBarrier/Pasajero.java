
package TEORIA.TrenCyclicBarrier;
import TP5.Ejercicio3.*;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Pasajero implements Runnable{
    
    private Tren tren;
    private Boleteria boleteria;
    
    public Pasajero (Tren tren, Boleteria boleteria){
        this.tren = tren;
        this.boleteria = boleteria;
    }
    
    public void run(){
        
        while (true){
            
            try {
                
                this.caminarHaciaVendedor();
                boleteria.comprarTicket();
                this.tren.subirseAlTren();
                System.out.println(Thread.currentThread().getName() + " dice: Adios!");
                
            } catch (InterruptedException ex) {
                Logger.getLogger(Pasajero.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BrokenBarrierException ex) {
                Logger.getLogger(Pasajero.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
           
    }
    
    public void caminarHaciaVendedor(){
        
        // Simula el tiempo que le toma al pasajero a dirigirse donde se encuentra el vendedor.
        try { 
            Random tiempo = new Random();
            Thread.sleep((tiempo.nextInt(9)+1) *100);
            System.out.println(Thread.currentThread().getName() + " está dirigiendose hacia donde se encuentra el vendedor de tickets.");
        } catch (InterruptedException ex) {
            Logger.getLogger(Pasajero.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
