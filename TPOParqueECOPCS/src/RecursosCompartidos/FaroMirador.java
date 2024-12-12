package RecursosCompartidos;

import java.util.concurrent.Semaphore;

public class FaroMirador {

    private boolean asignarColaUno;
    private int cantColaUno;
    private int cantColaDos;
    private final Semaphore mutexColas;
    private final Semaphore semEscalera;
    private final Semaphore semToboganUno;
    private final Semaphore semToboganDos;
    private final Semaphore semAtencion;
    private final Semaphore semVisitante;
    private final Semaphore semAdmin;
    private final Semaphore semFaro;
    
    public FaroMirador (int maxEnEscaleras) {
        this.cantColaUno = 0;
        this.cantColaDos = 0;
        this.mutexColas = new Semaphore(1);
        this.semEscalera = new Semaphore(maxEnEscaleras);
        this.semFaro = new Semaphore(maxEnEscaleras * 2);
        this.semToboganUno = new Semaphore(1, true);
        this.semToboganDos = new Semaphore(1, true);
        this.semAtencion = new Semaphore(1, true);
        this.semVisitante = new Semaphore(1);
        this.semAdmin = new Semaphore(0);
    }
    
    // metodos de hilo AdministradorTobogan.
    public void esperarVisitante() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " espera por un visitante.");
        this.semAdmin.acquire();
    }
    
    public void administrarToboganes() throws InterruptedException {
        this.mutexColas.acquire();
        if (cantColaUno < cantColaDos) { 
            cantColaUno++;
            asignarColaUno = true;
        } else {
            cantColaDos++;
           asignarColaUno = false;
        }
        this.mutexColas.release();
        
        System.out.println(Thread.currentThread().getName() + " decide que el visitante ira al tobogan " + (asignarColaUno? "uno" : "dos") + ".");
        
        this.semVisitante.release(); 
    }
    
    // metodos de hilo Visitante
    public void subirEscalera() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " quiere subir por la escalera.");
        this.semEscalera.acquire();
        System.out.println(Thread.currentThread().getName() + " está subiendo por la escalera caracol.");
    }
    
    public void salirDeEscalera() throws InterruptedException{
        this.semFaro.acquire();
        this.semEscalera.release();
    }
    
    public boolean solicitarTobogan() throws InterruptedException {
        boolean irAColaUno;
        
        this.semAtencion.acquire(); // un hilo requiere la atencion del admin.
        
        System.out.println(Thread.currentThread().getName() + " solicita tobogan a " + "\u001B[35m" + "admin " + "\u001B[0m" + ".");
        
        this.semAdmin.release(); // avisa al admin que quiere un tobogan
        this.semVisitante.acquire(); // espera hasta que el admin tome la decision
        irAColaUno = asignarColaUno;
        this.semAtencion.release();
        
        return irAColaUno;
    }
    
    public void irATobogan(boolean irAColaUno) throws InterruptedException {
        if (irAColaUno)
            this.semToboganUno.acquire();
        else
            this.semToboganDos.acquire();
        
        this.mutexColas.acquire();
        if (irAColaUno)
            cantColaUno--;
        else
            cantColaDos--;
        this.mutexColas.release();
    }
    
    public void salirDeTobogan(boolean enToboganUno) {
        if (enToboganUno)
            this.semToboganUno.release();
        else
            this.semToboganDos.release();
        
        this.semFaro.release();
        System.out.println(Thread.currentThread().getName() + " salio del tobogan " + (enToboganUno? "uno" : "dos") + " y ya se encuentra en la pileta.");
    }
    
}
