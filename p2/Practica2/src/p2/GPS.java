/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2;

import com.eclipsesource.json.JsonObject;

/**
 *
 * @author Sergio López Ayala
 */
public class GPS {
    protected int x;
    protected int y;
    
    /**
     *
     * @author Sergio López Ayala
     */
    public GPS(){
        x = 0;
        y = 0;
    }
    /**
    * @param objeto Mensaje recibido por el servidor
    * @author Sergio López Ayala
    */
    public void parsearCoordenadas(JsonObject objeto){
        x = objeto.get("gps").asObject().get("x").asInt();
        y = objeto.get("gps").asObject().get("y").asInt();
    }
    

    /**
     * Metodo get para acceder a la x.
     * @author Miguel Keane Cañizares 
    */
    public int getX(){
        return this.x;
    }
    
     /**
     * Metodo get para acceder a la y.
     * @author Miguel Keane Cañizares
    */
    public int getY(){
        return this.y;
    }
   
	 
	 @Override
	 public String toString(){
		 String str="["+x+","+y+"]";
		 return str;
	 }

}
