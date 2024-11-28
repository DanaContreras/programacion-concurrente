package Main;

import Hilos.Conductor;
import Hilos.Visitante;
import RecursosCompartidos.ColectivoFolklorico;
import Utiles.TextoAColor;

public class MainTransporte {
    
    public static void main(String[] args) {
        TextoAColor txtColor = new TextoAColor();
        int cantVisitantes = 7;
        int maxVisitantes = 4;
        
        // recurso compartido
        ColectivoFolklorico colectivo = new ColectivoFolklorico(maxVisitantes);
        
        // creacion de hilos
        Thread conductor = new Thread(new Conductor(colectivo), txtColor.cambiarColor("Violeta", "Conductor"));
        Thread[] visitante = new Thread[cantVisitantes];
        
        for (int i = 0; i < cantVisitantes; i++)
            visitante[i] = new Thread(new Visitante(colectivo), txtColor.cambiarColor("Rojo", "Visitante ") + txtColor.cambiarColorTexto(i, "" +(i + 1) + ""));
        
        // estado start
        conductor.start();
        for (int i = 0; i < cantVisitantes; i++)
            visitante[i].start();
    }
    
}
