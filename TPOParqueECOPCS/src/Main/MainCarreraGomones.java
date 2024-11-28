package Main;

import RecursosCompartidos.CarreraGomones;
import Hilos.Visitante;
import Hilos.Maquinista;
import Utiles.TextoAColor;

public class MainCarreraGomones {
    
     public static void main(String[] args) {
         
         TextoAColor txtColor = new TextoAColor();
         int maxVisitantes  = 6;
         int maxBicicletas = 2;
         int maxTren = 3;
         int maxGomIndiv = 3;
         int maxGomPareja = 1;
                 
         // recurso compartido
         CarreraGomones carrera = new CarreraGomones(maxBicicletas, maxTren, maxGomIndiv, maxGomPareja);
                 
         // creacion de hilos
         Thread visitante[] = new Thread[maxVisitantes];
         Thread maquinista = new Thread(new Maquinista(carrera), txtColor.cambiarColor("Amarillo", "Maquinista"));
         
         for (int i = 0; i < maxVisitantes; i++)
             visitante[i] = new Thread(new Visitante(carrera), txtColor.cambiarColor("Rojo", "Visitante " + (i+1)));
         
         // estado start
         maquinista.start();
         for(int i = 0; i < maxVisitantes; i++)
             visitante[i].start();
     }
    
}
