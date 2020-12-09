package TP6.Ejercicio5;

/*
 * @author Dana ~
 */

public class ControlPuente implements Runnable{

    private Puente puente;
    
    public ControlPuente(Puente puente){
        this.puente = puente;
    }
    
    public void run (){
        
        while (true){
            puente.permitirAcceso();
            puente.bloquearAutos();
            
        }
    }
    
}
