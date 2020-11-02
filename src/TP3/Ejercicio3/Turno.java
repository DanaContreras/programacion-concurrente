
package TP3.Ejercicio3;

public class Turno {
    
    private int variableTurno = 1;
    
    public Turno(){
        this.variableTurno = 1;
    }
    
    public synchronized int getTurno(){
        return variableTurno;
    }
    
    public synchronized void modificarTurno(){
        this.variableTurno ++;
        
        if (variableTurno == 4)
            variableTurno = 1;
    }
    
}
