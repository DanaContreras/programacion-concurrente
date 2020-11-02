package TP4.Ejercicio9;

import TP4.Ejercicio9.Cliente;
import TP4.Ejercicio9.Taxi;
import TP4.Ejercicio9.Taxista;

public class Main9 {

         public static void main(String[] args) {
                  
                  // Recurso compartido.
                  Taxi auto = new Taxi();
                  
                  // Creacion de hilos.
                  int i, cantClientes = 10;
                  Thread conductor = new Thread (new Taxista(auto), "Taxista");
                  Thread[] cliente = new Thread[cantClientes];
                  
                  for (i = 0; i < cantClientes; i++) 
                           cliente[i] = new Thread (new Cliente(auto), "Cliente " + (i+1));
                  
                  conductor.start();
                  for (i=0; i<cantClientes; i++)
                           cliente[i].start();
         }
}
