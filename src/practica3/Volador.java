package practica3;

/**
 *
 * @author 
 */
public class Volador extends Vehiculo{
	public Volador(){
		combustible=new Combustible(2);
	}
	
	@Override
	public Direccion llegarMeta(){
	return Direccion.N;
	}
}
