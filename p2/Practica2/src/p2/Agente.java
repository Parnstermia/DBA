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

import java.util.*;
/**
 *
 * @author Sergio López Ayala/ Thomas LESBROS
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
    private String accion;
    
    //Clave a enviar con cada mensaje en el campo Key del JSon
    private String ClaveConexion = "";
    private AgentID miContacto;
	 
	 //Set que guarda el conjunto de posiciones en las que el agente ya ha estado
	 private ArrayList<Posicion> memoria;
	 
	 //boolean temporal
    boolean temporal=true;
	
    /**
    * Método para la elección del siguiente movimiento
    * @return orden a enviar
    * @author Diego Alfonso Candelaria Rodríguez
    */
	public String pensar(){
		Direccion direccion=Direccion.N;
		String orden="";
		
		if(miRadar.posicionMeta(12)){
			System.out.println("\n\n  ESTAMOS EN LA PUTA META  \n\n");
			orden="logout";
		}
		else{
			if(miBateria.deboRecargar()){
				orden="refuel";
				miBateria.Recargar();
			}
			else{
				miBateria.usar();
				
				Posicion posicionActual=new Posicion();
				posicionActual.x=miGPS.getX();
				posicionActual.y=miGPS.getY();
				memoria.add(posicionActual);

				if(temporal){
					memoria.clear();
					temporal=false;
				}
				
				ArrayList<Nodo> nodos=new ArrayList<Nodo>(8);
				
				Nodo nodo;
				Posicion pos;
				
				System.out.println("\nEsquina superior izquierda***********************************\n");
				//Esquina superior izquierda
				pos=new Posicion(miGPS.x-1,miGPS.y-1);
				if(posicionPermitida(new Posicion(1,1), pos)){
					nodo=new Nodo(pos, miEscaner.miVector.get(6), Direccion.NW);
					nodos.add(nodo);
				}
				System.out.println("\nMitad superior***********************************\n");
				//Mitad superior
				pos=new Posicion(miGPS.x,miGPS.y-1);
				if(posicionPermitida(new Posicion(1,2), pos)){
					nodo=new Nodo(pos, miEscaner.miVector.get(7), Direccion.N);
					nodos.add(nodo);
				}
				System.out.println("\nEsquina superior derecha***********************************\n");
				//Esquina superior derecha
				pos=new Posicion(miGPS.x+1,miGPS.y-1);
				if(posicionPermitida(new Posicion(1,3), pos)){
					nodo=new Nodo(pos, miEscaner.miVector.get(8), Direccion.NE);
					nodos.add(nodo);
				}
				System.out.println("\nIzquierda***********************************\n");
				//Izquierda
				pos=new Posicion(miGPS.x-1,miGPS.y);
				if(posicionPermitida(new Posicion(2,1), pos)){
					double biatch=miEscaner.miVector.get(11);
					nodo=new Nodo(pos, biatch, Direccion.W);
					nodos.add(nodo);
				}
				System.out.println("\nDerecha***********************************\n");
				//Derecha
				pos=new Posicion(miGPS.x+1,miGPS.y);
				if(posicionPermitida(new Posicion(2,3), pos)){
					nodo=new Nodo(pos, miEscaner.miVector.get(13), Direccion.E);
					nodos.add(nodo);
				}
				System.out.println("\nEsquina inferior izquierda***********************************\n");
				//Esquina inferior izquierda
				pos=new Posicion(miGPS.x-1,miGPS.y+1);
				if(posicionPermitida(new Posicion(3,1), pos)){
					nodo=new Nodo(pos, miEscaner.miVector.get(16), Direccion.SW);
					nodos.add(nodo);
				}
				System.out.println("\nMitad inferior***********************************\n");
				//Mitad inferior
				pos=new Posicion(miGPS.x,miGPS.y+1);
				if(posicionPermitida(new Posicion(3,2), pos)){
					nodo=new Nodo(pos, miEscaner.miVector.get(17), Direccion.S);
					nodos.add(nodo);
				}
				System.out.println("\nEsquina inferior derecha***********************************\n");
				//Esquina inferior derecha
				pos=new Posicion(miGPS.x+1,miGPS.y+1);
				if(posicionPermitida(new Posicion(3,3), pos)){
					nodo=new Nodo(pos, miEscaner.miVector.get(18), Direccion.SE);
					nodos.add(nodo);
				}
				
				System.out.println("\nLA POSICION ACTUAL ES: \n"+miGPS);
				System.out.println("\nEL ESCANER ES:\n"+miEscaner);
				System.out.println("\nEL RADAR ES:\n"+miRadar);
				System.out.println("\nEL CONJUNTO DE NODOS ES: \n"+nodos);
				
				
				if ( nodos.isEmpty() ){
					System.out.println("no hay movimientos disponibles");
					orden = "logout";
				}else{
					Nodo menor=nodos.get(0);
					for(int i=1 ; i<nodos.size() ; i++){
						if(nodos.get(i).distancia < menor.distancia)
							menor=nodos.get(i);
						System.out.println("\nEL NODO MENOR ES: "+menor);
					}
					
					orden=menor.direccion.getString();
				}
				
			}
		}
		
		System.out.println("\nLA ORDEN DEVUELTA EN PENSAR ES: "+orden);
		return orden;
	}
	
	/**
	 * Método para comprobar si una posición es permitida
	 * @param posicionRelativa indica una posición en una matriz 5x5
	 * @param posicionAbsoluta indica una posición en el mapa en el que se mueve el agente
	 * @author Diego Alfonso Candelaria Rodríguez
	 */
	private boolean posicionPermitida(Posicion posicionRelativa, Posicion posicionAbsoluta){
		if(!miRadar.posicionBloqueada(posicionRelativa.x*5+posicionRelativa.y)){
			System.out.println("\n LA POSICION NO ESTA BLOQUEADA \n");
			if(memoria.contains(posicionAbsoluta)){
				System.out.println("\n LA MEMORIA CONTIENE LA POSICION \n");
				return false;
			}
			else{
				System.out.println("\n LA MEMORIA NO CONTIENE LA POSICION \n");
				return true;
			}
		}
		else{
			System.out.println("\n LA POSICION ESTA BLOQUEADA \n");
			return false;
		}
	}
    
    /**
    * Método para recibir mensajes del servidor
    * @author Sergio López Ayala y Thomas LESBROS
    */
    public void recibirMensaje(){
        try {
            inbox = receiveACLMessage();
				System.out.println("****************************************************************************");
				System.out.println(inbox.getContent());
				System.out.println("****************************************************************************");
            JsonObject objeto = Json.parse(inbox.getContent()).asObject();
            if( objeto.get("scanner") != null){
                System.out.println("\nRecibida percepción del escaner");
                miEscaner.parsearEscaner(objeto);
            }else if( objeto.get("radar") != null){
                System.out.println("Recibida percepción del radar");
                miRadar.parsearCoordenadas(objeto);
            }else if( objeto.get("gps") != null){
                System.out.println("Recibida percepción del gps");
                miGPS.parsearCoordenadas(objeto);
            }else if( objeto.get("result") != null){
                System.out.println("Recibido resultado");
                gestionResultados(objeto);
            }else if( objeto.get("trace") != null){
                JsonArray ja = objeto.get("trace").asArray();
                byte data[] = new byte[ja.size()];
                for(int i=0; i<data.length; i++){
                    data[i]=(byte) ja.get(i).asInt();
                }
                String title;
                title = String.format("traza de "+miMapa+".png");
                FileOutputStream fos = new FileOutputStream(title);
                fos.write(data);
                fos.close();
                System.out.println("Traza Guardada como 'Traza.png'");
            }
        }catch(InterruptedException exception){
            System.err.println("Error al percibir");
            System.err.println(exception.toString());
        }catch(IOException ex){
            System.err.println("Excepción al hacer la traza");
        }
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
                if(!accion.equals("logout")){
                    estado = LOGEADO;
                }else{
                    estado = FIN;
                }
                break;
            case "CRASHED":
                System.err.println("El agente se ha chocado");
                break;
            case "BAD_COMMAND":
                System.err.println("Error, orden desconocida");
                break;
            case "BAD_PROTOCOL":
                System.err.println("Error de protocolo");
                break;
            case "BAD_KEY":
                System.err.println("Error en la clave enviada");
                break;
            case "BAD_MAP":
                System.err.println("Mapa especificado inválido");
                break;
            default:
                ClaveConexion = resultado;
                break;
        }
    }
    /**
    * Método para la gestión del login al servidor
    * @author Sergio López Ayala y Thomas LESBROS
    */
    public void login(){
        JsonObject objeto = new JsonObject();
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
        outbox.setContent(objeto.toString());
        System.out.println(outbox);
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
        ClaveConexion = "";
		  memoria=new ArrayList<Posicion>();
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
                    System.out.println("Agente "+this.getName()+" esperando respuesta");
						  
                    for(int i = 0 ; i < 5 ; i++)
                        recibirMensaje();
                    
                    if( !ClaveConexion.equals("")){
                        estado = LOGEADO;
                    }
                    else{
                        estado = NOLOGEADO;
                    }
                    break;
                case LOGEADO:
                    System.out.println("Agente("+this.getName()+"), ESTADO: LOGEADO");
							
							System.out.println("\nLA MEMORIA: \n"+memoria);
							accion = pensar();
							System.out.println("Orden a realizar : " + accion);
                    
                    if(!accion.equals("logout")){
                        outbox = new ACLMessage();
                        outbox.setSender(this.getAid());
                        outbox.setReceiver(miContacto);

                        if(!objeto.isEmpty()){
                            objeto = new JsonObject();
                        }

                        objeto.add("command", accion );
                        
                        objeto.add("key", ClaveConexion.toString() );

                        outbox.setContent(objeto.toString());
                        this.send(outbox);
                        System.out.println(ClaveConexion);
                        estado = ESCUCHANDO;
                    }else{
                        System.out.println("Pasamos a estado fin");
                        estado = FIN;
                    }
                    break;
                case ESCUCHANDO:
                    System.out.println("Agente("+this.getName()+") Esperando respuesta");
                    
                    for(int i = 0; i < 4; i++){
                        recibirMensaje();
                    }
                    break;
                case FIN:
                    logout();
                    terminar = true;
                    break;
            }
        }
    }
    
    /**
    *
    * @author Thomas LESBROS y Sergio López Ayala
    */
    public void logout(){
        System.out.println("Agente ("+this.getName()+") realiza logout");
        outbox = new ACLMessage();
        outbox.setSender(this.getAid());
        outbox.setReceiver(miContacto);
        JsonObject objeto = new JsonObject();
        
        objeto.add("command", "logout" );
        objeto.add("key", ClaveConexion );

        outbox.setContent(objeto.toString());
        this.send(outbox);
        System.out.println("Esta ahora deslogeado");
        
        
        //Recibimos 5 mensajes:
        //1- OK
        //2,3,4 - Percepciones
        //5- traza
        for(int i = 0; i < 5; i++){
            recibirMensaje();
        }

        
    }
}