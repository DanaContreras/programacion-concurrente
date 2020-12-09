package EjercParcial.Atomos;
import java.util.Random;
import Utiles.TextoAColor;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * @author Dana ~
 */

public class GeneradorAtomo implements Runnable{

    TextoAColor color = new TextoAColor();
    
    private Agua generacion;
    private char[] tipoAtomo;
    private Random valorRandom;
    
    public GeneradorAtomo(Agua agua, char[] tipoAtomo){
        
        this.tipoAtomo = tipoAtomo;
        this.generacion = agua;
        this.valorRandom = new Random();
    }
    
    public void run(){
        
        int valor, i = 0;
        String texto;
        while(true){
            
            i++;
            valor = valorRandom.nextInt(tipoAtomo.length);
            if (tipoAtomo[valor] == 'O')
                texto = "Oxigeno" + i;
            else
                texto = "Hidrogeno" + i;
                        
            new Thread(new Atomo(generacion, tipoAtomo[valor]), color.cambiarColorTexto(valor, texto)).start();
            
            this.simularTiempo();
        }
        
        
    }
    
    private void simularTiempo (){
      
        try {
            System.out.println(Thread.currentThread().getName() + " generando atomos...");
            Thread.sleep((valorRandom.nextInt(9)+1)*1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(GeneradorAtomo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
