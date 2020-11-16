package LectoresEscritoresMonitores;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dana^^
 */
public class Libro {

    private int pagEscritas;
    private int pagTotales;
    private int cantEnLibro;
    private int escritoresEnEspera;
    
    public Libro(int pagTotales){
        this.pagEscritas = 0;
        this.pagTotales = pagTotales;
        this.cantEnLibro = 0;
        this.escritoresEnEspera = 0;
    }
    
    public synchronized boolean empezarALeer (int numPag){
        
        boolean exito = true;
        
        System.out.println(Thread.currentThread().getName() + " quiere leer la pagina " + numPag + ".");
        
        if (numPag > this.pagTotales)
            exito = false;
        else{
            
            while (pagEscritas == 0 || numPag > pagEscritas  || escritoresEnEspera != 0){
                try {
                    this.wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Libro.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            this.cantEnLibro++;
        }
            
        
        return exito;
    }
    
    public synchronized void terminarDeLeer(){
        
        System.out.println(Thread.currentThread().getName() + " termino de leer una pagina.");
        this.cantEnLibro--;
        this.notifyAll();
    }
    
    
    public synchronized boolean empezarAEscribir(){
        
        System.out.println(Thread.currentThread().getName() + " quiere escribir.");
        
        boolean exito = false;
        
        while (cantEnLibro != 0){
            try {
                this.wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Libro.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if (this.pagEscritas < this.pagTotales){
            exito = true;
            this.cantEnLibro++;
        }
        
        return exito;
    }
    
    public synchronized void terminarDeEscribir(){
        
        this.pagEscritas++;
        System.out.println(Thread.currentThread().getName() + " termino de escribir la pagina " + this.pagEscritas + ".");
        this.cantEnLibro--;
        this.notifyAll();
    }
}
