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
 * @author Sergio López Ayala 
 */
public class Escaner {
    public double[] miVector;
    
    public Escaner(){
        miVector = new double[25];
    }
    public void parsearEscaner(JsonObject objeto){
        int i = 0;
        for (JsonValue j : objeto.get("radar").asArray()){     
            miVector[i] = j.asDouble();
            i++;
        } 
    }
}
