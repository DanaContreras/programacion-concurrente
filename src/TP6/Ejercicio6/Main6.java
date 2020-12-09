package TP6.Ejercicio6;
import Utiles.TextoAColor;

/*
 * @author Dana ~
 */

public class Main6 {

    public static void main(String[] args) {
        
        TextoAColor color = new TextoAColor();
        
        int topeMax = 5, topeMin =2, topePersonas = 8, cantPersonas, numVisitantes = 10, numVisitantesEnSilla = 3, numPersonal = 11, numInvest = 11;
        cantPersonas = numVisitantes + numVisitantesEnSilla + numPersonal + numInvest;
        char[] tipoPersona = {'V', 'I', 'P'}; // V:Visitante, I:Investigador, P:PersonalMantenimiento.
        
        // Recurso compartido.
        Observatorio sala = new Observatorio(topeMax, topeMin, topePersonas, tipoPersona);
        
        // Creacion de Hilos.
        Thread[] persona = new Thread[cantPersonas];
        
        int enSilla = numVisitantes + numVisitantesEnSilla, personal = enSilla + numPersonal;
        
        for (int i = 0; i < cantPersonas; i++) {
            
            if (i < numVisitantes)
                persona[i] = new Thread (new Persona ('V', false, sala), color.colorROJO("Visitante " + (i+1)));
            else if (i < enSilla)
                persona[i] = new Thread (new Persona ('V', true, sala), color.colorPURPLE("Visitante en silla " + (i+1)));
            else if (i < personal)
                persona[i] = new Thread (new Persona ('P', false, sala), color.colorCYAN("Personal " + (i+1)));
            else
                persona[i] = new Thread (new Persona('I', false, sala), color.colorAMARILLO("Investigador" + (i+1)));
        }
        
        for (int i = 0; i < cantPersonas; i++) {
            persona[i].start();
        }
        
    }
    
}
