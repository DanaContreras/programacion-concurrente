package TP4.Ejercicioo14;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cocinero implements Runnable{

    // Texto de color.
    public static final String ROJO = "\u001B[31m";
    public static final String FINALIZAR_COLOR = "\u001B[0m";
    
    private Confiteria confiteria;
    
    public Cocinero (Confiteria lugar){
        this.confiteria = lugar;
    }
    
    public void run(){
        while (true){
            this.confiteria.cocineroEsperaEmpleado();
            this.prepararComida();
            this.confiteria.entregarComida();
        }
    }
    
    private void prepararComida(){
        // Simila el tiempo de preparacion.
        
        try {
            Random tiempo = new Random();
            System.out.println(ROJO + Thread.currentThread().getName() + FINALIZAR_COLOR + " se encuentra preparando la comida.");
            Thread.sleep((tiempo.nextInt(9) + 1) * 100);
        } catch (InterruptedException ex) {
            Logger.getLogger(Cocinero.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    }
    
}
