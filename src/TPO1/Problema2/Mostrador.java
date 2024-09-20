package Problema2;
import java.util.LinkedList;
import java.util.Queue;

public class Mostrador {
    
    // recurso compartido
    
    private final int maxPedidos;
    private final Queue<Pedido> colaPendientes;
    private final Queue<Pedido> colaReparto;
    private final Object monitorPendientes;
    
    public Mostrador (int maxPedidos) {
        this.maxPedidos = maxPedidos;
        this.colaPendientes = new LinkedList<>();
        this.colaReparto = new LinkedList<>();
        this.monitorPendientes = new Object();
    };
    
    // metodo de generador
    public void hacerPedido (Pedido pedido) {
        synchronized (monitorPendientes) {
            colaPendientes.add(pedido);
            monitorPendientes.notifyAll();
        }
    }
            
    // metodos de maestro pizzero
    public Pedido obtenerPedido () throws InterruptedException{
        Pedido pedido;
        synchronized (monitorPendientes) {
            while (colaPendientes.isEmpty())
                monitorPendientes.wait();
            pedido = colaPendientes.poll();
        }
        System.out.println(Thread.currentThread().getName() + " va a hacer el pedido de " + pedido.getCantidad() + " pizza/s " + pedido.getNombrePizza() + " para " + pedido.getNombreCliente());
        return pedido;
    }
    
    public synchronized void dejarPedidoEnMostrador (Pedido pedido) throws InterruptedException{
        
        while (colaReparto.size() > maxPedidos){
            System.out.println(Thread.currentThread().getName() + " encontro el mostrador lleno. Toca esperar...");
            this.wait();
        }
            
        colaReparto.add(pedido);
        System.out.println(Thread.currentThread().getName() + " dejo pedido en mostraador para repartir. El mostrador tiene " + colaReparto.size() + " pedidos a repartir.");
        this.notifyAll();
    }
    
    // metodo usado por repartidor.
    public synchronized Pedido agarrarPedidoDeMostrador () throws InterruptedException{
        
        while (colaReparto.isEmpty())
            this.wait();
        
        Pedido pedido = colaReparto.poll();
        System.out.println(Thread.currentThread().getName() + " agarro del mostrador el pedido de " + pedido.getCantidad() + " pizza/s " + pedido.getNombrePizza() + " para " + pedido.getNombreCliente());
        this.notifyAll();
        
        return pedido;
    }
    
}
