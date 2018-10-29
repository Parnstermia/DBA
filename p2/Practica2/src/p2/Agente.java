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
import java.io.FileOutputStream;
import java.io.IOException;
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
    private AgentID miContacto;
    
    /**
    * Método para la elección del siguiente movimiento
    * @return orden a enviar
    * @author 
    */
    public String pensar(){
        String orden = "";
        // TO DO
        
        
        return orden;
    }
    
   /**
    * Método para la gestión de los resultados del servidor
    * Resultados posibles : 
    * OK --> todo va bien, podemos seguir dando órdenes
    * CRASHED--> nos hemos chocado con una pared, o no tenemos batería
    * BAD_COMMAND --> orden no reconocida por el agente
    * BAD_PROTOCOL --> fallo en el envío de mensaje
    * BAD_KEY --> Clave enviada errónea
    * @param objeto mensaje que contiene un Json de resultado
    * @author Sergio López Ayala
    */   
    public void gestionResultados(JsonObject objeto){
        String resultado = objeto.get("result").asString();
        switch(resultado){
            case "OK":
                // TO DO
                // Comprobar si estado es final
                // y hace falta cambiar a FIN
                estado = LOGEADO;
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
    /**
    * Método para la gestión del login al servidor
    * @author Sergio López Ayala
    */
    public void login(){
        JsonObject objeto = new JsonObject();
        System.out.println("10");
        try{
            objeto.add("command", "login");
            objeto.add("world", miMapa);
            objeto.add("radar", this.getName());
            objeto.add("scanner", this.getName());
            objeto.add("gps", this.getName());
        }catch( Exception e){
            System.err.println("Fallo al serializar login");
        }
        outbox = new ACLMessage();
        outbox.setSender(this.getAid());
        outbox.setReceiver(miContacto);
        System.out.println("A");
        System.out.println(objeto.asString());
        outbox.setContent(objeto.asString());
        System.out.println("11");
        this.send(outbox);
    }
    
    /**
    * @param aid id del agente manejado por Magentix
    * @param mapa nivel a ejecutar
    * @param contacto Receptor de nuestros mensajes
    * @author Sergio López Ayala
    */
    public Agente(AgentID aid, String mapa, String contacto) throws Exception{
        super(aid);
        miMapa = mapa;
        miContacto = new AgentID(contacto);
    }
    
    /**
    * Iniciación del agente
    * @author Sergio López Ayala
    */
    @Override
    public void init(){
        miBateria = new Bateria();
        miEscaner = new Escaner();
        miRadar = new Radar();
        miGPS = new GPS();
        inbox = null;
        outbox = null;
        estado = NOLOGEADO;
    }
    
    /**
     *
     * @author Sergio López Ayala
     */
    @Override
    public void execute(){
        JsonObject objeto = new JsonObject();
        
        while(!terminar){
            switch(estado){
                case NOLOGEADO:
                    login();
                    estado = ESCUCHALOGIN;
                    break;
                case ESCUCHALOGIN:
                    System.out.println("Agente("+this.getName()+") Esperando respuesta");
                    boolean repetir=true;
                    while (repetir)  {
                        try {
                            System.out.println("1");
                            inbox = receiveACLMessage();
                            System.out.println("8");
                            objeto = Json.parse(inbox.getContent()).asObject();
                            String resultado = objeto.get("result").asString();
                            System.out.println("2");
                            switch(resultado){
                                case "BAD_MAP":
                                    System.out.println("3");
                                    // TO DO
                                    System.err.println("Mapa especificado inválido");
                                    break;
                                case "BAD_PROTOCOL":
                                    System.out.println("4");
                                    // TO DO
                                    System.err.println("error al crear el JSON");
                                    break;
                                default:
                                    System.out.println("5");
                                    ClaveConexion = resultado;
                                    System.out.println(ClaveConexion);
                                    logout();
                                    System.out.println("6");
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
                    String movimiento = pensar();
                    outbox = new ACLMessage();
                    outbox.setSender(this.getAid());
                    outbox.setReceiver(miContacto);
                    
                    if(!objeto.isEmpty()){
                        objeto = new JsonObject();
                    }
                    
                    objeto.add("command", movimiento );
                    objeto.add("key", ClaveConexion );
                    
                    outbox.setContent(objeto.asString());
                    this.send(outbox);
                    estado = ESCUCHANDO;
                    break;
                case ESCUCHANDO:
                    // TO DO
                    System.out.println("Agente("+this.getName()+") Esperando respuesta");
                    
                    for(int i = 0; i < 4; i++)  {
                        try {
                            inbox = receiveACLMessage();
                            objeto = Json.parse(inbox.getContent()).asObject();
                            
                            if( objeto.get("escaner") != null){
                                miEscaner.parsearEscaner(objeto);
                            }else if( objeto.get("radar") != null){
                                miRadar.parsearCoordenadas(objeto);
                            }else if( objeto.get("gps") != null){
                                miGPS.parsearCoordenadas(objeto);
                            }else if( objeto.get("result") != null){
                                gestionResultados(objeto);
                               
                            } 
                            
                            
                        } catch (Exception ex) {
                            System.err.println("Agente("+this.getName()+") Error al recibir login");
                            repetir=false;
                        }                        
                    }
                    break;
                case FIN:
                    terminar = true;
                    break;
            }
        }
    }
    
    /**
    *
    * @author Thomas LESBROS 
    */
    @Override
    public void finalize(){
        //TO-DO
        //Logout
        outbox = new ACLMessage();
        outbox.setSender(this.getAid());
        outbox.setReceiver(miContacto);
        JsonObject objeto = new JsonObject();
        
        objeto.add("command", "logout" );
        objeto.add("key", ClaveConexion );

        outbox.setContent(objeto.asString());
        this.send(outbox);
        System.err.println("Esta ahora deslogeado");
        
        //Recibir traza
        
        try{
            System.err.println("Recibiendo traza...");
            ACLMessage inbox = this.receiveACLMessage();
            JsonObject injson=Json.parse(inbox.getContent()).asObject();
            JsonArray ja = injson.get("trace").asArray();
            byte data[] = new byte[ja.size()];
            for(int i=0; i<data.length; i++){
                data[i]=(byte) ja.get(i).asInt();
            }
            FileOutputStream fos = new FileOutputStream("Traza.png");
            fos.write(data);
            fos.close();
            System.err.println("Traza Guardada como 'Traza.png'");
        }catch(InterruptedException | IOException ex){
            System.err.println("Error al hacer la traza");
        }
    }
    public void logout(){
        //TO-DO
        //Logout
        System.out.println("7");
        outbox = new ACLMessage();
        outbox.setSender(this.getAid());
        outbox.setReceiver(miContacto);
        JsonObject objeto = new JsonObject();
        
        objeto.add("command", "logout" );
        objeto.add("key", ClaveConexion );

        outbox.setContent(objeto.asString());
        this.send(outbox);
        System.err.println("Esta ahora deslogeado");
        
        //Recibir traza
        
        try{
            System.err.println("Recibiendo traza...");
            ACLMessage inbox = this.receiveACLMessage();
            JsonObject injson=Json.parse(inbox.getContent()).asObject();
            JsonArray ja = injson.get("trace").asArray();
            byte data[] = new byte[ja.size()];
            for(int i=0; i<data.length; i++){
                data[i]=(byte) ja.get(i).asInt();
            }
            FileOutputStream fos = new FileOutputStream("Traza.png");
            fos.write(data);
            fos.close();
            System.err.println("Traza Guardada como 'Traza.png'");
        }catch(InterruptedException | IOException ex){
            System.err.println("Error al hacer la traza");
        }
    }
}
