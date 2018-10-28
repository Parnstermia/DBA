/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

/**
 *
 * @author Awake
 */
public class Radar {
    protected int[] miVector = new int[25];
    
    public void parseCoordinates(JsonObject objeto){
        int i = 0;
        for (JsonValue j : objeto.get("radar").asArray()){
            miVector[i] = j.asInt();
            i++;
        } 
    }
    
    public boolean posicionBloqueada(int posicion){
        return (miVector[posicion] == 1);
    }
}
