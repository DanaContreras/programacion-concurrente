package TP4.Ejercicioo13;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Empleado implements Runnable {

    // Texto de color.
    public static final String CYAN = "\u001B[36m";
    public static final String FINALIZAR_COLOR = "\u001B[0m";
    
    private Confiteria confiteria;
    
    public Empleado (Confiteria lugar){
        this.confiteria = lugar;
    }
    
    public void run(){
    
        this.caminarHaciaConfiteria();
        
        if (this.confiteria.verificarDisponibilidad()){
            this.confiteria.realizarPedido();
            this.confiteria.desocuparAsiento();
        }
        else
            System.out.println(CYAN + Thread.currentThread().getName() + FINALIZAR_COLOR + " no encontro asiento disponible, asi que se va.\n");
        
    }
    
    private void caminarHaciaConfiteria(){
        // Simula el tiempo de caminata hacia la confiteria.
        
        Random tiempo = new Random();
        
        try {
            System.out.println(CYAN + Thread.currentThread().getName() + FINALIZAR_COLOR + " se encuentra caminando hacia la confiteria.\n");
            Thread.sleep((tiempo.nextInt(500)+1) * 100);
        } catch (InterruptedException ex) {
            Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
