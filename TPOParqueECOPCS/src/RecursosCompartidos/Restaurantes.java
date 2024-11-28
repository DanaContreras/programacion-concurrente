package RecursosCompartidos;

import java.util.concurrent.Semaphore;

public class Restaurantes {

    private final int[] cantRest;
    private final int[] maxRest;
    private final Semaphore mutexRestauranteUno;
    private final Semaphore mutexRestauranteDos;
    private final Semaphore mutexRestauranteTres;
    private final Semaphore semRestauranteUno;
    private final Semaphore semRestauranteDos;
    private final Semaphore semRestauranteTres;
    
    public Restaurantes (int maxRest1, int maxRest2, int maxRest3) {
        this.semRestauranteUno = new Semaphore(maxRest1, true);
        this.semRestauranteDos = new Semaphore(maxRest2, true);
        this.semRestauranteTres = new Semaphore(maxRest3, true);
        
        this.cantRest = new int[3];
        this.maxRest = new int[3];
        this.maxRest[0] = maxRest1;
        this.maxRest[1] = maxRest2;
        this.maxRest[2] = maxRest3;
        this.mutexRestauranteUno = new Semaphore(1, true);
        this.mutexRestauranteDos = new Semaphore(1, true);
        this.mutexRestauranteTres = new Semaphore(1, true);
    }
    
    // metodos usados por hilo Visitante.
    public void irARestaurante (int tipoRest, String tipoComida) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " quiere ir a " + tipoComida + " al restaurante " + (tipoRest + 1));
        
        switch (tipoRest) {
            case 0 -> this.semRestauranteUno.acquire();
            case 1 -> this.semRestauranteDos.acquire();
            case 2 -> this.semRestauranteTres.acquire();
        }
        
        contarLugares(tipoRest);
    }
    
    private void contarLugares (int tipoRest) throws InterruptedException {
        switch (tipoRest) {
            case 0 -> {
                this.mutexRestauranteUno.acquire();
                cantRest[tipoRest] = cantRest[tipoRest] + 1;
                System.out.println(Thread.currentThread().getName() + " obtuvo un lugar en el restaurante " + (tipoRest + 1) + ". Ocupados " + cantRest[tipoRest] + "/" + maxRest[tipoRest] + ".");
                this.mutexRestauranteUno.release();
            }
            case 1 -> {
                this.mutexRestauranteDos.acquire();
                cantRest[tipoRest] = cantRest[tipoRest] + 1;
                System.out.println(Thread.currentThread().getName() +" obtuvo un lugar en el restaurante " + (tipoRest + 1) + ". Ocupados " + cantRest[tipoRest] + "/" + maxRest[tipoRest] + ".");
                this.mutexRestauranteDos.release();
            }
            case 2 -> {
                this.mutexRestauranteTres.acquire();
                cantRest[tipoRest] = cantRest[tipoRest] + 1;
                System.out.println(Thread.currentThread().getName() +" obtuvo un lugar en el restaurante " + (tipoRest + 1) + ". Ocupados " + cantRest[tipoRest] + "/" + maxRest[tipoRest] + ".");
                this.mutexRestauranteTres.release();
            }
        }
        
    }
    
    public void salirDeRestaurante (int tipoRest) throws InterruptedException {
        switch (tipoRest) {
            case 0 -> this.semRestauranteUno.release();
            case 1 -> this.semRestauranteDos.release();
            case 2 -> this.semRestauranteTres.release();
        }
        desocuparLugar(tipoRest);
    }
    
    private void desocuparLugar (int tipoRest) throws InterruptedException {
        switch (tipoRest) {
            case 0 -> {
                this.mutexRestauranteUno.acquire();
                cantRest[tipoRest] = cantRest[tipoRest] - 1;
                System.out.println(Thread.currentThread().getName() + " salio del restaurante " + (tipoRest + 1) + ". Ocupados " + cantRest[tipoRest] + "/" + maxRest[tipoRest] + ".");
                this.mutexRestauranteUno.release();
            }
            case 1 -> {
                this.mutexRestauranteDos.acquire();
                cantRest[tipoRest] = cantRest[tipoRest] - 1;
                System.out.println(Thread.currentThread().getName() +" salio del restaurante " + (tipoRest + 1) + ". Ocupados " + cantRest[tipoRest] + "/" + maxRest[tipoRest] + ".");
                this.mutexRestauranteDos.release();
            }
            case 2 -> {
                this.mutexRestauranteTres.acquire();
                cantRest[tipoRest] = cantRest[tipoRest] - 1;
                System.out.println(Thread.currentThread().getName() +" salio del restaurante " + (tipoRest + 1) + ". Ocupados " + cantRest[tipoRest] + " lugares disponibles.");
                this.mutexRestauranteTres.release();
            }
        }
    }
    
}
