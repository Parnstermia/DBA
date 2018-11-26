package practica3;

/**
 *
 * @author 
 */
public abstract class Vehiculo {
	protected Radar radar;
	protected Combustible combustible;
	protected GPS gps;
	
	public abstract Direccion llegarMeta();
}