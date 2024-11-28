package RecursosCompartidos;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class NadoDelfines {
    
    private boolean comenzoActividad;
    private boolean finalizoActividad;
    private int cantEnGrupoActual;
    private int cantPiletasOcupadas;
    private int cantTotal;
    private final int minTotal;
    private final int maxPiletas;
    private final int maxEnGrupo;
    private final ReentrantLock lockPiletas;
    private final ReentrantLock lockActividad;
    private final Condition esperaGrupos;
    private final Condition esperaUltimoGrupo;
    private final Condition esperaSalida;
    private final Condition terminarActividad;
    
    public NadoDelfines (int maxPiletas, int maxGrupo) {
        this.comenzoActividad = false;
        this.finalizoActividad = false;
        this.cantEnGrupoActual = 0;
        this.cantPiletasOcupadas = 1;
        this.cantTotal = 0;
        this.maxPiletas = maxPiletas;
        this.maxEnGrupo = maxGrupo;
        this.minTotal = maxPiletas * maxEnGrupo - maxEnGrupo;
        this.lockPiletas = new ReentrantLock(true);
        this.lockActividad = new ReentrantLock(true);
        this.esperaGrupos = lockActividad.newCondition();
        this.esperaUltimoGrupo = lockActividad.newCondition();
        this.esperaSalida = lockActividad.newCondition();
        this.terminarActividad = lockActividad.newCondition();
    }
    
    // metodos de hilo Instructor de delfines.
    public void esperarComienzoActividad() throws InterruptedException{
        System.out.println(Thread.currentThread().getName() + " espera la formacion de grupos para comenzar.");
      
        this.lockActividad.lock();
        try {
            while (cantTotal < minTotal)
                this.esperaGrupos.await();
        } finally {
            this.lockActividad.unlock();
        }  
    }
    
    public void esperarGrupoIncompleto() throws InterruptedException{
        boolean decideEmpezar = false;
        this.lockActividad.lock();
        System.out.println(Thread.currentThread().getName() + " espera por el ultimo grupo.");
        try {
            while (!decideEmpezar) {
                decideEmpezar = !esperaUltimoGrupo.await(10, TimeUnit.SECONDS); // instructor espera 10 segundos por si un visitante ocupa un lugar en ultimo grupo
            }
            comenzoActividad = true;
        } finally {
            this.lockActividad.unlock();
        }
        this.lockPiletas.lock();
        try {
            if (cantPiletasOcupadas < maxPiletas || cantEnGrupoActual < maxEnGrupo)
                System.out.println(Thread.currentThread().getName() + " espero y determino que el grupo esta incompleto.");
            else
                System.out.println(Thread.currentThread().getName() + " determino que el grupo esta completo y empezara la actividad.");
        } finally {
            this.lockPiletas.unlock();
        }
    }
    
    public void terminarActividad() {
        System.out.println(Thread.currentThread().getName() + " termino la actividad y le avisa a los visitantes.");
        
        this.lockActividad.lock();
        try {
             finalizoActividad = true;
             this.terminarActividad.signalAll(); // avisa a los hilos visitantes que la actividad se da por terminada la actividad.
        } finally {
            this.lockActividad.unlock();
        }
        
        this.lockPiletas.lock();
        try {
            cantEnGrupoActual = 0;
            cantPiletasOcupadas = 1;
        } finally {
            this.lockPiletas.unlock();
        }
    }
    
    public void esperarFinalizacionActividad() throws InterruptedException{
        
        this.lockActividad.lock();
        System.out.println(Thread.currentThread().getName() + " esperara a que se desocupen las piletas.");
        try {
            while (cantTotal != 0)
                this.esperaSalida.await();
            comenzoActividad = false;
            finalizoActividad = false;
        } finally {
            this.lockActividad.unlock();
        }
    }
    
    // metodos de hilo Visitante.
    
    public int getMinTotal() {
        return minTotal;
    }
    
    public boolean verificarHorarioActividad() throws InterruptedException{
        boolean horarioValido;
        
        this.lockActividad.lock();
        try {
            horarioValido = !comenzoActividad;
        } finally {
            this.lockActividad.unlock();
        }
        
        System.out.println(Thread.currentThread().getName() + " verifica que " + (horarioValido? "SI" : "NO") + " esta en un horario valido.");

        return horarioValido;
    }
    
    public boolean unirseAGrupo () throws InterruptedException{
        boolean hayDisponibilidad  = true;
        
        this.lockPiletas.lock();
        try {
            if (cantPiletasOcupadas < maxPiletas) {
                if (cantEnGrupoActual < maxEnGrupo) {
                    cantEnGrupoActual++;
                } else { // se lleno una pileta
                    cantPiletasOcupadas++;
                    cantEnGrupoActual = 1;
                }
            } else { // ultimo grupo
                if (cantEnGrupoActual < maxEnGrupo) {
                    cantEnGrupoActual++;
                } else {
                    hayDisponibilidad = false;
                }
            }
            
            if (hayDisponibilidad) {
                System.out.println(Thread.currentThread().getName() + " encontro un grupo para realizar la actividad. Grupo: " + cantPiletasOcupadas + " num: " + cantEnGrupoActual);
                this.lockActividad.lock();
                try {
                cantTotal++;
                if (cantPiletasOcupadas < maxPiletas)
                    this.esperaGrupos.signal();
                else
                    this.esperaUltimoGrupo.signal();
                } finally {
                    this.lockActividad.unlock();
                }
            }
            else
                System.out.println(Thread.currentThread().getName() + " no encontro disponibilidad en la actividad de nado con delfines. ");
                
        } finally {
            this.lockPiletas.unlock();
        }
        
        return hayDisponibilidad;
    }
    
    public boolean dejarGrupo() throws InterruptedException{
        boolean esUltimo = false;
        
        this.lockActividad.lock();
        try {
            while (!finalizoActividad)
                this.terminarActividad.await();
            
            cantTotal --;
            this.esperaSalida.signal();
            
            if (cantTotal == 0)
                esUltimo = true;
        } finally {
            this.lockActividad.unlock();
        }
       
        System.out.println(Thread.currentThread().getName() + " termino la actividad y deja el grupo.");
        
        return esUltimo;
    }
}
