package RecursosCompartidos;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CarreraGomones {

    private int cantTotal;
    private final int maxEnCarrera;
    private int cantEnTren;
    private int numLugar;
    private final int maxBicicletas;
    private final int maxTren;
    private final CyclicBarrier puntoPartida;
    private final CyclicBarrier puntoLlegada;
    private final Semaphore mutexNumLugar;
    private final Semaphore mutexTren;
    private final Semaphore mutexMaxTotal;
    private final Semaphore semBicicletas;
    private final Semaphore semDisponibilidadTren;
    private final Semaphore semEnTren;
    private final Semaphore semMaquinista;
    private final Semaphore semGomIndiv;
    private final Semaphore semGomPareja;

    public CarreraGomones(int maxBicicletas, int maxTren, int maxGomIndiv, int maxGomPareja) {
        this.cantTotal = 0;
        this.maxEnCarrera = maxGomIndiv + maxGomPareja * 2;
        this.cantEnTren = 0;
        this.numLugar = 0;
        this.maxBicicletas = maxBicicletas;
        this.maxTren = maxTren;
        this.puntoPartida = new CyclicBarrier(maxEnCarrera, () -> System.out.println("CarreraGomones: El cupo esta completo. LA CARRERA COMIENZA EN 3, 2, 1, YA!"));
        this.puntoLlegada = new CyclicBarrier(maxEnCarrera, this::accionEnBarrera);
        this.mutexNumLugar = new Semaphore(1);
        this.mutexTren = new Semaphore(1);
        this.mutexMaxTotal = new Semaphore(1);
        this.semBicicletas = new Semaphore(maxBicicletas);
        this.semDisponibilidadTren = new Semaphore(maxTren);
        this.semEnTren = new Semaphore(0);
        this.semMaquinista = new Semaphore(0);
        this.semGomIndiv = new Semaphore(maxGomIndiv);
        this.semGomPareja = new Semaphore(maxGomPareja * 2);
    }

    private void accionEnBarrera() {
        try {
            System.out.println("CarreraGomones: LA CARRERA TERMINO");
            this.mutexNumLugar.acquire();
            this.numLugar = 0;
            this.mutexNumLugar.release();
        } catch (InterruptedException ex) {
            Logger.getLogger(CarreraGomones.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // metodos de hilo maquinista
    public void esperarTrenCompleto() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " espera hasta que se llene el tren.");
        this.semMaquinista.acquire();
        System.out.println(Thread.currentThread().getName() + " verifico que el tren se lleno. Comienza el viaje.");
    }

    public void finalizarRecorridoTren() throws InterruptedException {
        this.mutexTren.acquire();
        this.cantEnTren = 0;
        this.mutexTren.release();

        this.semEnTren.release(maxTren);
    }

    // metodos de hilo visitante.
     public int getMaxTotal() {
        return maxBicicletas + maxTren;
    }
    
    public boolean[] solicitarTipoTransporte(boolean enBici) throws InterruptedException {
        boolean eleccion[] = new boolean[2];
        boolean hayDisponibilidad;
        
        if (enBici){ // solicita un tipo de transporte, caso contrario espera disponibilidad
            hayDisponibilidad = this.semBicicletas.tryAcquire();
            if (!hayDisponibilidad){
                enBici = false;
                hayDisponibilidad = this.semDisponibilidadTren.tryAcquire();
            }
        } else {
            hayDisponibilidad = this.semDisponibilidadTren.tryAcquire();
            if (!hayDisponibilidad) {
                enBici = true;
                hayDisponibilidad = this.semBicicletas.tryAcquire();
            }
        }
        
        if (!hayDisponibilidad)
            System.out.println(Thread.currentThread().getName() + " no consiguio transporte, no hara la actividad por ahora.");
        else {
            this.mutexMaxTotal.acquire();
            cantTotal++;
            this.mutexMaxTotal.release();
        }
            
        eleccion[0] = hayDisponibilidad;
        eleccion[1] = enBici;
        
        return eleccion;
    }

    public void subirseAlTren() throws InterruptedException {

        this.mutexTren.acquire();
        this.cantEnTren++;
        
        System.out.println(Thread.currentThread().getName() + " se subio al tren.");
        
        if (this.cantEnTren == this.maxTren) {
            this.semMaquinista.release();
        }
        
        this.mutexTren.release();
        this.semEnTren.acquire();
    }

    public boolean solicitarGomon(boolean enGomIndiv) throws InterruptedException {
        boolean enGomIndivIntento;
        if (enGomIndiv) {
            enGomIndivIntento = this.semGomIndiv.tryAcquire();
            if (!enGomIndivIntento){
                System.out.println(Thread.currentThread().getName() + " no consiguio un gomon individual, opta por el gomon en pareja.");
                this.semGomPareja.acquire();
            } else
                System.out.println(Thread.currentThread().getName() + " elige un gomon individual.");
        } else {
            enGomIndivIntento = !this.semGomPareja.tryAcquire();
            if (enGomIndivIntento) {
                 System.out.println(Thread.currentThread().getName() + " no consiguio un gomon en pareja, opta por el gomon individual.");
                this.semGomIndiv.acquire();
            } else
                System.out.println(Thread.currentThread().getName() + " elige un gomon en pareja.");
        }
        return enGomIndivIntento;
    }
    
    public void comenzarCarrera() throws InterruptedException, BrokenBarrierException {
        this.puntoPartida.await(); // los hilos esperan hasta que se cumpla el cupo maxCarreras
    }

    public void terminarCarrera(boolean enGomonIndiv) throws InterruptedException, BrokenBarrierException {
        
        this.mutexNumLugar.acquire();
        this.numLugar++;
        System.out.println(Thread.currentThread().getName() + " obtuvo el puesto " + numLugar + ".");
        this.mutexNumLugar.release();
        
        this.puntoLlegada.await(); // el hilo termino la carrera y espera al resto de hilos.
        
        if (enGomonIndiv)
            this.semGomIndiv.release();
        else
            this.semGomPareja.release();
        
        System.out.println(Thread.currentThread().getName() + " devuelve gomon " + (enGomonIndiv? "individual" : "en pareja" ) + ". Agarra sus pertenencias.");
    }

    public boolean devolverLugar(boolean enBici) throws InterruptedException { 
        boolean esUltimo = false;
        
        this.mutexMaxTotal.acquire();
        cantTotal--;
        if (cantTotal == 0) {
            this.semBicicletas.release(maxBicicletas);
            this.semDisponibilidadTren.release(maxTren);
            esUltimo = true;
            System.out.println(Thread.currentThread().getName() + " es el ultimo participante.");
        }
        this.mutexMaxTotal.release();
        
        return esUltimo;
    }

}
