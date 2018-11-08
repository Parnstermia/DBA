package p2;

/**
 * Clase diseñada para encapsular el concepto de posición en la que se encuentra el agente y para ser 
 * utilizada en en un HashSet.
 * @author Diego Alfonso Candelaria Rodríguez
 */
public class Posicion {
	public int x;
	public int y;
	
        /**
        * Constructor por defecto de Posicion
        * @author Diego Alfonso Candelaria Rodríguez
        */
	public Posicion(){
            x=0;
            y=0;
	}
	
        /**
        * Constructor con argumentos de Posicion
        * @author Diego Alfonso Candelaria Rodríguez
        * @param x int indica la posicion en el eje X
        * @param y int indica la posición en el eje Y
        */
	public Posicion(int x, int y){
            this.x=x;
            this.y=y;
	}
	
        /**
        * Método que compara si la posición del objeto por argumento es igual que la local
        * @author Diego Alfonso Candelaria Rodríguez
        * @param obj Object
        */
	@Override
	public boolean equals(Object obj){
            boolean equal=false;

            if (obj == null) return false;
            if (!(obj instanceof Posicion)) return false;
            if (obj == this) return true;
            if(((Posicion)obj).x == this.x && ((Posicion)obj).y == this.y) equal=true;

            return equal;
	}
	
        /**
        * Método para devolver la posición local en Hash
        * @author Diego Alfonso Candelaria Rodríguez
        */
	@Override
	public int hashCode(){
            int hash=17;
            hash = hash * 31 + x;
            hash = hash * 31 + y;

            return hash;
	}
	
        /**
        * Método para devolver la posición actual en un String. 
        * @author Diego Alfonso Candelaria Rodríguez
        */
	@Override
	public String toString(){
            String str="["+x+","+y+"]";
            return str;
	}
}
