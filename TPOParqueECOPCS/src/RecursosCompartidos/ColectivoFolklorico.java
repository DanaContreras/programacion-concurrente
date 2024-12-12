package RecursosCompartidos;

public class ColectivoFolklorico {
    
    private boolean comenzoRecorrido;
    private boolean terminoRecorrido;
    private boolean fueNotificado;
    private int cantVisitantes;
    private final int maxVisitantes;
    
    public ColectivoFolklorico (int maxVisitantes) {
        this.comenzoRecorrido = false;
        this.terminoRecorrido = false;
        this.fueNotificado = true;
        this.cantVisitantes = 0;
        this.maxVisitantes = maxVisitantes;
    }
    
    // metodos usados por hilo Conductor
    public synchronized void esperarVisitantes() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " espera por visitantes para empezar recorrido al parque.");
        
            while (fueNotificado || cantVisitantes == 0) {
                fueNotificado = false;
                this.wait(2000);
            }
            
            comenzoRecorrido = true;
            System.out.println(Thread.currentThread().getName() + " espero demasiado y comienza el recorrido con " + cantVisitantes + "/" + maxVisitantes + " visitantes.");
    }
    
    public synchronized void terminarRecorrido() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " avisa a visitantes que pueden bajarse.");
        terminoRecorrido = true;
        this.notifyAll();
        
        while (comenzoRecorrido)
            this.wait();
    }
    
    // metodos usados por hilo Visitante.
    public synchronized void subirseColectivo() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " quiere subirse al colectivo folklorico.");
        while (comenzoRecorrido || cantVisitantes == maxVisitantes)
            this.wait();
            
        cantVisitantes++;
        fueNotificado = true;
        this.notifyAll();
        System.out.println(Thread.currentThread().getName() + " subio al colectivo. Ocupado " + cantVisitantes + "/" + maxVisitantes + ".");
    }
    
    public synchronized void bajarseColectivo() throws InterruptedException {
   
        while (!terminoRecorrido)
            this.wait();
        
        cantVisitantes--;
        System.out.println(Thread.currentThread().getName() + " se bajo del colectivo.");
            
        if (cantVisitantes == 0) {
            comenzoRecorrido = false;
            terminoRecorrido = false;
            this.notifyAll();
        }
    }
    
}
