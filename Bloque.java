package juego;

import java.awt.Color;
import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

//Esta clase se encarga de dibujar los bloques.
public class Bloque {
	private int x;
	private int y;
	private int ancho;
	private int alto;
	private Color color;
	private boolean roto;
	private Image imagen;

	public Bloque(int x, int y, int ancho, int alto, Color color) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.color = color;
		this.roto = false;

		if (color.equals(Color.CYAN)) {// COMPARA ENTRE DOS COLORES PARA VER CUAL SE PUEDE ROMPER O CUAL NO.
			this.imagen = Herramientas.cargarImagen("imgbloques/bloqueDuro.png");
		} else if (color.equals(Color.RED)) {// EN ESTE CASO EL ROJO ES EL QUE SE PUEDE ROMPER.
			this.imagen = Herramientas.cargarImagen("imgbloques/bloqueRoto.png");
		}
	}

	// Dibuja los bloques y coloca su respectiva imagen.
	public void dibujar(Entorno entorno) {
		entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, this.color);

		if (this.imagen != null) {
			entorno.dibujarImagen(imagen, this.x, this.y, 0);
		}
	}

	// Retorna el eje X del bloque .
	public int getX() {
		return x;
	}

	// Retorna el eje Y del bloque.
	public int getY() {
		return y;
	}

	// Retorna el eje ancho del bloque .
	public int getAncho() {
		return ancho;
	}

	// Retorna el eje alto del bloque .
	public int getAlto() {
		return alto;
	}

	// Cambia a Verdadero a this.roto.
	public void romper() {
		this.roto = true;
	}

	// Retorna si el bloque esta roto.
	public boolean estaRoto() {
		return this.roto;
	}

	// Retorna el color del bloque .
	public Color getColor() {
		return color;
	}

	// Retorna el eje Y del bloque .
	public void setY(int y) {
		this.y = y;
	}
}
