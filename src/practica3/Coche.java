package practica3;

/**
 *
 * @author 
 */
public class Coche extends Vehiculo {
	public Coche(){
		combustible=new Combustible(1);
		radar=new Radar(5);
		gps=new GPS();
		escaner=new Escaner();
	}
	
	@Override
	public Direccion llegarMeta(){
		return Direccion.N;
	}
}
