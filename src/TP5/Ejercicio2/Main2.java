package TP5.Ejercicio2;
import Utiles.TextoAColor;

public class Main2 {

    public static void main(String[] args) {
        
        TextoAColor color = new TextoAColor();
        
        // Recurso compartido.
        int i, cantComederos = 5, cantMaxComieron = 8, cantGatos = 7, cantPerros = 10;
        char[] especie = {'G', 'P'};
        Comedor2 comedor = new Comedor2(cantComederos, cantMaxComieron, especie);
        
        // Creacion de hilos.
        Thread[] gato = new Thread[cantGatos];
        Thread[] perro = new Thread[cantPerros];
        
        for (i = 0; i < cantGatos; i++) {
            gato[i] = new Thread (new Mascota2 ('G', comedor), color.cambiarColorTexto(i, "Gato " + (i+1)));
        }
        
        for (i = 0; i < cantPerros; i++) {
            perro[i] = new Thread (new Mascota2 ('P', comedor), color.cambiarColorTexto(i, "Perro " + (i+1)));
        }
        
        for (i = 0; i < cantGatos; i++) {
            gato[i].start();
        }
        
        for (i = 0; i < cantPerros; i++) {
            perro[i].start();
        }
    }
    
}
