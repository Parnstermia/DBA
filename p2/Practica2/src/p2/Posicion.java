/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2;

/**
 * Clase diseñada para encapsular el concepto de posición en la que se encuentra el agente y para ser 
 * utilizada en en un HashSet.
 * @author Diego Alfonso Candelaria Rodríguez
 */
public class Posicion {
	public int x;
	public int y;
	
	public Posicion(){
		x=0;
		y=0;
	}
	
	public Posicion(int x, int y){
		this.x=x;
		this.y=y;
	}
	
	@Override
	public boolean equals(Object obj){
		boolean equal=false;
		
		if (obj == null) return false;
		if (!(obj instanceof Posicion)) return false;
		if (obj == this) return true;
		if(((Posicion)obj).x == this.x && ((Posicion)obj).y == this.y) equal=true;
		
		return equal;
	}
	
	@Override
	public int hashCode(){
		int hash=17;
		hash = hash * 31 + x;
		hash = hash * 31 + y;
		
		return hash;
	}
	
	@Override
	public String toString(){
		String str="["+x+","+y+"]";
		return str;
	}
}
