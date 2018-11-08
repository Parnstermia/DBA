/*
 *      CLASE PARA ALMACENAR EL CONOCIMIENTO COMPARTIDO DE LOS DIFERENTES AGENTES
 *  
 */
package p2;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;



/**
 *
 * @author Miguel Keane Cañizares
 */
public class Conocimiento {
    private static Conocimiento instance = null;
    private int mapa_id;
    private int[][] matrizMapa;       //Matriz del mapa
    private int[][] matrizMapaOpt;   //Matriz ya optimizada del mapa, con la información que queremos
    private int[] pos_actual = new int[2];
   
   
        
    public final int LIBRE = 0;
    public final int PARED = 1;
    public final int OBJETIVO = 2;
    public final int DESCONOCIDO = 3;
    
    
    /**
    *   Constructor que inicializa la Matriz de Conocimiento
    * 
    * @author Miguel Keane Cañizares
    * 
    * 
    */
    
    public Conocimiento(){
        this.crearMatriz();
       
    }
    
    
    /**
    * Metodo que devuelve la instancia de Conocimiento correspondiente
    *
    * @author Miguel Keane Cañizares
    * @param id  identificador de la matriz del mapa correspondiente
     * @return instance Instancia correspondiente al identificador
    */
    public static Conocimiento obtenerInfo(){
        if (instance ==null){
            instance = new Conocimiento();  //Si no tenemos información sobre el mapa que se pide, creamos nueva instancia de información
        }
        return instance; 
    }
    
     /**
    * Metodo que traspasa el conocimiento del mapa principal, a otro mapa,
    * tomando aquellas posiciones no visitadas como paredes.
    *
    * @author Miguel Keane Cañizares
    * 
     *
    */
    public int[][] getMap(){
      
        return this.matrizMapa;
    }
    
    
    
    /**
    * Metodo que recibe la información del agente y lo añade al mapa local. 
    *
    * @author Miguel Keane Cañizares
    * @param radar Clase que contiene la información parseada del radar
    * @param gps Clase que contiene la información parseada del GPS
    * @param numMov Número que indica cuantos movimientos ha hecho el agente
     *
    */
    
    public void actualizarConocimiento(Radar radar, GPS gps, int numMov){           
            // Obtenemos la posición del agente.
            //System.out.println("Entra a actuaizar conocimeinto");
            this.pos_actual[0]= gps.getX();
            this.pos_actual[1]= gps.getY();
            
            //Obtenemos la información del radar
            ArrayList<Integer> infoRadar= radar.getMiVector();
            
            for (int i=0; i < 5 ; i++){  // 5 es el tamaño de las filas (y columnas) que "ve" el radar
                for (int j=0; j< 5; j++){
                    int x = pos_actual[0]  + i; // Ajustamos la posición compensando la posición actual del agente
                    int y= pos_actual[1]  +j;
                    int estado = infoRadar.get(i*5 + j);
       
                    
                    //Actualizamos la matriz
                    actualizarMatriz(x, y, estado);
                }
            }
           // System.out.println("Sale de actualizar conocimiento");
    }
    
      /**
    * Metodo para actualizar la matriz que almacenará los datos que tengamos del mapa
    *
    * @author Miguel Keane Cañizares
    * 
    * @param x int con cooredena X
    * @param y int con coordenada Y
    * @param estado int con el estado que guardaremos en la matriz
    *
    */
    private void actualizarMatriz(int x, int y, int estado){
        //System.out.println("Entra a actualizar matriz: "+ x + "  " + y+ "  " + estado);
        this.matrizMapa[x][y] = estado;

    }
    
   
    
    
     /**
    * Metodo que identifica el mapa de la instancia actual
    *
    * @author Miguel Keane Cañizares
    * @param id  identificador de la matriz del mapa correspondiente
     *
    */
    private void setMapaID(int id){
        this.mapa_id = id; 
    }
    
     /**
    * Metodo para crear la matriz que almacenará los datos que tengamos del mapa
    *
    * @author Miguel Keane Cañizares
    *
    */
     private void crearMatriz(){
       
            int tamMatriz = 500;
            
            
            System.out.println("Creamos matriz limpia con tamaño: " + tamMatriz);
            
            
                
                // Creamos la matriz del mapa
                this.matrizMapa = new int[tamMatriz][tamMatriz];

                
                for(int i=0; i < tamMatriz ; i++){
                    
                    for(int j=0; j < tamMatriz; j++){
                        this.matrizMapa[i][j] = DESCONOCIDO;
                    }
                }
                    
                    
               
       
    }
    
    
    
  
     /**
    * Metodo para dibujar el mapa que tenemos guardado por pantalla
    *
    * @author Miguel Keane Cañizares
    *
    */
    public void drawMap(){
        System.out.println("| Mapa actual - Filas: " + this.matrizMapa.length + " | Columnas: " + this.matrizMapa[0].length);
        for(int i = 0; i < matrizMapa.length ;i++) System.out.print("▉▉▉");
        System.out.println("");
        for(int i = 0; i < this.matrizMapa.length; i++){
            for (int j = 0; j < this.matrizMapa[i].length; j++) {
                int value = this.matrizMapa[i][j];
                if(j == 0) System.out.print("▉▉▉");
                //if(pos_actual[0] == i && pos_actual[1] == j) System.out.print(" ⎔ ");
                if(pos_actual[0] == i && pos_actual[1] == j) System.out.print(" ● ");
                else{
                    switch (value) {
                        case 0:
                            System.out.print(" 0 ");
                            break;
                        case 1:
                            System.out.print(" 1 ");
                            break;
                        case 2:
                            System.out.print(" ╳ ");
                            break;
                        default:
                            if(value < 10) System.out.print(" " + value+ " ");
                            else if(value < 100) System.out.print(" " + value);
                            else System.out.print(value);
                            break;
                    }
                }
            }
            System.out.print("\n");
        }
        
        System.out.println("/////////////////////////////////////////////////////////////////////////////////////////////////////");
    }
  
    
    
    
    
    
    
}
