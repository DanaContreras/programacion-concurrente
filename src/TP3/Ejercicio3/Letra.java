
package TP3.Ejercicio3;

public class Letra implements Runnable{
   
    private char nombre;
    private int id;
    private Turno turno;
    
    public Letra (char letra, int id, Turno turno){
        this.nombre = letra;
        this.id = id;
        this.turno = turno;
    }
   
    public void run(){
        
        int i;
        for (i=0; i<2; i++) // Cantidad de veces que se repite la secuencia.
            imprimir();
    }
    
    private  void imprimir(){
        
        boolean seguir = true;
        
        while (seguir){
            
            // Turno del hilo.
            if (turno.getTurno() == this.id){ 
                
                int i;
                for (i = 0; i < id; i++)
                    System.out.print(nombre);

                turno.modificarTurno(); 
                seguir = false;
            }
        }    
    }
    
}
