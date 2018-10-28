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
 * @author Sergio López Ayala
 */
public class Main {

    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) {
        Agente roboto=null;
        ////////////////////////////////////////////
        // No hacer push a los datos del grupo
        String virtualhost = "";
        String username = "";
        String pass = "";
        /////////////////////////////////////////////////
        String mapa = "map1";
        
        
        AgentsConnection.connect("isg2.ugr.es",6000, virtualhost, username, pass, false);
        
        try {
            roboto = new Agente(new AgentID("Smith"), mapa);
        } catch (Exception ex) {
            System.err.println("Error creando agentes");
            System.exit(1);
        }
        roboto.start();
    }
    
}
