package Utiles;

public class PedidoSnorkel {
    
    private int ordenPedido;
    private boolean disponibilidad;
    
    public PedidoSnorkel(int ordenPedido) {
        this.ordenPedido = ordenPedido;
        this.disponibilidad = false;
    }

    public int getOrdenPedido() {
        return ordenPedido;
    }
    
    public void setOrdenPedido(int ordenPedido) {
        this.ordenPedido = ordenPedido;
    }
    
    public boolean getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }
    
}
