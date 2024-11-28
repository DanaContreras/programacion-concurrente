package RecursosCompartidos;

import Utiles.PedidoSnorkel;
import java.util.LinkedList;
import java.util.Queue;

public class Snorkel {
    
    private int ordenLlegada;
    private int maxEquipos;
    private Queue<PedidoSnorkel> pedidosPendientes;
    private Queue<PedidoSnorkel> pedidosAtendidos;
    private final Object monitorAtender;
    private final Object monitorPedidosAtendidos;
    
    public Snorkel (int maxEquipos) {
        this.ordenLlegada = 0;
        this.maxEquipos = maxEquipos;
        this.pedidosPendientes = new LinkedList<>();
        this.pedidosAtendidos = new LinkedList<>();
        this.monitorAtender = new Object();
        this.monitorPedidosAtendidos = new Object();
    }
    
    // metodos de AsistenteSnorkel
    public void atenderVisitante() throws InterruptedException {
        
        PedidoSnorkel pedidoActual;
        
        synchronized(monitorAtender) {
            while (pedidosPendientes.isEmpty())
                this.monitorAtender.wait();
            
            System.out.println(Thread.currentThread().getName() + " le solicitaron un equipo. Verificara si hay uno disponible.");
            
            pedidoActual = pedidosPendientes.poll();
            if (maxEquipos == 0)
                pedidoActual.setDisponibilidad(false);
            else {
                maxEquipos--;
                pedidoActual.setDisponibilidad(true);
            }
            
            System.out.println(Thread.currentThread().getName() + " le avisa al Visitante con orden " + pedidoActual.getOrdenPedido() + " que " + (pedidoActual.getDisponibilidad()? "hay" : "no hay") + " equipo disponible. Quedan " + maxEquipos);
        }
        
        synchronized (monitorPedidosAtendidos) {
            pedidosAtendidos.add(pedidoActual);
            monitorPedidosAtendidos.notifyAll();
        }
    }
    
    
    // metodos del hilo Visitante.
    public int pedirEquipo() {
        synchronized(monitorAtender) {
            ordenLlegada++;
            pedidosPendientes.add(new PedidoSnorkel(ordenLlegada));
            monitorAtender.notifyAll();
            System.out.println(Thread.currentThread().getName() + " con orden " + ordenLlegada + " pidio un equipo.");
        }
        return ordenLlegada;
    }
    
    public boolean esperarSerAtendido(int ordenLlegada) throws InterruptedException {
        PedidoSnorkel pedidoActual;
        
        synchronized(monitorPedidosAtendidos) {
            pedidoActual = pedidosAtendidos.peek();
            while (pedidoActual == null || pedidoActual.getOrdenPedido() != ordenLlegada) {
                this.monitorPedidosAtendidos.wait();
                pedidoActual = pedidosAtendidos.peek();
            }
                
            pedidosAtendidos.poll();
            System.out.println(Thread.currentThread().getName() + " con orden " + pedidoActual.getOrdenPedido() + ""+ (pedidoActual.getDisponibilidad()? "" : " NO" ) + " obtuvo un equipo." );
        }
        
        return pedidoActual.getDisponibilidad();
    }
    
    public void devolverEquipo() {
        synchronized(monitorAtender) {
            maxEquipos++;
        }
        
        System.out.println(Thread.currentThread().getName() + " dejo el equipo en el stand. Hay " + maxEquipos + " equipos disponibles.");
    }
    
}
