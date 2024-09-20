package Problema2;

public class Pedido {
    
    private int idPizza;
    private int cantidad;
    private String nombrePizza;
    private String nombreCliente;

    public Pedido (int idPizza, int cantidad, String nombrePizza, String nombreCliente) {
        this.idPizza = idPizza;
        this.cantidad = cantidad;
        this.nombrePizza = nombrePizza;
        this.nombreCliente = nombreCliente;
    }

    public int getIdPizza() {
        return idPizza;
    }

    public void setIdPizza (int idPizza) {
        this.idPizza = idPizza;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad (int cantidad) {
        this.cantidad = cantidad;
    }
    
    public String getNombrePizza() {
        return nombrePizza;
    }

    public void setNombrePizza (String nombrePizza) {
        this.nombrePizza = nombrePizza;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente (String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }
    
}
