package practica3;

/**
 *
 * @author 
 */
public class Camion extends Vehiculo {
	public Camion(){
		 combustible=new Combustible(4);
	}
	
	@Override
	public Direccion llegarMeta(){
		return Direccion.N;
	}
}
