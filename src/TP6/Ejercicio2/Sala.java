package TP6.Ejercicio2;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dana^^
 */

public class Sala {

    private int cantNormal;
    private int cantAnormal;
    private int cantActual;
    private int cantEnSala;
    private int jubiladosEnEspera;
    private int tempActual;
    private int tUmbral;
    
    public Sala (int normal, int anormal, int umbral){
        this.cantNormal = normal;
        this.cantAnormal = anormal;
        this.cantActual = 0; // cantidad de personas que pueden entrar en la sala actualmente.
        this.cantEnSala = 0;
        this.jubiladosEnEspera = 0;
        this.tempActual = 0;
        this.tUmbral = umbral;
    }
    
    // Metodo utilizado por Medidor.
    public synchronized void notificarTemperatura(int temperatura){
        
        System.out.print(Thread.currentThread().getName() + " notifica cambio en temperatura de " + this.tempActual + " a " + temperatura + " grados.");
        
        this.tempActual = temperatura;
        if (tempActual < tUmbral)
            this.cantActual = this.cantNormal;
        else
            this.cantActual = this.cantAnormal;
        
        System.out.println(" Ahora pueden entrar " + this.cantActual + " personas.");
    }
    
    // Metodo utilizado por Persona/Jubilado.
    public synchronized void entrarSala(){
        
        System.out.println(Thread.currentThread().getName() + " esta fuera de la sala.");
        
        try {
            while(!(cantEnSala < cantActual && jubiladosEnEspera == 0))
                this.wait();
        } catch (InterruptedException ex) {
            Logger.getLogger(Sala.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println(Thread.currentThread().getName() + " entro a la sala.");
        this.cantEnSala++;
    }
    
    public synchronized void entrarSalaJubilado(){
        
        System.out.println(Thread.currentThread().getName() + " esta fuera de la sala.");
        this.jubiladosEnEspera++;
        while(!(cantEnSala < cantActual)){
            try {
                this.wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Sala.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.jubiladosEnEspera--;
        System.out.println(Thread.currentThread().getName() + " entro a la sala.");
        this.cantEnSala++;
    }
    
    public synchronized void salirSala(){
        System.out.println(Thread.currentThread().getName() + " sale de la sala.");
        this.cantEnSala--;
        this.notifyAll();
    }
    
}
