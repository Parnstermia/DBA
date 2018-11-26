package practica3;

/**
 * Enum diseñado para encapsular el concepto de dirección en la que se mueve el agente.
 * @author Diego Alfonso Candelaria Rodríguez
 */
public enum Direccion {
	N("moveN"), S("moveS"), E("moveE"), W("moveW"), NE("moveNE"), NW("moveNW"), SE("moveSE"), SW("moveSW");
	
	private final String direccion;
	
	public String getString(){
		return this.direccion;
	}
	
	private Direccion(String direccion){
		this.direccion=direccion;
	}
}