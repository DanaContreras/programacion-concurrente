package Problema2;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MaestroPizzero implements Runnable{
    
    private final Mostrador mostrador;
    private final Random numRandom;
    
    public MaestroPizzero (Mostrador mostrador) {
        this.mostrador = mostrador;
        this.numRandom = new Random();
    }
    
    @Override
    public void run () {
        Pedido pedido;
        try {
            while (true) {
                pedido = this.mostrador.obtenerPedido();
                cocinar(pedido);
                this.mostrador.dejarPedidoEnMostrador(pedido);
            }
        } catch (InterruptedException e) {
            Logger.getLogger(MaestroPizzero.class.getName()).log(Level.SEVERE, null, e);
        }
        
    }
    
    private void cocinar (Pedido pedido) {
        try {
            Thread.sleep((numRandom.nextInt(9) + 1) * 1000);
            System.out.println(Thread.currentThread().getName() + " termino de cocinar " + pedido.getCantidad() + " pizza/s " + pedido.getNombrePizza() + " para " + pedido.getNombreCliente());
        } catch (InterruptedException e) {
             Logger.getLogger(MaestroPizzero.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
