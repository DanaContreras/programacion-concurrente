package TP6.Ejercicio7;
import Utiles.TextoAColor;

/*
 * @author Dana ~
 */

public class Main7 {

    public static void main(String[] args) {
        
        TextoAColor color = new TextoAColor();
        
        char[] tipoHorno = {'A', 'B', 'C'};
        int cantTipo = tipoHorno.length, pesoA = 2, pesoB = 1, pesoC =3, cantHornos = 3, cantRobots = 2, pesoMaximo = 10;
        int[] pesoPastel = {pesoA, pesoB, pesoC};

        // Recurso compartido.
        Pasteleria pasteleria = new Pasteleria(pesoMaximo, pesoPastel);

        // Creacion de hilos.
        Thread brazo = new Thread (new BrazoMecanico(pasteleria), color.colorROJO("Brazo Mecanico"));
        Thread[] horno = new Thread[cantHornos];
        Thread[] robot = new Thread[cantRobots];

        for (int i = 0; i < cantHornos; i++) {
            horno[i] = new Thread (new Horno(i%cantTipo,pasteleria), color.colorPURPLE("Horno " + tipoHorno[i%cantTipo]));
        }
        
        for (int i = 0; i < cantRobots; i++) {
            robot[i] = new Thread (new Empaquetador(pasteleria), color.colorCYAN("Empaquetador " + color.cambiarColorTexto(i,""+(i+1))));
        }
        
        brazo.start();
        
        for (int i = 0; i < cantHornos; i++) {
            horno[i].start();
        }
        
        for (int i = 0; i < cantRobots; i++) {
            robot[i].start();
        }
    }
    
}
