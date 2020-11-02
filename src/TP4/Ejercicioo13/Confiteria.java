package TP4.Ejercicioo13;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Confiteria {

     // Texto de color.
    public static final String CYAN = "\u001B[36m";
    public static final String VERDE = "\u001B[32m";
    public static final String FINALIZAR_COLOR = "\u001B[0m";
    
    private Semaphore semAsiento;
    private Semaphore semEmpleado;
    private Semaphore semMozo;
    
    public Confiteria (){
        
        this.semAsiento = new Semaphore (1);
        this.semEmpleado = new Semaphore (0, true);
        this.semMozo = new Semaphore (0);
        
    }
    
    // Metodos utilizados por Empleado.
    
    public boolean verificarDisponibilidad() {
        return this.semAsiento.tryAcquire();
    }
    
    public void realizarPedido(){
        
        try {
            
            System.out.println(CYAN + Thread.currentThread().getName() + FINALIZAR_COLOR + " encontro un asiento disponible. Realiza su pedido.\n");
            
            this.semMozo.release(); // El mozo comienza a hacer su trabajo.
            this.semEmpleado.acquire(); // El empleado espera su pedido.
            
        } catch (InterruptedException ex) {
            Logger.getLogger(Confiteria.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void desocuparAsiento(){
        this.semAsiento.release();
    }
    
    
    // Metodos utilizados por Mozo.
    
    public void esperarAEmpleado(){
        try {
            System.out.println(VERDE + Thread.currentThread().getName() + FINALIZAR_COLOR + " esta esperando a un empleado.\n");
            this.semMozo.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(Confiteria.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void entregarPedido(){
        
        System.out.println(VERDE + Thread.currentThread().getName() + FINALIZAR_COLOR + " entrego el pedido.\n");
        this.semEmpleado.release();
    }
}
