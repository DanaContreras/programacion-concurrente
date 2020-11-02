package TP4.Ejercicioo14;

public class Main13 {

    public static void main(String[] args) {
        
        // Recurso compartido.
        int cantAsientos = 2;
        String[] bebida = {"Cafe","Jugo","Gaseosa","Te","Licuado"};
        String[] comida = {"Torta","Sandwich","Pollo frito","Facturas","Milanesas"};
        Confiteria confiteria = new Confiteria (cantAsientos, bebida, comida);
        
        // Creacion de hilos.
        int i, cantEmpleados = 6;
        Thread mozo = new Thread (new Mozo (confiteria), "Mozo");
        Thread cocinero = new Thread (new Cocinero (confiteria), "Cocinero");
        Thread[] empleado = new Thread [cantEmpleados];
        
        for (i = 0; i < cantEmpleados; i++) {
            empleado[i] = new Thread (new Empleado (confiteria), "Empleado " + (i+1));
        }
        
        mozo.start();
        cocinero.start();
        for (i = 0; i < cantEmpleados; i++) {
            empleado[i].start();
        }
    }
    
}
