package EjercParcial.SistemaImpresion;
import Utiles.TextoAColor;

/*
 * @author Dana ~
 */

public class Main1 {

    public static void main(String[] args) {
        
        /*
            Solucion implementada con semaforos generales, por mayor simplicidad ya que los mismo permiten
            la exclusion mutua de las secciones criticas del codigo, y el acceso o bloqueo de N los hilos
            (para el caso del buffer limitado).
        */
        
        TextoAColor color = new TextoAColor();
        
        int numUsuarios = 10, capacidadBuffer = 4;
        char[] letra = {'A', 'B', 'C', 'D', 'F', 'G', 'H'};
        
        // Recurso compartido.
        SpoolerImpresora impresora = new SpoolerImpresora (capacidadBuffer);
        
        // Creacion de hilos.
        Thread servidor = new Thread (new ServidorImpresion (impresora), color.colorCYAN("Servidor"));
        Thread[] usuario = new Thread[numUsuarios];
        
        for (int i = 0; i < numUsuarios; i++) {
            usuario[i] = new Thread (new Usuario (impresora, letra), color.colorROJO("Usuario") + color.cambiarColorTexto(i, ""+(i+1)+""));
        }
        
        servidor.start();
        for (int i = 0; i < numUsuarios; i++) {
            usuario[i].start();
        }
        
    }
}
