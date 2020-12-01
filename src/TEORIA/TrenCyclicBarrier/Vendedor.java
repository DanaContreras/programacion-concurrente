
package TEORIA.TrenCyclicBarrier;

import TP5.Ejercicio3.*;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Vendedor implements Runnable{
    
    private Boleteria boleteria;
    
    public Vendedor (Boleteria boleteria){
        this.boleteria = boleteria;
    }
    
    public void run(){
        
        while (true){
            this.boleteria.esperarPasajero();
            this.generarTicket();
            this.boleteria.entregarTicket();
        }
        
    }
    
    public void generarTicket(){
        // Simula el tiempo del vendedor en realizar el proceso de venta del ticket.
        try { 
            Random tiempo = new Random();
            Thread.sleep((tiempo.nextInt(9)+1) *100);
            System.out.println(Thread.currentThread().getName() + " esta imprimiendo ticket.");
        } catch (InterruptedException ex) {
            Logger.getLogger(Pasajero.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
