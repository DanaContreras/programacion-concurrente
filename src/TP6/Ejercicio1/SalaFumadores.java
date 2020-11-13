package TP6.Ejercicio1;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dana^^
 */

public class SalaFumadores {

    private int ingreFaltante;
    private String[] ingrediente;
    
    public SalaFumadores(){
        this.ingreFaltante = -1; // No hay ingredientes en la mesa.
        this.ingrediente = new String[]{"Tabaco", "Papel", "Fosforo"};
    }
    
    public synchronized void colocar(int numIngrediente){
        
        try {
            while (this.ingreFaltante != -1)
                this.wait();
            
            this.ingreFaltante = numIngrediente;
            
            int j = 0;
            String[] ingreEnMesa = new String[2];
            for (int i = 0; i < 3; i++) {
                if(this.ingreFaltante != i){
                    ingreEnMesa[j] = this.ingrediente[i];
                    j++;        
                }
            }
            
            System.out.println(Thread.currentThread().getName() + "coloco dos ingredientes: " + ingreEnMesa[0] + " y " + ingreEnMesa[1]);
            this.notifyAll();
        } catch (InterruptedException ex) {
            Logger.getLogger(SalaFumadores.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public synchronized void entraFumar(int id){
        
        try {
            while(this.ingreFaltante != id)
                this.wait();
            
            System.out.println(Thread.currentThread().getName() + " tiene el ingrediente faltante: " + this.ingrediente[id] + ". Prepara el cigarro.");
            
        } catch (InterruptedException ex) {
            Logger.getLogger(SalaFumadores.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public synchronized void terminaFumar(){
        
        System.out.println(Thread.currentThread().getName() + " termina de fumar.");
        this.ingreFaltante = -1;
        this.notifyAll();
        
    }
    
}
