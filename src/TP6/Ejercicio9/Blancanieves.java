package TP6.Ejercicio9;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * @author Dana ~
 */

public class Blancanieves implements Runnable{

    private Casa hogar;
    private Random numRandom;

    public Blancanieves (Casa hogar){
        this.hogar = hogar;
        this.numRandom = new Random();
    }
    
    public void run(){
        
        try {
            while (true){
                this.hogar.esperarEnanito();
                this.simularTiempo();
                this.hogar.servirComida();
                
                
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Blancanieves.class.getName()).log(Level.SEVERE, null, ex);
        }
 
    }
    
    private void simularTiempo (){

        try {
            System.out.println ("°°°°°°" +Thread.currentThread().getName() + " esta preparando la comida...");
            Thread.sleep((numRandom.nextInt(9)+1)*100);
        } catch (InterruptedException ex) {
            Logger.getLogger(Blancanieves.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
