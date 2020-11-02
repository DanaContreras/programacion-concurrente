
package TP3.Ejercicio1;

public class Main1 {

    public static void main(String[] args) {
        
        VerificarCuenta vc = new VerificarCuenta();
        Thread Luis = new Thread(vc, "Luis");
        Thread Manuel = new Thread(vc, "Manuel");
        
        Luis.start();
        Manuel.start();
        
    }
    
    /*
        Resolucion INCISO 1:
            a) El resultado al ejecutar el algoritmo varias veces es que la cuenta se sobregira, debido a que se
               produce incosistencias en el atributo balance dentro de la clase CuentaBanco.
               La mejora para la protección de los datos es sincronizar el metodo hacerRetiro(...) y moverlo al
               recurso compartido (en este caso CuentaBanco), para garantizar la atomicidad del mismo. Tambien
               el metodo retiroBancario, pasa a ser un metodo privado ya que solo el metodo hacerRetiro(..) debe
               utilizarlo.
    */
    
}
