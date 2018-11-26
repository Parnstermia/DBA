package practica3;

import es.upv.dsic.gti_ia.core.AgentID;
import es.upv.dsic.gti_ia.core.AgentsConnection;
import org.apache.log4j.BasicConfigurator;

import java.util.ArrayList;
import org.apache.log4j.BasicConfigurator;

/**
 *
 * @author 
 */
public class Main {

    public static void main(String[] args) {
        AgenteAdministrador roboto;
        BasicConfigurator.configure();
        ////////////////////////////////////////////
        // No hacer push a los datos del grupo
        String virtualhost = "";
        String username = "";
        String pass = "";
        ////////////////////////////////////////////



        String mapa = "";
        
        BasicConfigurator.configure();
		  
        AgentsConnection.connect("isg2.ugr.es",6000, virtualhost, username, pass, false);
        
        ArrayList<AgentID> aids = new ArrayList<>();
        for(int i = 0; i < 4; i ++) aids.add(new AgentID("Agente-" + i));
        try {
            
            roboto = new AgenteAdministrador(new AgentID("Agente"), virtualhost, mapa, aids);
            for(int i = 0; i < 4; i++){
                AgenteConMovimiento agent;
                agent = new AgenteConMovimiento(aids.get(i), virtualhost);
                agent.start();
            }
            roboto.start();
            
        } catch (Exception ex) {
            System.err.println("Error creando agentes");
            System.exit(1);
        }
    }
    
}