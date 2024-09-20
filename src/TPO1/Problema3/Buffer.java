package Problema3;
import java.util.Queue;
import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
    
    // Recurso compartido.
    
    private boolean insertarEnColaUno;
    private final Queue<String> colaUno;
    private final Queue<String> colaDos;
    private final ReentrantLock lockEtiqueta;
    private final ReentrantLock lockInsertor;
    private final ReentrantLock lockExtractor;
    
    public Buffer () {
        this.insertarEnColaUno = true;
        this.colaUno = new LinkedList<>();
        this.colaDos = new LinkedList<>();
        this.lockEtiqueta = new ReentrantLock();
        this.lockInsertor = new ReentrantLock();
        this.lockExtractor = new ReentrantLock();
    }
    
    // metodos usados por insertores
    public void insertarDato (String dato) throws InterruptedException {
        
        lockInsertor.lock();
        lockEtiqueta.lock();
        
        if (insertarEnColaUno)
            colaUno.add(dato);
        else
            colaDos.add(dato);
        
        System.out.println(Thread.currentThread().getName() + " inserto en ¿cola uno?: " + insertarEnColaUno + ", el dato: " + dato);
        
        lockEtiqueta.unlock();
        lockInsertor.unlock();
    }
    
    // metodo usado por extractores
    public void extraerDato () throws InterruptedException {
        
        int cant;
        String dato;
        lockExtractor.lock();
        
        if (insertarEnColaUno) {
            dato = colaDos.poll();
            cant = colaDos.size();
        }     
        else {
            dato = colaUno.poll();
            cant = colaUno.size();
        }
        
        if (dato == null)
            System.out.println(Thread.currentThread().getName() + " no encontro elementos para extraer.");
        else
            System.out.println(Thread.currentThread().getName() + " extrajo el dato '" + dato + "' en ¿colaUno?: " + insertarEnColaUno + ". Quedan en la cola para extraer: " + cant);

        lockEtiqueta.lock();
        if (!insertarEnColaUno && colaUno.isEmpty())
            insertarEnColaUno = true;
        else if (insertarEnColaUno && colaDos.isEmpty())
            insertarEnColaUno = false;
        lockEtiqueta.unlock();
        
        lockExtractor.unlock();
        
    }
    
}
