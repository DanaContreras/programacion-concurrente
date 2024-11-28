package Hilos;

import RecursosCompartidos.MundoAventura;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControlTirolesa implements Runnable{
    
    private final MundoAventura mundoAventura;
    private final Random valorRandom;
    
    public ControlTirolesa (MundoAventura mundoAventura) {
        this.mundoAventura = mundoAventura;
        this.valorRandom = new Random();
    }

    @Override
    public void run() {
        boolean dirOeste = true;
        while (true) {
            try {
                this.mundoAventura.esperarVisitantesTirolesa(dirOeste);
                simularTiempo(" termino recorrido tirolesa " + (dirOeste? "OESTE ----> ESTE" : "OESTE <---- ESTE") + ".");
                this.mundoAventura.terminarTirolesa(dirOeste);
                dirOeste = !dirOeste;
            } catch (InterruptedException ex) {
                Logger.getLogger(ControlTirolesa.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
        
    private void simularTiempo (String msj) {
        try {
            Thread.sleep((valorRandom.nextInt(9) + 1) * 1000);
            System.out.println(Thread.currentThread().getName() + msj);
        } catch (InterruptedException ex) {
            Logger.getLogger(ControlTirolesa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
