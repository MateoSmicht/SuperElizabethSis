package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

//Esta Clase se encarga de hacer funcion la bolaDeFuego que tiera el 
//dinosaurioColosal.
public class BolaDeFuego {
	private int x;
	private int y;
	private int radio;
	private int velocidad;
	private Image imagen;

	public BolaDeFuego(int x, int y) {
		this.x = x;
		this.y = y;
		this.radio = 50;
		this.velocidad = 1;
		this.imagen = Herramientas.cargarImagen("imagenBolaDeFuego/bolaDeFuego.png");
	}

	// Este constructor indica como se va a mover la bolaDeFuego
	// y con que velocidad.
	public void mover() {
		this.y = this.y + this.velocidad;
	}

	// Dibuja el circulo y la imagen de la bolaDeFuego.
	public void dibujar(Entorno entorno) {
		entorno.dibujarCirculo(this.x, this.y, 2 * this.radio, Color.YELLOW);
		entorno.dibujarImagen(imagen, this.x, this.y, 0);
	}

	// Indica Los valor de x.
	public int getX() {
		return x;
	}

	// Indiqca el valor de y.
	public int getY() {
		return y;
	}

	// Indica el radio del la bola.
	public int getRadio() {
		return radio;
	}

	// Indica el revote contra el suelo y con el -1 vuelve a subir.
	public void rebotar() {
		this.velocidad = this.velocidad * (-1);
	}

	// Da un valor de y.
	public void setY(int y) {
		this.y = y;
	}
}
