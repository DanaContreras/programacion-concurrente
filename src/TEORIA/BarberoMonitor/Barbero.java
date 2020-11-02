package TEORIA.BarberoMonitor;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Barbero implements Runnable {

    private BarberiaConMonitor barberia;
    
    public Barbero (BarberiaConMonitor b){
        this.barberia = b;
    }
        
    public void run (){

        while (true){
            this.barberia.esperarCliente();
            this.simularTiempoTrabajo();
            this.barberia.terminarCorte();
        }

    }

    private void simularTiempoTrabajo(){
        
        Random tiempo = new Random();
        
        try {
            System.out.println(Thread.currentThread().getName() + " se encuentra cortando el pelo.");
            Thread.sleep((tiempo.nextInt(9)+1) *100);
        } catch (InterruptedException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
