/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import java.util.ArrayList;

/**
 *
 * @author Sergio López Ayala
 */
public class Radar {
    protected ArrayList<Integer> miVector;
	 private final int PERMITIDO=0;
	 private final int BLOQUEADO=1;
	 private final int META=2;
    
    /**
    * Constructor del componente radar, genera un vector de 5x5 casillas
    * @author Sergio López Ayala
    */
    public Radar(){
        miVector = new ArrayList<Integer>(25);
    }
    
    /**
     * Metodo get para acceder al vector con la información parseada de JSON.
     * @author Miguel Keane Cañizares
    */
    public ArrayList<Integer> getMiVector(){
        return miVector;
    }
    
    /**
    * @param objeto mensaje devuelto por el agente externo con información
    * relativa a el sistema radar
    * @author Sergio López Ayala
    */
    public void parsearCoordenadas(JsonObject objeto){
        int i = 0;
        for (JsonValue j : objeto.get("radar").asArray()){
            miVector.add(i, j.asInt());
            i++;
        } 
    }
    
    /**
    * Comprueba si la casilla enviada por posición es una pared o no
    * CASILLA_LIBRE = 0, CASILLA_BLOQUEADO = 1, CASILLA_META = 2
    * @param posicion posición a comprobar
    * @author Sergio López Ayala
    */
    public boolean posicionBloqueada(int posicion){
       return miVector.get(posicion) == BLOQUEADO;
    }
    
        /**
    * Comprueba si la casilla enviada cumple el objetivo de victoria
    * CASILLA_LIBRE = 0, CASILLA_BLOQUEADO = 1, CASILLA_META = 2
    * @param posicion posición a comprobar
    * @author Sergio López Ayala
    */
    public boolean posicionMeta(int posicion){
        return (miVector.get(posicion) == META);
    }
	 
	 @Override
	public String toString(){
		String str="[]";
		if(miVector.size()>0){
			str="";
			for(int i=0 ; i<5 ; i++){
				str+="[";
				for(int j=0 ; j<5 ; j++){
					str=str+miVector.get(i*5+j)+",";
				}
				str+="]\n";
			}
		}
		return str;
//		return miVector.toString();
	 }
}
