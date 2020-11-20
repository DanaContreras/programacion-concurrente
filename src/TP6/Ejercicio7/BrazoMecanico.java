package TP6.Ejercicio7;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * @author Dana ~
 */

public class BrazoMecanico implements Runnable{

    private Pasteleria pasteleria;
    private Random tiempo;
    
    public BrazoMecanico (Pasteleria paste){
        this.pasteleria = paste;
        this.tiempo = new Random();
    }
    
    public void run(){
        
        while (true){
            this.pasteleria.retirarCaja();
            this.simularTiempo();
            this.pasteleria.reponerCaja();
        }
        
    }
    
    private void simularTiempo(){
        
        try {
            Thread.sleep((tiempo.nextInt(9)+1)*100);
        } catch (InterruptedException ex) {
            Logger.getLogger(BrazoMecanico.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
