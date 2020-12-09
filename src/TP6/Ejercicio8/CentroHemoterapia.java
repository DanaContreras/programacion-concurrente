package TP6.Ejercicio8;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
import Utiles.Cola;

/*
 * @author Dana ~
 */

public class CentroHemoterapia {

    private int turnoActual; //Manejo de turno como un rango. Turno esta en el rango 
    private int proximoTurno;
    private int numCamas;
    private int numSillasEspera;
    private int numRevistas;
    private Cola ordenTurno;
    private ReentrantLock lock;
    private Condition esperarRevista;
    private Condition esperarCentro;
    
    public CentroHemoterapia(int camas, int sillas, int revistas){
        
        this.turnoActual = camas;
        this.proximoTurno = 0;
        this.numCamas = camas;
        this.numSillasEspera = sillas;
        this.numRevistas = revistas;
        this.ordenTurno = new Cola();
        this.lock = new ReentrantLock();
        this.esperarRevista = lock.newCondition();
        this.esperarCentro = lock.newCondition();
    }
    
    public int solicitarTurno(){
        
        this.lock.lock();
        System.out.println(Thread.currentThread().getName()+" llega al lugar y saca turno.");
        proximoTurno++;
        this.lock.unlock();
        
        return proximoTurno;
    }
    
    public boolean verificarTurno (int turno){
        
        boolean exito = false;
        
        this.lock.lock();
        exito = (turno <= this.turnoActual);
        this.lock.unlock();
        
        return exito;
    }
    
    public void esperarEnSala(int decision, int turno){
        
        try{
            this.lock.lock();
            
            boolean seguir, enSilla = (decision == 0 && numSillasEspera > 0), conRevista;
            this.ordenTurno.poner(turno);
            
            if (enSilla){ // Persona decide usar una silla.
                numSillasEspera--;
                System.out.println(Thread.currentThread().getName()+ " se sienta en una silla.");
            }
            else
                System.out.println(Thread.currentThread().getName()+ " espera parada.");
            
            seguir = true;
            while (numRevistas == 0 && seguir){
                System.out.println(Thread.currentThread().getName()+ " no consigue revista. Comienza a ver tele.");
                this.esperarRevista.await();
                seguir = (turno>this.turnoActual) || ((int)this.ordenTurno.obtenerFrente()!= turno);
            }
            
            conRevista = (turno > this.turnoActual);
            
            if (conRevista){
                numRevistas--;
                System.out.println(Thread.currentThread().getName()+ " agarra una revista.");
                this.esperarCentro.await();
                conRevista = (this.turnoActual != turno);
            }
            
            if (enSilla)
                numSillasEspera++;
            
            if (conRevista){
                numRevistas++;
                this.esperarRevista.signal();
            }    
            
            this.ordenTurno.sacar();
            
        }catch(InterruptedException exc){
            
        }finally{
            this.lock.unlock();
        }
    }
    
    public void entrarACentro (){
        

        try{
            this.lock.lock();

            numCamas--;
            System.out.println(Thread.currentThread().getName()+" entra al centro a donar sangre.");
            
        } finally{
            this.lock.unlock();
        }  
        
    }
    
    public void salirDeCentro (){
        
        try{
            this.lock.lock();
            
            this.numCamas++;
            System.out.println(Thread.currentThread().getName() + " sale del establecimiento.");
            this.turnoActual++;
            
            this.esperarCentro.signalAll();
            this.esperarRevista.signalAll();
            
        } finally {
            this.lock.unlock();
        }
        
    }
}
