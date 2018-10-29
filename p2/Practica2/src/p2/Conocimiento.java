/*
 *      CLASE PARA ALMACENAR EL CONOCIMIENTO COMPARTIDO DE LOS DIFERENTES AGENTES
 *  
 */
package p2;

import com.mysql.jdbc.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Miguel Keane Cañizares
 */
public class Conocimiento {
    private static Conocimiento instance = null;
    private int mapa_id;
    private int[][] matrizMapa;
    private int[][] matrizMapaOpt;
    private int[] pos_actual = new int[2];
    //private final int TAM_VISION = 5;
    //private final int MIN_SIDE = 200;
    //private int actual_max_size = MIN_SIDE;
        
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
    private Conocimiento(int id){
         Connection connection = null;
        this.setMapaID(id);
        try {
            connection = (Connection) DriverManager.getConnection("jdbc:sqlite:mapas.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            
            String crearTablaSQL = "CREATE TABLE IF NOT EXISTS Mapa_" + this.mapa_id + " ("+
                "pos_x INTEGER NOT NULL,"+
                "pos_y INTEGER NOT NULL,"+
                "radar INTEGER DEFAULT 0,"+
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
    * Metodo que identifica el mapa de la instancia actual
    *
    * @author Miguel Keane Cañizares
    * @param id  identificador de la matriz del mapa correspondiente
     *
    */
    private void setMapaID(int id){
        this.mapa_id = id; 
    }
    
    private void crearMatriz(){
        
    }
    
    
}
