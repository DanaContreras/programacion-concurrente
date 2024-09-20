package Problema3;
import Utiles.TextoAColor;

/*

    Mecanismo de sincronizacion elegido: Locks.
    - Se utiliza lockEtiqueta para proteger la variable compartida insertarEnColaUno. Dicha variable determina en que cola un hilo
      extractor e insertor debe extraer e insertar datos respectivamente.
      Si bien el lockEtiqueta se encuentra en insertores y extractores, lo hacen de tal maera que garantiza que hilos insertores
      y extractores inserten y extraigan datos del buffer de forma simultanea en un momento dado.
    - Se utiliza lockExtractor para asegurar la exclusion mutua de la cola para extraer. De forma similar, se utiliza lockInsertor.

    Decisiones:
    - Se asume que hilos insertores y extractores no cooperan entre si, ya que el enunciado no lo expecifica.

*/

public class Sistema {
    
    public static void main(String[] args) {
        
        TextoAColor txtColor = new TextoAColor ();
        
        int i;
        int cantInsertores = 4;
        int cantExtractores = 2;
        String[] dato = {"El", "dia", "esta", "nublado"};
        
        // recurso compartido
        Buffer buffer = new Buffer();
        
        // creacion de hilos
        Thread[] insertor = new Thread[cantInsertores];
        Thread[] extractor = new Thread[cantExtractores];
        
        for (i = 0; i < cantInsertores; i++)
            insertor[i] = new Thread(new Insertor(buffer, dato), txtColor.cambiarColorTexto(i, "Insertor " + (i + 1)));
        
        for (i = 0; i < cantExtractores; i++)
            extractor[i] = new Thread(new Extractor(buffer), txtColor.cambiarColorTexto(i, "Extractor " + (i + 1)));
        
        // cambio de estado
        for (i = 0; i < cantInsertores; i++)
            insertor[i].start();
        
        for (i = 0; i < cantExtractores; i++)
            extractor[i].start();
    }
    
}
