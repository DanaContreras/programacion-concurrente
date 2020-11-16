package LectoresEscritoresMonitores;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Lector implements Runnable{

    private Libro libro;
    private int numPagina;
    private Random tiempo;
    
    public Lector(Libro libro){
        this.libro = libro;
        this.numPagina = 1;
        this.tiempo = new Random();
    }
    
    public void run(){
        
        while (this.libro.empezarALeer(numPagina)){          
            this.leer();
            this.libro.terminarDeLeer();
            this.numPagina++;
        }
        System.out.println(Thread.currentThread().getName() + " no puede leer mas, termino todas las paginas.");
    }
    
    private void leer(){
        
        try {
            System.out.println(Thread.currentThread().getName() + " esta leyendo...");
            Thread.sleep((this.tiempo.nextInt(10)+1)*100);
        } catch (InterruptedException ex) {
            Logger.getLogger(Escritor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
