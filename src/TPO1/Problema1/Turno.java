package Problema1;
import java.util.concurrent.Semaphore;

public class Turno {
    
    // Recurso compartido entre hilos Persona.
    
    private int cantEnActividad;
    private final int maxCapacidad;
    private final String[] actividad;
    private final Semaphore semInstructor;
    private final Semaphore semPersonaEnActividad;
    private final Semaphore semPersonaEsperando;
    private final Semaphore semTurno;
    private final Semaphore semAcroTela;
    private final Semaphore semLyraAcrobatica;
    private final Semaphore semYogaEnAro;
    private final Semaphore semMutex; // para la variable cantEnActividad.
   
    public Turno (int capacidadTurno, String[] actividad) {
        int cupo = (int) (capacidadTurno / 3);
        this.cantEnActividad = 0;
        this.maxCapacidad = capacidadTurno;
        this.actividad = actividad;
        this.semInstructor = new Semaphore(0);
        this.semPersonaEnActividad = new Semaphore(0);
        this.semPersonaEsperando = new Semaphore(0);
        this.semTurno = new Semaphore(maxCapacidad, true);
        this.semAcroTela = new Semaphore(cupo, true);
        this.semLyraAcrobatica = new Semaphore(cupo, true);
        this.semYogaEnAro = new Semaphore(cupo, true);
        this.semMutex = new Semaphore(1);
    }
   
    // metodos de instructor ------------------------------------------------------------------------------------------------------------------------------------------------------------
    public void esperarActividadCompleta() throws InterruptedException{
        // el instructor espera que se llenen los cupos
        this.semInstructor.acquire();
    }
    
    public void finalizarActividad (int parte) {
        System.out.println(Thread.currentThread().getName() + " termino la parte " + parte + " de las actividades.");
        this.semPersonaEnActividad.release(maxCapacidad);
    }
    
    // metodos de persona -------------------------------------------------------------------------------------------------------------------------------------------------------------
    public void pedirTurno() throws InterruptedException {
         this.semTurno.acquire();
         System.out.println(Thread.currentThread().getName() + " consiguio un nuevo turno en el salon.");
    }
    
    public int pedirActividadUno (int[] eleccion) throws InterruptedException {
        int numActividad = eleccion[0];
        
        System.out.println(Thread.currentThread().getName() + " quiere como primera actividad: " + actividad[numActividad]);
        
        if (!entrarActividad(numActividad)) {
            numActividad = (numActividad + 1) % 3; // elije otra actividad
            System.out.println(Thread.currentThread().getName() + " no encontro cupo en " + actividad[eleccion[0]] + ", intentará en " + actividad[numActividad]);
            if (!entrarActividad(numActividad)) {
                numActividad = (numActividad + 1) % 3;
                entrarActividad(numActividad); 
                System.out.println(Thread.currentThread().getName() + " por descarte hara " + actividad[numActividad]);
            }
        }
        
        return numActividad;
    }
    
    public void realizarActividad(int numActividad, int parte) throws InterruptedException{
        System.out.println(Thread.currentThread().getName() + " realizara la actividad " + actividad[numActividad] + " en la parte " + parte);
        
        this.semMutex.acquire();
        cantEnActividad ++;
        if (cantEnActividad == maxCapacidad)
            this.semInstructor.release(); // el ultimo hilo desbloquea al instructor.
        this.semMutex.release();
     
        this.semPersonaEnActividad.acquire(); // el hilo se bloquea hasta que el hilo instructor lo libere.
    }
    
    public void terminarActividad(int numActividad) throws InterruptedException{
        System.out.println(Thread.currentThread().getName() + " termino la actividad " + actividad[numActividad]);
        
        salirActividad (numActividad);
        
        this.semMutex.acquire();
        cantEnActividad--;
        if (cantEnActividad == 0)  {// el ultimo hilo que termina su actividad, libera los permisos para que comience la segunda parte del turno.
            this.semPersonaEsperando.release(maxCapacidad);
            System.out.println(Thread.currentThread().getName() + " fue la ultima persona en terminar su actividad.");
        }
        this.semMutex.release();
        
        this.semPersonaEsperando.acquire();
    }
    
    public int pedirActividadDos (int[] eleccion) throws InterruptedException {
        
        int numActividad = eleccion[1];
        
        System.out.println(Thread.currentThread().getName() + " quiere como segunda actividad: " + actividad[numActividad]);
        
        if (!entrarActividad(numActividad)) {
            numActividad = 3 - (eleccion[0] + eleccion[1]); // entra a la actividad restante (no es segunda actividad elegida)
            System.out.println(Thread.currentThread().getName() + " no encontro cupo en " + actividad[eleccion[1]] + ", intentará en " + actividad[numActividad]);
            if (!entrarActividad(numActividad)) {
                numActividad = eleccion[0];
                entrarActividad(numActividad); // no pudo entrar a ninguna otra y se queda con la primera actividad elegida.
                System.out.println(Thread.currentThread().getName() + " no encontro cupo y repetira la actividad " + actividad[numActividad]);
            }
        }
    
        return numActividad;
    }
    
    public void terminarActividadDos (int numActividad) throws InterruptedException{
        System.out.println(Thread.currentThread().getName() + " termino la actividad " + actividad[numActividad]);
        
        salirActividad(numActividad);
        
        // asegura que todas las personas salieron de las actividades, y permite que nuevos hilos comiencen un turno nuevo. 
        this.semMutex.acquire();
        cantEnActividad--;
        if (cantEnActividad == 0){
            this.semTurno.release(maxCapacidad);
        }
        this.semMutex.release();
       
        System.out.println(Thread.currentThread().getName() + " termino turno. Es hora de ir a casa.");
    }
    
    private boolean entrarActividad (int numActividad) {
        boolean entro = false;
        
        switch (numActividad) {
            case 0 -> entro = this.semAcroTela.tryAcquire();
            case 1 -> entro = this.semLyraAcrobatica.tryAcquire();
            case 2 -> entro = this.semYogaEnAro.tryAcquire();
        }
       
        if (entro)
            System.out.println(Thread.currentThread().getName() + " consiguio cupo en " + actividad[numActividad]);
        
        return entro;
    }
    
    private void salirActividad (int numActividad) {
        switch (numActividad) {
            case 0 -> this.semAcroTela.release();
            case 1 -> this.semLyraAcrobatica.release();
            case 2 -> this.semYogaEnAro.release();
        }
       
        System.out.println(Thread.currentThread().getName() + " libero cupo en " + actividad[numActividad]);
    }
    
}
