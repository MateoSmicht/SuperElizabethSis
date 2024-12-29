package juego;
import java.awt.Color;
import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;
public class Misil  {
	private double x;
    private double y;
    private double radio;
    private double velocidadX;
    Color transparente = new Color(0, 0, 255, 50);
    private Image imagenIzquierda;
    private Image imagenDerecha;
    
    
    public Misil(double x, double y, double velocidadX) {
        this.x = x;
        this.y = y;
        this.radio = 10;
        this.velocidadX = velocidadX; // Velocidad de la bolita
        this.imagenIzquierda=Herramientas.cargarImagen("imagenTirex/proyectilMob.png");
        this.imagenDerecha=Herramientas.cargarImagen("imagenTirex/proyectilMobDerecha.png");
    }

    
    public void mover() {
 
        this.x += velocidadX;
    }

    public void dibujar(Entorno entorno) {
        entorno.dibujarCirculo(this.x, this.y, this.radio, Color.WHITE);
        if (velocidadX<0)
        {
        	 entorno.dibujarImagen(imagenIzquierda, this.x, this.y,0);
        }
        else {
        	 entorno.dibujarImagen(imagenDerecha, this.x, this.y,0);
        }
    
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getRadio() {
        return radio;
    }
    public void setY(double d) {
		this.y=d;
	}
}  


