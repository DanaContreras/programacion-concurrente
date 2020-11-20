package Utiles;

public class Cola
{
    // Clase Cola con implementacion dinamica.
    
    private Nodo frente;
    private Nodo fin;
    
    // Constructor.
    
    public Cola()
    {
        // Se crea una cola vacia.
        
        this.frente = null;
        this.fin = null;
        
    }
    
    // Interfaz.
    
    public boolean poner (Object nuevoElem)
    {
        /* Pone un nuevo elemento a la cola, y retorna verdadero para
           indicar que la accion fue realizada. */
        
        Nodo aux = new Nodo (nuevoElem, null);
        
        if (this.frente == null) // Cola vacia.
        {
            this.frente = aux; // Frente y fin apuntan al unico nodo de la Cola.
            this.fin = aux;
        }
        else
        {    // Hay al menos un elemento.
            this.fin.setEnlace (aux); // Enlaza nodo fin con el nuevo nodo.
            this.fin = this.fin.getEnlace(); // Fin apunta ahora al nuevo nodo.
        }  
        
        return true;
    }
    
    public boolean sacar()
    {
        /* Saca el elemento que esta en frente de la cola. Devuelve verdadero
           si el elemento se pudo sacar y falso en caso contrario.*/
        
        boolean realizado;
        if (this.frente == null) // Cola vacia.
            realizado = false;
        else
        {
            this.frente = this.frente.getEnlace(); // Frente apunta al nodo siguiente.
            if (this.frente == null) // Cola vacia, actualiza fin.
                this.fin = null;
            realizado = true;
        }
        
        return realizado;
    }
    
    public Object obtenerFrente ()
    {
        // Devuelve el elemento ubicado en el frente.
        
        Object elemFrente;
        
        if (this.frente == null) // Cola vacia.
            elemFrente = null;
        else
            elemFrente = this.frente.getElem();
        
        return elemFrente;
    }
    
    public boolean esVacia ()
    {
        // Devuelve verdadero si la cola no tiene elementos y falso en caso contrario.
        
        return this.frente == null;
    }
    
    public void vaciar ()
    {
        // Saca todos los elementos de la estructura.
        
        this.frente = null;
        this.fin = null;
    }
    
    public Cola clone ()
    {
        // Devuelve una copia exacta de los datos de la estructura original.
        
        Cola clon = new Cola();
        Nodo aux1, aux2 ;
        
        if (this.frente != null) // Mientras haya elementos en la Cola.
        {
            aux1 = this.frente; // Variable que recorre los nodos de la cola original.
            clon.frente = new Nodo (aux1.getElem(), null); // Se crea el primer nodo de la cola en el clon.
            aux2 = clon.frente; // Variable que recorre los nodos de la cola clon.
            aux1 = aux1.getEnlace();
        
            while (aux1 != null) // Mientras haya nodos por recorrer en la cola original.
            {
                aux2.setEnlace (new Nodo (aux1.getElem(), null)); // Se crea un nuevo nodo que se enlaza con el nodo actual.
                aux2 = aux2.getEnlace(); // La variable se mueve al nuevo nodo de la cola clon.
                aux1 = aux1.getEnlace(); // La variable se mueve al nodo siguiente en la cola original.
            }
        
            clon.fin = aux2; // Se actualiza el fin de la cola clon.
        }
        
        return clon;
    }
    
    public String toString ()
    {
        /* Devuelve una cadena de caracteres formada por todos los elementos
           de la cola para mostrarla por pantalla. */
        
        Nodo aux = this.frente; // Nodo que recorre la cola.
        String cadena;
        
        if (this.frente == null) // Cola vacia.
            cadena = "Cola vacia.";
        else
        {
            cadena = "[";
            while (aux != null) // Mientras haya nodos por recorrer.
            {
                cadena = cadena + " " + aux.getElem().toString();
                aux = aux.getEnlace();
            }
            cadena = cadena + " ]";
        }
        
        return cadena;
    }
}
