package practica3;

/**
 *
 * @author 
 */
public class Camion extends Vehiculo {
	public Camion(){
		combustible=new Combustible(4);
		radar=new Radar(11);
		gps=new GPS();
		escaner=new Escaner();
	}
	
	@Override
	public Direccion llegarMeta(){
		return Direccion.N;
	}
}
