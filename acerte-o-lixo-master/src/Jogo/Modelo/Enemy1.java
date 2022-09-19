package Jogo.Modelo;

import java.awt.Image;
import java.awt.Rectangle;
import java.security.SecureRandom;
import java.util.Random;
import javax.swing.ImageIcon;

public class Enemy1 {
	private Image imagem;
	private int x, y;
	private int largura, altura;
	private boolean isVisivel;
	private static int START = 9;
	private static int END = 3;
	private static double VELOCIDADE = 1.25;
//	private static final int LARGURA = 938;
	
	Random rand = new SecureRandom();
	String imagePath = String.format("res\\lixo%d.png", rand.nextInt(Math.abs(START - END) + 1) + END);

	public Enemy1(int x, int y) {
		this.x = x;
		this.y = y;
		isVisivel = true;	
	}
	
	public void load() {
		ImageIcon referencia = new ImageIcon(imagePath);
		imagem = referencia.getImage();
		
		this.largura = imagem.getWidth(null);
		this.altura = imagem.getHeight(null);
	}
	
	public void update() {
		this.y += VELOCIDADE;
//		if (this.x > LARGURA) {
//			isVisivel = false;
//		}
	}
	
	public Rectangle getBounds() {
		return new Rectangle (x, y, largura, altura);
	}

	public boolean isVisivel() {
		return isVisivel;
	}

	public void setVisivel(boolean isVisivel) {
		this.isVisivel = isVisivel;
	}

	public static double getVELOCIDADE() {
		return VELOCIDADE;
	}

	public static void setVELOCIDADE(int vELOCIDADE) {
		VELOCIDADE = vELOCIDADE;
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
