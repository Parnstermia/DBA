package practica3;

/**
 *
 * @author 
 */
public abstract class Vehiculo {
	public Radar radar;
	public Bateria bateria;
	public int consumo;
	
	
    public abstract Direccion llegarMeta();
}
