package TP6.Ejercicio7;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * @author Dana ~
 */

public class Horno implements Runnable{

    private int tipo; // 0:PastelA, 1:PastelB, 2:PastelC;
    private Pasteleria pasteleria;
    private Random tiempo;
    
    public Horno (int tipo, Pasteleria past){
        this.tipo = tipo;
        this. pasteleria = past;
        this.tiempo = new Random();
    }
    
    public void run(){
        
        while (true){
            this.simularTiempo();
            this.pasteleria.agregarPastel(this.tipo);
        }
        
    }
    
    private void simularTiempo(){
        
        try {
            Thread.sleep((tiempo.nextInt(9)+1)*100);
        } catch (InterruptedException ex) {
            Logger.getLogger(Horno.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
