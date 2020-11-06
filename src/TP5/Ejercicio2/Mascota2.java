package TP5.Ejercicio2;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Mascota2 implements Runnable{

    private char tipo;
    private Comedor2 comedor;
    
    public Mascota2 (char tipoAnimal, Comedor2 comedor){
        this.tipo = tipoAnimal;
        this.comedor = comedor;
    }
    
    public void run(){
        
        boolean seguir = false;
        while(!seguir){
            
            seguir = this.comedor.verificarDisponibilidad(tipo);
            if (seguir){
                this.comedor.comenzarAComer(tipo);
                this.simularTiempo();
                this.comedor.terminarDeComer();
            }
            else
                this.comedor.esperar(tipo);
            
        }
    }
    
    public void simularTiempo(){
        
        Random tiempo = new Random();
        
        try {
            Thread.sleep((tiempo.nextInt(9)+1)*100);
        } catch (InterruptedException ex) {
            Logger.getLogger(Mascota2.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
