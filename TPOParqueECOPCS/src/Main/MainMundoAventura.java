package Main;

import Hilos.ControlSaltoEnAltura;
import Hilos.ControlTirolesa;
import Hilos.Visitante;
import RecursosCompartidos.MundoAventura;
import Utiles.TextoAColor;
import java.util.Scanner;

public class MainMundoAventura {
    
    public static void main(String[] args) {
        
        TextoAColor txtColor = new TextoAColor();
        Scanner teclado = new Scanner(System.in);
        int maxVisitantes = 5;
        int maxCuerdas = 2;
        int maxEnSaltos = 2;
        int maxEnTirolesa = 2;
        int tipoAct;
        
        System.out.println("Tipo actividad:");
        System.out.println("0: Cuerdas");
        System.out.println("1: Salto");
        System.out.println("2: Tirolesa");
        tipoAct  = teclado.nextInt();
        teclado.close();
        
        // recurso compartido.
        MundoAventura mundoAventura = new MundoAventura(maxCuerdas, maxEnSaltos, maxEnTirolesa);

        // creacion de hilos
        Thread controlSalto = new Thread(new ControlSaltoEnAltura(mundoAventura), txtColor.cambiarColor("Verde", "ControlSaltoEnAltura"));
        Thread controlTirolesa = new Thread(new ControlTirolesa(mundoAventura), txtColor.cambiarColor("Verde", "ControlTirolesa"));
        Thread[] visitante = new Thread[maxVisitantes];

        for (int i = 0; i < maxVisitantes; i++) 
            visitante[i] = new Thread(new Visitante(mundoAventura, tipoAct), txtColor.cambiarColor("Rojo", "Visitante ") + txtColor.cambiarColorTexto(i, "" +(i + 1) + ""));
        
        // estado start
        controlSalto.start();
        controlTirolesa.start();
        for (int i = 0; i < maxVisitantes; i++) 
            visitante[i].start();
        
    }
    
} 
