package Problema1;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Instructor implements Runnable {
    
    private final Turno turno;
    private final Random numRandom;
    
    public Instructor (Turno turno) {
        this.turno = turno;
        this.numRandom = new Random();
    }
    
    @Override
    public void run () {
        try {
            while (true) {
                this.turno.esperarActividadCompleta();
                darActividad(1);
                this.turno.finalizarActividad(1);
                this.turno.esperarActividadCompleta();
                darActividad(2);
                this.turno.finalizarActividad(2);
            }
        } catch (InterruptedException e) {
                Logger.getLogger(Instructor.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    private void darActividad (int numAct) {
        try {
            System.out.println(Thread.currentThread().getName() + " va a comenzar las actividades de la parte " + numAct);
            Thread.sleep((numRandom.nextInt(9) + 1) * 1000);
            System.out.println(Thread.currentThread().getName() + " dio las actividades de la parte " + numAct);
        } catch (InterruptedException e) {
            Logger.getLogger(Instructor.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
}
