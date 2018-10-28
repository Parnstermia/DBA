/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2;
import es.upv.dsic.gti_ia.core.AgentID;
import es.upv.dsic.gti_ia.core.ACLMessage;
import es.upv.dsic.gti_ia.core.SingleAgent;
import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
/**
 *
 * @author Sergio López Ayala
 */
public class Agente extends SingleAgent{
    //Estados del Agente
    private final int NOLOGEADO=0, ESCUCHALOGIN = 1, LOGEADO=2, ESCUCHANDO=3, FIN=4;
    
    //Elementos del Agente (podrían externalizarse a agentes...)
    private Bateria miBateria;
    private Escaner miEscaner;
    private Radar miRadar;
    private GPS miGPS;
    
    //Contenedores de información de los mensajes
    private ACLMessage inbox, outbox;
    
    //Variables de control
    private boolean terminar = false;
    private int estado = 0;
    private String miMapa;
    
    //Clave a enviar con cada mensaje en el campo Key del JSon
    private String ClaveConexion = "";
    
    public Agente(AgentID aid, String mapa) throws Exception{
        super(aid);
        miMapa = mapa;
    }
    
    @Override
    public void init(){
        miBateria = new Bateria();
        inbox = null;
        outbox = null;
        estado = NOLOGEADO;
    }
    
    @Override
    public void execute(){
        JsonObject objeto = new JsonObject();
        
        while(!terminar){
            switch(estado){
                case NOLOGEADO:
                    try{
                        objeto.add("command", new String("login"));
                        objeto.add("world", miMapa);
                        objeto.add("radar", this.getName());
                        objeto.add("scanner", this.getName());
                        objeto.add("gps", this.getName());
                    }catch( Exception e){
                        System.err.println("Fallo al serializar login");
                    }
                    outbox = new ACLMessage();
                    outbox.setSender(this.getAid());
                    outbox.setReceiver(null); //TO DO cambiar receiver
                    this.send(outbox);
                    estado = ESCUCHALOGIN;
                    
                    break;
                case ESCUCHALOGIN:
                    System.out.println("Agente("+this.getName()+") Esperando respuesta");
                    boolean repetir=true;
                    while (repetir)  {
                        try {
                            inbox = receiveACLMessage();
                            objeto = Json.parse(inbox.getContent()).asObject();
                            String resultado = objeto.get("result").asString();
                            
                            switch(resultado){
                                case "BAD_MAP":
                                    // TO DO
                                    System.err.println("Mapa especificado inválido");
                                    break;
                                case "BAD_PROTOCOL":
                                    // TO DO
                                    System.err.println("error al crear el JSON");
                                    break;
                                default:
                                    ClaveConexion = resultado;
                                    // LOGEADO O ESCUCHANDO?
                                    estado = LOGEADO;
                                    repetir = false;
                                    break;
                            }
                        } catch (Exception ex) {
                            System.err.println("Agente("+this.getName()+") Error al recibir login");
                            repetir=false;
                        }                        
                    }
                    break;
                case LOGEADO:
                    // TO DO
                    
                    break;
                case ESCUCHANDO:
                    // TO DO
                    System.out.println("Agente("+this.getName()+") Esperando respuesta");
                    
                    for(int i = 0; i < 4; i++)  {
                        try {
                            inbox = receiveACLMessage();
                            objeto = Json.parse(inbox.getContent()).asObject();
                            
                            if( objeto.get("escaner") != null){
                                //Recibido mensaje del escaner
                                //Pasar mensaje al escáner y parsearlo apropiadamente
                                //TO DO
                            }else if( objeto.get("radar") != null){
                                //Recibido mensaje del radar
                                //Pasar mensaje al radar y parsearlo apropiadamente
                                //TO DO
                            }else if( objeto.get("gps") != null){
                                //Recibido mensaje del gps
                                //Pasar mensaje al gps y parsearlo apropiadamente
                                //TO DO
                            }else if( objeto.get("result") != null){
                                String resultado = objeto.get("result").asString();
                                switch(resultado){
                                    case "OK":
                                        // TO DO
                                        
                                        break;
                                    case "CRASHED":
                                        // TO DO
                                        System.err.println("El agente se ha chocado");
                                        break;
                                    case "BAD_COMMAND":
                                        //Requiere terminar y volver a logear
                                        // TO DO
                                        System.err.println("Error, orden desconocida");
                                        break;
                                    case "BAD_PROTOCOL":
                                        //Requiere terminar y volver a logear
                                        // TO DO
                                        System.err.println("Error de protocolo");
                                        break;
                                    case "BAD_KEY":
                                        //Requiere terminar y volver a logear
                                        // TO DO
                                        System.err.println("Error en la clave enviada");
                                        break;
                                }
                            } 
                            
                            
                        } catch (Exception ex) {
                            System.err.println("Agente("+this.getName()+") Error al recibir login");
                            repetir=false;
                        }                        
                    }
                    break;
                case FIN:
                    // TO DO
                    
                    break;
            }
        }
    }
    
    @Override
    public void finalize(){
        
    }
    
    
}
