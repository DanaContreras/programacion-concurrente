
package TP3.Ejercicio2;

public class Main2 {

    public static void main(String[] args) {
        
        Player jugador = new Player();
        
        // Creacion de hilos por medio de la interfaz Runnable.
        Personaje o = new Personaje (-3, jugador);
        Personaje c = new Personaje(3, jugador);
        
        Thread orco = new Thread(o, "Orco");
        Thread curandero = new Thread (c, "Curandero");
        
        orco.start();
        curandero.start();
        
        try{
            orco.join();
            curandero.join();
            System.out.println("El JUGADOR tiene " + jugador.getVida() + " puntos de VIDA.");
        } catch (InterruptedException excep){}  
    }
    
}

/*
    Resolucion del INCISO 2:

        a) Al ejecutar el algoritmo varias veces, se concluye que el resultado final es en ocasiones inconsistentes.
           Aparecen los siguientes valores: 10, 13 y 7.

        b) En base a los resultados obtenidos se concluye que los datos inconsistentes se producen debido a que variable
           "vida" es compartida por los hilos "Orco" y "Curandero", por lo tanto, se producen inconsistencia en el resultado
            final, cuando uno de los hilos aún no opera sobre la vida del jugador y le toca el tiempo de ejecucion al otro hilo.
           
        c) La solución del ejercicio aparece comentada dentro del algoritmo; la misma consiste en tratar la sección crítica
           del código para que se ejecute en exclusión mutua, utilizando en este caso la palabra synchronized en el metodo
           modificarVida(...), garantizando la atomicidad de la operacion.

*/