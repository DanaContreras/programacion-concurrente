package TP6.Ejercicio2;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dana^^
 */

public class Medidor implements Runnable{

    private Sala sala;
    private Random tiempo;
    
    public Medidor(Sala sala){
        this.sala = sala;
        this.tiempo = new Random();
    }
    
    public void run(){
        
        while(true){
            this.sala.notificarTemperatura(tiempo.nextInt(30)+10);
            this.simularTiempo();
        }
        
    }
    
    private void simularTiempo(){
      
        try {
            Thread.sleep((tiempo.nextInt(9)+1)*100);
        } catch (InterruptedException ex) {
            Logger.getLogger(Jubilado.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
