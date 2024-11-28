package Main;

import Hilos.AsistenteSnorkel;
import Hilos.Visitante;
import RecursosCompartidos.Snorkel;
import Utiles.TextoAColor;

public class MainSnorkel {
    
    public static void main(String[] args) {

        TextoAColor txtColor = new TextoAColor();
        int maxEquipos = 4;
        int maxAsistentes = 2;
        int maxVisitantes = 15;

        // recurso compartido
        Snorkel snorkel = new Snorkel(maxEquipos);

        // creacion de hilos
        Thread asistente[] = new Thread[maxAsistentes];
        Thread visitante[] = new Thread[maxVisitantes];

        for (int i = 0; i < maxAsistentes; i++)
            asistente[i] = new Thread(new AsistenteSnorkel(snorkel), txtColor.cambiarColor("Cyan", "AsistenteSnorkel " + (i+1)));

        for (int i = 0; i < maxVisitantes; i++)
            visitante[i] = new Thread(new Visitante(snorkel), txtColor.cambiarColor("Rojo", "Visitante ") + txtColor.cambiarColorTexto(i, "" +(i + 1) + ""));
        
       // estado start
        for (int i = 0; i < maxAsistentes; i++)
            asistente[i].start();
        
        for (int i = 0; i < maxVisitantes; i++)
            visitante[i].start();
    }
}
