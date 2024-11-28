package Hilos;

import RecursosCompartidos.ColectivoFolklorico;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conductor implements Runnable{
    
    private final ColectivoFolklorico colectivo;
    private final Random valorRandom;
    
    public Conductor (ColectivoFolklorico colectivo) {
        this.colectivo = colectivo;
        this.valorRandom = new Random();
    }

    @Override
    public void run() {
        while (true) {
            try {
                this.colectivo.esperarVisitantes();
                simularTiempo(" termino recorrido y llego al parque.");
                this.colectivo.terminarRecorrido();
            } catch (InterruptedException ex) {
                Logger.getLogger(Conductor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void simularTiempo (String msj) {
        try {
            Thread.sleep((valorRandom.nextInt(9) + 1) * 1000);
            System.out.println(Thread.currentThread().getName() + msj);
        } catch (InterruptedException ex) {
            Logger.getLogger(Conductor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
