package EjercParcial.SistemaImpresion;
import java.util.concurrent.Semaphore;
import Utiles.Cola;

/*
 * @author Dana ~
 */

public class SpoolerImpresora {

    private int turno; // Se otorga un turno a cada peticion hecha por el usuario.
    private int turnoActual;
    private Cola colaPrimaria; // Manejo del buffer primario.
    private Cola colaSecundaria; // Manejo del buffer secundario.
    private Semaphore mutex; // Semaforo para la seccion critica que todo hilo debe acceder.
    private Semaphore mutexPrimario; // Semaforo para controlar el buffer primario.
    private Semaphore mutexSecundario; // Semaforo para controlar el buffer secundario.
    private Semaphore semBufferPrimario; // Semaforo para controlar la capacidad del buffer primario limitado.
    private Semaphore semServidor; // Semaforo para controlar el acceso del hilo ServidorImpresion a los buffers.
    
    public SpoolerImpresora (int capacidadBuffer){
        
        this.turno = 0;
        this.turnoActual = 1;
        this.colaPrimaria = new Cola();
        this.colaSecundaria = new Cola();
        this.mutex = new Semaphore(1, true);
        this.mutexPrimario = new Semaphore (1,true);
        this.mutexSecundario = new Semaphore(1,true);
        this.semBufferPrimario = new Semaphore (capacidadBuffer, true);
        this.semServidor = new Semaphore (0);
    }        
    
    public char solicitarEscritura(char letra) throws InterruptedException{

        
        mutex.acquire();
        
        turno++; // Variable que asigna los turnos. Compartida por todos los hilos.
        Solicitud solicitud = new Solicitud (turno, letra);
        
        System.out.println(Thread.currentThread().getName()+ " solicita operacion de escritura.");
        
        mutex.release();
        
        char tipo = 'P';
        boolean usoBufferPrimario = semBufferPrimario.tryAcquire(); // Se intenta escribir en buffer primario en caso que haya disponibilidad.
        
        if (!usoBufferPrimario){
            escribirBufferSecundario(solicitud);
            tipo = 'S';
        }
        else
            escribirBufferPrimario(solicitud);
        
        semServidor.release(); // Se le avisa al ServidorImpresion que hay un nuevo elemento a imprimir.
        
        return tipo; // Para simular el tiempo que demora cada operacion segun el tipo de buffer.
    }
    
    private void escribirBufferPrimario(Solicitud turno) throws InterruptedException{
        
        mutexPrimario.acquire();
        
        colaPrimaria.poner(turno);
        System.out.println(Thread.currentThread().getName()+ " va a escribir la letra " + turno.getLetra() + " en el buffer primario.");

        mutexPrimario.release();
        
    }
    
    private void escribirBufferSecundario(Solicitud turno) throws InterruptedException{
        
        mutexSecundario.acquire();
        
        colaSecundaria.poner(turno);
        System.out.println(Thread.currentThread().getName()+ " escribe la letra " + turno.getLetra() + " en el buffer secundario.");
        
        mutexSecundario.release();
        
    }
    
    public char imprimirDato() throws InterruptedException{
        
        /*
            El metodo consiste en intentar imprimir una letra si corresponde su turno. Para ello comienza con el buffer principal.
            Si la letra que no se encuentra en el, recurre al buffer secundario. Como existe un semaforo semServidor,
            se garantiza que en alguno de los dos buffer se encuentre una letra por imprimir.
        */
        
        
        int turnoEnCola;
        char letra = ' ', tipoBuffer = 'P';
        boolean enOtraCola = false;
        Solicitud solicitud;
        
        semServidor.acquire(); // Servidor puede entrar a imprimir una letra.
        
        mutexPrimario.acquire();
        
        solicitud = (Solicitud) colaPrimaria.obtenerFrente();
        
        if (solicitud != null){
            turnoEnCola = solicitud.getTurno();

            if (turnoActual == turnoEnCola){

                letra = solicitud.getLetra();
                colaPrimaria.sacar();

                System.out.println(Thread.currentThread().getName()+ " debe imprimir la letra " + letra + " porque tiene turno " + turnoActual);

                semBufferPrimario.release(); // Se libera un espacio del buffer limitado.

            } else
                enOtraCola = true;
        }
        else
            enOtraCola = true;
        
        mutexPrimario.release();
        
        if (enOtraCola){
        
            mutexSecundario.acquire();
            letra = ((Solicitud) colaSecundaria.obtenerFrente()).getLetra();
            colaSecundaria.sacar();
            
            System.out.println(Thread.currentThread().getName()+ " debe imprimir la letra " + letra + " porque tiene turno " + turnoActual);
            
            tipoBuffer = 'S';
            mutexSecundario.release(); 
        }
    
        turnoActual++;
        
        return tipoBuffer;
    }
    
}
