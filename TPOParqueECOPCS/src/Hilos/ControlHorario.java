package Hilos;

import java.util.logging.Level;
import java.util.logging.Logger;
import RecursosCompartidos.Ingreso;

public class ControlHorario implements Runnable{

    private final Ingreso ingreso;
    
    public ControlHorario (Ingreso ingreso) {
        this.ingreso = ingreso;
    }

    @Override
    public void run() {
        while (true) {
            try {
                this.ingreso.setHora();
                simularHora();
            } catch (InterruptedException ex) {
                Logger.getLogger(ControlHorario.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void simularHora() {
        try {
            Thread.sleep(10000);
            System.out.println(Thread.currentThread().getName() + " cambio la hora y ahora son las " + ingreso.getHora());
        } catch (InterruptedException ex) {
            Logger.getLogger(ControlHorario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
