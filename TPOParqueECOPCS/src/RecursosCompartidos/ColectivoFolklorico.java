package RecursosCompartidos;

public class ColectivoFolklorico {
    
    private boolean comenzoRecorrido;
    private boolean terminoRecorrido;
    private boolean fueNotificado;
    private int cantVisitantes;
    private final int maxVisitantes;
    private final Object monitorDisponibilidad;
    private final Object monitorVisitante;
    
    public ColectivoFolklorico (int maxVisitantes) {
        this.comenzoRecorrido = false;
        this.terminoRecorrido = false;
        this.fueNotificado = true;
        this.cantVisitantes = 0;
        this.maxVisitantes = maxVisitantes;
        this.monitorDisponibilidad = new Object();
        this.monitorVisitante = new Object();
    }
    
    // metodos usados por hilo Conductor
    public void esperarVisitantes() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " espera por visitantes para empezar recorrido al parque.");
        
        synchronized (monitorDisponibilidad) {
            while (fueNotificado || cantVisitantes == 0) {
                fueNotificado = false;
                this.monitorDisponibilidad.wait(2000);
            }
            
            comenzoRecorrido = true;
            System.out.println(Thread.currentThread().getName() + " espero demasiado y comienza el recorrido con " + cantVisitantes + "/" + maxVisitantes + " visitantes.");
        }
    }
    
    public void terminarRecorrido() throws InterruptedException {
        synchronized (monitorVisitante) {
            System.out.println(Thread.currentThread().getName() + " avisa a visitantes que pueden bajarse.");
            terminoRecorrido = true;
            this.monitorVisitante.notifyAll();
        }
        
        synchronized (monitorDisponibilidad) {
            while (comenzoRecorrido)
                this.monitorDisponibilidad.wait();
        }
    }
    
    // metodos usados por hilo Visitante.
    public void subirseColectivo() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " quiere subirse al colectivo folklorico.");
        
        synchronized (monitorDisponibilidad) {
            while (comenzoRecorrido || cantVisitantes == maxVisitantes)
                this.monitorDisponibilidad.wait();
            
            cantVisitantes++;
            fueNotificado = true;
            this.monitorDisponibilidad.notifyAll();
            System.out.println(Thread.currentThread().getName() + " subio al colectivo. Ocupado " + cantVisitantes + "/" + maxVisitantes + ".");
        }
    }
    
    public void bajarseColectivo() throws InterruptedException {
        
        synchronized (monitorVisitante) {
            while (!terminoRecorrido)
                this.monitorVisitante.wait();
        }
        
        synchronized (monitorDisponibilidad) {
            cantVisitantes--;
            System.out.println(Thread.currentThread().getName() + " se bajo del colectivo.");
            
            if (cantVisitantes == 0) {
                comenzoRecorrido = false;
                
                synchronized (monitorVisitante) {
                    terminoRecorrido = false;
                }
                
                this.monitorDisponibilidad.notifyAll();
            }
        }
    }
    
}
