package practica3;

import javafx.util.Pair;
import java.lang.Math;

/**
 *
 * @author Diego Alfonso Candelaria Rodríguez
 */
public class Escaner {
   private double matriz[][];
	private final int tamanio=3;
	
	public Escaner(){
		matriz=new double[tamanio][tamanio];
	}
	
	public void calcularMatriz(Pair actual, Pair objetivo){
		int x=-1;
		int y=-1;
		for(int i=0 ; i<tamanio ; i++){
			for(int j=0 ; j<tamanio ; j++){
				Pair celda=new Pair((int)actual.getKey()-x,(int)actual.getValue()-y);
				matriz[i][j]=calcularDistancia(celda,objetivo);
				y+=1;
			}
			x+=1;
			y=-1;
		}
	}
	
	private double calcularDistancia(Pair p1, Pair p2){
		return Math.sqrt(Math.pow((double)p1.getKey()-(double)p2.getKey(),2.0)+
			Math.pow((double)p1.getValue()-(double)p2.getValue(),2.0));
	}
	
	public double[][] getMatriz(){
		return matriz;
	}
	
	public int getTamanio(){
		return tamanio;
	}
}
