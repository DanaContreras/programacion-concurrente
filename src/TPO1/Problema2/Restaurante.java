package Problema2;
import Utiles.TextoAColor;

/*

    Mecanismo de sincronizacion elegido: Monitores.
    - Dada a la naturaleza del problema, analogo al problema del consumidor-productor, se plantea una solucion simple con monitores.
    - Se utiliza el objeto monitorPendiente que asegura la exclusión mutua para la cola de pedidos pendientes. El hilo generador crea pedidos
      y los agrega a la cola. Por el contrario,  un hilo MaestroPizzero extrae de la cola un pedido a preparar, en caso que la cola estuviera vacia,
      espera hasta que se le notifique que hay pedidos para extraer.
    - Se utiliza el objeto Mostrador para la exclusion mutua de la cola de pedidos para repartir. Sucede una dinamica similar a la anterior
      donde un hilo MaestroPizzero agrega al mostrador las pizzas realizadas (con un limite de maxPedidos) y un hilo Repartidor extrae los
      pedidos preparados para su reparto, con su limitacion de maxViajes a realizar y la espera que realiza en caso que no haya pedidos a
      repartir.

*/

public class Restaurante {

    public static void main(String[] args) {
        
        TextoAColor txtColor = new TextoAColor();
        
        int i;
        int maxPedidos = 2;
        int maxViajes = 10;
        int cantRepartidores = 6;
        int cantMaestros = 8;
        String[] tipoPizza = {"napolitana", "vegana"};
        String[] cliente = {"Pedro", "Sara", "Pepito", "Lucia"};
        
        // recurso compartido
        Mostrador mesa = new Mostrador(maxPedidos);
        
        // creacion de hilos
        Thread generador = new Thread(new GeneradorPedido(tipoPizza, cliente, mesa), txtColor.cambiarColor("Violeta", "Generador"));
        Thread[] repartidor = new Thread[cantRepartidores];
        Thread[] maestroPizzero = new Thread[cantMaestros];
        
        for (i = 0; i < cantRepartidores; i++)
            repartidor[i] = new Thread(new Repartidor(maxViajes, mesa), txtColor.cambiarColorTexto(i, "Repartidor " + (i + 1)));
        
        for (i = 0; i < cantMaestros; i++)
            maestroPizzero[i] = new Thread(new MaestroPizzero(mesa), txtColor.cambiarColorTexto(i, "Maestro pizzero " + (i + 1)));

        // cambio de estado a runnable
        generador.start();
        
        for (i = 0; i < cantRepartidores; i++)
            repartidor[i].start();
        
        for (i = 0; i < cantMaestros; i++)
            maestroPizzero[i].start();
        
    }
    
}
