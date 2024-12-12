package RecursosCompartidos;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Snorkel {
    
    private int ordenLlegada;
    private int maxSnorkel;
    private int maxSalvavidas;
    private int maxPatasDeRana;
    private final Queue<Integer> pedidosPendientes;
    private final Queue<Integer> pedidosAtendidos;
    private final ReentrantLock lockAtender;
    private final ReentrantLock lockPedidoAtendido;
    private final Condition esperaAtender;
    private final Condition esperaPedidoAtendido;
    private final Condition esperaDisponibilidad;
    
    public Snorkel (int maxSnorkel, int maxSalvavidas, int maxPatasDeRana) {
        this.ordenLlegada = 0;
        this.maxSnorkel = maxSnorkel;
        this.maxSalvavidas = maxSalvavidas;
        this.maxPatasDeRana = maxPatasDeRana;
        this.pedidosPendientes = new LinkedList<>();
        this.pedidosAtendidos = new LinkedList<>();
        this.lockAtender = new ReentrantLock(true);
        this.lockPedidoAtendido = new ReentrantLock(true);
        this.esperaAtender = lockAtender.newCondition();
        this.esperaDisponibilidad = lockAtender.newCondition();
        this.esperaPedidoAtendido = lockPedidoAtendido.newCondition();
    }
    
    // metodos de AsistenteSnorkel
    public void atenderVisitante() throws InterruptedException {
        
        boolean haySnorkel, haySalvavidas, hayPatasDeRana;
        int pedidoActual; // num del pedido
        
        this.lockAtender.lock();
        try {        
            while (pedidosPendientes.isEmpty())
                esperaAtender.await();
            
            System.out.println(Thread.currentThread().getName() + " le solicitaron un equipo. Verificara si hay uno disponible.");
            
            pedidoActual = pedidosPendientes.poll();
            
            haySnorkel = (maxSnorkel != 0);
            if (haySnorkel)
                maxSnorkel--;
            
            haySalvavidas = (maxSalvavidas != 0);
            if (haySalvavidas)
                maxSalvavidas--;
            
            hayPatasDeRana = (maxPatasDeRana != 0);
            if (hayPatasDeRana)
                maxPatasDeRana--;
            
            if (!haySnorkel || !haySalvavidas || !hayPatasDeRana)
                System.out.println(Thread.currentThread().getName() + " no encontro un equipo completo. Quedan snorkel:" + maxSnorkel + " salvavidas:" + maxSalvavidas + " patas de rana: " + maxPatasDeRana);
            
            while (!haySnorkel || !haySalvavidas || !hayPatasDeRana) {
                esperaDisponibilidad.await();
                
                if (!haySnorkel && maxSnorkel != 0) {
                    haySnorkel = true; 
                    maxSnorkel--; 
                }

                if (!haySalvavidas && maxSalvavidas != 0) {
                    haySalvavidas = true;
                    maxSalvavidas--;
                }

                if (!hayPatasDeRana && maxPatasDeRana != 0) {
                    hayPatasDeRana = true;
                    maxPatasDeRana--;
                }
            }
            
            System.out.println(Thread.currentThread().getName() + " le avisa al Visitante con orden " + pedidoActual + " que hay equipo disponible. Quedan snorkel:" + maxSnorkel + " salvavidas:" + maxSalvavidas + " patas de rana: " + maxPatasDeRana);
        } finally {            
            this.lockAtender.unlock();
        }
            
        this.lockPedidoAtendido.lock();
        try {
            pedidosAtendidos.add(pedidoActual);
            esperaPedidoAtendido.signalAll();
        } finally {
            this.lockPedidoAtendido.unlock();
        }
        
    }
    
    // metodos del hilo Visitante.
    public int pedirEquipo() {
        this.lockAtender.lock();
        try {
            ordenLlegada++;
            pedidosPendientes.add(ordenLlegada);
            esperaAtender.signalAll();
            System.out.println(Thread.currentThread().getName() + " con orden " + ordenLlegada + " pidio un equipo.");
        } finally {
            this.lockAtender.unlock();
        }
        return ordenLlegada;
    }
    
    public void esperarSerAtendido(int ordenLlegada) throws InterruptedException {
        Integer pedidoActual;
        
        this.lockPedidoAtendido.lock();
        try {
            pedidoActual = pedidosAtendidos.peek();
            while (pedidoActual == null || pedidoActual != ordenLlegada) {
                esperaPedidoAtendido.await();
                pedidoActual = pedidosAtendidos.peek();
            }
            
            pedidosAtendidos.poll();
            System.out.println(Thread.currentThread().getName() + " con orden " + pedidoActual + " obtuvo un equipo." );
        } finally {
            this.lockPedidoAtendido.unlock();
        }
    }
    
    public void devolverEquipo() {
        this.lockAtender.lock();
        try {
            maxSnorkel++;
            maxSalvavidas++;
            maxPatasDeRana++;
            esperaDisponibilidad.signalAll();
        } finally {
            this.lockAtender.unlock();
        }
        
        System.out.println(Thread.currentThread().getName() + " dejo el equipo en el stand. Snorkel:" + maxSnorkel + " salvavidas:" + maxSalvavidas + " patas de rana: " + maxPatasDeRana);
    }
    
}
