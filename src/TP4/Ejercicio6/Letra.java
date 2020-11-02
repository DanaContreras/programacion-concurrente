package TP4.Ejercicio6;

public class Letra implements Runnable {

         private char nombre;
         private int cantRepet; // cantidad de veces que se repite la letra.
         private int cantSecuencia = 3; // cantidad de veces que se repite la secuencia. Por default, repete tres veces.
         private Turno turno;

         public Letra(char letra, int cantRepet, Turno turno) {
                  this.nombre = letra;
                  this.cantRepet = cantRepet;
                  this.turno = turno;
         }

         public char getNombre() {
                  return nombre;
         }

         public void setNombre(char nombre) {
                  this.nombre = nombre;
         }

         public int getCantRepet() {
                  return cantRepet;
         }

         public void setCantRepet(int cantRepet) {
                  this.cantRepet = cantRepet;
         }

         public void run() {
                  int i;
                  for (i = 0; i < cantSecuencia; i++) 
                           this.turno.imprimirLetra(nombre, cantRepet);
         }
         
}
