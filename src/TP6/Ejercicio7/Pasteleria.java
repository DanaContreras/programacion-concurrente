package TP6.Ejercicio7;
import Utiles.Cola;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

/*
 * @author Dana ~
 */

public class Pasteleria {

    private int pesoMaximo;
    private int pesoActualCaja;
    private int cantEnCaja;
    private int[] pesoPastel;
    private boolean hayCaja;
    private boolean necesitaCambio;
    private Cola mostrador;
    private ReentrantLock cerrojoPastel;
    private ReentrantLock cerrojoCaja;
    private Condition esperarPastel;
    private Condition esperarCaja;
    private Condition controlBrazo;
    
    
    public Pasteleria(int pesoMax, int[] pesoPastel){
        this.pesoMaximo = pesoMax;
        this.pesoActualCaja = 0;
        this.cantEnCaja = 0;
        this.pesoPastel = pesoPastel;
        this.hayCaja = true;
        this.necesitaCambio = false;
        this.mostrador = new Cola();
        this.cerrojoPastel = new ReentrantLock();
        this.cerrojoCaja = new ReentrantLock();
        this.esperarPastel = cerrojoPastel.newCondition();
        this.esperarCaja = cerrojoCaja.newCondition();
        this.controlBrazo = cerrojoCaja.newCondition();
    }
    
    // Metodo utilizado por Horno.
    public void agregarPastel(int tipo){
        
        try{
            this.cerrojoPastel.lock();
            System.out.println(Thread.currentThread().getName()+" agrega un nuevo pastel al mostrador.");
            this.mostrador.poner(tipo);
            this.esperarPastel.signal();
        }finally{
            this.cerrojoPastel.unlock();
        }
    }
    
    // Metodos utilizados por Empaquetador.
    public int tomarPastel(){
        
        int peso = 0;
        try{
            this.cerrojoPastel.lock();
            while (this.mostrador.esVacia()){
                System.out.println(Thread.currentThread().getName()+ " no encuentra ningun pastel en el mostrador.");
                esperarPastel.await();
            }
            
            peso = this.pesoPastel[(int)this.mostrador.obtenerFrente()];
            this.mostrador.sacar();
            System.out.println(Thread.currentThread().getName()+" agarra un pastel del mostrador.");
        } catch(InterruptedException exc){
        }finally{
            this.cerrojoPastel.unlock();
        }
        
        return peso;
    }
    
    public void soltarPastel(int pesoPastel){
        
        try{
            this.cerrojoCaja.lock();
            
            boolean pesoNoValido = (pesoActualCaja + pesoPastel)> pesoMaximo;
            while (!hayCaja || pesoNoValido){
                
                if (pesoNoValido){
                    this.necesitaCambio = true;
                    controlBrazo.signal(); // El brazo comienza a trabajar.
                    System.out.println(Thread.currentThread().getName() + " no puede agregar el pastel. Necesita cambio de caja.");
                
                }else
                    System.out.println(Thread.currentThread().getName() + " no puede agregar el pastel. No hay caja.");
                
                esperarCaja.await();
                pesoNoValido = (pesoActualCaja + pesoPastel)> pesoMaximo;
            }
                    
            cantEnCaja++;
            pesoActualCaja = pesoActualCaja + pesoPastel;
            System.out.println(Thread.currentThread().getName() + " deja el pastel en la caja.");
            
        }catch(InterruptedException exc){
        }finally{
            this.cerrojoCaja.unlock();
        }
        
    }
    
    // Metodos utilizados por BrazoMecanico.
    public void retirarCaja(){
        
        try{
            this.cerrojoCaja.lock();
            while(!necesitaCambio){
                this.controlBrazo.await();
            }
            
            this.hayCaja = false;
            System.out.println(Thread.currentThread().getName() + " retira la caja con " + cantEnCaja + " pasteles.");
        } catch (InterruptedException exc){
        }finally{
            this.cerrojoCaja.unlock();
        }
        
    }
    
    public void reponerCaja(){
        
        try{
            this.cerrojoCaja.lock();
            System.out.println(Thread.currentThread().getName() +" pone una nueva caja.");
            this.cantEnCaja = 0;
            this.pesoActualCaja = 0;
            this.hayCaja = true;
            this.necesitaCambio = false;
            this.esperarCaja.signalAll();
        } finally{
            this.cerrojoCaja.unlock();
        }
        
    }
    
}
