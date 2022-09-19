package Jogo.Modelo;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

public class Ator {
	private int x, y;
	private int dx, dy;
	private Image imagem;
	private int altura, largura;
	private boolean isVisivel;
	
	public Ator() {
		this.x = 300;
		this.y = 500;
		isVisivel = true;
	}
	
	public void load() {
		ImageIcon referencia = new ImageIcon("res\\lixeira.png");
		imagem = referencia.getImage();
		altura = imagem.getHeight(null);
		largura = imagem.getWidth(null);
	}
	
	public void update() {
		x += dx;
		y += dy;
	}
	
	public Rectangle getBounds() {
		return new Rectangle (x, y, largura, altura);
	}
	
	public void keyPressed(KeyEvent tecla) {
		int codigo = tecla.getKeyCode();
		if ((codigo == KeyEvent.VK_LEFT) && (podeSeMoverEsquerda())) {
			dx = -3;
		} else if ((codigo == KeyEvent.VK_RIGHT) && (podeSeMoverDireita())) {
			dx = 3;
		}
	}	
	
	public void keyRelease(KeyEvent tecla) {
		int codigo = tecla.getKeyCode();
		if (codigo == KeyEvent.VK_LEFT) {
			dx = 0;
		} else if (codigo == KeyEvent.VK_RIGHT) {
			dx = 0;
		}
	}
	
	public boolean podeSeMoverEsquerda() {
		return x >= 0;
	}
	
	public boolean podeSeMoverDireita() {
		return x <= 650;
	}
	
	public boolean isVisivel() { 
		return isVisivel;
	}

	public void setVisivel(boolean isVisivel) {
		this.isVisivel = isVisivel;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Image getImagem() {
		return imagem;
	}
}
