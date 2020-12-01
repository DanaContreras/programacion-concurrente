package TEORIA.TrenCyclicBarrier;

import TP5.Ejercicio3.*;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControlTren implements Runnable{
    
    private Tren tren;
    
    public void setTren (Tren t){
        this.tren = t;
    }
    
    @Override
    public void run(){
        this.simularRecorrido();
        System.out.println("El recorrido del tren termino, todos los pasajeros se bajan.");
    }
    
    private void simularRecorrido(){
        
        try { 
            Random tiempo = new Random();
            Thread.sleep((tiempo.nextInt(9)+1) *100);
            System.out.println("El tren está andando.");
        } catch (InterruptedException ex) {
            Logger.getLogger(Pasajero.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
