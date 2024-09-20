package Problema3;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Extractor implements Runnable{
  
    private final Buffer buffer;
    private final Random numRandom;
    
    public Extractor (Buffer buffer) {
        this.buffer = buffer;
        this.numRandom = new Random();
    }
    
    @Override
    public void run () {
        while (true) {
            try {
                this.buffer.extraerDato();
                simularDescanso();
            } catch (InterruptedException e) {
                Logger.getLogger(Extractor.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }
    
    private void simularDescanso () {
        try {
            Thread.sleep((numRandom.nextInt(9)+1)*1000);
        }catch (InterruptedException e) {
            Logger.getLogger(Extractor.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
}
