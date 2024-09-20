package Problema3;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Insertor implements Runnable{
    
    private final String[] dato;
    private final Buffer buffer;
    private final Random numRandom;
    
    public Insertor (Buffer buffer, String dato[]) {
        this.buffer = buffer;
        this.dato = dato;
        this.numRandom = new Random();
    }
    
    @Override
    public void run () {
        int numDato;
        try {
            while (true) {
                numDato = simularGeneracionDato();
                this.buffer.insertarDato(dato[numDato]);
                simularDescanso();
            }
        } catch (InterruptedException e) {
                Logger.getLogger(Insertor.class.getName()).log(Level.SEVERE, null, e);
            }
    }
    
    private int simularGeneracionDato () {
        int numDato = 0;
        try {
            Thread.sleep((numRandom.nextInt(9)+1)*1000);
            numDato = numRandom.nextInt(dato.length);
        }catch (InterruptedException e) {
            Logger.getLogger(Insertor.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return numDato;
    }
    
    private void simularDescanso () {
        try {
            Thread.sleep((numRandom.nextInt(9)+1)*1000);
        }catch (InterruptedException e) {
            Logger.getLogger(Insertor.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
}
