package Main;

import Hilos.*;
import RecursosCompartidos.*;
import Utiles.TextoAColor;

public class Main {
    
    public static void main(String[] args) {
        
        // General
        TextoAColor txtColor = new TextoAColor();
        int maxVisitantes = 15;
        
        // Ingreso
        int horaInicio = 9;
        int horaDesvio = 17;
        int horaFin = 18;
        int maxMolinetes = 3;
        
        // ColectivoFolklorico
        int maxEnColectivo = 4;
        
        // Shop
         int maxCajas = 2;
        
        // NadoDelfines
        int maxPiletas = 4;
        int maxEnGrupo = 2;
        int minEnPiletas = maxEnGrupo * (maxPiletas -1);     
        int duracion = 20;
        
        // Snorkel
        int maxEquipos = 4;
        int maxAsistentes = 2;
        
        // Restaurantes
        int maxRest1 = 4;
        int maxRest2 = 2;
        int maxRest3 = 1;
        
        // MundoAventura
        int maxCuerdas = 2;
        int maxEnSaltos = 2;
        int maxEnTirolesa = 2;
        
        // FaroMirador
        int maxEnEscalera = 2;
        
        // CarreraGomones
        int maxGomIndiv = 2;
        int maxGomPareja = 1;
        int maxTotal = maxGomIndiv + maxGomPareja * 2;
        int maxBicicletas = 2;
        int maxEnTren = maxTotal - maxBicicletas;
        
        /*
         * Recursos compartidos
         */

        Ingreso ingreso = new Ingreso(horaInicio, horaDesvio, horaFin, minEnPiletas, maxTotal, maxMolinetes);
        ColectivoFolklorico colectivo = new ColectivoFolklorico(maxEnColectivo);
        Shop shop = new Shop(maxCajas);
        NadoDelfines nadoDelfines = new NadoDelfines(maxPiletas, maxEnGrupo);
        Snorkel snorkel = new Snorkel(maxEquipos);
        Restaurantes restaurantes = new Restaurantes(maxRest1, maxRest2, maxRest3);
        MundoAventura mundoAventura = new MundoAventura(maxCuerdas, maxEnSaltos, maxEnTirolesa);
        FaroMirador faroMirador = new FaroMirador(maxEnEscalera);
        CarreraGomones carrera = new CarreraGomones(maxBicicletas, maxEnTren, maxGomIndiv, maxGomPareja);
        
        /*
         * Creacion de hilos
         */
        
        // General
        Thread[] visitantes = new Thread[maxVisitantes];
        
        for (int i = 0; i < maxVisitantes; i++) 
            visitantes[i] = new Thread(new Visitante(ingreso, colectivo, shop, nadoDelfines, snorkel, restaurantes, mundoAventura, faroMirador, carrera), txtColor.cambiarColor("Rojo", "Visitante ") + txtColor.cambiarColorTexto(i, "" +(i + 1) + ""));
        
        // Ingreso
        Thread controlHorario = new Thread(new ControlHorario(ingreso), txtColor.cambiarColor("Cyan", "HORARIO"));
        
        // ColectivoFolklorico
        Thread conductor = new Thread(new Conductor(colectivo), txtColor.cambiarColor("Violeta", "Conductor"));
        
        // NadoDelfines
        Thread instructorDelfines = new Thread(new InstructorDelfines(nadoDelfines, duracion),  txtColor.cambiarColor("Azul", "InstructorDelfines"));
        
        // Snorkel
        Thread[] asistente = new Thread[maxAsistentes];
        for (int i = 0; i < maxAsistentes; i++)
            asistente[i] = new Thread(new AsistenteSnorkel(snorkel), txtColor.cambiarColor("Verde", "AsistenteSnorkel " + (i+1)));
        
        // MundoAventura
        Thread controlSalto = new Thread(new ControlSaltoEnAltura(mundoAventura), txtColor.cambiarColor("Azul", "ControlSaltoEnAltura"));
        Thread controlTirolesa = new Thread(new ControlTirolesa(mundoAventura), txtColor.cambiarColor("Azul", "ControlTirolesa"));
        
        // FaroMirador
        Thread admin = new Thread(new AdministradorTobogan(faroMirador), txtColor.cambiarColor("Violeta", "AdminTobogan"));
        
        // CarreraGomones
        Thread maquinista = new Thread(new Maquinista(carrera), txtColor.cambiarColor("Amarillo", "Maquinista"));
        
        /*
         * Estado start
         */
        
        controlHorario.start();
        conductor.start();
        instructorDelfines.start();
        controlSalto.start();
        controlTirolesa.start();
        admin.start();
        maquinista.start();
        
        for (int i = 0; i < maxAsistentes; i++)
            asistente[i].start();
        
        for (int i = 0; i < maxVisitantes; i++)
            visitantes[i].start();
         
    }
    
}
