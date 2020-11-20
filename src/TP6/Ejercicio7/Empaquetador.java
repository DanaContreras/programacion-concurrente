package TP6.Ejercicio7;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * @author Dana ~
 */

public class Empaquetador implements Runnable{

    private Pasteleria pasteleria;
    private Random tiempo;
    private int pesoPastel;
    
    public Empaquetador (Pasteleria past){
        this.pasteleria = past;
        this.tiempo = new Random();
    }
    
    public void run(){
        
        while (true){
            pesoPastel = this.pasteleria.tomarPastel();
            this.simularTiempo();
            this.pasteleria.soltarPastel(pesoPastel);
            this.simularTiempo();
        }
        
    }
    
    private void simularTiempo(){
        
        try {
            Thread.sleep((tiempo.nextInt(9)+1)*100);
        } catch (InterruptedException ex) {
            Logger.getLogger(Empaquetador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
