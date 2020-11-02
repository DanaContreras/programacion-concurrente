
package TP3.Ejercicio2;
    
public class Personaje implements Runnable{
        
    private Player jugador;
    private int puntos;
  
    public Personaje (int cant, Player jugador){
         this.puntos = cant;
         this.jugador = jugador;
    }  
    
    public void run(){
        
        int vidaActual = jugador.getVida();
        vidaActual = vidaActual + puntos;
        jugador.setVida(vidaActual);
        
        // jugador.modificarVida(puntos);
    }
    
}
