package TEORIA.LectoresEscritoresMonitores;
import Utiles.TextoAColor;

public class Main {

    public static void main(String[] args) {
        
        TextoAColor color = new TextoAColor();
        int numPaginas = 8, cantEscritores = 3, cantLectores = 6;
        
        // Recurso compartido.
        Libro libro = new Libro (numPaginas);
        
        // Creacion de hilos.
        Thread[] escritor = new Thread[cantEscritores];
        Thread[] lector = new Thread[cantLectores];
        
        for (int i = 0; i < cantEscritores; i++) {
            escritor[i] = new Thread( new Escritor(libro), color.colorROJO("Escritor") + color.cambiarColorTexto(i, " " + (i+1)));
        }
        
        for (int i = 0; i < cantLectores; i++) {
            lector[i] = new Thread( new Lector(libro), color.colorCYAN("Lector") +  color.cambiarColorTexto(i, " " +(i+1)));
        }
        
        for (int i = 0; i < cantEscritores; i++) {
            escritor[i].start();
        }
        
        for (int i = 0; i < cantLectores; i++) {
            lector[i].start();
        }
    }
    
}
