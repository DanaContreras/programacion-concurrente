package TP6.Ejercicio9;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * @author Dana ~
 */

public class Enanito implements Runnable{

    private Casa hogar;
    private Random numRandom;

    public Enanito(Casa c){
        this.hogar = c;
        this.numRandom = new Random();
    }
    
    public void run(){

        try {
            
            for (int i = 0; i < 2; i++) {
                this.simularTiempo('T');
                this.hogar.comer();
                this.simularTiempo('C');
                this.hogar.terminarDeComer();
            }
            
            this.simularTiempo('D');
            
        } catch (InterruptedException ex) {
            Logger.getLogger(Enanito.class.getName()).log(Level.SEVERE, null, ex);
        }
      
        
    }
    
    private void simularTiempo (char accion){
        
        String texto = "";
        switch(accion){
            
            case 'T': // Trabajar.
                texto = " esta trabajando...";
                break;
                
            case 'C': // Comer.
                texto = " esta comiendo...";
                
            case 'D': // Dormir.
                texto = " se va a dormir...";
                break;
        }
        
        try {
            System.out.println("°°°°°°°° " + Thread.currentThread().getName() + texto);
            Thread.sleep((numRandom.nextInt(9)+1)*100);
        } catch (InterruptedException ex) {
            Logger.getLogger(Enanito.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
}
