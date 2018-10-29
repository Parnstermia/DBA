/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2;
    
import es.upv.dsic.gti_ia.core.AgentID;
import es.upv.dsic.gti_ia.core.AgentsConnection;

/**
 *
 * @author Sergio López Ayala/ Thomas LESBROS
 */
public class Main {

    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) {
        Agente roboto;
        ////////////////////////////////////////////
        // No hacer push a los datos del grupo
        String virtualhost = "Bellatrix";
        String username = "Hercules";
        String pass = "Benavente";
        ////////////////////////////////////////////
        String mapa = "map1";
        
        
        AgentsConnection.connect("isg2.ugr.es",6000, virtualhost, username, pass, false);
        
        try {
            roboto = new Agente(new AgentID("Smith6"), mapa, virtualhost);
            roboto.start();
        } catch (Exception ex) {
            System.err.println("Error creando agentes");
            System.exit(1);
        }
        
    }
    
}
