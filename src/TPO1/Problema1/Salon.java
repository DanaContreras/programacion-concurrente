package Problema1;
import Utiles.TextoAColor;

/*

    Mecanismo de sincronizacion elegido: Semaforos.
    - Se aprovecha los semaforos generales y el metodo tryAcquire() para controlar los cupos de cada actividad (semAcroTela, semLyraAcrobatica, semYogaEnAro).
    - Se utiliza un semaforo binario semMutex, para proteger la variable compartida que controla la cantidad de hilos que siguen en una actividad.
    - Se utiliza el semaforos general semPersonaEnActividad para esperar que todos los hilos terminen la primera parte, para pasar a la eleccion de la segunda parte.
    - Se utiliza el semaforo general semPersonaEsperando para simular que los hilos se encuentran en una actividad.
    - Se utiliza el semaforo binario semInstructor, el cual se libera una vez que se completo la capacidad del turno y se bloquea una vez terminado una parte del turno.

    Decisiones:
    - Se asume que la primera actividad tambien es elegida en el momento de forma aleatoria.
    - Se asume un hilo Instructor para una mejor gestion del inicio y finalización de cada parte del turno.
  
*/

public class Salon {
    
    public static void main(String[] args) {
        
        TextoAColor txtColor = new TextoAColor();
        
        int capacidadTurno = 12;
        int cantPersonas = 24;
        String[] actividad = {txtColor.cambiarColor("Rojo", "acro telas"), txtColor.cambiarColor("Verde", "lyra acrobatica"), txtColor.cambiarColor("Azul", "yoga en aro")};
        
        // recurso compartido
        Turno turno  = new Turno (capacidadTurno, actividad);
        
        // creacion de hilos
        Thread instructor = new Thread (new Instructor(turno), txtColor.cambiarColor("Amarillo", "INSTRUCTOR"));
        Thread[] persona = new Thread[cantPersonas];
        
        for (int i = 0; i < cantPersonas; i++)
                persona[i] = new Thread (new Persona (turno), txtColor.cambiarColorTexto(i, "Persona " + (i + 1)));
            
        for (int i = 0; i < cantPersonas; i++)
            persona[i].start();
        
        instructor.start();
    }
    
}
