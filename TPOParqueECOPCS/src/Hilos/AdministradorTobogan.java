package Hilos;

import RecursosCompartidos.FaroMirador;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdministradorTobogan implements Runnable{
    
    private final FaroMirador faroMirador;
    private final Random valorRandom;
    
    public AdministradorTobogan (FaroMirador faroMirador) {
        this.faroMirador = faroMirador;
        this.valorRandom = new Random();
    }

    @Override
    public void run() {
        while (true) {
            try {
                this.faroMirador.esperarVisitante();
                this.faroMirador.administrarToboganes();
            } catch (InterruptedException ex) {
                Logger.getLogger(AdministradorTobogan.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }   
}
