package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Princesa {
	private double x;
	private double y;
	private int ancho;
	private int alto;
	private double velocidadY; 
	private boolean gravity;
	private boolean pisando;
	private boolean enElAire;
	private double fuerzaSalto;
	private boolean agacharse;
	private int altoOriginal;
	private Bolita bolita;
	private int direccion; 
	private boolean disparo;
	private int vidas; 
	private Image imagenIzquierda;
	private Image imagenDerecha;
	private Image imagenIzquierdaAbajo;
	private Image imagenDerechaAbajo;
	Color transparente = new Color(0, 0, 0, 0);
	private Entorno entorno;
	
	
	public Princesa(int x, int y, int ancho, int alto, double velocidadY, boolean gravity,boolean pisando,boolean enElAire, double fuerzaSalto,int direccion,Entorno entorno) {	
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.velocidadY= velocidadY;
		this.gravity= gravity;
		this.pisando= pisando;
		this.enElAire= enElAire;
		this.fuerzaSalto= fuerzaSalto;
		this.altoOriginal= 38;
		this.direccion=direccion;
		this.disparo=false;
		this.vidas=3;
		this.imagenIzquierda = Herramientas.cargarImagen("imagenPrincesa/princesaIzquierda.png");
		this.imagenDerecha = Herramientas.cargarImagen("imagenPrincesa/princesaDerecha.png") ;
		this.imagenIzquierdaAbajo= Herramientas.cargarImagen("imagenPrincesa/princesaAgachadaIzquierda.png") ;
		this.imagenDerechaAbajo = Herramientas.cargarImagen("imagenPrincesa/princesaAgachadaDerecha.png") ;
		this.entorno= entorno;						
		
	}
	
	public void moverDerecha()
	{
		this.x = this.x + 3;
	}
	
	public void moverIzquierda()
	{
		this.x = this.x - 3;
	}
	
	   public void agacharse() {
	        this.agacharse = true;
	        this.alto = this.altoOriginal / 2-5; //REDUCE LA ALTURA AL ALGACHARSE
	        this.y += this.altoOriginal / 4; 
	    }

	    public void levantarse() {
	        this.agacharse = false;
	        this.alto = this.altoOriginal; //RESTAURA LA POSICION ORIGINAL 
	        this.y -= this.altoOriginal / 4;//PROGRESIVAMENTE
	    }
	public void gravedad(boolean x)  //INDICAMOS EL ESTADO DE LA GRAVEDAD
	{
		double grav=3;
		double y=this.y;
		if(x==true) { //SI ES TRUE SE ACTIVA
			this.y = (int) (y + grav);
		}
		}
	  public void salta(boolean x) { //INDICAMOS SI PUEDE SALTAR
	        if (x==true) {
	            this.velocidadY = this.fuerzaSalto;
	            this.enElAire = true; //Avisa a el constructor "limiteSalto" que esta en el aire.
	        }
	    }
	  
	  public boolean bloqueEncima(Bloque[] bloques) {
		    for (Bloque bloque : bloques) {
		       if (bloque != null) {//comprueba que el bloque no es nulo
		            boolean colisionX = this.x - this.ancho / 2 < bloque.getX() + bloque.getAncho() / 2 &&
		                                this.x + this.ancho / 2 > bloque.getX() - bloque.getAncho() / 2;
		            boolean colisionY = this.y - this.alto / 2 <= bloque.getY() + bloque.getAlto() / 2 &&
		                                this.y + this.alto / 2 >= bloque.getY() - bloque.getAlto() / 2;
		           
		            if (colisionX && colisionY | this.y<40) {
		                
		            	return true; // Hay un bloque justo encima de la princesa
		            }          
		       }
		    }
		    return false; // No hay bloque encima
		}
	  
	  public void limiteSalto(Bloque[] bloques) {
		    double tiemp = 0;
		    if (enElAire) {
		        double grav = -0.5;
		        this.velocidadY += grav;
		        this.y += this.velocidadY-0.5;
		        tiemp = tiemp + velocidadY;

		        // Verificar si hay un bloque encima
		        if (bloqueEncima(bloques)) {
		            this.velocidadY = 0; // Detener el salto
		            this.enElAire = false;// Comenzar a caer
		        }

		        // Tope hasta donde debe saltar
		        if (tiemp <= -15) {
		            this.velocidadY = 0;
		            this.enElAire = false;
		        }
		    }
		}
	public Bolita disparar() {
		int direccionBala = 5* direccion;
		bolita= new Bolita(x,y,direccionBala);
		return bolita;
	}
	
	public void estaPisando(boolean x) {
		if (x==true) {
			this.pisando=true;}
		else {
			this.pisando=false;
		}
	}

	//Modifica el estado ed "gravedad"
	public void hayGravedad(boolean x) {
		if (x==true) {
			this.gravity=true;}
		else {
			this.gravity=false;
		}
	}
	
	public void disminuirVida() {
        if (vidas > 0) {
            vidas--;
            
        }
	 }
	public void dibujar(Entorno entorno) {
	    entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, transparente);
	    

	    if (this.entorno.estaPresionada(this.entorno.TECLA_DERECHA)) {
	        if (this.entorno.estaPresionada(this.entorno.TECLA_ABAJO)) {
	            entorno.dibujarImagen(imagenDerechaAbajo, this.x, this.y, 0);
	        } else {
	            entorno.dibujarImagen(imagenDerecha, this.x, this.y, 0);
	        }
	    }else if (this.entorno.estaPresionada(this.entorno.TECLA_ABAJO)) {
	            entorno.dibujarImagen(imagenIzquierdaAbajo, this.x, this.y, 0);
	        }
	    else if (this.entorno.estaPresionada(this.entorno.TECLA_IZQUIERDA)) {
	        if (this.entorno.estaPresionada(this.entorno.TECLA_ABAJO)) {
	            entorno.dibujarImagen(imagenIzquierdaAbajo, this.x, this.y, 0);
	        } else {
	            entorno.dibujarImagen(imagenIzquierda, this.x, this.y, 0);
	        }
	    } else {
	        // Dibuja la imagen por defecto cuando no se presiona ninguna tecla de dirección
	        entorno.dibujarImagen(imagenIzquierda, this.x, this.y, 0);
	    }
	}

	

	public int getAltoOriginal() {
		return altoOriginal;
	}
	
	
	public double getX() {
		return x;
	}


	public double getY() {
		return y;
	}


	public int getAncho() {
		return ancho;
	}


	public boolean getAgacharse() {
        return agacharse;
    }
	
	public int getAlto() {
		return alto;
	}
	public double getVelocidadY() {
		return velocidadY;
	}
	
	public boolean getGravity() {
		return gravity;
	}
	
	public boolean getPisando() {
		return pisando;
	}
	public boolean getEnElAire( ) {
		return enElAire;
	}
	
	public void setDireccion(int d) {
		this.direccion=d;
	}
	public int getDireccion( ) {
		return direccion;
	}
	public boolean getDisparo( ) {
		return disparo;
	}
	public void setDisparo( boolean x) {
		disparo=x;
	}
	public void setY( double d) {
		y=d;
	}
	public void setVidas( int vidas) {
		this.vidas=vidas;
	}
	
	public int getVidas( ) {
		return vidas;
	}
	public void resetPosicion() {
		this.x=400;
		 
	}
}

