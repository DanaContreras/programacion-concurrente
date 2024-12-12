package Hilos;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.concurrent.BrokenBarrierException;
import RecursosCompartidos.*;

public class Visitante implements Runnable{
    
    private int cantActRealizadas = 0;
    private boolean esTest = true;
    
    private int tipoAct;
    private String actividadRealizar;
    private Ingreso ingreso;
    private ColectivoFolklorico colectivo;
    private Shop shop;
    private NadoDelfines nadoDelfines;
    private Snorkel snorkel;
    private Restaurantes restaurantes;
    private MundoAventura mundoAventura;
    private FaroMirador faroMirador;
    private CarreraGomones carrera;
    private final Random valorRandom = new Random();
    
    // constructores para test individules
    public Visitante (Ingreso ingreso) {
        this.ingreso = ingreso;
        this.actividadRealizar = "Ingreso";
    }
    
    public Visitante (ColectivoFolklorico colectivo) {
        this.colectivo = colectivo;
        this.actividadRealizar = "ColectivoFolklorico";
    }
    
    public Visitante(Shop shop) {
        this.shop = shop;
        this.actividadRealizar = "Shop";
    }
    
    public Visitante (NadoDelfines nadoDelfines) {
        this.nadoDelfines = nadoDelfines;
        this.actividadRealizar = "NadoDelfines";
    }
    
    public Visitante (Snorkel snorkel) {
        this.snorkel = snorkel;
        this.actividadRealizar = "Snorkel";
    }
    
    public Visitante (Restaurantes restaurantes) {
        this.restaurantes = restaurantes;
        this.actividadRealizar = "Restaurantes";
    }
    
    public Visitante (MundoAventura mundoAventura, int tipoAct) {
        this.mundoAventura = mundoAventura;
        this.tipoAct = tipoAct;
        this.actividadRealizar = "MundoAventura";
    }
    
    public Visitante(FaroMirador faroMirador) {
        this.faroMirador = faroMirador;
        this.actividadRealizar = "FaroMirador";
    }
    
    public Visitante (CarreraGomones carrera) {
        this.carrera = carrera;
        this.actividadRealizar = "CarreraGomones";
    }

    // constructor completo
    public Visitante (Ingreso ingreso, ColectivoFolklorico colectivo, Shop shop, NadoDelfines nadoDelfines, Snorkel snorkel, Restaurantes restaurantes, MundoAventura mundoAventura, FaroMirador faroMirador, CarreraGomones carrera) {
        this.ingreso = ingreso;
        this.colectivo = colectivo;
        this.shop = shop;
        this.nadoDelfines = nadoDelfines;
        this.snorkel = snorkel;
        this.restaurantes = restaurantes;
        this.mundoAventura = mundoAventura;
        this.faroMirador = faroMirador;
        
        this.carrera = carrera;
        this.esTest = false;
        this.actividadRealizar = "";
    }
   
    @Override
    public void run() {
        try {
            boolean seguir = true;
            boolean esUltimo = false;
            String activPrioridad;
            String activActual = "";
            String[] tipoActiv = {"Shop", "NadoDelfines", "Snorkel", "Restaurantes", "MundoAventura", "FaroMirador", "CarreraGomones"};
            
             if (esTest)
                activActual = actividadRealizar;
             else {
                irAlParque();
                ingresarMolinete();
             }
             
            do {
                if (!esTest) {
                    activActual = tipoActiv[valorRandom.nextInt(tipoActiv.length)];
                    activPrioridad = this.ingreso.definirTipoActividad(activActual);
                    if (activPrioridad.equals("")){
                        seguir = false;
                        System.out.println(Thread.currentThread().getName() + " no puede ingresar por horario.");
                    } else 
                        System.out.println(Thread.currentThread().getName() + " eligio actividad " + activActual + " e ingreso determino que hara " + activPrioridad + ".");
                    
                    activActual = activPrioridad;
                }
                
                switch (activActual) {
                    case "Ingreso" -> testIngreso();
                    case "ColectivoFolklorico" -> irAlParque();
                    case "Shop" -> irAlShop();
                    case "NadoDelfines" -> esUltimo = realizarNadoConDelfines();
                    case "Snorkel" -> realizarSnorkel();
                    case "Restaurantes" -> irARestaurante();
                    case "MundoAventura" -> realizarActivMundoAventura();
                    case "FaroMirador" -> realizarActivFaroMirador();
                    case "CarreraGomones" -> esUltimo = realizarActivCarreraGomones();
                }

                if (!esTest) {
                    if (activActual.equals("NadoDelfines") && esUltimo)
                        this.ingreso.salirTipoActividad(activActual, this.nadoDelfines.getMinTotal());
                    else if (activActual.equals("CarreraGomones") && esUltimo)
                        this.ingreso.salirTipoActividad(activActual, this.carrera.getMaxTotal());
                    
                    if (!activActual.equals(""))
                        cantActRealizadas++;
                }
                
            } while (!esTest && seguir);
            
            System.out.println(Thread.currentThread().getName() + "\u001B[31m" + " se va del parque. Hizo " + cantActRealizadas + " actividades!");
            
        } catch (InterruptedException | BrokenBarrierException ex) {
            Logger.getLogger(Visitante.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void testIngreso() throws InterruptedException {
        String tipo, tipoPrioridad;
        String[] tipoActiv = {"NadoDelfines", "CarreraGomones", "Aleatorio"};
        
        do {
            tipo = tipoActiv[valorRandom.nextInt(3)];
            tipoPrioridad = this.ingreso.definirTipoActividad(tipo);
            if (!tipoPrioridad.equals("")) {
                System.out.println(Thread.currentThread().getName() + " eligio tarea " + tipo + " y control determino que hara tarea " + tipoPrioridad);
                simularTiempo(1, " termino de hacer su tarea " + tipoPrioridad);
                this.ingreso.salirTipoActividad(tipoPrioridad, 1);
            } else {
                System.out.println(Thread.currentThread().getName() + " no puede ingresar por horario.");
            }
            simularTiempo(2, " se va a recorrer el parque y decidir que actividad hacer.");
        }while (esTest);
    }
    
    private void ingresarMolinete() throws InterruptedException {
        this.ingreso.usarMolinete();
        simularTiempo(1, " ingreso al parque pasando molinete.");
        this.ingreso.salirDeMolinete();
    }
    
    private void irAlParque() throws InterruptedException {
        boolean enColectivo;
        do {
            enColectivo = valorRandom.nextBoolean();
            
            if (enColectivo) {
                this.colectivo.subirseColectivo();
                this.colectivo.bajarseColectivo();
            } else {
                System.out.println(Thread.currentThread().getName() + " quiere ir en auto particular.");
                simularTiempo(1, " llego al parque en auto particular.");
            }
            simularTiempo(2, " se va a recorrer el parque y decidir que actividad hacer.");
        }while (esTest);
    }
    
    private void irAlShop() throws InterruptedException {
        do {
            this.simularTiempo(1," decide comprar un suvenir en la tienda.");
            this.shop.solicitarCaja();
            this.simularTiempo(1, " paga en la caja.");
            this.shop.desocuparCaja();
            this.simularTiempo(1, " se va a recorrer el parque y decidir que actividad hacer.");
        } while (esTest);
    }
    
    private boolean realizarNadoConDelfines() throws InterruptedException{
        boolean esUltimo = false;
        
        do {
            if (this.nadoDelfines.verificarHorarioActividad() && this.nadoDelfines.unirseAGrupo())
                esUltimo = this.nadoDelfines.dejarGrupo();
            
            this.simularTiempo(1, " se va a recorrer el parque y decidir que actividad hacer.");
        } while (esTest);
        
        return esUltimo;
    }
    
    private void realizarSnorkel() throws InterruptedException{
        int ordenLlegada;
        
        do {
            this.simularTiempo(4, " se va a recorrer el parque y decidir que actividad hacer.");
            ordenLlegada = this.snorkel.pedirEquipo();
            this.snorkel.esperarSerAtendido(ordenLlegada);
            simularTiempo(2, " termino de realizar Snorkel.");
            this.snorkel.devolverEquipo();
        } while (esTest);
    }
    
    private void irARestaurante() throws InterruptedException {
        int tipoRest;
        String[] tipoComida = {"almorzar", "merendar"};
        
        do {
            for (int i = 0; i < 2; i++) {
                tipoRest = valorRandom.nextInt(2);
                this.restaurantes.irARestaurante(tipoRest, tipoComida[i]);
                simularTiempo(1, " termino de comer en el restaurante " + (tipoRest + 1));
                this.restaurantes.salirDeRestaurante(tipoRest);
                simularTiempo(1, " se va a recorrer el parque y decidir que actividad hacer.");
            }
        } while (esTest);
    }
    
    public void realizarActivMundoAventura() throws InterruptedException {
        int tipoActiv;

        do {
            if (!esTest)
                tipoActiv = valorRandom.nextInt(3);
            else
                tipoActiv = this.tipoAct;
    
            this.mundoAventura.realizarActividad(tipoActiv);
            
            if (tipoActiv == 0)
                simularTiempo(1, " termino de usar la cuerda.");
            
            this.mundoAventura.dejarActividad(tipoActiv);
            
             if (tipoActiv == 2) {
                tipoActiv++;
                simularTiempo(tipoActiv, " recorrio lugar y decide volver por tirolesa este.");
                this.mundoAventura.realizarActividad(tipoActiv);
                this.mundoAventura.dejarActividad(tipoActiv);
             } else if (tipoActiv == 3) {
                tipoActiv --;
                simularTiempo(tipoActiv, " recorrio lugar y decide volver por tirolesa oeste.");
                this.mundoAventura.realizarActividad(tipoActiv);
                this.mundoAventura.dejarActividad(tipoActiv);
             }
            
            this.simularTiempo(2, " se va a recorrer el parque y decidir que actividad hacer.");
        } while (esTest);
        
    }
    
    public void realizarActivFaroMirador() throws InterruptedException {
        boolean irAToboganUno;
        
        do {
            this.faroMirador.subirEscalera();
            simularTiempo(2, " subio a la cima por la escalera caracol.");
            this.faroMirador.salirDeEscalera();
            irAToboganUno = this.faroMirador.solicitarTobogan();
            this.faroMirador.irATobogan(irAToboganUno);
            this.faroMirador.salirDeTobogan(irAToboganUno);
            simularTiempo(1, " termino de bajar por el tobogan " + (irAToboganUno? "uno" : "dos") + ".");
            this.faroMirador.salirDeTobogan(irAToboganUno);
            this.simularTiempo(1, " termino de estar en la pileta.");
            simularTiempo(2, " se va a recorrer el parque y decidir que actividad hacer.");
        } while (esTest);
        
    }
    
    private boolean realizarActivCarreraGomones() throws InterruptedException, BrokenBarrierException{
        
        boolean esUltimo = false;
        boolean eleccion[];
        
        do {
            boolean enBici = this.valorRandom.nextBoolean();
            boolean enGomonIndiv = this.valorRandom.nextBoolean();
            
            eleccion = this.carrera.solicitarTipoTransporte(enBici);

            if (eleccion[0]) { // disponibilidad de transporte.
                if (eleccion[1]) {
                    System.out.println(Thread.currentThread().getName() + " quiere ir al rio en bicicleta.");
                    simularTiempo(1, " llego a la carrera de gomones en bicicleta.");
                } else {
                    System.out.println(Thread.currentThread().getName() + " quiere ir al rio en tren.");
                    this.carrera.subirseAlTren();
                }

                enGomonIndiv = this.carrera.solicitarGomon(enGomonIndiv);
                this.carrera.comenzarCarrera();
                simularTiempo(1, " llego al final de la carrera de gomones.");
                this.carrera.terminarCarrera(enGomonIndiv);

                if (eleccion[1])
                    simularTiempo(1, " llego al stand para devolver la bicicleta.");
                else
                    this.carrera.subirseAlTren();

                esUltimo = this.carrera.devolverLugar(eleccion[1]);
            }
            
            this.simularTiempo(2, " se va a recorrer el parque y decidir que actividad hacer.");
        } while (esTest);
        
        return esUltimo;
    }
    
    private void simularTiempo (int tiempo, String msj) {
        try {
            Thread.sleep((valorRandom.nextInt(9) + 1) * 1000 * tiempo);
            System.out.println(Thread.currentThread().getName() + msj);
        } catch (InterruptedException ex) {
            Logger.getLogger(Visitante.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
