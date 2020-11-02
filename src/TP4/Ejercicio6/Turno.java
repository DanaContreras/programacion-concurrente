package TP4.Ejercicio6;

import java.util.concurrent.Semaphore;

public class Turno {

         private Semaphore mutex1, mutex2, mutex3;

         public Turno () {
                  this.mutex1 = new Semaphore(1);
                  this.mutex2 = new Semaphore(0);
                  this.mutex3 = new Semaphore(0);
         }

         public void imprimirLetra(char letra, int cantRepet) {
                  // Metodo encargado de imprimir una letra cantRepet de veces.

                  switch (letra) {

                           case 'A':
                                    try {
                                             mutex1.acquire();
                                    } catch (InterruptedException exc) {
                                             System.out.println(exc.getMessage());
                                    }
                                    imprimir(letra, cantRepet);
                                    mutex2.release();
                                    break;

                           case 'B':
                                    try {
                                             mutex2.acquire();
                                    } catch (InterruptedException exc) {
                                             System.out.println(exc.getMessage());
                                    }
                                    imprimir(letra, cantRepet);
                                    mutex3.release();
                                    break;

                           case 'C':
                                    try {
                                             mutex3.acquire();
                                    } catch (InterruptedException exc) {
                                             System.out.println(exc.getMessage());
                                    }
                                    imprimir(letra, cantRepet);
                                    mutex1.release();
                                    break;

                           default:
                                    System.out.println("Letra no contemplada.");
                                    break;
                  }
         }

         private void imprimir(char letra, int cantRepet) {
                  int i;
                  for (i = 0; i < cantRepet; i++) 
                           System.out.print(letra);
         }

}
