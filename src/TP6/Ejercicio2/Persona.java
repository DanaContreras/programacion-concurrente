package TP6.Ejercicio2;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dana^^
 */

public class Persona implements Runnable{

    protected Sala sala;
    protected Random tiempo;
    
    public Persona(Sala sala){
        this.sala = sala;
        this.tiempo = new Random();
    }
    
    public void run(){
        
        this.simularTiempo('E');
        this.sala.entrarSala();
        this.simularTiempo('S');
        this.sala.salirSala();
    }
    
    protected void simularTiempo(char accion){
        
        String texto;

        switch(accion){
            case 'E':
                texto = " esta caminando hacia la entrada de la sala.";
                break;
                
            case 'S':
                texto = " esta mirando las exposiciones del museo.";
                break;
                
            default:
                texto = "";
        }
        
        try {
            Thread.sleep((tiempo.nextInt(30)+1)*100);
            System.out.println(Thread.currentThread().getName() + texto);
        } catch (InterruptedException ex) {
            Logger.getLogger(Persona.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
