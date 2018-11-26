package practica3;

/**
 *
 * @author 
 */
public class Bateria {
    private final int  maximoCarga = 100;
    private final int  minimoCarga = 10;
    private final int  cargaPorAccion = 1;
    
    private int carga;
    
    public Bateria(){
        carga = 0;
    }
    public Bateria(int estadoActual){
        carga = estadoActual;
    }
    public int getCarga(){
        return carga;
    }
    
    public void Recargar(){
        carga = maximoCarga;
    }
    
    public void usar(){
        carga -= cargaPorAccion;
    }
    
    public boolean deboRecargar(){
        if( carga < minimoCarga){
            return true;
        }else{
            return false;
        }
    }
}
