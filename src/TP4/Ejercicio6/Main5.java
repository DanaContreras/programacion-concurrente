package TP4.Ejercicio6;

public class Main5 {

         public static void main(String[] args) {

                  int i;
                  Turno turno = new Turno();

                  // Creacion de hilos.
                  Thread[] hilo = new Thread[3];
                  char caracter = 'A';

                  for (i = 0; i < 3; i++) {
                           hilo[i] = new Thread (new Letra(caracter, i + 1, turno));
                           caracter++;
                  }

                  for (i = 0; i < 3; i++) {
                           hilo[i].start();
                  }

         }

}
