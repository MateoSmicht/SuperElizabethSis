package juego;

import entorno.Entorno;
import entorno.Herramientas;

import java.awt.Color;
import java.awt.Image;

//Esta clase se encarga de controlar y dibuar a los enemigos.
public class Enemigo {
	private int x, y, ancho, alto, velocidad;
	private Color color;
	private boolean hayGravedad;
	private boolean estaPisando;
	private int gravedad;
	private int direccion;
	private Image imagenIzquierdo;
	private Image imagenDerecho;
	Color transparente = new Color(0, 0, 255, 50);
	Bolita[] bolitas;
	Enemigo[] enemigos;

	public Enemigo(int x, int y, int ancho, int alto, int velocidad, Color color, int direccion) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.velocidad = velocidad;
		this.color = transparente;
		this.hayGravedad = true; // DEVUELVE TRUE SI SE ACTIVA LA GRAVEDAD.
		this.estaPisando = false; // DEVUELVE TRUE SI EL ENEMIGO TOCA EL SUELO.
		this.gravedad = 3; // FUERZA DE GRAVEDAD .
		this.direccion = 1; // INDICA QUE DIRRECION ES LA QUE DISPARA.
		this.imagenIzquierdo = Herramientas.cargarImagen("imagenTiRex/tiRexIzquierdo.png");
		this.imagenDerecho = Herramientas.cargarImagen("imagenTiRex/tiRexDerecho.png");
		this.color = transparente;
	}

	// Indica el movimiento del enemigo.
	public void mover() {
		this.x += this.velocidad;
	}

	// Indica que cambia de direccion para no irse de los limites.
	public void rebotar() {
		this.velocidad = this.velocidad * (-1);
	}

	// dibuja a los dibuja sus rectangulos y coloca las imagenes
	// dependiendo de donde esten mirando.
	public void dibujar(Entorno entorno) {
		entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, this.color);
		if (getDireccion() == -1) {
			entorno.dibujarImagen(imagenIzquierdo, this.x, this.y, 0);
		} else {
			entorno.dibujarImagen(imagenDerecho, this.x, this.y, 0);
		}
	}

	// Retorna la x de enemigo.
	public int getX() {
		return this.x;
	}

	// Retorna la y de enemigo.
	public int getY() {
		return this.y;
	}

	// Retorna el ancho de los enemigos.
	public int getAncho() {
		return this.ancho;
	}

	// Retorna el alto de los enemigos.
	public int getAlto() {
		return this.alto;
	}

	// Indica si hay gravedad.
	public void enemigoHayGravedad(boolean hayGravedad) {
		this.hayGravedad = hayGravedad;
	}

	// Maneja la gravedad de los enemigos.
	public void enemigoGravedad(int gravity) {
		if (hayGravedad) {
			this.y += gravity;
		}
	}

	// Indica si el enemigo esta pinsando un bloque.
	public void enemigoEstaPisando(boolean estaPisando) {
		this.estaPisando = estaPisando;
	}

	// Indica si hay gravedad.
	public boolean isHayGravedad() {
		return hayGravedad;
	}

	// Indica un booleano si esta pinsando un bloque.
	public boolean isEstaPisando() {
		return estaPisando;
	}

	// Indica la velocidad de los enemigos.
	public int getVelocidad() {
		return velocidad;
	}

	// Indica el valor de la gravedad de los enemigos.
	public int getGravity() {
		return this.gravedad;
	}

	// Indica la dirrecion.
	public void setDireccion(int d) {
		this.direccion = d;
	}

	// Indica el valor de x
	public void setX(int X) {
		this.x = X;
	}

	// Indica la dirreccion dnde esta mirando el enemigo.
	public int getDireccion() {
		return direccion;
	}

	// Indica la velocidad que es D.
	public void setVelocidad(int d) {
		this.velocidad = d;
	}

}
