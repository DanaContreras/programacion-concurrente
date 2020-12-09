package TP5.Ejercicio2;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Comedor2 {

    // Recurso compartido.
    
    private int cantComederos;
    private int cantComiendo;
    private int cantComieron;
    private int cantMax; // Indica la cantidad maxima permitida de mascotas de la misma especie que ya comieron.
    private int cantEnEspera;
    private int cantEsperaMismaEspecie;
    private char turno;
    private char[] especie;
    private Semaphore semComedero;
    private Semaphore semEnEspera;
    private ReentrantLock cerrojo;
    
    public Comedor2 (int cantComederos, int cantMax, char[] especie){
        
        this.cantComederos = cantComederos;
        this.cantComiendo = 0;
        this.cantComieron = 0;
        this.cantMax = cantMax;
        this.cantEnEspera = 0;
        this.cantEsperaMismaEspecie = 0;
        this.turno = especie[(new Random()).nextInt(especie.length)];
        this.semComedero = new Semaphore(this.cantComederos);
        this.semEnEspera = new Semaphore (0);
        this.especie = especie;
        this.cerrojo = new ReentrantLock();
    }
    
    public boolean verificarDisponibilidad(char tipo){
        
        boolean verificacion;

        try{
            this.cerrojo.lock();
            verificacion = (this.turno == tipo && this.cantComieron < this.cantMax);
                
        } finally{
            this.cerrojo.unlock();
        }
       
        return verificacion;
    }
    
    public void esperar(char tipo){
        try {
            this.cerrojo.lock();
            System.out.println(Thread.currentThread().getName() + " no puede comer en este momento. Espera su turno.");
            if (this.turno == tipo)
                this.cantEsperaMismaEspecie++;
            this.cantEnEspera++;
            this.cerrojo.unlock();
            
            this.semEnEspera.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(Comedor2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void comenzarAComer(char tipo){
        
        int cantOcupar = 1;
        
        try {
            this.cerrojo.lock();
            this.cantComiendo++;
            this.cantComieron++;
            System.out.println(Thread.currentThread().getName() + " entra al comedero.");
            
            if (this.turno == 'P')
                cantOcupar++;
        } finally{
            this.cerrojo.unlock();
        }
            
        try {   
            this.semComedero.acquire(cantOcupar);
            System.out.println(Thread.currentThread().getName() +  " comienza a comer.");
        }   catch (InterruptedException ex) {
            Logger.getLogger(Comedor2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void terminarDeComer(){
        
        try{
            this.cerrojo.lock();
            
            System.out.println(Thread.currentThread().getName() + " termina de comer. Se va.");
            
            cantComiendo--;
            
            if (cantComiendo == 0){
                
                int otrasEspecies = this.cantEnEspera - this.cantEsperaMismaEspecie;
                if (otrasEspecies > 0){
                    
                    // Se cambia de turno.
                    boolean seguir = true;
                    int i = 0;
                    while(seguir){
          
                        if (this.turno == this.especie[i]){
                            this.turno = this.especie[(i+1) % this.especie.length];
                            seguir = false;
                        }
                        i++;
                    }
                    
                    this.cantEsperaMismaEspecie = 0;
                }
                
                if (this.cantEnEspera > 0){
                    this.cantComieron = 0;
                    this.semEnEspera.release(this.cantEnEspera);
                    this.cantEnEspera = 0;
                }
                System.out.println("semEspera = " + semEnEspera.availablePermits());
            }
            
            int cantOcupar = 1;
            if (this.turno == 'P')
                cantOcupar++;
            this.semComedero.release(cantOcupar);
        } finally{
            this.cerrojo.unlock();
        }
        
    }
}
