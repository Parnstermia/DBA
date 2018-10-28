/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2;
import es.upv.dsic.gti_ia.core.AgentID;
import es.upv.dsic.gti_ia.core.ACLMessage;
import es.upv.dsic.gti_ia.core.SingleAgent;
import org.codehaus.jettison.json.JSONObject;
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
        JSONObject objeto = new JSONObject();
        
        while(!terminar){
            switch(estado){
                case NOLOGEADO:
                    try{
                        objeto.put("command", new String("login"));
                        objeto.put("world", miMapa);
                        objeto.put("radar", this.getAid());
                        objeto.put("scanner", this.getAid());
                        objeto.put("gps", this.getAid());
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
                            String respuesta = inbox.getContent();
                            objeto.get("result");
                            switch(respuesta){
                                case "BAD_MAP":
                                    // TO DO
                                    System.err.println("Mapa especificado inválido");
                                    break;
                                case "BAD_PROTOCOL":
                                    // TO DO
                                    System.err.println("error al crear el JSON");
                                    break;
                                default:
                                    ClaveConexion = respuesta;
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
