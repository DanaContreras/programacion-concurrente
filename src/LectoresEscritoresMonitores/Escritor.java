/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LectoresEscritoresMonitores;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Dana^^
 */
public class Escritor implements Runnable{

    private Libro libro;
    private Random tiempo;
    
    public Escritor(Libro libro){ 
        this.libro = libro;
        this.tiempo = new Random();
    }
    
    public void run(){
        
        while(this.libro.empezarAEscribir()){
            this.escribir();
            this.libro.terminarDeEscribir();
        }
        System.out.println(Thread.currentThread().getName() + " no puede escribir. Libro terminado.");
    }
    
    private void escribir(){
        
        try {
            System.out.println(Thread.currentThread().getName() + " esta escribiendo...");
            Thread.sleep((this.tiempo.nextInt(10)+1)*100);
        } catch (InterruptedException ex) {
            Logger.getLogger(Escritor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
