package EjercParcial.Atomos;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * @author Dana ~
 */

public class Atomo implements Runnable{

    private char tipo;
    private Agua generacion;
    private Random numRandom;
    
    public Atomo(Agua gen, char tipo){
       
        this.tipo = tipo;
        this.generacion = gen;
        this.numRandom = new Random();
    }
    
    public void run(){
        
        try {
            this.simularTiempo();
            
            boolean hacerAgua;
            
            if (tipo == 'O')
                hacerAgua = this.generacion.OListo();
            else
                hacerAgua = this.generacion.HListo();
            
            if (hacerAgua)
                this.generacion.hacerAgua();
            
        } catch (InterruptedException ex) {
            Logger.getLogger(Atomo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void simularTiempo (){
        
        try {
            System.out.println(Thread.currentThread().getName() + " esta vagando por el espacio...");
            Thread.sleep((numRandom.nextInt(9)+1)*1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Atomo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
