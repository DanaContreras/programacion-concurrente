package TEORIA.BarberoSemGenerales;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente implements Runnable{

    private Barberia barberia;
    
    public Cliente (Barberia local){
        this.barberia = local;
    }
    
    public void run(){
        
        boolean seguir = true;
        char opcion = 'c';
        
        while (seguir){
            this.simularCaminata(opcion);
            if (this.barberia.entrarABarberia()){
                System.out.println(Thread.currentThread().getName() + " se sienta en una silla de espera.");
                this.barberia.solicitarCorte();
                this.barberia.salirDeBarberia();
                seguir = false;
            }
            opcion = 'v';
        }
    }
    
    private void simularCaminata(char opcion){
        
        String textoAMostrar = "";
        Random tiempo = new Random();
        
        switch(opcion){
            case 'c': // caminata.
                textoAMostrar = Thread.currentThread().getName() + " se encuentra caminando hacia la Barberia.";
                break;
            case 'v': // vuelta.
                textoAMostrar = Thread.currentThread().getName() + " encontro la barberia llena. Se va a dar una vuelta.";
        }
        
        try {
            System.out.println(textoAMostrar);
            Thread.sleep((tiempo.nextInt(9)+1) *100);
        } catch (InterruptedException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
