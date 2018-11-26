package practica3;

/**
 *
 * @author 
 */
public class Coche extends Vehiculo{
	public Coche(){
		combustible=new Combustible(1);
	}
	
	@Override
	public Direccion llegarMeta(){
		return Direccion.N;
	}
}
