
package TEORIA.TrenCyclicBarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;


public class Tren {
    
    // Recurso compartido.
    
    private CyclicBarrier barreraTren;

    public Tren (int cant, ControlTren controlador){
        this.barreraTren = new CyclicBarrier(cant, controlador);
    }
    
    public void subirseAlTren() throws InterruptedException, BrokenBarrierException{    
        System.out.println(Thread.currentThread().getName() + " intenta subirse al tren.");
        this.barreraTren.await(); // Los hilos quedan bloqueados hasta que se ocupen todos los asientos.
    }
 
}
