package juego;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

//Esta clase es donde se ejecuta todas las acciones y manejo del juego.
public class Juego extends InterfaceJuego {
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	private Menu menu;
	private boolean enMenu;
	private boolean jugando;
	private boolean enInstrucciones;
	private boolean gameOver;
	private boolean gameWin;
	private Image imagen;

	// Variables y métodos propios de cada grupo
	// ...

	private Princesa princesa;
	private Plataforma plataforma;
	private Bolita bolita;

	private CrearEnemigos crearenemigos;
	private Enemigo[] enemigos;
	private List<Misil> misiles;
	private DinosaurioColosal dinosauriocolosal;
	private BolaDeFuego boladefuego;
	private int tiempo;
	private Magma magma;
	private Items manzana;
	private Items moneda;
	private Items escudo;
	private int cantidadEnemigosEliminados;
	private int puntosPorKill;
	private int posicionCamaraY;
	private int contTiempo;

	Juego() {
		this.entorno = new Entorno(this, " Super Elizabeth Sis, Volcano Edition - Grupo ... - v1", 800, 600);
		new Herramientas();

		this.menu = new Menu(entorno.ancho(), entorno.alto());
		this.enMenu = true;
		this.jugando = false;
		this.enInstrucciones = false;
		this.gameOver = false;
		this.gameWin = false;
		this.imagen = Herramientas.cargarImagen("imagenFondo/Fondo.png");
		this.princesa = new Princesa(entorno.ancho() / 2, entorno.alto() - 40, 20, 38, 0, false, true, false, -10, 1,
				this.entorno);
		this.magma = new Magma(400, 1196);
		this.manzana = new Items(1);
		this.moneda = new Items(3);
		this.escudo = new Items(2);
		this.plataforma = new Plataforma(15, 24);// (FILAS, BLOQUES POR FILA)
		this.crearenemigos = new CrearEnemigos(2, 23); // (CANTIDAD ENEMIGOS, CUANTAS FILAS HAY)
		this.enemigos = this.crearenemigos.getEnemigos();
		this.dinosauriocolosal = new DinosaurioColosal(entorno.ancho() / 2, -950, 90, 140, 1, Color.YELLOW, false);
		this.misiles = null;
		this.misiles = new ArrayList<>(); // Inicializar la lista de bolitas
		this.tiempo = 0; // Contador del tiempo
		this.cantidadEnemigosEliminados = 0; // Contador de dinosaurios eliminados
		this.puntosPorKill = 0; // Puntos por kill
		this.posicionCamaraY = 0;
		this.contTiempo = 0;
		Herramientas.loop("musica/musica.wav");
		// Inicia el juego!
		this.entorno.iniciar();
	}

	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y por lo
	 * tanto es el método más importante de esta clase. Aquí se debe actualizar el
	 * estado interno del juego para simular el paso del tiempo.
	 */
	public void tick() {

		// Dibuja la Imange de fondo.
		entorno.dibujarImagen(imagen, entorno.ancho() / 2, entorno.alto() / 2, 0);
		// Menu de Inicio.
		if (enMenu) {
			menu.dibujar(entorno);// DIBUJA EL MENU DE INICIO.

			if (entorno.sePresiono(entorno.TECLA_ARRIBA)) {
				menu.moverSeleccionArriba();// ESTO SIRVE PARA MOVER HACIA ARRIBA EN LA SELECCION DE OPCIONES.
			}
			if (entorno.sePresiono(entorno.TECLA_ABAJO)) {
				menu.moverSeleccionAbajo();// ESTO SIRVE PARA MOVER HACIA ABAJO EN LA SELECCION DE OPCIONES.
			}
			if (entorno.sePresiono(entorno.TECLA_ENTER)) {// ESTO SIRVE PARA CONFIRMAR LA OPCION.
				String opcion = menu.getOpcionSeleccionada();
				if (opcion.equals("Jugar")) {// SI SELECCIONAS JUGAR ENTRAS DIRECTAMENTE AL JUEGO.
					enMenu = false;
					jugando = true;
				} else if (opcion.equals("Instrucciones")) {// SI SELECCIONAS INTRODUCCION TE DICEN LAS REGLAS.
					enMenu = false;
					enInstrucciones = true;
				} else if (opcion.equals("Salir")) {// SI SELECCIONES SALIR, CERRAS LA VENTANA DEL JUEGO
					System.exit(0);
				}
			}
		}

		else if (enInstrucciones) {
			dibujarInstrucciones();// DIBUJA LAS INSTRUCCIONES

			if (entorno.sePresiono(entorno.TECLA_ENTER)) {// CUANDO PRECIONAS ENTER EN INTRODDCION VOLVES AL MENU.
				enInstrucciones = false;
				enMenu = true;

			}
		}

		else if (jugando) {
			procesarJuego();// INICIA EL JUEGO
			if (princesa.getVidas() == 0) {// INDICA QUE SI TIENE 0 VIDAS LA PRINCESA TE SALTA EL GAME OVER.
				gameOver = true;
				jugando = false;
				Herramientas.play("musica/GameOver.wav");// MUSICA
			} else if (dinosauriocolosal != null && dinosauriocolosal.getVidas() == 0) {
				gameWin = true;// SI MATAS AL COLOSAL TE SALTA LA PANTALLA DE GANADOR.
				jugando = false;
				Herramientas.play("musica/victory.wav");// MUSICA

			}
		} else if (gameOver) {

			dibujarGameOver();// DIBUJA LA PANTALLA GAME OVER.
			if (entorno.sePresiono(entorno.TECLA_ENTER)) {
				reiniciarJuego();// SI PRECIONES ENTER SE REINICIA Y VOLVES AL MENU.

				enMenu = true;
			}
		} else if (gameWin) {// DIBUJA LA PANTALLA GANADOR.
			dibujarGameWin();
			if (entorno.sePresiono(entorno.TECLA_ENTER)) {
				reiniciarJuego();// SI PRECIONES ENTER SE REINICIA Y VOLVES AL MENU.
				enMenu = true;
			}
		}
	}

	private void procesarJuego() {

		if (contTiempo > 0) {
			entorno.cambiarFont("Arial", 13, Color.WHITE);
			entorno.escribirTexto("inmune", princesa.getX() + 20, princesa.getY() - 15);// TE HACES INMUNE.
		}
		if (princesa.getY() < 100) {// ESTO ACOMODA EL EJE Y PARA QUE LA PRINCESA PUEDA IR MAS ARRIBA.
			posicionCamaraY = -1;
			ajustarY(posicionCamaraY);
		}
		this.plataforma.dibujar(this.entorno); // DIBUJA PLATAFORMAS.
//////////////////ITEMS/////////////////////////////////////////////////        
		itemUtil();// VERIFICA LA GRAVEDAD DE CADA ITEM Y LOS DIBUJA.
		if (manzana != null) {
			if (manzana.itemsGravedad(this.plataforma) == false) {
				manzana.gravedad();
			}
			this.manzana.dibujar(this.entorno);
		}
		if (moneda != null) {
			if (moneda.itemsGravedad(this.plataforma) == false) {
				moneda.gravedad();
			}

			this.moneda.dibujar(this.entorno);
		}
		if (escudo != null) {
			if (escudo.itemsGravedad(this.plataforma) == false) {
				escudo.gravedad();
			}
			this.escudo.dibujar(this.entorno);
		}
/////////////////// FIN DE ITEMS////////////////////////////////////////////
		/////////////////// DINOSAURIOS////////////////////////////////////////////
		// ESTO INDICA CUANDO HAY SOBREPOSICION ENTRE DOS ENEMIGOS Y SE DESPLAZAN PARA
		/////////////////// LADOS DIFERENTES.
		this.crearenemigos.sobrePosicionMob();
		misilOFF(); // Si impacta el misil con una bala de la princesa, este desaparece.
		muereMob(); // Dinosaurios mueren
		for (int i = 0; i < enemigos.length; i++) {
			if (enemigos[i] != null) {
				if (enemigos[i].getY() > 1000) {
					enemigos[i] = null;
				}
			}
		}

		this.crearenemigos.moverMobs(); // MUEVE LOS ENEMIGOS.
		// Disparos dinosaurios
		tiempo = tiempo + 1;
		for (int i = 0; i < enemigos.length; i++) {
			if (enemigos[i] != null) {
				if (tiempo % 110 == 0 && (this.enemigos[i].getX() % 3 == 0 || this.enemigos[i].getY() % 3 == 0)) {
					dispararMob(this.enemigos[i]);
				}
			}
		}
		// GRAVEDAD DE LOS ENEMIGOS.
		for (int l = 0; l < this.enemigos.length; l++) {
			if (enemigos[l] != null) {
				if (mobPisa(this.enemigos[l])) {
					this.enemigos[l].enemigoHayGravedad(false);
					this.enemigos[l].enemigoEstaPisando(true);
				} else {
					this.enemigos[l].enemigoHayGravedad(true);
					this.enemigos[l].enemigoGravedad(this.enemigos[l].getGravity());
					this.enemigos[l].enemigoEstaPisando(false);
				}
			}
		}
		actualizarMisil();
		dibujarMisil();
//////////////////////FIN DE LOS DINOSAURIOS//////////////////////////////////
		////////////////////// DINOSAURO COLOSAL//////////////////////////////////
		if (colosalMuere() == true) {
			if (dinosauriocolosal.getVidas() == 0) {
				this.dinosauriocolosal = null;// INDICA QUE CUANDO MUERE EL COLOSAL DESAPAREZCA
			}
		}
		if (this.dinosauriocolosal != null) {
			if (this.dinosauriocolosal.colosalPisa(this.plataforma)) {// INDICA LA GRAVEDAD Y SI PISA O NO UN BLOQUE.
				this.dinosauriocolosal.enemigoHayGravedad(false);
				this.dinosauriocolosal.enemigoEstaPisando(true);
			} else {
				this.dinosauriocolosal.enemigoHayGravedad(true);
				this.dinosauriocolosal.enemigoGravedad(dinosauriocolosal.getGravity());
				this.dinosauriocolosal.enemigoEstaPisando(false);
			}
			if (princesa.getY() > 100 && this.dinosauriocolosal.getDisparo() == false) {
				boladefuego = this.dinosauriocolosal.disparar();
				this.dinosauriocolosal.setDisparo(true);
			}
			// DIBUJA EL DISPARO Y SE MUEVE.
			if (dinosauriocolosal.getDisparo() == true && this.boladefuego.getY() < 800) {
				this.boladefuego.dibujar(entorno);
				this.boladefuego.mover();
			} else {
				boladefuego = null;
				this.dinosauriocolosal.setDisparo(false);
			}

			this.dinosauriocolosal.dibujar(this.entorno);// DIBUJA AL COLOSAL.
			this.dinosauriocolosal.mover();
			this.dinosauriocolosal.rebotar();
		}
//////////////////////FIN DEL DINOSAURO COLOSAL/////////////////////////////////////////////////	
		////////////////////// PRINCESA/////////////////////////////////////////////////
		dibujarVidas(); // DIBUJA LAS VIDAS
		if (contTiempo > 0) {
			contTiempo--;// ESTO LO QUE HACE ES QUE CUANDO LE SAQUE UNA VIDA SE HAGA INMORTAL POR UNOS
							// SEG.
		}
		if (muerePrincesa() && contTiempo == 0) {
			princesa.disminuirVida();
			this.princesa.dibujar(this.entorno);
			contTiempo = 250;
			System.out.println("vidas princesa: " + princesa.getVidas());

		}

		// LA PRINCESA MUERE
		if (princesa.getVidas() > 0) {
			// MOVER DERECHA
			if (this.entorno.estaPresionada(this.entorno.TECLA_DERECHA)
					&& this.princesa.getX() + this.princesa.getAncho() / 2 < this.entorno.ancho()) {
				this.princesa.moverDerecha();
				this.princesa.setDireccion(1);
			}
			// MOVER IZQUIERDA
			if (this.entorno.estaPresionada(this.entorno.TECLA_IZQUIERDA)
					&& this.princesa.getX() - this.princesa.getAncho() / 2 > 0) {
				this.princesa.moverIzquierda();
				this.princesa.setDireccion(-1);
			}
			// SALTA
			if (this.entorno.sePresiono(this.entorno.TECLA_ARRIBA) | (this.entorno.sePresiono(Teclas.TECLA_X))
					&& this.princesa.getX() + this.princesa.getAlto() / 2 < this.entorno.ancho()) {
				if (this.princesa.getPisando() == true) {
					this.princesa.salta(true);
					Herramientas.play("musica/salto.wav");
				}
			}
			// PASAR LOS BLOQUES AL LIMITESALTO.
			this.princesa.limiteSalto(this.plataforma.getBloques());
			// LA PRICESA SE AGACHA.
			if (this.entorno.estaPresionada(this.entorno.TECLA_ABAJO) && !this.princesa.getAgacharse()) {
				this.princesa.agacharse();
			} else if (!this.entorno.estaPresionada(this.entorno.TECLA_ABAJO) && this.princesa.getAgacharse()) {
				// SI ESTA NO ESTA APRETADA LA TECLA DE ABAJO DE LA PRINCESA ELLA SE LEVANTA.

				this.princesa.levantarse();
			}
			// DISPARO DE LA PRINCESA.
			if (this.entorno.sePresiono(Teclas.TECLA_C)) {// DISPARA CON LA "C"
				if (this.princesa.getDisparo() == false) {
					bolita = princesa.disparar();
					this.princesa.setDisparo(true);
					Herramientas.play("musica/disparo.wav");
				}
			}
			// DIBUJA EL DISPARO Y SE MUEVE.
			if (princesa.getDisparo() == true && this.bolita.getX() < 800 && this.bolita.getX() > 0) {
				this.bolita.dibujar(this.entorno);
				this.bolita.mover();
			} else {
				bolita = null;
				princesa.setDisparo(false);
			}

			// GRAVEDAD DE LA PRINCESA.
			if (princesaPisaX(this.princesa)) {
				this.princesa.hayGravedad(false);
				this.princesa.gravedad(this.princesa.getGravity());
				this.princesa.estaPisando(true);

			} else {
				this.princesa.hayGravedad(true);
				this.princesa.gravedad(this.princesa.getGravity());
				this.princesa.estaPisando(false);
			}
			// BLOQUE QUE SE PUEDE ROMPER CUADNO COLISIONA.
			Bloque[] bloques = plataforma.getBloques();
			for (int i = 0; i < bloques.length; i++) {
				if (bloques[i] != null && bloques[i].getColor().equals(Color.RED)
						&& this.princesa.getPisando() == false) {
					if (this.princesa.getY() + this.princesa.getAlto() / 2 >= bloques[i].getY()
							- bloques[i].getAlto() / 2
							&& this.princesa.getY() - 5 - this.princesa.getAlto() / 2 <= bloques[i].getY()
									+ bloques[i].getAlto() / 2
							&& this.princesa.getX() + this.princesa.getAncho() / 2 >= bloques[i].getX()
									- bloques[i].getAncho() / 2
							&& this.princesa.getX() - this.princesa.getAncho() / 2 <= bloques[i].getX()
									+ bloques[i].getAncho() / 2) {
						plataforma.romperBloque(i);
					}
				}
			}

			this.princesa.dibujar(this.entorno);// DIBUJA PRINCESA.
			this.crearenemigos.dibujar(this.entorno); // DIBUJA ENEMIGOS.
			if (colosalMuere() == false) {
				this.dinosauriocolosal.dibujar(this.entorno);// DIBUJA EL COLOSAL.
			}

			if (princesa.getY() < 500) {
				magma.sube(true);// INDICA QUE EMPIEZA A SUBIR EL MAGMA.
			}
			this.magma.dibujar(this.entorno);// DIBUJA EL MAGMA.

		}
		// INDICA QUE DIBUJA EN PANTALLA LOS ENEMIGOS ASESINADOS Y LOS PUNTOS QUE VAS
		// TIENEDO.
		entorno.cambiarFont("Arial", 20, Color.WHITE);
		entorno.escribirTexto("Puntos: " + this.puntosPorKill, 20, 540);
		entorno.escribirTexto("Asesinados: " + this.cantidadEnemigosEliminados, 20, 500);
	}

	// AFUERA DEL TICK!!!!!

	// DIBUJA LAS INSTRUCCIONES DE JUEGO.
	private void dibujarInstrucciones() {
		entorno.dibujarRectangulo(0, 0, 800 * 2, 1200, 0, Color.black);
		entorno.cambiarFont("Arial", 20, Color.white);
		entorno.escribirTexto("Instrucciones del Juego:", 70, 60);
		entorno.escribirTexto("1. La princesa Elizabeth se movera al preciona las teclas derecha e izquierda.", 70,
				100);
		entorno.escribirTexto("2. Usa la x o tecla hacia arriba para saltar.", 70, 140);
		entorno.escribirTexto("3. Usa la flecha abajo para agacharte y esquivar los disparos.", 70, 180);
		entorno.escribirTexto("4. Presiona 'C' para disparar.", 70, 220);
		entorno.escribirTexto("5. Evita a los enemigos y sus disparos.", 70, 260);
		entorno.escribirTexto("6. Recoge los objetos para conseguir beneficios.", 70, 300);
		entorno.escribirTexto("7. !CUIDADO! con el magma que surge rapidamente, ¡no lo toque! o perderas.", 70, 340);
		entorno.escribirTexto("8. sube hasta la cima y mata al Dinosaurio Colosal", 70, 380);
		entorno.escribirTexto("9. ¡Gana la partida!", 70, 420);
		entorno.escribirTexto("10.¡Buena suerte y diviértete!", 70, 460);
		entorno.escribirTexto("Presiona Enter para volver al menú", 70, 500);
	}

	// DIBUJA LA PANTALLA GAME OVER.
	public void dibujarGameOver() {

		entorno.cambiarFont("Arial", 150, Color.RED);
		entorno.escribirTexto("Game Over", entorno.ancho() / 2 - 400, entorno.alto() / 2 - 150);
		entorno.cambiarFont("Arial", 40, Color.WHITE);
		entorno.escribirTexto("Enemigos Asesinados: " + cantidadEnemigosEliminados, entorno.ancho() / 2 - 200,
				entorno.alto() / 2 - 50);
		entorno.escribirTexto("Puntos Totales: " + puntosPorKill, entorno.ancho() / 2 - 200, entorno.alto() / 2);
		entorno.cambiarFont("Arial", 40, Color.WHITE);
		entorno.escribirTexto("Presiona Enter para volver al menú", entorno.ancho() / 2 - 300,
				entorno.alto() / 2 + 100);

	}

	// DIBUJA LA PANTALLA DE GANADOR.
	public void dibujarGameWin() {
		entorno.cambiarFont("Arial", 150, Color.GREEN);
		entorno.escribirTexto("¡Ganaste!", entorno.ancho() / 2 - 300, entorno.alto() / 2 - 150);
		entorno.cambiarFont("Arial", 40, Color.WHITE);
		entorno.escribirTexto("Enemigos Asesinados: " + cantidadEnemigosEliminados, entorno.ancho() / 2 - 200,
				entorno.alto() / 2 - 50);
		entorno.escribirTexto("Puntos Totales: " + puntosPorKill, entorno.ancho() / 2 - 200, entorno.alto() / 2);
		entorno.cambiarFont("Arial", 40, Color.WHITE);
		entorno.escribirTexto("Presiona Enter para volver al menú", entorno.ancho() / 2 - 300,
				entorno.alto() / 2 + 100);

	}

///////////////////// ENEMIGO ///////////////////////////////////
	// ENEMGIO MUERE
	private void muereMob() {
		for (int l = 0; l < this.enemigos.length; l++) {
			if (bolita != null) {
				if (enemigos[l] != null) {
					double distancia = Math.sqrt(Math.pow(bolita.getX() - enemigos[l].getX(), 2)
							+ Math.pow(bolita.getY() - enemigos[l].getY(), 2));
					if (distancia <= bolita.getRadio() + 5) {
						enemigos[l] = null;
						puntosPorKill += 2;
						cantidadEnemigosEliminados += 1;
						bolita = null;
						princesa.setDisparo(false);
					}
				}
			}
		}
	}

	// SI LA BALA DE LA PRINCESA IMPACTA UN MISIL , ESTE SE ROMPE
	private boolean misilOFF() {
		if (this.bolita != null) {
			for (Misil misil : this.misiles) {
				double distancia = Math
						.sqrt(Math.pow(bolita.getX() - misil.getX(), 2) + Math.pow(bolita.getY() - misil.getY(), 2));
				if (distancia <= bolita.getRadio() + 5) {
					this.misiles.remove(misil);
					return true;
				}
			}
		}
		return false;
	}

	// DISPARON DE LOS ENEMIGOS.
	private void dispararMob(Enemigo enemigo) {
		double velocidadX = 5 * enemigo.getDireccion();
		Misil nuevaMisil = new Misil(enemigo.getX(), enemigo.getY(), velocidadX);
		this.misiles.add(nuevaMisil);
	}

	private void actualizarMisil() {
		for (Misil misil : this.misiles) {
			misil.mover();
		}
		
		//ELIMINAR BOLISTAS QUE SE SALEN DE LA PANTALLA.
		this.misiles.removeIf(misil -> misil.getX() > this.entorno.ancho() || misil.getX() < 0);
	}
	//DIBUAJ EL MISIL.
	private void dibujarMisil() {
		for (Misil misil : this.misiles) {
			misil.dibujar(this.entorno);
		}
	}
	//ENEMIGO TOCA EL PISO
	private boolean mobPisa(Enemigo enemigo) {
		for (Bloque bloque : this.plataforma.getBloques()) {
			if (bloque != null) {
				boolean colisionX = bloque.getX() - bloque.getAncho() / 2 < enemigo.getX() + enemigo.getAncho() / 2
						&& bloque.getX() + bloque.getAncho() / 2 > enemigo.getX() - enemigo.getAncho() / 2;
				boolean colisionY = enemigo.getY() + enemigo.getAlto() / 2 >= bloque.getY() - bloque.getAlto() / 2
						&& enemigo.getY() + enemigo.getAlto() / 2 <= bloque.getY() + bloque.getAlto() / 2;
				if (colisionX && colisionY) {
					return true; // EL ENEMIGO ESTA PISANDO ESTE BLOQUE 
				}
			}
		}
		return false; //EL ENEMIGO NO ESTABA PISANDO ESTA BLOQUE
	}
	//COLOSAL MUERE
	private boolean colosalMuere() {
		if (this.bolita != null) {
			if (this.dinosauriocolosal != null) {
				if (boladefuego != null) {
					double distancia = Math.sqrt(Math.pow(bolita.getX() - dinosauriocolosal.getX(), 2)
							+ Math.pow(bolita.getY() - dinosauriocolosal.getY(), 2));
					double bolitaTocaBolaDeFuego = Math.sqrt(Math.pow(boladefuego.getX() - bolita.getX(), 2)
							+ Math.pow(boladefuego.getY() - bolita.getY(), 2));
						if (distancia <= (dinosauriocolosal.getAncho())) {
							bolita = null;
							princesa.setDisparo(false);
							dinosauriocolosal.setVidas(dinosauriocolosal.getVidas() - 1);
							puntosPorKill = puntosPorKill + 10;
							return true;
						}	
					if (bolitaTocaBolaDeFuego <= boladefuego.getRadio()) { 	//SI EL DISPARO DE LA PRINCESA COLISONA CON
																			//LA BOLA DE FUEGO, EL DISPARO DE LA
																			// PRINCESA DESAPARECE
						bolita = null;
						princesa.setDisparo(false);
					}
				}
			}
		}
		return false;
	}

	////////////////////////////////// princesa/////////////////////////////////
	// PRINCESA MUERE
	// COLISION CON BLOQUES.
	private boolean princesaPisaX(Princesa princesa) {
		for (Bloque bloque : this.plataforma.getBloques()) {
			if (bloque != null) {
				boolean colisionX = bloque.getX() - bloque.getAncho() / 2 < princesa.getX() + princesa.getAncho() / 2
						&& bloque.getX() + bloque.getAncho() / 2 > princesa.getX() - princesa.getAncho() / 2;
				boolean colisionY = princesa.getY() + princesa.getAlto() / 2 >= bloque.getY() - 3 - bloque.getAlto() / 2
						&& princesa.getY() + princesa.getAlto() / 2 <= bloque.getY() - 3 + bloque.getAlto() / 2;
				if (colisionX && colisionY) {
					return true; // LA PRINCESA ESTA PINSANDO EL BLOQUE 
				}
			}
		}
		return false; // LA PRINCESA NO ESTA PINSANDO EL BLOQUE
	}

	private boolean muerePrincesa() {
		for (Misil misil : misiles) {
			for (int i = 0; i < enemigos.length; i++) {
				if (this.dinosauriocolosal != null) {
					if (enemigos[i] != null) {
						double distanciaMisil = Math
								.sqrt(Math.pow(misil.getX() - princesa.getX(), 2) + Math.pow(misil.getY() - 
										princesa.getY(), 2));// SI IMPACTA UN MISIL LA PRINCESA MUERE.
						
						double distanciaMob = Math
								.sqrt(Math.pow(enemigos[i].getX() - princesa.getX(), 2) + Math.pow(enemigos[i].getY() - 
										princesa.getY(), 2));//SI TOCA A UN ENEMIGO LA PRINCESA MUERE
						double distanciaColosal = Math.sqrt(Math.pow(dinosauriocolosal.getX() - princesa.getX(), 2)
								+ Math.pow(dinosauriocolosal.getY() - // Si toca al enemigo princesa muere
										princesa.getY(), 2));

						if (boladefuego != null) {
							double distanciaBola = Math.sqrt(
									Math.pow(boladefuego.getX() - princesa.getX(), 2) + Math.pow(boladefuego.getY() - // Si
																														// toca
																														// la
																														// bola
																														// la
																														// princesa
																														// muere
											princesa.getY(), 2));
							if (distanciaMisil <= misil.getRadio() || distanciaMob <= enemigos[i].getAncho()
									|| distanciaBola <= boladefuego.getRadio()
									|| distanciaColosal <= dinosauriocolosal.getAncho()) { // Si princesa toca bola de
																							// fuego muere

								return true;
							}
							if ((princesa.getY() + princesa.getAncho()) > magma.getYdelPuntoMasAlto()) { // Princesa
																											// toca el
																											// magma
								contTiempo = 0;
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}
	//AJUSTA LA CAMA PARA QUE ENFOQUE BIEN A LA PRINCESA CUANDO SUBI MAS ARRIBA DEL BORDE.
	private void ajustarY(int y) {
		posicionCamaraY = y;
		for (Bloque bloque : this.plataforma.getBloques()) {
			if (bloque != null) {
				bloque.setY(bloque.getY() - posicionCamaraY);
			}
		}
		for (Misil misil : this.misiles) {
			misil.setY(misil.getY() - posicionCamaraY);
		}
		if (boladefuego != null) {
			this.boladefuego.setY(boladefuego.getY() - posicionCamaraY);
			if (bolita != null) {
				this.bolita.setY(bolita.getY() - posicionCamaraY);
			}
			this.magma.setY(magma.getY() - posicionCamaraY * 2);
		}
	}
	//DIBUJA LAS VIDAS.
	private void dibujarVidas() {
		Image imagenCorazon = Herramientas.cargarImagen("imagenDeCorazon/corazon.png");
		int numVidas = princesa.getVidas();
		for (int i = 0; i < numVidas; i++) {
			int x = 20 + (i * 40); // Espaciado de 30 píxeles entre círculos
			int y = 40; // Coordenada Y fija para todos los círculos
			entorno.dibujarCirculo(x, y, 10, Color.RED); // Dibuja el círculo
			entorno.dibujarImagen(imagenCorazon, x, y, 0);

		}
	}

	// Determinamos que hace cada item
	private void itemUtil() {
		if (manzana != null) {
			double distanciaManzana = Math.sqrt(
					Math.pow(manzana.getX() - princesa.getX(), 2) + Math.pow(manzana.getY() - princesa.getY(), 2));
			if (distanciaManzana <= manzana.getAncho() + 2) {
				manzana = null;
				entorno.cambiarFont("Arial", 13, Color.WHITE);
				entorno.escribirTexto("+1 VIDA", princesa.getX() + 20, princesa.getY() - 15);
				princesa.setVidas(princesa.getVidas() + 1);

			}
		}
		if (moneda != null) {
			double distanciaMoneda = Math
					.sqrt(Math.pow(moneda.getX() - princesa.getX(), 2) + Math.pow(moneda.getY() - princesa.getY(), 2));
			if (distanciaMoneda <= moneda.getAncho() + 2) {
				moneda = null;
				entorno.cambiarFont("Arial", 13, Color.WHITE);
				entorno.escribirTexto("+10", princesa.getX() + 20, princesa.getY() - 15);
				puntosPorKill = puntosPorKill + 100;
			}
		}
		if (escudo != null) {
			double distanciaEscudo = Math
					.sqrt(Math.pow(escudo.getX() - princesa.getX(), 2) + Math.pow(escudo.getY() - princesa.getY(), 2));
			if (distanciaEscudo <= escudo.getAncho() + 2) {
				escudo = null;
				contTiempo = 200;
			}
		}
	}
	//REINICIA EL JUEGO.
	public void reiniciarJuego() {

		this.princesa = new Princesa(entorno.ancho() / 2, entorno.alto() - 40, 20, 38, 0, false, true, false, -10, 1,
				this.entorno);
		this.plataforma = new Plataforma(15, 24);
		this.magma = new Magma(400, 1196);
		this.crearenemigos = new CrearEnemigos(2, 23);
		this.enemigos = this.crearenemigos.getEnemigos();
		this.dinosauriocolosal = new DinosaurioColosal(entorno.ancho() / 2, -950, 90, 140, 1, Color.YELLOW, false);
		this.misiles = new ArrayList<>();
		this.tiempo = 0;
		this.cantidadEnemigosEliminados = 0;
		this.puntosPorKill = 0;
		this.posicionCamaraY = 0;
		this.contTiempo = 0;
		this.gameOver = false;
		this.gameWin = false;
		this.enMenu = true;
		this.jugando = false;
		this.enInstrucciones = false;

	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
}
