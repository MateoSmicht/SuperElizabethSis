package juego;

import java.awt.Color;
import java.awt.Image;
import java.util.Random;

import entorno.Entorno;
import entorno.Herramientas;

//Esta clase dibuja el rectangulo, dibuja imagen y maneja
//los item aleatorios que salen por el juego.
public class Items {
	private int x;
	private double y;
	private int ancho;
	private double alto;
	Color transparente = new Color(0, 0, 0, 0);
	private Image imagenMoneda;
	private Image imagenEscudo;
	private Image imagenManzana;
	private int n;
	Random rand = new Random();

	public Items(int n) {
		this.x = rand.nextInt(5, 750);
		;// RANDOM PARA QUE LOS ITEM SALGAN ALEATORIAMENTE EN EL JUEGO EN X.
		this.y = rand.nextInt(-1200, 600);// RANDOM PARA QUE LOS ITEM SALGAN ALEATORIAMENTE EN EL JUEGO EN X.
		this.ancho = 25;
		this.alto = 25;
		this.n = n;// UNA VARIABLE QUE FUE UTILIZADA PARA INDICAR QUE TIPO DE ITEM
					// ERA(MANZANA,MONEDA O ESCUDO).
		this.imagenManzana = Herramientas.cargarImagen("Items/manzana.png");
		this.imagenEscudo = Herramientas.cargarImagen("Items/escudo.png");
		this.imagenMoneda = Herramientas.cargarImagen("Items/moneda.png");

	}

	// esto hace que el los item cuando no tengan un bloque
	// debajo se caigan hasta colisionar con un bloque.
	public boolean itemsGravedad(Plataforma plataforma) {

		for (Bloque bloque : plataforma.getBloques()) {
			if (bloque != null) {
				boolean colisionX = bloque.getX() - bloque.getAncho() / 2 < this.getX() + this.getAncho() / 2 // ESTOS
																												// ES LA
																												// COLISION
																												// EN EL
																												// EJE
																												// X.
						&& bloque.getX() + bloque.getAncho() / 2 > this.getX() - this.getAncho() / 2;
				boolean colisionY = this.getY() + this.getAlto() / 2 >= bloque.getY() - bloque.getAlto() / 2// ESTO ES
																											// LA
																											// COLISION
																											// EN EL EJE
																											// Y.
						&& this.getY() + this.getAlto() / 2 <= bloque.getY() + bloque.getAlto() / 2;
				if (colisionX && colisionY) {
					return true;// RETORNA VERDAD ESO SIGNIFICA QUE COLISIONO CON UN BLOQUE.
				}
			}
		}
		return false;// RETORNA FALSO SEGNIFICA QUE NO COLISIONO CON UN BLOQUE.
	}

	// Es la gravedad con la que caen los items.
	public void gravedad() {
		double gravity = 1;
		this.y = this.y + gravity;
	}

	// Dibuja cada item dependiendo de que sea.
	public void dibujar(Entorno entorno) {
		entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, transparente);

		if (n == 1) {// DIBUJA LA MANZANA
			entorno.dibujarImagen(imagenManzana, this.x, this.y, 0);
		}

		if (n == 2) {// DIBUJA EL ESCUDO
			entorno.dibujarImagen(imagenEscudo, this.x, this.y, 0);
		}
		if (n == 3) {// DIBJA LA MONEDA.
			entorno.dibujarImagen(imagenMoneda, this.x, this.y, 0);
		}

	}

	// Retorna el X del item.
	public int getX() {
		return x;
	}

	// Retorna la Y del item.
	public double getY() {
		return y;
	}

	// Retorna el ancho del item.
	public int getAncho() {
		return ancho;
	}

	// retorna el alto del item.
	public double getAlto() {
		return alto;
	}

}
