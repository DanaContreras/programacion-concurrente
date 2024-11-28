package RecursosCompartidos;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MundoAventura {

    // para actividad de cuerdas.
    private int ordenCuerdas;
    private int ordenCuerdasActual;
    private int maxCuerdas; // corresponde a la cantidad de cuerdas.
    private final ReentrantLock lockCuerdas;
    private final Condition esperaCuerdas;
    
    // para actividad de saltos a 5mts
    private boolean comenzoSalto;
    private boolean terminoSalto;
    private int ordenSalto;
    private int ordenSaltoActual;
    private int cantEnSalto;
    private final int maxEnSaltos; 
    private final ReentrantLock lockSalto;
    private final Condition esperaDisponibilidadSalto;
    private final Condition esperaFinalizacionSalto;
    private final Condition esperaControlSalto;
    
    // para actividad tirolesa.
    private boolean comenzoTirolesa;
    private boolean terminoTirolesa;
    private int ordenTirolesaOeste;
    private int ordenTirolesaOesteActual;
    private int ordenTirolesaEste;
    private int ordenTirolesaEsteActual;
    private int cantEnTirolesaOeste;
    private int cantEnTirolesaEste;
    private final int maxEnTirolesa;
    private final ReentrantLock lockTirolesa;
    private final Condition esperaTirolesaOeste;
    private final Condition esperaTirolesaEste;
    private final Condition esperaFinalizacionTirolesa;
    private final Condition esperaControlTirolesa;
    
    public MundoAventura (int maxCuerdas, int maxEnSaltos, int maxEnTirolesa) {
        this.ordenCuerdas = 0;
        this.ordenCuerdasActual = 1;
        this.maxCuerdas = maxCuerdas;
        this.lockCuerdas = new ReentrantLock(true);
        this.esperaCuerdas = lockCuerdas.newCondition();
        
        this.comenzoSalto = false;
        this.terminoSalto = false;
        this.ordenSalto = 0;
        this.ordenSaltoActual = 1;
        this.cantEnSalto = 0;
        this.maxEnSaltos = maxEnSaltos;
        this.lockSalto = new ReentrantLock(true);
        this.esperaDisponibilidadSalto = lockSalto.newCondition();
        this.esperaFinalizacionSalto = lockSalto.newCondition();
        this.esperaControlSalto = lockSalto.newCondition();
        
        this.comenzoTirolesa= false;
        this.terminoTirolesa = false;
        this.ordenTirolesaOeste = 0;
        this.ordenTirolesaOesteActual = 1;
        this.ordenTirolesaEste = 0;
        this.ordenTirolesaEsteActual = 1;
        this.cantEnTirolesaOeste = 0;
        this.cantEnTirolesaEste = maxEnTirolesa;
        this.maxEnTirolesa = maxEnTirolesa;
        this.lockTirolesa = new ReentrantLock(true);
        this.esperaTirolesaOeste = lockTirolesa.newCondition();
        this.esperaTirolesaEste = lockTirolesa.newCondition();
        this.esperaFinalizacionTirolesa = lockTirolesa.newCondition();
        this.esperaControlTirolesa = lockTirolesa.newCondition();
    }
    
    // metodos de hilo ControlSaltoEnAltura.
    public void esperarVisitantesSalto() throws InterruptedException{
        boolean esperoDemasiado = false;
        
        this.lockSalto.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " espera por visitantes para salto.");
            while (!esperoDemasiado)
                esperoDemasiado = esperaControlSalto.await(15, TimeUnit.SECONDS);
            
            comenzoSalto = true;
            terminoSalto = false;
            System.out.println(Thread.currentThread().getName() + " da por comenzado el salto con " + cantEnSalto + " visitantes.");
        } finally {
            this.lockSalto.unlock();
        }
    }
    
    public void terminarSalto() {
        this.lockSalto.lock();
        try {
            terminoSalto = true;
            comenzoSalto = false;
            cantEnSalto = 0;
            esperaFinalizacionSalto.signalAll();
            
        } finally {
            this.lockSalto.unlock();
        }
    }
    
    // metodos de hilo ControlTirolesa.
    public void esperarVisitantesTirolesa (boolean dirOeste) throws InterruptedException {
        boolean esperoDemasiado = false;
        
        this.lockTirolesa.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " espera por visitantes en " + (dirOeste? "OESTE" : "ESTE") + ".");
            
            while (!esperoDemasiado)
                esperoDemasiado = !esperaControlTirolesa.await(10, TimeUnit.SECONDS);
            
            comenzoTirolesa = true;
            
            if (dirOeste)
                System.out.println(Thread.currentThread().getName() + " da por comenzado el recorrido de tirolesa OESTE ----> ESTE con " + cantEnTirolesaOeste + " visitantes.");
            else
                System.out.println(Thread.currentThread().getName() + " da por comenzado el recorrido de tirolesa OESTE <---- ESTE con " + cantEnTirolesaEste + " visitantes.");
                
        } finally {
            this.lockTirolesa.unlock();
        }
    }
    
    public void terminarTirolesa(boolean dirOeste) {
        boolean avisar;
        this.lockTirolesa.lock();
        try {
            terminoTirolesa = true;
            comenzoTirolesa = false;
            
            if (dirOeste) {
                avisar = (cantEnTirolesaOeste != 0);
                cantEnTirolesaEste = 0;
            } else {
                avisar = (cantEnTirolesaEste != 0);
                cantEnTirolesaOeste =  0;
            }
            
            
            if (avisar)
                this.esperaFinalizacionTirolesa.signalAll();
            else {
                terminoTirolesa = false;
                if (dirOeste) {
                    cantEnTirolesaOeste = maxEnTirolesa;
                    this.esperaTirolesaEste.signalAll();
                }
                else {
                    cantEnTirolesaEste = maxEnTirolesa;
                    this.esperaTirolesaOeste.signalAll();
                }
                System.out.println(Thread.currentThread().getName() + " notifica a los visitantes que pueden bajarse.");
            }
        } finally {
            this.lockTirolesa.unlock();
        }
    }
    
    // metodos de hilo Visitante.
    public void realizarActividad (int tipoActiv) throws InterruptedException {
        switch (tipoActiv) {
            case 0 -> realizarJuegoCuerdas();
            case 1 -> realizarJuegoSalto();
            case 2 -> realizarJuegoTirolesaOeste();
            case 3 -> realizarJuegoTirolesaEste();
        }
    }
    
    private void realizarJuegoCuerdas() throws InterruptedException {
        this.lockCuerdas.lock();
        try {
            ordenCuerdas++;
            int llegada = ordenCuerdas;
            while (llegada != ordenCuerdasActual || maxCuerdas == 0)
                this.esperaCuerdas.await();
            
            ordenCuerdasActual++;
            maxCuerdas--;
            this.esperaCuerdas.signalAll();
            
            System.out.println(Thread.currentThread().getName() + " con orden " + llegada + " obtuvo una cuerda para la actividad. Quedan " + maxCuerdas);
        } finally {
            this.lockCuerdas.unlock();
        }
    }
    
    private void realizarJuegoSalto() throws InterruptedException {
        int llegada;
        
        this.lockSalto.lock();
        try {
            ordenSalto++;
            llegada = ordenSalto;
            
            System.out.println(Thread.currentThread().getName() + " con orden de llegada " + llegada + " quiere realizar salto.");
            
            while (cantEnSalto == maxEnSaltos || comenzoSalto || llegada != ordenSaltoActual ) // no hay disponibilidad o no es su turno
                esperaDisponibilidadSalto.await();
            
            cantEnSalto++;
            ordenSaltoActual++;
            
            System.out.println(Thread.currentThread().getName() + " obtuvo un lugar en salto. Quedan " + (maxEnSaltos - cantEnSalto) + " lugares.");
            
            esperaDisponibilidadSalto.signalAll();
            esperaControlSalto.signal();
              
        } finally {
            this.lockSalto.unlock();
        }
    }
    
    private void realizarJuegoTirolesaOeste() throws InterruptedException {
        int llegada;
        
        this.lockTirolesa.lock();
        try {
            ordenTirolesaOeste++;
            llegada = ordenTirolesaOeste;
            
            System.out.println(Thread.currentThread().getName() + " con orden de llegada " + llegada + " quiere subirse a tirolesa. Recorrido OESTE ----> ESTE.");
            
            while (llegada != ordenTirolesaOesteActual || comenzoTirolesa || cantEnTirolesaOeste == maxEnTirolesa )
                esperaTirolesaOeste.await();
            
            cantEnTirolesaOeste++;
            ordenTirolesaOesteActual++;
            
            System.out.println(Thread.currentThread().getName() + " obtuvo un lugar oeste. Quedan " + (maxEnTirolesa - cantEnTirolesaOeste) + " lugares.");
            
            esperaTirolesaOeste.signalAll();
            esperaControlTirolesa.signal();
            
        } finally {
            this.lockTirolesa.unlock();
        }
        
    }
    
    private void realizarJuegoTirolesaEste() throws InterruptedException {
        int llegada;
        
        this.lockTirolesa.lock();
        try {
            ordenTirolesaEste++;
            llegada = ordenTirolesaEste;
            
            System.out.println(Thread.currentThread().getName() + " con orden de llegada " + llegada + " quiere subirse a tirolesa. Recorrido OESTE <---- ESTE.");
            
            while (llegada != ordenTirolesaEsteActual || comenzoTirolesa || cantEnTirolesaEste == maxEnTirolesa)
                esperaTirolesaEste.await();
            
            cantEnTirolesaEste++;
            ordenTirolesaEsteActual++;
            
            System.out.println(Thread.currentThread().getName() + " obtuvo un lugar este. Quedan " + (maxEnTirolesa - cantEnTirolesaEste) + " lugares.");
            
            esperaTirolesaEste.signalAll();
            esperaControlTirolesa.signal();
        } finally {
            this.lockTirolesa.unlock();
        }
    }
    
    public void dejarActividad (int tipoActiv) throws InterruptedException {
         switch (tipoActiv) {
            case 0 -> dejarJuegoCuerdas();
            case 1 -> dejarJuegoSalto();
            case 2 -> dejarJuegoTirolesaOeste();
            case 3 -> dejarJuegoTirolesaEste();
        }
    }

    private void dejarJuegoCuerdas() {
        this.lockCuerdas.lock();
        try { 
            maxCuerdas++;
            System.out.println(Thread.currentThread().getName() + " termino juego de cuerdas y libera la cuerda. Ahora hay " + maxCuerdas + " disponibles.");
            esperaCuerdas.signalAll();
        } finally {
            this.lockCuerdas.unlock();
        }
    }
    
    private void dejarJuegoSalto() throws InterruptedException{
        this.lockSalto.lock();
        try {
            while (!terminoSalto)
                esperaFinalizacionSalto.await();
            
            esperaDisponibilidadSalto.signalAll();
            System.out.println(Thread.currentThread().getName() + " termino juego de salto y libera lugar.");
        } finally {
            this.lockSalto.unlock();
        }
    }
    
    private void dejarJuegoTirolesaOeste() throws InterruptedException {
        this.lockTirolesa.lock();
        try {
            while (!terminoTirolesa)
                esperaFinalizacionTirolesa.await();
            
            cantEnTirolesaOeste--;
            if (cantEnTirolesaOeste == 0) {
                terminoTirolesa = false;
                cantEnTirolesaOeste = maxEnTirolesa; // evita que visitantes del oeste puedan acceder a tirolesa.
                esperaTirolesaEste.signalAll();
            }
            
            System.out.println(Thread.currentThread().getName() + " dejo la tirolesa en OESTE.");
        } finally {
            this.lockTirolesa.unlock();
        }
    }
    
    private void dejarJuegoTirolesaEste() throws InterruptedException {
        this.lockTirolesa.lock();
        try {
            while (!terminoTirolesa )
                esperaFinalizacionTirolesa.await();
            
            cantEnTirolesaEste--;
            if (cantEnTirolesaEste == 0) {
                terminoTirolesa = false;
                cantEnTirolesaEste = maxEnTirolesa;
                esperaTirolesaOeste.signalAll();
            }

            System.out.println(Thread.currentThread().getName() + " dejo la tirolesa en ESTE.");
        } finally {
            this.lockTirolesa.unlock();
        }
    }
}