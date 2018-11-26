package practica3;


import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import es.upv.dsic.gti_ia.core.ACLMessage;
import es.upv.dsic.gti_ia.core.AgentID;
import java.util.ArrayList;

/**
 *
 * @author 
 */
public class AgenteAdministrador extends Agente{
    
    private static final int ESTADO_SUBSCRIPCION =1;
    private static final int ESTADO_ERROR =2;
    private static final int ESTADO_PROPAGACION =3; 
    private ArrayList<AgentID> agentes;
    private String nivel;
    private int performative;
    private int estado;
    
    
    public AgenteAdministrador(AgentID aid, String host, String nivel, ArrayList<AgentID> subditos) throws Exception{
        super(aid, host);
        this.nivel = nivel;
        estado = ESTADO_SUBSCRIPCION;
        agentes = subditos;
    }
    
    /**
     *
     * @return 
     */
    @Override
    public boolean recibirMensaje(){
        ACLMessage inbox;
        try {
            inbox = receiveACLMessage();
            JsonObject objeto = Json.parse(inbox.getContent()).asObject();
            
            if( objeto.get("result") != null){
                conversationID = inbox.getConversationId();
                return true;
            }else if(objeto.get("details") != null){
                return false;
            }else{
                return false;
            }
            
        }catch(InterruptedException e){
            System.err.println(e.toString());
            return false;
        }
    }
    
    
    @Override
    public void execute(){
        JsonObject objeto;
        boolean exito = false;
        
        switch(estado){
            case ESTADO_SUBSCRIPCION:
                objeto = new JsonObject();
                objeto.add("world", nivel);
                performative = ACLMessage.SUBSCRIBE;
                enviarMensaje(objeto, new AgentID(host), performative, null, null);
                
                exito = recibirMensaje();
                if(exito){
                    estado = ESTADO_PROPAGACION;
                }else{
                    estado = ESTADO_ERROR;
                }
                break;
            case ESTADO_ERROR:
                System.out.println("Se ha producido un error");
                System.out.println("Host: " + host);
                System.out.println("Mundo: " + nivel);
                System.out.println("Performativa: "  + performative);
                
                break;
            case ESTADO_PROPAGACION:
                objeto = new JsonObject();
                objeto.add("orden","checkin");
                performative = ACLMessage.REQUEST;
                for(int i = 0; i < agentes.size(); i++){
                    enviarMensaje(objeto, agentes.get(i), performative, conversationID, null);
                    exito = recibirMensaje();
                    if(!exito){
                        System.out.println("Un agente no quiere hacer check");
                    }
                }
                
                break;
        }
        
        
    }
}
