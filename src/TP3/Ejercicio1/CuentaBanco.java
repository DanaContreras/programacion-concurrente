
package TP3.Ejercicio1;

public class CuentaBanco {
    
    private int balance = 50;

    public CuentaBanco() {
    }

    public synchronized int getBalance() {
        return balance;
    }

    private synchronized  void retiroBancario(int retiro) {
        balance = balance - retiro;
    }
    
    public synchronized void hacerRetiro(int cantidad) throws InterruptedException {
        if (this.balance >= cantidad) {
            System.out.println(Thread.currentThread().getName()
                    + "esta realizando un retiro de: $" + cantidad + ".");
            Thread.sleep(1000);
            this.retiroBancario(cantidad);
            System.out.println(Thread.currentThread().getName() + ":Retiro realizado.");
            System.out.println("\033[32m"+Thread.currentThread().getName() + "\033[32m Los fondos son de: $" + this.balance);
        } else {
            System.out.println("\033[31mNo hay suficiente dinero en la cuenta para realizar el retiro Sr." + Thread.currentThread().getName());
            System.out.println("Su saldo actual es de $"
                    + this.balance);
            Thread.sleep(1000);
        }
    }
}
