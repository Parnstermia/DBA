/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2;

import com.eclipsesource.json.JsonObject;

/**
 *
 * @author Awake
 */
public class GPS {
    protected int x;
    protected int y;
    
    public GPS(){
        x = 0;
        y = 0;
    }
    
    public void parseCoordinates(JsonObject objeto){
        x = objeto.get("gps").asObject().get("x").asInt();
        y = objeto.get("gps").asObject().get("y").asInt();
    }
    
}
