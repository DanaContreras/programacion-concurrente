package EjercParcial.SistemaImpresion;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * @author Dana ~
 */

public class Usuario implements Runnable {

    private SpoolerImpresora impresora;
    private char[] letras;
    private Random valorRandom;
    
    public Usuario (SpoolerImpresora imp, char[] letras){
        
        this.impresora = imp;
        this.letras = letras;
        this.valorRandom = new Random();
    }
    
    public void run(){
        
        char tipoBuffer;
        
        try {

            tipoBuffer = this.impresora.solicitarEscritura(letras[valorRandom.nextInt(letras.length)]);
            this.simularTiempo(tipoBuffer);

        } catch (InterruptedException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void simularTiempo (char tipoBuffer){
        
        try {
            
            int tiempo = 0;
            
            switch (tipoBuffer){
            
                case 'P': // Buffer principal.
                    tiempo = ((valorRandom.nextInt(9)+1)*100);
                    break;
                case 'S': // Buffer secundario.
                    tiempo = ((valorRandom.nextInt(9)+1)*1000);
                    break;
                
            }
            System.out.println(Thread.currentThread().getName() + " escribiendo...");
            Thread.sleep(tiempo);
        } catch (InterruptedException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
