package p2;

/**
 * Clase dedicada a la gestión de la batería
 * @author Sergio López Ayala
 */
public class Bateria {
    private final int  maximoCarga = 100;
    private final int  minimoCarga = 10;
    private final int  cargaPorAccion = 1;
    
    private int carga;
    
     /**
     * Constructor por defecto
     * @author Sergio López Ayala
     */
    public Bateria(){
        carga = 0;
    }
     /**
     *  Constructor actualizado
     * @author Sergio López Ayala
     * @param estadoActual int indica el estado actual de la batería
     */
    public Bateria(int estadoActual){
        carga = estadoActual;
    }
    
     /**
     * Método get para obtener el nivel de carga
     * @author Sergio López Ayala
     */
    public int getCarga(){
        return carga;
    }
    
     /**
     *  Método para recargar la batería cuando se vaya a gastar
     * @author Sergio López Ayala
     */
    public void Recargar(){
        carga = maximoCarga;
    }
    
     /**
     *  Método que utiliza la batería, haciendo que disminuya
     * @author Sergio López Ayala
     */
    public void usar(){
        carga -= cargaPorAccion;
    }
    
     /**
     *  Método que comprueba si la carga de la batería ha bajado de un mínimo
     * @author Sergio López Ayala
     */
    public boolean deboRecargar(){
        if( carga < minimoCarga){
            return true;
        }else{
            return false;
        }
    }
}
