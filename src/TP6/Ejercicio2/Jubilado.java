package TP6.Ejercicio2;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dana^^
 */

public class Jubilado extends Persona implements Runnable{

    public Jubilado(Sala sala){
        super(sala);
    }
    
    public void run(){
        
        this.simularTiempo('E');
        this.sala.entrarSalaJubilado();
        this.simularTiempo('S');
        this.sala.salirSala();
    }
    
}
