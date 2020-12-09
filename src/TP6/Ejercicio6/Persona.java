package TP6.Ejercicio6;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * @author Dana ~
 */

public class Persona implements Runnable{

    private char tipoPersona;
    private boolean enSilla;
    private Observatorio sala;
    private Random numRandom;
    
    public Persona(char tipo, boolean enSilla, Observatorio obs){
        this.tipoPersona = tipo;
        this.enSilla = enSilla;
        this.sala = obs;
        numRandom = new Random();
    }
    
    public void run(){
        
        try {
            
            simularTiempo('C');
            sala.entrarASala(tipoPersona, enSilla);
            simularTiempo(tipoPersona);
            sala.salirDeSala(tipoPersona, enSilla);
            
        } catch (InterruptedException ex) {
            Logger.getLogger(Persona.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    private void simularTiempo (char accion){
        
        String texto = "";
        switch(accion){
            
            case 'C': // Caminar
                texto = " esta diriguiendose hacia el observatorio...";
                break;
                
            case 'I': // Investigador.
                texto = " esta analizando las estrellas y realizando su investigacion...";
                break;
                
            case 'V': // Visitantes.
                texto = " esta estudiando las estrellas...";
                break;
                
            case 'P': // Mantenimiento.
                texto = " esta limpiando la sala...";
                break;
        }
        
        try {
            System.out.println("*** " + Thread.currentThread().getName() + texto);
            Thread.sleep((numRandom.nextInt(9)+1)*100);
        } catch (InterruptedException ex) {
            Logger.getLogger(Persona.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
