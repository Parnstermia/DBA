package practica3;

/**
 *
 * @author 
 */
public abstract class Vehiculo {
	protected Radar radar;
	protected Combustible combustible;
	protected GPS gps;
	protected Escaner escaner;
	
	public abstract Direccion llegarMeta();
	
	public void consumirCombustible(){
		combustible.consumir();
	}
}