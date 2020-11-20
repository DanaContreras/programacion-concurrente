package Utiles;

class Nodo
{
    // Clase Nodo para un tipo generico de elementos.
    
    private Object elem;
    private Nodo enlace;
    
    // Constructor.
    
    public Nodo (Object elem, Nodo enlace)
    {
        this.elem = elem;
        this.enlace = enlace;
    }
    
    // Interfaz.
    
    public void setElem (Object elem)
    {
        this.elem = elem;
    }
    
    public void setEnlace (Nodo enlace)
    {
        this.enlace = enlace;
    }
    
    public Object getElem ()
    {
        return this.elem;
    }
    
    public Nodo getEnlace ()
    {
        return this.enlace; 
    }
}
