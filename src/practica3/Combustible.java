package practica3;

/**
 *	Clase diseñada para encapsular el concepto del combustible de un vehículo.
 * @author Diego Alfonso Candelaria Rodríguez
 */
public class Combustible {
	private final int capacidad=100;
	private int tanque;
   private final int consumo;
   
   public Combustible(int consumo){
		tanque=capacidad;
      this.consumo=consumo;
   }
	
	public void consumir(){
		tanque-=consumo;
	}
	
	public void recargar(){
		tanque=100;
	}
	
	public int combustibleDisponible(){
		return tanque;
	}
}
