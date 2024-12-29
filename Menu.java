package juego;
import entorno.Entorno;
import java.awt.Color;
public class Menu {
	 private int ancho;
	    private int alto;
	    private String[] opciones;
	    private int seleccion;
	    Color transparente = new Color(0, 0, 0, 0);
	


			public Menu(int ancho, int alto) {
	        this.ancho = ancho;
	        this.alto = alto;
	        this.opciones = new String[]{"Jugar", "Instrucciones", "Salir"};
	        this.seleccion = 0;
	    }

	    public void dibujar(Entorno entorno) {
	        entorno.dibujarRectangulo(ancho / 2, alto / 2, 400, 300, 0, transparente);
	        entorno.cambiarFont("Arial", 50, Color.WHITE);
	        for (int i = 0; i < opciones.length; i++) {
	            if (i == seleccion) {
	                entorno.cambiarFont("Arial", 50, Color.YELLOW);
	            } else {
	                entorno.cambiarFont("Arial", 50, Color.WHITE);
	            }
	            entorno.escribirTexto(opciones[i], ancho / 2 - 100, alto / 2 - 100 + i * 100);
	        }
	    }
	   
	    
	    
	   
	    
	    
	   
	  
	    	
	    
	   
	    
	    
	    public void moverSeleccionArriba() {
	        if (seleccion > 0) {
	            seleccion--;
	        }
	    }

	    public void moverSeleccionAbajo() {
	        if (seleccion < opciones.length - 1) {
	            seleccion++;
	        }
	    }

	    public String getOpcionSeleccionada() {
	        return opciones[seleccion];
	    }
	}


