package Problema1;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Persona implements Runnable{
    
    private final Turno turno;
    private final int[] eleccion;
    private final Random valorRandom;
    
    public Persona (Turno turno) {
        this.turno = turno;
        this.valorRandom = new Random();
        this.eleccion = new int[2];
    }
    
    @Override
    public void run () {
        try {
            this.turno.pedirTurno();

            eleccion[0] = elegirActividad();
            eleccion[0] = this.turno.pedirActividadUno(eleccion);
            this.turno.realizarActividad(eleccion[0], 1);
            this.turno.terminarActividad(eleccion[0]);

            eleccion[1] = elegirActividad();
            eleccion[1] = this.turno.pedirActividadDos(eleccion);
            this.turno.realizarActividad(eleccion[1], 2);
            this.turno.terminarActividadDos(eleccion[1]);
        } catch (InterruptedException e) {
            Logger.getLogger(Persona.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    private int elegirActividad() {

        int excluir = this.eleccion[0]; // se evita la primera actividad.
        int randomNumber = valorRandom.nextInt(2);
        
        if (randomNumber >= excluir)
            randomNumber++;
        
        return randomNumber;
    }
}
