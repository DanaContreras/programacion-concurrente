package RecursosCompartidos;

import java.util.concurrent.Semaphore;

public class Shop {
  
    private final Semaphore semCajas;
    
    public Shop (int maxCajas) {
        this.semCajas = new Semaphore(maxCajas, true);
    }
    
    // metodos del hilo Visitante
    public void solicitarCaja() throws InterruptedException {
        this.semCajas.acquire();
        System.out.println(Thread.currentThread().getName() + " consiguio una caja.");
    }
    
    public void desocuparCaja() {
        this.semCajas.release();
        System.out.println(Thread.currentThread().getName() + " desocupo la caja.");
    }
    
}
