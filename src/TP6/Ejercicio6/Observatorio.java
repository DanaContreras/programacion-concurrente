package TP6.Ejercicio6;

import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * @author Dana ~
 */

public class Observatorio {

    private int topeActual;
    private int topeMax;
    private int topeMin;
    private int topeHilos; // Variable para evitar inanicion entre hilos.
    private int cantHilosTope; // Variable que lleva el conteo de hilos del mismo tipo que ya entraron en sala.
    private int cantEnSala;
    private int cantSillaEnSala; // Variable para controlar cuantas personas en silla de rueda hay en la sala actualmente.
    private int cantEnEspera;
    private int turno;
    private int[] cantPersonasEsperando; // 0:visitante, 1:Personas
    private char[] tipoPersona;
    private boolean primerHilo;
    private boolean otroHiloEnEspera;
    private boolean mismoHiloEnEspera;
    
    public Observatorio(int topeMax, int topeMin, int topePersonas, char[] tipoPersona){
        this.topeActual = topeMax;
        this.topeMax = topeMax;
        this.topeMin = topeMin;
        this.topeHilos = topePersonas;
        this.cantHilosTope = 0;
        this.cantEnSala = 0;
        this.cantSillaEnSala = 0;
        this.cantEnEspera = 0;
        this.tipoPersona = tipoPersona;
        this.primerHilo = true;
        this.otroHiloEnEspera = false;
        this.mismoHiloEnEspera = false;
    }
    
    public synchronized void entrarASala (char tipoPersona, boolean enSilla) throws InterruptedException{  
        
        System.out.println(Thread.currentThread().getName()+ " esta en la puerta de la sala.");
        
        primerTurno(tipoPersona);
        
        boolean respetaTope = (turno == tipoPersona) && (cantHilosTope < topeHilos);
        cantEnEspera++;
        System.out.println("cant en sala >= tope " + (cantEnSala >= topeActual));
        System.out.println("turno = " + tipoPersona);
        System.out.println("respetaTope cantHilos<tope " +respetaTope );
        
        while (cantEnSala >= topeActual || turno != tipoPersona || !respetaTope){
            
            System.out.println(Thread.currentThread().getName()+ " no logro entrar a la sala. Espera afuera.");
            
            if (turno != tipoPersona && !otroHiloEnEspera)
                otroHiloEnEspera = true;
            else if (turno == tipoPersona)
                mismoHiloEnEspera = true;
            
            this.wait();
            
            respetaTope = (turno == tipoPersona) && (cantHilosTope < topeHilos);

        }
        cantEnEspera--;
        System.out.println(Thread.currentThread().getName()+ " logro entrar a la sala.");
        cantEnSala++;
        cantHilosTope++;
        
        if (enSilla){ // Visitantes en silla de ruedas.
            topeActual = topeMin;  
            cantSillaEnSala++;
        }
        
        if (tipoPersona == 'I')
            topeActual = 1;
        
        System.out.println("Cant en sala = " + cantEnSala);
        System.out.println("Tope de sala=" + topeActual);
        
    }
    
    private void primerTurno(char tipo){
        
        if (primerHilo){
            turno = tipo;
            primerHilo = false;
        }
        
    }
    
    public synchronized void salirDeSala (char tipoPersona, boolean enSilla){
        
        System.out.println(Thread.currentThread().getName()+ " sale de la sala.");
        
        cantEnSala--;
        
        if (enSilla)
            cantSillaEnSala--;
        
        controlarTurno();
        
        if (turno == 'V' && cantSillaEnSala == 0)
                topeActual = topeMax; 
        
        if (turno != 'I' && topeActual ==1)
            topeActual = topeMax;
        System.out.println("Cant en sala = " + cantEnSala);
        System.out.println("Tope de sala=" + topeActual);
        
        this.notifyAll();
    }
    
    private void controlarTurno (){
        
        if (cantEnSala == 0){
            
            System.out.println("cantEnEspera " + cantEnEspera);
           
            if (otroHiloEnEspera){

                // Se cambia de turno, ya que hay otros hilos en espera.
                boolean seguir = true;
                int i = 0;
                while(seguir){

                    if (this.turno == this.tipoPersona[i]){
                        this.turno = this.tipoPersona[(i+1) % this.tipoPersona.length];
                        seguir = false;
                    }

                    i++;
                }
            }
                
            if (this.cantEnEspera > 0){
                this.cantHilosTope = 0;
            }  
            
            mismoHiloEnEspera = false;
            otroHiloEnEspera = false;
        }     
    }
    
}
