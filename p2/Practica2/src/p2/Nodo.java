/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
	
	public Nodo(){
		posicion.x=0;
		posicion.y=0;
		distancia=0;
		direccion=Direccion.N;
                pasado = false;
	}
	
	public Nodo(Posicion pos, double dist, Direccion dir){
		posicion=new Posicion(pos.x,pos.y);
		distancia=dist;
		direccion=dir;
	}
	
	@Override
	public int compareTo(Nodo nodo){
		int compare=0;
		
		if(distancia>nodo.distancia)
			compare=1;
		else if(distancia<nodo.distancia)
			compare=-1;
		else
			compare=0;
		
		return compare;
	}
	
	@Override
	public String toString(){
		String str=posicion.toString()+"\n"+distancia+"\n"+direccion+"\n";
		return str;
	}
}
