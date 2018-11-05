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
    *   Método que inicializa la Matriz de Conocimiento
    * 
    * @author Miguel Keane Cañizares
    * @param id  identificador de la matriz del mapa correspondiente
    * 
    */
    public Conocimiento(int id){
        Connection connection = null;
        this.setMapaID(id);
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:mapas.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            
            String crearTablaSQL = "CREATE TABLE IF NOT EXISTS Mapa_" + this.mapa_id + " ("+
                "pos_x INTEGER NOT NULL,"+
                "pos_y INTEGER NOT NULL,"+
                "state INTEGER DEFAULT "+ DESCONOCIDO + ","+
                "CONSTRAINT pk_posicion PRIMARY KEY (pos_x, pos_y)"+
            ")";

            int state = statement.executeUpdate(crearTablaSQL);
            this.crearMatriz();
        } catch (SQLException e) {
            System.err.println("Error en la creación de la tabla SQL");
            System.err.println(e);
        } finally {
            try {
                if(connection != null) connection.close();
            } catch(SQLException e) {
                // connection close failed.
                System.err.println(e);
            }
        }
    }
    
    
    /**
    * Metodo que devuelve la instancia de Conocimiento correspondiente
    *
    * @author Miguel Keane Cañizares
    * @param id  identificador de la matriz del mapa correspondiente
     * @return instance Instancia correspondiente al identificador
    */
    public static Conocimiento obtenerInfo(int id){
        if (instance ==null){
            instance = new Conocimiento(id);  //Si no tenemos información sobre el mapa que se pide, creamos nueva instancia de información
        }else {
            instance.setMapaID(id);
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
        int tam = this.matrizMapa.length;
        int aux_comprobacion=0;
        this.matrizMapaOpt = new int[tam][tam];
        for (int i = 0; i < tam; i++){
            for (int j=0; j< tam; j++){
                aux_comprobacion = matrizMapa[i][j];
                if ( aux_comprobacion == 3){
                    this.matrizMapaOpt[i][j] = 1; // Todo lo desconocido, lo guardamos como pared. 
                }else{
                    this.matrizMapaOpt[i][j]= aux_comprobacion;
                } 
            }
        }
        return this.matrizMapaOpt;
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
        Connection connection=null; 
        try{
            
            // Obtenemos la posición del agente. 
            this.pos_actual[0]= gps.getX();
            this.pos_actual[1]= gps.getY();
            
            //Obtenemos la información del radar
            ArrayList<Integer> infoRadar= radar.getMiVector();
            
            // Nos conectamos a la base de datos SQL
            
            connection = DriverManager.getConnection("jdbc:sqlite:mapas.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            
            for (int i=0; i < 5 ; i++){  // 5 es el tamaño de las filas (y columnas) que "ve" el radar
                for (int j=0; j< 5; j++){
                    int x = pos_actual[0] - 2 + i; // Ajustamos la posición compensando la posición actual del agente
                    int y= pos_actual[1] - 2 +j;
                 //   int estado = infoRadar[i*5 + j];
                    
                    
//                    String querySQL = "INTERT OR REPLACE INTO Mapa_"+ this.mapa_id+"(pos_x,pos_y,radar, state) VALUES("
//                            + x + ","
//                            + y + ","
//                            + estado
//                            + ");";
                    
                    
                    // Ejecutamos el query
                    //statement.executeUpdate(querySQL);
                    
                    //Actualizamos la matriz
                    //actualizarMatriz(x, y, estado);
                }
            }
            
        } catch(SQLException e){
            System.err.println("Error actualizando el mapa");
            System.err.println(e);
        } finally{
            try{
                if(connection != null) connection.close();
            } catch (SQLException e){
                System.err.println(e); //Falla el cerrar la conexión
            }
        }
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
        Connection connection = null;
        try {
            int tamMatriz = 0;
            // Nos conectamos a la Base de Datos
            connection = DriverManager.getConnection("jdbc:sqlite:mapas.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            
            String contadorSQL;
            ResultSet datos;

            // Calculamos el tamaño máximo de la matriz
            // Por posición X
            contadorSQL = "SELECT MAX(pos_x) AS count FROM Mapa_" + this.mapa_id + ";";
            
            datos = statement.executeQuery(contadorSQL);
            while(datos.next()){
                tamMatriz = Math.max(tamMatriz, (datos.getInt("count") + 1));
            }
            
            // Por posición Y
            contadorSQL = "SELECT MAX(pos_y) AS count FROM Mapa_" + this.mapa_id + ";";
            
            datos = statement.executeQuery(contadorSQL);
            while(datos.next()){
                tamMatriz = Math.max(tamMatriz, (datos.getInt("count") + 1));
            }     
            
            System.out.println("El tamaño máximo de la matriz es de: " + tamMatriz);
            
            if(tamMatriz > 0) { 
                
                // Creamos la matriz del mapa
                this.matrizMapa = new int[tamMatriz][tamMatriz];

                // Hacemos un query para obtener la información y volcarla en la matriz
                datos = statement.executeQuery("SELECT * FROM Mapa_"+this.mapa_id +";");
                while(datos.next()){
                    this.matrizMapa[datos.getInt("pos_x")][datos.getInt("pos_y")] = datos.getInt("state");
                }
            }else{
                this.matrizMapa = new int[200][200];  // Si no tenemos datos adecuados, ponemos un tamaño por defecto para la matriz
            }
        } catch(SQLException e){
            System.err.println(e);
        } finally {
            try {
                if(connection != null) connection.close();
            } catch(SQLException e) {
                // connection close failed.
                System.err.println(e);
            }
        }
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
        this.matrizMapa[x][y] = estado;
    }
    
  
    
    
    
    
    
    
}
