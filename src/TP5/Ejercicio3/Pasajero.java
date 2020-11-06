
package TP5.Ejercicio3;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Pasajero implements Runnable{
    
    private Tren tren;
    
    public Pasajero (Tren t){
        this.tren = t;
    }
    
    public void run(){
        
        while (true){
            
            this.caminarHaciaVendedor();
            tren.comprarTicket();
            this.tren.subirseAlTren();
            tren.bajarseDelTren();
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
