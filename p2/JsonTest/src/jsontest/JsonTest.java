package jsontest;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class JsonTest {

    public static void main(String[] args) {
        int v[] = {2004,2008,2011,2015};
        String n = "Mariano";
        int e = 55;
        double al = 1.80, an = 0.75;

        // Codificar variable en JSON
        // 1. Crear el objeto 
        JsonObject objeto = new JsonObject();
        // 2. Añadir pares <clave,valor>
        objeto.add("nombre", n);
        objeto.add("edad",e);
        objeto.add("dimensiones",new JsonObject().add("altura", al).add("anchura", an));
        JsonArray vector = new JsonArray();
        for (int i=0; i<v.length; i++)
            vector.add(v[i]);
        objeto.add("vector", vector);
        // 3. Serializar el objeto en un String
        String resultado = objeto.toString();
        // 4. Manejar el String
        System.out.println("Resultado serializado = <"+resultado+">");
        
        // Decodificar variables en JSON
        String fuente = resultado;
        // 1. Parsear el String original y almacenarlo en un objeto 
        objeto = Json.parse(fuente).asObject();
        // 2. Extraer los valores asociados a cada clave
        System.out.println("Nombre= "+objeto.get("nombre").asString());
        System.out.println("Edad= "+objeto.get("edad").asInt());
        System.out.println("Altura= "+objeto.get("dimensiones").asObject().get("altura").asDouble());
        System.out.println("Anchura= "+objeto.get("dimensiones").asObject().get("anchura").asDouble());
        
        try{
            if(objeto.get("nombre") != null){
                System.out.println("Nombre existe");
            }else{
                System.out.println("Nombre no existe");
            }
            System.out.println("Anchura= "+objeto.get("dim"));
        }catch(Exception excep){
            System.out.println("No existe");
        }
        System.out.print("Elecciones presentadas= ");
        for (JsonValue j : objeto.get("vector").asArray())
            System.out.print(j.asInt()+", ");
        /*
        JsonArray vector2 = objeto.get("vector").asArray();
        for (int i=0; i<vector2.size(); i++)
            System.out.print(vector2.get(i).asInt()+", ");
        */
    }
    
}