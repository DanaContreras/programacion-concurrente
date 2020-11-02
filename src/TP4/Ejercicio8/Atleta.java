package TP4.Ejercicio8;

public class Atleta implements Runnable {

    private Testigo testigo;

    public Atleta(Testigo tes) {
        this.testigo = tes;
    }

    public void run() {
        testigo.correr();
    }
}
