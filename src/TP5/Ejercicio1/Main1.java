/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TP5.Ejercicio1;
import Utiles.TextoAColor;
/**
 *
 * @author Dana^^
 */
public class Main1 {

    public static void main(String[] args) {
        
        TextoAColor color = new TextoAColor();
        
        // Recurso compartido.
        int i, cantComederos = 5, cantMaxComieron = 8, cantGatos = 7, cantPerros = 10;
        char[] especie = {'G', 'P'};
        Comedor1 comedor = new Comedor1(cantComederos, cantMaxComieron, especie);
        
        // Creacion de hilos.
        Thread[] gato = new Thread[cantGatos];
        Thread[] perro = new Thread[cantPerros];
        
        for (i = 0; i < cantGatos; i++) {
            gato[i] = new Thread (new Mascota1 ('G', comedor), color.cambiarColorTexto(i, "Gato " + (i+1)));
        }
        
        for (i = 0; i < cantPerros; i++) {
            perro[i] = new Thread (new Mascota1 ('P', comedor), color.cambiarColorTexto(i, "Perro " + (i+1)));
        }
        
        for (i = 0; i < cantGatos; i++) {
            gato[i].start();
        }
        
        for (i = 0; i < cantPerros; i++) {
            perro[i].start();
        }
    }
    
}
