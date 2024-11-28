
package Hilos;

import RecursosCompartidos.Snorkel;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AsistenteSnorkel implements Runnable {

    private final Snorkel snorkel;
    
    public AsistenteSnorkel (Snorkel snorkel) {
        this.snorkel = snorkel;
    }
    
    @Override
    public void run() {
        try {
            while (true) {
                this.snorkel.atenderVisitante();
                Thread.sleep(1000);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(AsistenteSnorkel.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
   
}
