package practica3;

/**
 *
 * @author 
 */
public class Radar {
	private int matriz[][];
	private int tamanio;
	
	public Radar(int tamanio){
		matriz = new int[tamanio][tamanio];
		this.tamanio = tamanio;
	}
	
	public int getTamanio(){
		return tamanio;
	}
	
	public int[][] getMatriz(){
		return matriz;
	}
}
