package TP4.Ejercicioo13;

public class Main13 {

    public static void main(String[] args) {
        
        // Recurso compartido.
        Confiteria confiteria = new Confiteria ();
        
        // Creacion de hilos.
        int i, cantEmpleados = 6;
        Thread mozo = new Thread (new Mozo(confiteria), "Mozo");
        Thread[] empleado = new Thread [cantEmpleados];
        
        for (i = 0; i < cantEmpleados; i++) {
            empleado[i] = new Thread (new Empleado (confiteria), "Empleado " + (i+1));
        }
        
        mozo.start();
        for (i = 0; i < cantEmpleados; i++) {
            empleado[i].start();
        }
    }
    
}
