package RecursosCompartidos;

import java.util.concurrent.Semaphore;

public class Ingreso {
    
    // horario
    private int horaActual;
    private final int horaInicio;
    private final int horaDesvio;
    private final int horaFin;
    private final Semaphore mutexHora;
    
    // ingreso
    private int cantEnPiletas;
    private int cantEnCarrera;
    private final int minEnPileta;
    private final int minEnCarrera;
    private final Semaphore mutexActividad;
    private final Semaphore semMolinetes;
    
    public Ingreso (int horaInicio, int horaDesvio, int horaFin, int minEnPileta, int minEnCarrera, int maxMolinetes) {
        this.horaActual = horaInicio;
        this.horaInicio = horaInicio;
        this.horaDesvio = horaDesvio;
        this.horaFin = horaFin;
        this.mutexHora = new Semaphore(1, true);
        
        this.cantEnPiletas = 0;
        this.cantEnCarrera = 0;
        this.minEnPileta= minEnPileta;
        this.minEnCarrera = minEnCarrera;
        this.mutexActividad = new Semaphore(1, true);
        this.semMolinetes = new Semaphore(maxMolinetes, true);
    }
    
    // metodos usados por ControlHorario
    public int getHora() {
        return horaActual;
    }

    public int setHora() throws InterruptedException {        
        this.mutexHora.acquire();
        horaActual++;
        if (horaActual >= 24) 
            horaActual = 0; 
        
        if (horaActual == horaInicio)
            System.out.println("EL PARQUE ABRE SUS PUERTAS");
        else if (horaActual == horaFin)
            System.out.println("EL PARQUE CIERRA SUS PUERTAS");
        else if (horaActual == horaDesvio)
            System.out.println("CONTROL PRIORIZA ACTIVIDADES CON MINIMO DE VISITANTES");
        this.mutexHora.release();
        
        return horaActual;
    }

    // metodo usado por hilo Visitante
    public String definirTipoActividad(String tipoActiv) throws InterruptedException {
        String tipoActividad = "";
        boolean puedeIngresar;
        boolean desvio;
        
        this.mutexHora.acquire();
        puedeIngresar = (horaActual >= horaInicio && horaActual < horaFin); 
        desvio = (horaActual >= horaDesvio);
        this.mutexHora.release();
        
        this.mutexActividad.acquire();
        
       if (puedeIngresar) {
            if (desvio) {
                // prioriza las actividades incompletas
                if (cantEnPiletas < minEnPileta || (cantEnPiletas > 0 &&  cantEnPiletas % minEnPileta != 0)) {
                    tipoActividad = "NadoDelfines";
                    cantEnPiletas++;
                } else if (cantEnCarrera < minEnCarrera && (cantEnCarrera > 0 && cantEnCarrera % minEnCarrera != 0)) {
                    tipoActividad = "CarreraGomones";
                    cantEnCarrera++;
                } else {
                    // ambas actividades están completas, se asigna alternativa
                    tipoActividad = (tipoActiv.equals("NadoDelfines") || tipoActiv.equals("CarreraGomones")) ? "Restaurantes" : tipoActiv;
                }
            } else {
                // Fuera de hora de desvío, asignar según preferencia
                if (tipoActiv.equals("NadoDelfines")) {
                    cantEnPiletas++;
                } else if (tipoActiv.equals("CarreraGomones")) {
                    cantEnCarrera++;
                }
                
                tipoActividad = tipoActiv;
            } 
        } else { // consulta si quedaron hilos visitantes en espera.
           if (cantEnPiletas > 0 && cantEnPiletas < minEnPileta) {
               cantEnPiletas++;
               tipoActividad = "NadoDelfines";
           } else if (cantEnCarrera > 0 && cantEnCarrera < minEnCarrera) {
               cantEnCarrera++;
               tipoActividad = "CarreraGomones";
           }
       }
        this.mutexActividad.release();
        
        return tipoActividad;
    }
    
    public void salirTipoActividad(String tipoActiv, int cantVisitantes) throws InterruptedException {
        this.mutexActividad.acquire();
        if (tipoActiv.equals("NadoDelfines"))
            cantEnPiletas -= cantVisitantes;
        else if (tipoActiv.equals("CarreraGomones"))
            cantEnCarrera -= cantVisitantes;
        this.mutexActividad.release();
    }
    
    public void usarMolinete() throws InterruptedException {
        this.semMolinetes.acquire();
        System.out.println(Thread.currentThread().getName() +" usara molinete.");
    }
    
    public void salirDeMolinete() {
        this.semMolinetes.release();
        System.out.println(Thread.currentThread().getName() + " dejo de usar molinete.");
    }
    
}
