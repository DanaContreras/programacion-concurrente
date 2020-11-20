package Utiles;

public class Pila
{
    // Clase Pila con implementación dinamica.
    
    private Nodo tope;
    
    // Constructor.
    
    public Pila ()
    {
        this.tope = null;
    }
    
    // Interfaz.
    
    public boolean apilar (Object elem)
    {
        // Se agrega un nuevo elemento a la pila, convirtiendose en el tope.
        
        this.tope = new Nodo (elem, this.tope);
        return true;
    }
    
    public boolean desapilar ()
    {
        // Saca el elemento tope de la pila.
        
        boolean realizado;
        
        if (this.tope == null) // Pila vacia.
            realizado = false;
        else
        {
            this.tope = this.tope.getEnlace();
            realizado = true;
        }
        
        return realizado;
    }
    
    public Object obtenerTope()
    {
        // Devuelve el elemento tope de la pila.
        
        Object elemTope;
        
        if (this.tope==null) // Pila vacia.
            elemTope = null;
        else
            elemTope = this.tope.getElem();
        
        return elemTope;
    }
    
    public boolean esVacia()
    {
        // Devuelve verdadero si la pila no tiene elementos y falso en caso contrario.
        
        boolean vacia;
        
        if (this.tope==null)
            vacia = true;
        else
            vacia = false;
        
        return vacia;
    }
    
    public void vaciar()
    {
        // Saca todos los elementos de la pila.
        
        this.tope = null;
    }
    
    public Pila clone()
    {
        // Devuelve una copia exacta de los datos de la estructuta original.
        
        Pila clon = new Pila();
        Nodo aux1, aux2;
        
        aux1 = this.tope; // Nodo auxiliar que recorre la pila original.
        clon.tope = new Nodo (aux1.getElem(), null);
        aux2 = clon.tope; // Nodo auxiliar que recorre la pila clon.
        aux1 = aux1.getEnlace();
        
        while (aux1 != null)
        {
            aux2.setEnlace(new Nodo(aux1.getElem(), null)); // Enlace con un nuevo nodo (en clon).
            aux2 = aux2.getEnlace(); // La variable se mueve al nuevo nodo de la pila clon.
            aux1 = aux1.getEnlace(); // La variable se mueve al nodo enlazado (en el original)
        }
        
        return clon;
    }
    
    public String toString()
    {
        /* Devuelve una cadena de caracteres formada por todos los elementos de
           la pila para poder mostrarla por pantalla. */
        
        String cadena;
        
        if (this.tope == null)
            cadena = "Pila vacía.";
                    
        else 
        {   
            Nodo aux = this.tope;
            cadena = "[";
            while (aux != null)
            {
                cadena += aux.getElem().toString();
                aux = aux.getEnlace();
                if (aux != null)
                    cadena += " ";
            }
            cadena += "]";
        }    
        return cadena;
    }
}
