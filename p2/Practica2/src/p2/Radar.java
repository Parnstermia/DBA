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
public class Radar {
    protected int[] miVector;
    
    /**
    * Constructor del componente radar, genera un vector de 5x5 casillas
    * @author Sergio López Ayala
    */
    public Radar(){
        miVector = new int[25];
    }
    
    /**
     * Metodo get para acceder al vector con la información parseada de JSON.
     * @author Miguel Keane Cañizares
    */
    public int[] getMiVector(){
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
            miVector[i] = j.asInt();
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
        return (miVector[posicion] == 1);
    }
    
        /**
    * Comprueba si la casilla enviada cumple el objetivo de victoria
    * CASILLA_LIBRE = 0, CASILLA_BLOQUEADO = 1, CASILLA_META = 2
    * @param posicion posición a comprobar
    * @author Sergio López Ayala
    */
    public boolean posicionMeta(int posicion){
        return (miVector[posicion] == 2);
    }
}
