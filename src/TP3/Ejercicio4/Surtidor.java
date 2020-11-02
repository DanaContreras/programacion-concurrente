
package TP3.Ejercicio4;

public class Surtidor {
    
    private int litrosDispon;
    
    public Surtidor (){
        this.litrosDispon = 100;
    }

    public synchronized int getLitrosDispon() {
        return litrosDispon;
    }

    public synchronized void setLitrosDispon(int litrosDispon) {
        this.litrosDispon = litrosDispon;
    }
    
    public synchronized boolean cargarNafta (int cantKmRecorridos){
        /* Metodo encargado de modificar los litros disponibles del surtidor.
           Un auto consume 1 litro de combustible por cada 12 kilometros. */
        
        boolean realizado = false;
        
        if (litrosDispon > 0){
            
            int consumoInicial = cantKmRecorridos/12, consumoFinal;
            realizado = true;
            
            System.out.println("Surtidor antes: "+ this.litrosDispon + " litros disponibles.");
            
            if (consumoInicial > litrosDispon) // Se consumen los litros disponibles en el surtidor.
                consumoFinal = litrosDispon;
            else
                consumoFinal = consumoInicial;
                
            this.litrosDispon = this.litrosDispon - consumoFinal;
          
            System.out.println("\033[32mEl auto con PATENTE " + Thread.currentThread().getName() + " cargo " + consumoFinal + " litros.\nSurtidor después: "+ this.litrosDispon + " litros disponibles.\n");
        }
        
        return realizado;
    }
 
}
