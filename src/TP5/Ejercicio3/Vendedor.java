
package TP5.Ejercicio3;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Vendedor implements Runnable{
    
    private Tren tren;
    
    public Vendedor (Tren t){
        this.tren = t;
    }
    
    public void run(){
        
        while (true){
            this.tren.esperarPasajero();
            this.generarTicket();
            this.tren.entregarTicket();
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
