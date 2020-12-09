package EjercParcial.Atomos;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

/*
 * @author Dana ~
 */

public class Agua {

    private final int capacidadTotal;
    private int capacidadActual;
    private int cantOxigeno;
    private int cantHidrogeno;
    private int cantDespiertos; // limita los hilos de Hidrogeno despiertos.
    private int ordenLlegada; // turno.
    private final ReentrantLock lockAtomo;
    private final Condition oxigenoListo;
    private final Condition hidrogenoListo;
    private final ReentrantLock lockAgua;

    public Agua(int capacidad) {
        this.capacidadTotal = capacidad;
        this.capacidadActual = 0;
        this.cantOxigeno = 0;
        this.cantHidrogeno = 0;
        this.cantDespiertos = 0;
        this.ordenLlegada = 0;
        this.lockAtomo = new ReentrantLock(true);
        this.oxigenoListo = lockAtomo.newCondition();
        this.hidrogenoListo = lockAtomo.newCondition();
        this.lockAgua = new ReentrantLock(true);    
    }

    public boolean OListo() throws InterruptedException {

        boolean hacerAgua = false;

        this.lockAtomo.lock();
        
        cantOxigeno++;
        
        while (cantHidrogeno < 2) {
            
            System.out.println("***** " + Thread.currentThread().getName()+ " espera a que se cumplan las condiciones...");
            oxigenoListo.await();
        }

        System.out.println(Thread.currentThread().getName()+ " esta listo.");
        
        ordenLlegada++;
        if (ordenLlegada == 1) { // es el  encargado de notificar a los dos atomos de hidrogeno.
            System.out.println(Thread.currentThread().getName()+ " encontro dos atomos de Hidrogeno.");
            hidrogenoListo.signalAll();
            
        } else if (ordenLlegada == 3){ // como es el ultimo atomo, el mismo es el encargado de llamar al metodo hacerAgua().
            hacerAgua = true;
            actualizarValores();
        }
            
        this.lockAtomo.unlock();

        return hacerAgua;
    }

    public boolean HListo() throws InterruptedException {

        boolean hacerAgua = false;
        
        lockAtomo.lock();
        
        cantHidrogeno++;

        while (cantOxigeno < 0  || cantHidrogeno <2 || cantDespiertos > 2) {
            
            System.out.println("***** " + Thread.currentThread().getName()+ " espera a que se cumplan las condiciones...");
            hidrogenoListo.await();
            cantDespiertos ++;
        }
        
        System.out.println(Thread.currentThread().getName()+ " esta listo.");
        
        ordenLlegada++;
        
        if (ordenLlegada == 1) {
            System.out.println(Thread.currentThread().getName()+ " encontro un atomo de H y otro de O.");
            cantDespiertos++;
            oxigenoListo.signal();
            hidrogenoListo.signal();
        }
        else if (ordenLlegada == 3){
            hacerAgua = true;
            actualizarValores();
        }

        lockAtomo.unlock();

        return hacerAgua;
    }

    private void actualizarValores(){
        
        cantHidrogeno -= 2;
        cantOxigeno--;
        ordenLlegada = 0;
        cantDespiertos = 0;
    }
    
    public void hacerAgua() {

        lockAgua.lock();
        System.out.println(Thread.currentThread().getName()+ " invoca el metodo hacerAgua().");
        
        capacidadActual++;
        if (capacidadActual == capacidadTotal){
            System.out.println("Se llena recipiente. Se envia para su distribución.");
            capacidadActual = 0;
        }
        else
            System.out.println("Se llena un el recipiente con agua. Falta " + (capacidadTotal-capacidadActual) + " para llenarse.");
        
        lockAgua.unlock();

    }
    
}
