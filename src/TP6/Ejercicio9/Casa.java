package TP6.Ejercicio9;
import java.util.concurrent.Semaphore;

/*
 * @author Dana ~
 */

public class Casa {

    private Semaphore semAtencion;
    private Semaphore semBlancanieves;
    private Semaphore semEnanito;
    private Semaphore semSillas;
    
    public Casa (int sillas){
        this.semAtencion = new Semaphore(1);
        this.semBlancanieves = new Semaphore(0);
        this.semEnanito = new Semaphore(0,true);
        this.semSillas = new Semaphore(sillas,true);
    }
    
    // Metodos utilizado por Enanito.
    public void comer() throws InterruptedException{
        
        System.out.println(Thread.currentThread().getName()+ " llega a casa. Quiere comer.");
        semSillas.acquire(); // Se ocupa una silla.
        System.out.println(Thread.currentThread().getName()+ " ocupa la silla.");
        semAtencion.acquire(); // Blancanieves sirve a un solo enanito a la vez.
        System.out.println(Thread.currentThread().getName()+ " le pide a blancanieves comida.");
        semBlancanieves.release(); // Se libera a Blancanieves para que se ocupe de la comida.
        semEnanito.acquire(); // Se bloquea.
    }
    
    public void terminarDeComer(){
        
        System.out.println(Thread.currentThread().getName()+ " termina de comer. Se retira a trabajar.");
        semSillas.release(); // Libera un lugar
 
    }
    
    // Metodos utilizados por Blancanieves.
    public void esperarEnanito() throws InterruptedException{
        
        System.out.println(Thread.currentThread().getName()+ " comienza a pasear por la casa.");
        this.semBlancanieves.acquire();
    }
    
    public void servirComida(){
        
        System.out.println(Thread.currentThread().getName()+ " le entrega la comida al enanito.");
        semEnanito.release(); // Se libera al enanito para que siga con sus tareas.
        semAtencion.release(); // Se vuelve a permitir que un enanito pida comida.
    }
    
}
