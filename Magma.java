package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;
//ESTA CLASE CREA EL MAGMA
public class Magma {
	private int x;
	private double y;
	private int ancho;
	private double alto;
	private Color color;
	private Image imagen;
	
	public Magma(int x, int y) {
		this.x = x;
		this.y = y;
		this.ancho = 950;
		this.alto = 1200;
		this.color = Color.PINK;
		this.imagen=Herramientas.cargarImagen("magma/magma.png");
 
    }

	//EL MAGMA SUBE.
	public void sube(boolean x)  //Sube true
	{
		double velocidad=0.1;
		if(x==true) { //SI ES TRUE SE ACTIVA
			this.y = this.y- velocidad;
		}
		}
	
	public int getYdelPuntoMasAlto() {    //Devuelve la posicion de la parte mas alta del magma
		int punto= (int) (this.y-(this.alto/2));
		return punto;
	}

	public void dibujar(Entorno entorno) {
		entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, this.color);
		
		if(this.imagen!=null) {
			entorno.dibujarImagen(imagen,this.x,this.y,0);
		}
	}

	public int getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public int getAncho() {
		return ancho;
	}

	public double getAlto() {
		return alto;
	}
	public void setAlto(double a) {
		this.alto=a;
	}
	
    public Color getColor() {
        return color;
    }
    public void setY(double y) {
		this.y=(int) y;
	}
    
}



