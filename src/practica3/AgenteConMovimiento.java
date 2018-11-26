/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica3;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import es.upv.dsic.gti_ia.core.ACLMessage;
import es.upv.dsic.gti_ia.core.AgentID;

/**
 *
 * @author Awake
 */
public class AgenteConMovimiento extends Agente{
    private static final int ESTADO_INICIO = 0;
    private Vehiculo miVehiculo;
    private Radar miRadar;
    private GPS miGPS;
    private Escaner miEscaner;
    private Mapa miMapa;
    private Bateria miBateria;
    
    private int estado;
    private boolean ejecutar = true;
    private String nextOrder;
    private int performative;
    public AgenteConMovimiento(AgentID aid, String host) throws Exception {
        super(aid, host);
        estado = ESTADO_INICIO;
        nextOrder = null;
    }
    
    
    @Override
    public boolean recibirMensaje(){
        boolean exito = false;
         ACLMessage inbox;
        try {
            inbox = receiveACLMessage();
            JsonObject objeto = Json.parse(inbox.getContent()).asObject();
            
            if( objeto.get("result") != null){
                conversationID = inbox.getConversationId();
                exito = true;
            }else if(objeto.get("details") != null){
                exito = false;
            }else if(objeto.get("orden") != null){
                String orden =  objeto.get("orden").asString();
                nextOrder = orden;
                conversationID = inbox.getConversationId();
                exito = true;
            }
            
        }catch(InterruptedException e){
            System.err.println(e.toString());
        }
        
        return exito;
    }
    
    @Override
    public void execute(){
        boolean exito;
        JsonObject objeto;
        while(ejecutar){
            switch(estado){
                case ESTADO_INICIO:
                    exito = recibirMensaje();
                    if(exito){
                        if(nextOrder != null){
                            performative = ACLMessage.REQUEST;
                            objeto = new JsonObject();
                            objeto.add("command", nextOrder);
                            enviarMensaje(objeto, new AgentID(host), performative, conversationID, null);
                        }
                    }
                    break;
            }
        }
    }
}
