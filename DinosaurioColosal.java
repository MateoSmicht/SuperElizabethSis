package juego;

import entorno.Entorno;
import entorno.Herramientas;

import java.awt.Color;
import java.awt.Image;

//Esta clase se encrga de crear y manejar 
//el jefe final del juego (DinosaurioColosal)
public class DinosaurioColosal {
	private int x, y, ancho, alto, velocidad;
	private boolean hayGravedad;
	private boolean estaPisando;
	private int gravedad;
	private int direccion;
	private boolean disparo;
	private BolaDeFuego boladefuego;
	private int vidas;
	private Image imagen;
	Color transparente = new Color(0, 0, 0, 0);

	public DinosaurioColosal(int x, int y, int ancho, int alto, int velocidad, Color color, boolean disparo) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.velocidad = velocidad;
		this.hayGravedad = true; // DEVUELVE TRUE SI SE ACTIVA LA GRAVEDAD
		this.estaPisando = false; // DESVUELVE TRUE SI EL DINOSAURIO TOCA EL SUELO
		this.gravedad = 3; // FUERZA DE GRAVEDAD
		this.disparo = false;
		this.vidas = 3; // EL COLOSAL RESISTE 3 DISPAROS (OSEA TIENE 3 VIDAS)
		this.imagen = Herramientas.cargarImagen("imagenTiRex/jefe.png");
	}

	// ColosalPisa indica cuando pisa en la plataforma
	// osea cuando colisiona con un bloque
	public boolean colosalPisa(Plataforma plataforma) {
		for (Bloque bloque : plataforma.getBloques()) {
			if (bloque != null) {
				boolean colisionX = bloque.getX() - bloque.getAncho() / 2 < this.getX() + this.getAncho() / 2
						&& bloque.getX() + bloque.getAncho() / 2 > this.getX() - this.getAncho() / 2;
				boolean colisionY = this.getY() + this.getAlto() / 2 >= bloque.getY() - bloque.getAlto() / 2
						&& this.getY() + this.getAlto() / 2 <= bloque.getY() + bloque.getAlto() / 2;
				if (colisionX && colisionY) {
					return true; // EL COLOSAL ESTA PISANDO ALGUN BLOQUE
				}
			}
		}
		return false; // EL COLOSAL NO ESTA PISANDO NINGUN BLOQUE
	}

	public BolaDeFuego disparar() {
		boladefuego = new BolaDeFuego(x, y);
		return boladefuego;
	}

	public void mover() {
		this.x += this.velocidad;
	}

	public void rebotar() {
		if (this.getX() == 750 || this.getX() == 50) {
			this.velocidad = this.velocidad * (-1);
		}
	}

	public void dibujar(Entorno entorno) {

		entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, transparente);
		entorno.dibujarImagen(imagen, this.x, this.y, 0);
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public int getAncho() {
		return this.ancho;
	}

	public int getAlto() {
		return this.alto;
	}

	public void enemigoHayGravedad(boolean hayGravedad) {
		this.hayGravedad = hayGravedad;
	}

	public void enemigoGravedad(int gravity) {
		if (hayGravedad) {
			this.y += gravity;
		}
	}

	public void enemigoEstaPisando(boolean estaPisando) {
		this.estaPisando = estaPisando;
	}

	public boolean isHayGravedad() {
		return hayGravedad;
	}

	public boolean isEstaPisando() {
		return estaPisando;
	}

	public boolean getDisparo() {
		return disparo;
	}

	public void setDisparo(boolean d) {
		this.disparo = d;
	}

	public int getVelocidad() {
		return velocidad;
	}

	public int getGravity() {
		return this.gravedad;
	}

	public void setDireccion(int d) {
		this.direccion = d;
	}

	public void setX(int X) {
		this.x = X;
	}

	public int getDireccion() {
		return direccion;
	}

	public void setVelocidad(int d) {
		this.velocidad = d;
	}

	public void setVidas(int vidas) {
		this.vidas = vidas;
	}

	public int getVidas() {
		return vidas;
	}
}
