package TP5.Ejercicio1;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Mascota1 implements Runnable{

    private char tipo;
    private Comedor1 comedor;
    
    public Mascota1 (char tipoAnimal, Comedor1 comedor){
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
            Logger.getLogger(Mascota1.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
