
package Hilos;

import RecursosCompartidos.NadoDelfines;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InstructorDelfines implements Runnable {

    private final int duracion;
    private final NadoDelfines nadoDelfines;
    
    public InstructorDelfines (NadoDelfines nadoDelfines, int duracion) {
        this.nadoDelfines = nadoDelfines;
        this.duracion = duracion;
    }
    
    @Override
    public void run() {
        while (true) {
            try {
                this.nadoDelfines.esperarComienzoActividad();
                this.nadoDelfines.esperarGrupoIncompleto();
                simularTiempo();
                this.nadoDelfines.terminarActividad();
                this.nadoDelfines.esperarFinalizacionActividad();
            } catch (InterruptedException ex) {
                Logger.getLogger(InstructorDelfines.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    private void simularTiempo() {
        try {
            System.out.println(Thread.currentThread().getName() + " comienza actividad!");
            Thread.sleep(duracion*1000);
            System.out.println(Thread.currentThread().getName() + " termino la actividad en las piletas con delfines.");
        } catch (InterruptedException ex) {
            Logger.getLogger(InstructorDelfines.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
