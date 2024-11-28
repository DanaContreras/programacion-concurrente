package Main;

import Hilos.ControlHorario;
import Hilos.Visitante;
import RecursosCompartidos.Ingreso;
import Utiles.TextoAColor;

public class MainIngreso {

    public static void main(String[] args) {
        
        TextoAColor txtColor = new TextoAColor();
        int maxVisitantes = 4;
        
        int horaInicio = 9;
        int horaDesvio = 17;
        int horaFin = 18;
        int maxMolinetes = 3;
        
        int maxPiletas = 4;
        int maxEnGrupo = 2;
        int minEnPiletas = maxEnGrupo * (maxPiletas -1);
        
        int maxGomIndiv = 2;
        int maxGomPareja = 1;
        int maxTotal = maxGomIndiv + maxGomPareja * 2;
        int maxBicicletas = 2;
        int maxEnTren = maxTotal - maxBicicletas;
        
        // recurso compartido
        Ingreso ingreso = new Ingreso(horaInicio, horaDesvio, horaFin, minEnPiletas, maxTotal, maxMolinetes);
        
        // creacion de hilos
        Thread controlHorario = new Thread(new ControlHorario(ingreso), txtColor.cambiarColor("Cyan", "HORARIO"));
        Thread[] visitante = new Thread[maxVisitantes];
        
        for (int i = 0; i < maxVisitantes; i++)
            visitante[i] = new Thread(new Visitante(ingreso), txtColor.cambiarColor("Rojo", "Visitante ") + txtColor.cambiarColorTexto(i, "" +(i + 1) + ""));
        
        // estado start
        controlHorario.start();
        
        for (int i = 0; i < maxVisitantes; i++)
            visitante[i].start();
        
    }
    
}
