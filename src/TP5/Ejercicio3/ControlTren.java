package TP5.Ejercicio3;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControlTren implements Runnable{
    
    private Tren tren;
    
    public ControlTren(Tren t){
        this.tren = t;
    }
    
    public void run(){
        
        while (true){
            this.tren.esperarATrenCompleto();
            this.simularRecorrido();
            this.tren.terminarRecorrido();
        }
        
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
