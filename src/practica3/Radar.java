package practica3;

/**
 *
 * @author 
 */
public class Radar {
    private int matriz[][];
    public int radio;
    
    public Radar(int radio){
        matriz = new int[radio][radio];
        this.radio = radio;
    }
}
