package Problema2;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GeneradorPedido implements Runnable {
    
    private final String[] tipoPizza;
    private final String[] nombreCliente;
    private final Mostrador mostrador;
    private final Random numRandom;

    public GeneradorPedido(String[] tipoPizza, String[] nombreCliente, Mostrador mostrador) {
        this.tipoPizza = tipoPizza;
        this.nombreCliente = nombreCliente;
        this.mostrador = mostrador;
        this.numRandom = new Random();
    }
    
    @Override
    public void run () {
        Pedido pedido;
        while (true) {
            pedido = generarPedido();
            System.out.println(Thread.currentThread().getName() + " genero un pedido de " + pedido.getCantidad() + " pizza/s " + pedido.getNombrePizza() + " para " + pedido.getNombreCliente());
            this.mostrador.hacerPedido(pedido);
            simularDescanso();
        }
    }
    
    private Pedido generarPedido () {
        
        int idPizza = numRandom.nextInt(tipoPizza.length);
        int cantidad = 1;
        
        if (tipoPizza[idPizza].equals("vegana"))
            cantidad++;
        
        return new Pedido(idPizza, cantidad, tipoPizza[idPizza], nombreCliente[numRandom.nextInt(nombreCliente.length)]);
    }
    
    private void simularDescanso () {
        try {
            Thread.sleep((numRandom.nextInt(9) + 1) * 500);    
        } catch (InterruptedException e) {
            Logger.getLogger(GeneradorPedido.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
}
