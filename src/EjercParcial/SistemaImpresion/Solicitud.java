package EjercParcial.SistemaImpresion;

/*
 * @author Dana ~
 */

public class Solicitud {

    private int turno;
    private char letra;
    
    public Solicitud(int turno, char letra){
        this.turno = turno;
        this.letra = letra;
    }
    
    public int getTurno(){
        return turno;
    }
    
    public char getLetra(){
        return letra;
    }

    public void setTurno(int turno) {
        this.turno = turno;
    }

    public void setLetra(char letra) {
        this.letra = letra;
    }
    
    
}
