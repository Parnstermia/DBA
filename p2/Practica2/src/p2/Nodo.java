package p2;

import java.lang.Comparable;

/**
 * Clase para encapsular el concepto de nodo, que servirá para realizar el análisis del 
 * camino a tomar por el agente.
 * @author Diego Alfonso Candelaria Rodríguez
 */
public class Nodo implements Comparable<Nodo> {
	public Posicion posicion;
	public double distancia;
	public Direccion direccion;
        public boolean pasado;
	
        /**
        * Constructor por defecto para inicializar el Nodo. 
        * @author Diego Alfonso Candelaria Rodríguez
        */
	public Nodo(){
		posicion.x=0;
		posicion.y=0;
		distancia=0;
		direccion=Direccion.N;
                pasado = false;
	}
	
        /**
        * Constructor con argumentos para inicializar el Nodo.
        * @author Diego Alfonso Candelaria Rodríguez
        * @param pos Posicion argumento que indica la posición del Nodo
        * @param dist double indica la distancia del nodo al objetivo
        * @param dir Direccion indica en qué dirección está el Nodo con respecto al coche
        */
	public Nodo(Posicion pos, double dist, Direccion dir){
		posicion=new Posicion(pos.x,pos.y);
		distancia=dist;
		direccion=dir;
	}
	
        /**
        * Método que compara el Nodo actual con otro para ver cual es mejor.
        * @author Diego Alfonso Candelaria Rodríguez
        * @param nodo Nodo indica el nodo con el que comparar el Nodo local.
        */
	@Override
	public int compareTo(Nodo nodo){
		int compare;
		
		if(distancia>nodo.distancia)
			compare=1;
		else if(distancia<nodo.distancia)
			compare=-1;
		else
			compare=0;
		
		return compare;
	}
	
        /**
        * Método para convertir los datos del Nodo en un String.
        * @author Diego Alfonso Candelaria Rodríguez
        */
	@Override
	public String toString(){
		String str=posicion.toString()+"\n"+distancia+"\n"+direccion+"\n";
		return str;
	}
}
