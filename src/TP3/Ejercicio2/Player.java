
package TP3.Ejercicio2;

public class Player {
    
    private int vida;
    
    // Constructor.
    public Player (){
        this.vida = 10;
    }
    
    // Interfaz.
    public synchronized int getVida(){
        return vida;
    }
  
    public synchronized void setVida(int vida) {
        this.vida = vida;
    }
    
//    public synchronized void modificarVida (int puntos){
//        
//        int vidaActual = this.getVida();
//        vidaActual = vidaActual + puntos;
//        this.setVida(vidaActual);
//        
//    }
    
}
