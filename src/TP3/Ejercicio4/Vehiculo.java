
package TP3.Ejercicio4;

public class Vehiculo {

    String id;
    String modelo;
    String marca;
    String color;

    public Vehiculo (String id){
        this.id = id;
        this.modelo = "";
        this.marca = "";
        this.color = "";
    }
    
    public Vehiculo (String id, String modelo, String marca, String color){
        this.id = id;
        this.modelo = modelo;
        this.marca = marca;
        this.color = color;
    }

    public String getId() {
        return this.id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    
}
