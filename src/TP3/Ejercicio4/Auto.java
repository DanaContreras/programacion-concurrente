
package TP3.Ejercicio4;

public class Auto extends Vehiculo implements Runnable{

    private String patente;
    private int kmARecorrer;
    private double kmFaltantesParaElService;
    private Surtidor surtidor;
    
    // Constructores.
    public Auto (String id, String patente, String modelo, String marca, String color, int kmFaltantesParaElService, int kmRecorridos, Surtidor surtidor) {
        super (id, modelo, marca, color);
        this.patente = patente;
        this.kmFaltantesParaElService = kmFaltantesParaElService;
        this.kmARecorrer = kmRecorridos;
        this.surtidor = surtidor;
    }
    
    public Auto (String id, String patente, int kmARecorrer, Surtidor surtidor){
        super(id);
        this.patente = patente;
        this.kmARecorrer = kmARecorrer;
        this.surtidor = surtidor;
    }
    
    // Interfaz.
    public int getKmRecorridos() {
        return kmARecorrer;
    }

    public void setKmRecorridos(int kmRecorridos) {
        this.kmARecorrer = kmRecorridos;
    }
    
    public void setSurtidor(Surtidor surt){
        this.surtidor = surt;
    }
    
    public double getKmFaltantesParaElService() {
        return kmFaltantesParaElService;
    }

    public void setKmFaltantesParaElService(double kmFaltantesParaElService) {
        this.kmFaltantesParaElService = kmFaltantesParaElService;
    }
    
    public void run(){
        
        recorrerKm();
        
        if (!surtidor.cargarNafta(kmARecorrer))
            System.out.println("\033[31mNo hay combustible para el auto con PATENTE " + this.patente + ".\n");
        
    }

    private void recorrerKm(){
        // Metodo encargado de simular los kilometros recorridos por el auto por medio del tiempo (utilizacion de sleep).
        
        try {
            Thread.sleep (this.kmARecorrer*100);
        } catch (InterruptedException e) {};

        System.out.println("El auto con PATENTE " + this.patente + " recorrio " + kmARecorrer + " kilometros.\n"
                            + "Necesita combustible.\n");
    }
    
}
