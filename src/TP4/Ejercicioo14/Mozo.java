package TP4.Ejercicioo14;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Mozo implements Runnable{

    // Texto de color.
    public static final String VERDE = "\u001B[32m";
    public static final String FINALIZAR_COLOR = "\u001B[0m";
    
    
    private Confiteria confiteria;
    
    public Mozo (Confiteria lugar){
        this.confiteria = lugar;
    }
    
    public void run(){
        
        while (true){
            this.confiteria.mozoEsperaEmpleado();
            this.prepararPedido();
            this.confiteria.entregarBebida();
        }
    }
    
    private void prepararPedido(){
        // Metodo que simula el tiempo de preparación del pedido.
        
        Random tiempo = new Random();
        
        try {
            System.out.println(VERDE + Thread.currentThread().getName() + FINALIZAR_COLOR + " se encuentra preparando la bebida.");
            Thread.sleep((tiempo.nextInt(5) + 1) * 100);
        } catch (InterruptedException ex) {
            Logger.getLogger(Mozo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
