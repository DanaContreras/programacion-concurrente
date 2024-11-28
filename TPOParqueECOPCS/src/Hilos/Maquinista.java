package Hilos;

import java.util.Random;
import RecursosCompartidos.CarreraGomones;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Maquinista implements Runnable {
    
    private final CarreraGomones carrera;
    private final Random valorRandom;
    
    public Maquinista (CarreraGomones carrera) {
        this.carrera = carrera;
        this.valorRandom = new Random();
    }

    @Override
    public void run() {
        try {
            while (true) {
                this.carrera.esperarTrenCompleto();
                simularTiempo( " termino el recorrido del tren.");
                this.carrera.finalizarRecorridoTren();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Maquinista.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void simularTiempo(String msj) throws InterruptedException{
            Thread.sleep((valorRandom.nextInt(9) + 1) * 1000);
            System.out.println(Thread.currentThread().getName() + msj);
    }
    
}
