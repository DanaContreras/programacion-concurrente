package Main;

import RecursosCompartidos.NadoDelfines;
import Hilos.InstructorDelfines;
import Hilos.Visitante;
import Utiles.TextoAColor;

public class MainNadoDelfines {
    
    public static void main(String[] args) {
        
        TextoAColor txtColor = new TextoAColor();
        int maxPiletas = 4;
        int maxEnGrupo = 2;
        int maxVisitantes = 7;
        int duracion = 20;
        
        // recurso compartido
        NadoDelfines nadoDelfines = new NadoDelfines(maxPiletas, maxEnGrupo);
        
        // creacion de hilos
        Thread instructorDelfines = new Thread(new InstructorDelfines(nadoDelfines, duracion),  txtColor.cambiarColor("Azul", "InstructorDelfines"));
        Thread visitante[] = new Thread[maxVisitantes];
        
        for (int i = 0; i < maxVisitantes; i++) {
            visitante[i] = new Thread(new Visitante(nadoDelfines), txtColor.cambiarColor("Rojo", "Visitante ") + txtColor.cambiarColorTexto(i, "" +(i + 1) + ""));
        }
        
        instructorDelfines.start();
        for (int i = 0; i < maxVisitantes; i++) {
            visitante[i].start();
        }
    }
    
}
