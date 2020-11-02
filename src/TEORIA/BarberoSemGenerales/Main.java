package TEORIA.BarberoSemGenerales;
import Utiles.TextoAColor;

public class Main {

    public static void main(String[] args) {
        
        TextoAColor texto = new TextoAColor();
        int i, cantClientes = 10, cantSillasEnEspera = 3;
        // Recurso compartido.
        Barberia local = new Barberia(cantSillasEnEspera);
        
        // Creacion de hilos.
        Thread barbero = new Thread(new Barbero(local), texto.cambiarColorTexto(5, "Barbero"));
        Thread[] cliente = new Thread[cantClientes];
        
        for (i = 0; i < cantClientes; i++) {
            cliente[i] = new Thread (new Cliente (local), texto.cambiarColorTexto(i, "Cliente " + (i+1)));
        }
        
        barbero.start();
        
        for (i = 0; i < cantClientes; i++) {
            cliente[i].start();
        }
    }
    
}
