package Hilos;

import RecursosCompartidos.MundoAventura;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControlSaltoEnAltura implements Runnable {

    private final MundoAventura mundoAventura;
    private final Random valorRandom;
    
    public ControlSaltoEnAltura (MundoAventura mundoAventura) {
        this.mundoAventura = mundoAventura;
        this.valorRandom = new Random();
    }

    @Override
    public void run() {
        while (true) {
            try {
                this.mundoAventura.esperarVisitantesSalto();
                simularTiempo(" da por finalizado la actividad de salto.");
                this.mundoAventura.terminarSalto();
            } catch (InterruptedException ex) {
                Logger.getLogger(ControlSaltoEnAltura.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void simularTiempo (String msj) {
        try {
            Thread.sleep((valorRandom.nextInt(9) + 1) * 1000);
            System.out.println(Thread.currentThread().getName() + msj);
        } catch (InterruptedException ex) {
            Logger.getLogger(ControlSaltoEnAltura.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
