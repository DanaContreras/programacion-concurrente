package Utiles;

public class Lista
{
    // Clase perteneciente a Lista con implementacion dinamica.
    
    private Nodo cabecera;
    private int longitud;
    
    // Constructor.
    public Lista ()
    {
        // Crea una lista vacia.
        cabecera = null;
        longitud = 0;
    }
    
    // Interfaz.
    
    private Nodo ubicarNodo (int pos)
    {
        // Método privado que dada una posicion, busca el elemento en cuestion para retornarlo.
        // Precondicion: la posicion dada es valida dentro de la lista.
        
        Nodo nodoBuscado = this.cabecera; // nodo que recorre la lista. Se posiciona en cabecera.
        int i ;
        
        for (i=1; i<pos; i++) // Se mueve entre los nodos hasta llegar a las posicion deseada.
            nodoBuscado = nodoBuscado.getEnlace();
        
        return nodoBuscado;
    }        
    
    public boolean insertar (Object elem, int pos)
    {
        /* Agrega el elemento pasado por parametro en la posicion pos. Si la
           posicion es 1<= pos <= longitud(lista)+1 devuelve verdadero, caso
           contrario la accion no se puede concretar y devuelve falso. */
        
        boolean realizado;
        
        if (pos < 1 || pos > this.longitud +1) // La posicion es invalida.
            realizado = false;
        else
        {
            if (pos == 1) // CASO ESPECIAL: el nuevo nodo se enlaza con la cabecera.
                this.cabecera = new Nodo (elem, this.cabecera);
            else
            {
                Nodo aux = ubicarNodo (pos-1); // Se encuentra el nodo ubicado en pos-1.
                aux.setEnlace(new Nodo (elem, aux.getEnlace())); // Se enlaza nodo en pos-1 con el nuevo nodo que posee
                                                                 // el elemento (y que esta enlazado con el nodo pos+1).
                
            }
            
            realizado = true; // La lista nunca se llena.
            this.longitud ++; // Se incrementa la longitud.
        }
        
        return realizado;
    }

    public boolean eliminar(int pos)
    {
        /* Elimina elemento de la posicion pos. Si la posicion es 1<= pos<= longitud(lista),
           devuelve verdadero. Si la posicion es invalida o la lista esta vacia,no se
           puede realizar la accion y devuelve falso.*/
        
        boolean realizado;
        
        if (pos < 1 || pos > this.longitud || this.cabecera == null) // La posicion es invalida o la lista esta vacia.
            realizado = false;
        
        else
        {
            if (pos == 1) // Caso especial: elimina el primer elemento de la lista.
                this.cabecera = this.cabecera.getEnlace(); // Se enlazo cabecera con segundo elemento o toma valor null.
            else
            {
                Nodo aux = ubicarNodo (pos-1); // Se encuentra el nodo ubicado en pos-1.
                aux.setEnlace((aux.getEnlace()).getEnlace()); // Se enlaza el nodo encontrado con el de la posicion pos+1.
            }
            realizado = true;
            this.longitud --; // Decrementa la longitud de la lista.
        }
        
        return realizado;
    }
    
    public Object recuperar (int pos)
    {
        // Devuelve elemento de la posicion pos. Precondicion: la posicion sea valida.
        Object elemBuscado;
        
        if (pos < 1 || pos > this.longitud || this.cabecera == null) // Posicion invalida o lista vacia.
            elemBuscado = null;
        else
            elemBuscado = ubicarNodo(pos).getElem(); // Recupera el elemento de la posicion pos.
        
        return elemBuscado;
        
    }
    
    public int localizar (Object buscado)
    {
        /* Devuelve la posicion en la que se encuentra la primera ocurrencia
           del elemento dentro de la lista. Si no lo encuentra devuelve -1. */
        
        int posElemento = 0; // Realiza un seguimiento de las posiciones dentro del while.
        boolean seguir = true;
        Nodo aux = this.cabecera; // Nodo auxiliar que recorre la lista en busca del elemento.
        
        while (aux != null && seguir) // Mientras haya elementos que recorrer o no se encuentre el elemento.
        {
            posElemento ++;
            if (aux.getElem().equals(buscado))
                seguir = false; // El elemento ha sido encontrado.
            else
                aux = aux.getEnlace();
            
        }
        
        if (seguir) // No se ha encontrado el elemento.
            posElemento = -1; // Se retorna una posicion invalida.
            
        return posElemento;
    }
    
    public int longitud ()
    {
        // Devuelve la cantidad de elementos que se encuentran en la lista.
        
        return this.longitud;
    }
    
    public boolean esVacia ()
    {
        // Devuelve verdadero si la lista no tiene elementos, caso contrario, devuelve falso.
        
        return (this.cabecera == null);
    }
    
    public void vaciar ()
    {
        // Quita todos los elementos de la lista.
        
        this.cabecera = null;
        this.longitud = 0;
    }    
    
    public Lista clone ()
    {
        // Devuelve una copia exacta de los datos de la estructura original.
        
        Lista clon = new Lista();
        Nodo aux1, aux2;
        
        if (this.cabecera != null) // Hay elementos en la lista.
        {
            aux1 = this.cabecera; // Nodo que recorre la lista original.
            clon.cabecera = new Nodo (aux1.getElem(), null);
            aux2 = clon.cabecera; // Nodo que recorre la lista clon.
            aux1 = aux1.getEnlace();
            
            while (aux1 != null)
            {
                aux2.setEnlace(new Nodo(aux1.getElem(), null)); // Se crea un nuevo nodo que se enlaza con el nodo anterior.
                aux2 = aux2.getEnlace();
                aux1 = aux1.getEnlace();
            }
            
            clon.longitud = this.longitud; // Se actualiza la longitud de la lista clon.
        }
        
        return clon;
    }
    
    public String toString ()
    {
        /* Crea y devuelve una cadena de caracteres formada por todos los
           elementos de la lista para poder mostrarla por pantalla. */
        
        String cadena;
        
        if (this.cabecera == null)
            cadena = "Lista vacia.";
                    
        else
        {
            Nodo aux = this.cabecera; // Nodo con el que se recorre la lista.
            cadena = "[";
            
            while (aux != null)
            {
                cadena = cadena + " " + aux.getElem().toString();
                aux = aux.getEnlace();
            }
            
            cadena = cadena + " ]";
        }    
        
        return cadena;
    }               
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    // No pertenecen al TDA.
    // Propias del Tipo.
    
    public Lista invertir ()
    {
        // Retorna una lista invertida a la actual.
        
        Lista listInvertida = new Lista();
        Nodo index; //Nodo que recorre la lista de forma recursiva
        
        index = this.cabecera; //Apunta al primer nodo de la lista.
        invertirLista (index, listInvertida);
        listInvertida.longitud = this.longitud;
        
        return listInvertida;
    }
    
    private Nodo invertirLista (Nodo index, Lista invertida)
    {
        Object elemActual = index.getElem(); // Se guarda el elemento del nodo.
        Nodo aux = new Nodo(elemActual, null);
        
        if (index.getEnlace() == null)
            invertida.cabecera = aux; // El primer nodo se enlaza con la cabecera de la lista invertida.
        else
        {
           aux = invertirLista (index.getEnlace(), invertida);
           aux.setEnlace (new Nodo (elemActual, null)); // Se enlaza nodo con el actual.
           aux = aux.getEnlace(); // aux apunta al nuevo nodo de lista invertida.
        }
        
        return aux;
    }
    
    public void eliminarApariciones (Object elemEliminar)
    {
        // Elimina las repeticiones del elemento pasado por parametro.
        
        Nodo aux1, aux2;
        aux1 = this.cabecera; // Nodo que recorre toda la lista.
        aux2 = aux1.getEnlace();
        
        if (this.cabecera != null) //Hay al menos un elemento. 
        {
            while (aux2 != null)
            { 
                if ((aux2.getElem()).equals(elemEliminar))
                {
                        aux1.setEnlace(aux2.getEnlace());
                        this.longitud--;
                }
                else
                    aux1 = aux1.getEnlace();

                aux2 = aux1.getEnlace(); 
            }

            if ((this.cabecera.getElem()).equals(elemEliminar)) // No se ha controlado el primer nodo.
                this.cabecera = this.cabecera.getEnlace();
        }
    }  
    
}
