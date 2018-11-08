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
public class Escaner {
    protected ArrayList<Double> miVector;
    
    public Escaner(){
        miVector = new ArrayList<Double>(25);
    }
    
    /**
     * Método para convertir los datos del JSON en datos que el programa pueda utilizar.
    * @param objeto Mensaje recibido por el servidor
    * @author Sergio López Ayala
    */
    public void parsearEscaner(JsonObject objeto){
        int i = 0;
        for (JsonValue j : objeto.get("scanner").asArray()){
            miVector.add(i, j.asDouble());
            i++;
        } 
    }
	 
        /**
        * Método para convertir los datos escner en un String
       * 
       * @author Sergio López Ayala
       */
	@Override
	public String toString(){
		String str="[]";
		if(miVector.size()>0){
			str="";
			for(int i=0 ; i<5 ; i++){
				str+="[";
				for(int j=0 ; j<5 ; j++){
					str=str+miVector.get(i*5+j)+"\t,\t";
				}
				str+="]\n";
			}
		}
		return str;
//		return miVector.toString();
	 }
}
