package Problema2;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Repartidor implements Runnable {
    
    private int cantActualViajes;
    private final int maxViajes;
    private final Mostrador mostrador;
    private final Random numRandom;

    public Repartidor (int maxViajes, Mostrador mostrador) {
        this.cantActualViajes = 1;
        this.maxViajes = maxViajes;
        this.mostrador = mostrador;
        this.numRandom = new Random();
    }
    
    @Override
   public void run () {
       Pedido pedido;
       try {
            while (true) {
                pedido = this.mostrador.agarrarPedidoDeMostrador();
                repartir(pedido);
                cantActualViajes++;
                if (cantActualViajes > maxViajes)
                    descansar();
            }
       } catch (InterruptedException e) {
           Logger.getLogger(Repartidor.class.getName()).log(Level.SEVERE, null, e);
       }
       
   }
   
   private void repartir(Pedido pedido) {
       try {
           Thread.sleep((numRandom.nextInt(9) + 1) * 1000);
           System.out.println(Thread.currentThread().getName() + " reparte " + pedido.getCantidad() + " pizza/s " + pedido.getNombrePizza() + " para " + pedido.getNombreCliente()+ ". Es su reparto n°: " + cantActualViajes);
       } catch (InterruptedException e) {
           Logger.getLogger(Repartidor.class.getName()).log(Level.SEVERE, null, e);
       }
   }
   
   private void descansar() {
       try {
           Thread.sleep((numRandom.nextInt(9) + 1) * 1000);
           System.out.println(Thread.currentThread().getName() + " se tomo un descanso y se comio una pizza que sobro.");
           cantActualViajes = 1;
       } catch (InterruptedException e) {
           Logger.getLogger(Repartidor.class.getName()).log(Level.SEVERE, null, e);
       }
   }
   
}
