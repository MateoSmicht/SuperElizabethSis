package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

//La clase bolita se encarga de hacer funcion las bolitas con cual disparan
//tanto la princesa como los enemigos
public class Bolita {
	private double x;
	private double y;
	private double radio;
	private double velocidadX;
	private Image imagenIzquierda;
	private Image imagenDerecha;
	Color transparente = new Color(0, 0, 0, 0);

	public Bolita(double x, double y, double velocidadX) {
		this.x = x;
		this.y = y;
		this.radio = 20;
		this.velocidadX = velocidadX; // Velocidad de la bolita
		this.imagenIzquierda = Herramientas.cargarImagen("imagenPrincesa/proyectilPrin.png");
		this.imagenDerecha = Herramientas.cargarImagen("imagenPrincesa/proyectilPrinDerecha.png");

	}

	// este constructor indique el movimiento de dicha bolitas.
	public void mover() {

		this.x += velocidadX;
	}

	// Dibuja el circulo de las pelotitas y las imagenes tanto si dispara
	// para la derecha como para la izquierda
	public void dibujar(Entorno entorno) {

		entorno.dibujarCirculo(this.x, this.y, this.radio, transparente);

		if (velocidadX > 0) {
			entorno.dibujarImagen(imagenDerecha, this.x, this.y, 0);
		} else {
			entorno.dibujarImagen(imagenIzquierda, this.x, this.y, 0);
		}
	}

	// Indica la x de la bolita.
	public double getX() {
		return x;
	}

	// Indica la y de la bolita.
	public double getY() {
		return y;
	}

	// Indica la radio de la bolita.
	public double getRadio() {
		return radio;
	}

	// Dada una y como parametro devuelve la misma y.
	public void setY(double y) {
		this.y = y;
	}
}
