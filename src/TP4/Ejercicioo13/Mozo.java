package TP4.Ejercicioo13;
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
            this.confiteria.esperarAEmpleado();
            this.prepararPedido();
            this.confiteria.entregarPedido();
        }
    }
    
    private void prepararPedido(){
        // Metodo que simula la 
        
        Random tiempo = new Random();
        
        try {
            System.out.println(VERDE + Thread.currentThread().getName() + FINALIZAR_COLOR + " se encuentra preparando el pedido.\n");
            Thread.sleep((tiempo.nextInt(90) + 10) * 100);
        } catch (InterruptedException ex) {
            Logger.getLogger(Mozo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
