package TP4.Ejercicioo14;
import java.util.concurrent.Semaphore;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.concurrent.locks.ReentrantLock;

public class Confiteria {

     // Texto de color.
    public static final String CYAN = "\u001B[36m";
    public static final String VERDE = "\u001B[32m";
    public static final String ROJO = "\u001B[31m";
    public static final String FINALIZAR_COLOR = "\u001B[0m";
    
    private int cantAsientos;
    private String[] bebida;
    private String[] comida;
    private ReentrantLock mutexAsientos;
    private Semaphore semEmpleado;
    private Semaphore semMozo;
    private Semaphore semCocinero;
    private Semaphore semAtencionMozo;
    private Semaphore semAtencionCocinero;
    
    public Confiteria (int cantAsientos, String[] bebida, String[] comida){
        
        this.cantAsientos = cantAsientos;
        this.bebida = bebida;
        this.comida = comida;
        this.mutexAsientos = new ReentrantLock();
        this.semEmpleado = new Semaphore (0, true);
        this.semMozo = new Semaphore (0);
        this.semCocinero = new Semaphore (0);
        this.semAtencionMozo = new Semaphore(1);
        this.semAtencionCocinero = new Semaphore(1);
    }
    
    // Metodos utilizados por Empleado.
    
    public boolean verificarDisponibilidad() {
        
        boolean disponible = false;
        try {
            
            this.mutexAsientos.lock();
            if (this.cantAsientos > 0){
                cantAsientos --;
                disponible = true;
                System.out.println(CYAN + Thread.currentThread().getName() + FINALIZAR_COLOR + " encontro un asiento disponible.");
            
            }
        } finally {
            this.mutexAsientos.unlock();
        }
        
        return disponible;
    }
    
    public void elegirMenu(){
        
            System.out.println(CYAN + Thread.currentThread().getName() + FINALIZAR_COLOR + " eligio menu.");
            Random num = new Random();
            int pedido = num.nextInt(3); 
            
            switch(pedido){
                case 0:
                    pedirBebida();
                    break;
                case 1:
                    pedirComida();
                    break;
                case 2:
                    pedirBebidaYComida();
                    break;
            }
    }
    
    private void pedirBebida(){
        
        try {
            semAtencionMozo.acquire();
            Random num = new Random();
            System.out.println(CYAN + Thread.currentThread().getName() + FINALIZAR_COLOR + " pidio " + bebida[num.nextInt(bebida.length)]);
            this.semMozo.release(); // El mozo comienza a hacer su trabajo.
            this.semEmpleado.acquire(); // El empleado espera su pedido.
        } catch (InterruptedException ex) {
            Logger.getLogger(Confiteria.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void pedirComida(){
        
        try {
            semAtencionCocinero.acquire();
            Random num = new Random();
            System.out.println(CYAN + Thread.currentThread().getName() + FINALIZAR_COLOR + " pidio " + comida[num.nextInt(comida.length)]);
            this.semCocinero.release();
            this.semEmpleado.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(Confiteria.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void pedirBebidaYComida(){
    
        System.out.println(CYAN + Thread.currentThread().getName() + FINALIZAR_COLOR + " piensa comprar bebida y comida.");
        this.pedirBebida();
        this.pedirComida();
  
    }
    
    public void desocuparAsiento(){
        try {
            
            System.out.println(CYAN + Thread.currentThread().getName() + FINALIZAR_COLOR + " termino de comer. Se retira de la confiteria.");
            this.mutexAsientos.lock();
            this.cantAsientos++;

        } finally {
            this.mutexAsientos.unlock();
        }
    }
    
    
    // Metodos utilizados por Mozo.
    
    public void mozoEsperaEmpleado(){
        try {
            System.out.println(VERDE + Thread.currentThread().getName() + FINALIZAR_COLOR + " esta esperando a un empleado mientras inventa nuevas versiones de pollo.");
            this.semMozo.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(Confiteria.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void entregarBebida(){
        System.out.println(VERDE + Thread.currentThread().getName() + FINALIZAR_COLOR + " entrego la bebida.");
        this.semEmpleado.release();
        this.semAtencionMozo.release(); // El mozo se encuentra disponible.
    }
    
    
    // Metodos utilizado por Cocinero.
    
     public void cocineroEsperaEmpleado(){
         
        try {
            System.out.println(ROJO + Thread.currentThread().getName() + FINALIZAR_COLOR + " esta esperando a un empleado mientras ordena su cocina.");
            this.semCocinero.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(Confiteria.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
     public void entregarComida(){
        System.out.println(ROJO + Thread.currentThread().getName() + FINALIZAR_COLOR + " entrego la comida.");
        this.semEmpleado.release();
        this.semAtencionCocinero.release(); // El cocinero se encuentra disponible.
     }
}
