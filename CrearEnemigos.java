package juego;

import java.awt.Color;
import java.util.Random;
import entorno.Entorno;

//Esta clase CrearEnemigo se encarga de crear 
//y mover a los enemgios.
public class CrearEnemigos {
	private int cantidadEnemigos;
	private int filas;
	Enemigo[] enemigos;
	Plataforma plataforma;

	// Pasamos como parametros la cantidad de filas que
	// deseamos y cuantos enemigos por cada fila.
	public CrearEnemigos(int cantidadEnemigos, int Filas) {
		this.cantidadEnemigos = cantidadEnemigos;
		this.filas = Filas;
		this.enemigos = new Enemigo[cantidadEnemigos * filas];
		crearMobs();
	}

	// Crea los enemgios por fila.
	public void crearMobs() {
		int anchoMob = 20;
		int altoMob = 45;
		Random rand = new Random();// ESTE RANDOM SON ASEGURA QUE APAREZCAN EN LUGARES DISTINTOS.
		int x = 0;
		int y = 0;
		int velocidad = 1;
		for (int i = 0; i < this.cantidadEnemigos; i++) {
			for (int j = 0; j < this.filas; j++) {
				if (i == 0) {
					x = rand.nextInt(5, 60); // POSICION DEL EJE X DEL ENEMIGO
					velocidad = 1;
				} else {
					if (i == 1) {
						x = rand.nextInt(690, 750); // POSICION DEL EJE Y DEL ENEMIGO
						this.enemigos[i].setDireccion(-1);
					}
				}
				y = 1460 - (100 * j); // Posicion eje y del dinosaurio
				this.enemigos[i * this.filas + j] = new Enemigo(x, y, anchoMob, altoMob, velocidad, Color.RED,
						velocidad);
			}
		}
	}

	// mueve a los enemigos en una dirrecion y cuando llegan a cierta distacia
	// que es antes de tocar el borde cambia a la direccion contraria.
	public void moverMobs() {
		for (int i = 0; i < enemigos.length; i++) {
			if (enemigos[i] != null) {
				this.enemigos[i].mover();
				if (this.enemigos[i].getX() == 788) { // CUANDO LLEGA AL EJE X 788 CAMBIA DE DIRECCION.
					this.enemigos[i].rebotar();
					this.enemigos[i].setDireccion(-1);
				}
				if (this.enemigos[i].getX() == 3) { // CUANDO LLEGA AL EJE X 3 CAMBIA DE DIRECCION.
					this.enemigos[i].rebotar();
					this.enemigos[i].setDireccion(1);
				}
			}
		}
	}

	// Este constructor es para que cuando se juntan
	// dos enemigos se separen inmediatamente.
	public void sobrePosicionMob() {
		for (int i = 0; i < this.enemigos.length; i++) {
			for (int h = 0; h < this.enemigos.length; h++)
				if (enemigos[i] != null && enemigos[h] != null) {
					if (enemigos[i] != enemigos[h]) {
						double distancia = Math.sqrt(Math.pow(enemigos[i].getX() - enemigos[h].getX(), 2)
								+ Math.pow(enemigos[i].getY() - enemigos[h].getY(), 2)); // DINOSAURIO COLISIONA CON
																							// OTRO DINOSAURIO.
						boolean colisionY = enemigos[h].getY() + enemigos[h].getAlto() / 2 >= enemigos[i].getY()
								- enemigos[i].getAlto() / 2 // DINOSAURIO CAE DE ARRIBA Y APLASTA AL OTRO MOB.
								&& enemigos[h].getY() + enemigos[h].getAlto() / 2 <= enemigos[i].getY()
										+ enemigos[i].getAlto() / 2;
						if (distancia <= enemigos[i].getAncho()) {
							if (enemigos[i].getVelocidad() == 1) { // PREGUNTAMOS HACIA DONDE MIRA PARA DARLES VELOCIDAD
								enemigos[i].setX(enemigos[i].getX() + 1);
							}
							if (enemigos[i].getVelocidad() == -1) {
								enemigos[i].setX(enemigos[i].getX() - 1);

							}
							if (colisionY == true && enemigos[i].getVelocidad() == 1) {
								enemigos[i].setX(enemigos[i].getX() + 2);
							}
							if (colisionY == true && enemigos[i].getVelocidad() == -1) {
								enemigos[i].setX(enemigos[i].getX() - 2);
							}
						}
					}
				}
		}
	}

	// Dibuja cada una de los enemigos en cada una de las filas.
	public void dibujar(Entorno entorno) {
		for (int i = 0; i < enemigos.length; i++) {
			if (enemigos[i] != null) {
				enemigos[i].dibujar(entorno);
			}
		}
	}

	// retorna cada uno de los enemigos que esta en la lista.
	public Enemigo[] getEnemigos() {
		return this.enemigos;
	}

}
