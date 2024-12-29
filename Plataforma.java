package juego;

import entorno.Entorno;
import entorno.Herramientas;

import java.awt.Color;
import java.util.Random;

public class Plataforma {

	private int filas;
	private int bloquesPorFila;
	private Bloque[] bloques;
	
	public Plataforma(int filas, int bloquesPorFila) {
		this.filas = filas;
		this.bloquesPorFila = bloquesPorFila;
		this.bloques = new Bloque[filas * bloquesPorFila];
		armadoPlataforma();
	}

	public void armadoPlataforma() {
		int anchoPantalla = 800;
		int altoPantalla = 600;
		int anchoBloque = anchoPantalla / bloquesPorFila;
		int altoBloque = 20; // Altura fija para los bloques
		int separacionY = 100; // Separacion vertical entre bloques
		Random rand = new Random();
		int cont = 0;
		int contTotal = 0;
		for (int i = 0; i < this.filas; i++) {
			cont = 0;
			contTotal = 0;
			for (int j = 0; j < this.bloquesPorFila; j++) {
				int x = j * anchoBloque + anchoBloque / 2;
				int y = altoPantalla - (i * separacionY + altoBloque / 2);
				Color color;
				if (i > 0 && rand.nextInt(5) <= 1 && cont < 10) {
					color = Color.RED;
					cont = cont + 1;
				} else {
					color = Color.CYAN;
				}
				this.bloques[i * this.bloquesPorFila + j] = new Bloque(x, y, anchoBloque, altoBloque, color);
				contTotal = contTotal + 1;
			}
		}
		}
	 
	public void dibujar(Entorno entorno) {
		for (Bloque bloque : bloques) {
			if (bloque != null) {
				bloque.dibujar(entorno);
			}
	}
	}
	 

	public void romperBloque(int indice) {
		if (indice >= 0 && indice < bloques.length) {
			bloques[indice] = null; // Romper el bloque
			Herramientas.play("musica/romperbloques.wav");
		}
	}

	public Bloque[] getBloques() {
		return bloques;
	}
	
}