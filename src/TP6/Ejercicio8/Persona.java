package TP6.Ejercicio8;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * @author Dana ~
 */

public class Persona implements Runnable{

    private int turno;
    private CentroHemoterapia centro;
    private Random numRandom;
    
    public Persona(CentroHemoterapia centro){
        this.centro  = centro;
        this.numRandom = new Random();
    }
    
    public void run(){
        
        this.simularTiempo('C');
        this.turno = centro.solicitarTurno();
        if (!this.centro.verificarTurno(turno)){
            int decision = this.numRandom.nextInt(2);
            this.centro.esperarEnSala(decision, turno);
        }
        this.centro.entrarACentro();
        this.simularTiempo('D');
        this.centro.salirDeCentro();
    }
    
                
    private void simularTiempo (char accion){
        
        String texto = "";
        switch(accion){
            
            case 'C': //caminata.
                texto = " se dirige al establecimiento...";
                break;
                
            case 'D': //donacion.
                texto = " esta donando sangre...";
                break;
        }
        
        try {
            System.out.println(Thread.currentThread().getName() + texto);
            Thread.sleep((numRandom.nextInt(9)+1)*100);
        } catch (InterruptedException ex) {
            Logger.getLogger(Persona.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
