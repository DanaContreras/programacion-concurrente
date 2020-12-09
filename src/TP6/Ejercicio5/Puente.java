package TP6.Ejercicio5;
import Utiles.Cola;
import java.util.concurrent.Semaphore;

/*
 * @author Dana ~
 */

public class Puente {

    private int turnoActual;
    private int generadorTurno;
    private int enEsperaNorte;
    private int enEsperaSur;
    
    private boolean esPrimerHilo; 
    private boolean direccion;
    private Cola cola;
    private Semaphore mutex;
    private Semaphore semPuente;
    
    
    public Puente (int cantMax){

        this.turnoActual = 1;
        this.generadorTurno = 0;
        this.cola = new Cola();
        this.mutex = new Semaphore(1);
        this.semPuente = new Semaphore (cantMax, true);
        this.semControl = new Semaphore(0);
    }
    
    public synchronized int pedirTurno(){
        // Metodo encargado de dar tu turno a cada hilo.
        generadorTurno++;
        return generadorTurno;
    }
    
    public void verificarDireccion(boolean direccion){
        
        mutex.acquire();
        boolean verificacion = (this.direccion == direccion);
        mutex.release();
        
    }
    
    public void entrarCocheDelNorte (int id) throws InterruptedException{
        
        esPrimero(true);
        
        mutex.acquire();
        enEsperaNorte++;
        mutex.release();
        
        this.semPuente.acquire();
        
        mutex.acquire();
        enEsperaNorte--;
        mutex.release();
     
    }
    
    public void entrarCocheDelSur(int id) throws InterruptedException{
        
        esPrimero(false);
        
        mutex.acquire();
        enEsperaSur++;
        mutex.release();
        
        this.semPuente.acquire();
        
        mutex.acquire();
        enEsperaSur--;
        mutex.release();
        
    }
    
    public void salirCocheDelNorte(int id){
        
        mutex.acquire();
        enEsperaNorte++;
        mutex.release();
        
    }
    
    public void salirCocheDelSur(int id){
        
    }
    
    private synchronized void esPrimero (boolean direccion){
        
        if (esPrimerHilo)
            this.direccion = direccion;
    }
    
    private void colaDeEspera(int id){
        // Metodo privadp que determina el primer 

        cola.poner(id);
    }
    
}
