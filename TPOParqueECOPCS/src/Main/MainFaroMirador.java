package Main;

import Hilos.AdministradorTobogan;
import Hilos.Visitante;
import RecursosCompartidos.FaroMirador;
import Utiles.TextoAColor;

public class MainFaroMirador {

    public static void main(String[] args) {
        
        TextoAColor txtColor = new TextoAColor();
        int maxEnEscalera = 2;
        int maxVisitantes = 7;
        
        // recurso compartido.
        FaroMirador faroMirador = new FaroMirador(maxEnEscalera);
        
        // creacion de hilos
        Thread admin = new Thread(new AdministradorTobogan(faroMirador), txtColor.cambiarColor("Violeta", "AdminTobogan"));
        Thread[] visitante = new Thread[maxVisitantes];
        
        for (int i = 0; i < maxVisitantes; i++)
            visitante[i] = new Thread(new Visitante(faroMirador), txtColor.cambiarColor("Rojo", "Visitante ") + txtColor.cambiarColorTexto(i, "" +(i + 1) + ""));
        
        // estado start
        admin.start();
        
        for (int i = 0; i < maxVisitantes; i++)
            visitante[i].start();
        
    }
    
}
