package Jogo.Modelo;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Fase extends JPanel implements ActionListener {
	private Image fundo;
	private Image newgame;
	private Ator ator;
	private Timer timer;
	private List<Enemy1> enemy1;
    private boolean emJogo = false;
	private int pontuacao;
	private int qtdDeInimigos = 50;
	private double aproveitamento;
	
			
	public Fase() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		setFocusable(true);
		setDoubleBuffered(true);
		
		this.definirImagens();
		
		ator = new Ator();
		ator.load();
		
		addKeyListener(new TecladoAdapter());
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				if (emJogo == false) {
					emJogo = true;
					try {
						tocaTrilhaSonora();
					} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
						e.printStackTrace();
					}
					repaint();
				}
			}
		});
				
		timer = new Timer(5, this);
		timer.start();
		
		inicializaInimigos();
	}
	
	public void definirImagens() {
		int nmFundo = new Random().nextInt(6);
		ImageIcon referencia = new ImageIcon("res\\fundo" + nmFundo + ".png");
		this.fundo = referencia.getImage();
		
		ImageIcon newgame = new ImageIcon("res\\newgame.png");
		this.newgame = newgame.getImage();
	}
	
	public void inicializaInimigos() {
		//int qtdDeInimigos = 50;
		enemy1 = new ArrayList<Enemy1>();
		for (int i = 0; i < qtdDeInimigos; i++) {
			int x = (int) (Math.random() * 700 + 30);
			int y = (int) (Math.random() * (-10000) + 100);
			enemy1.add(new Enemy1(x, y));
		}
	}
	
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		if (emJogo == true) {
			g2d.drawImage(fundo, 0, 0, null);
			g2d.drawImage(ator.getImagem(), ator.getX(), ator.getY(), this);

			this.exibirPontuacao(g2d);
			
			for (int o = 0; o < enemy1.size(); o++) {
				Enemy1 in = enemy1.get(o);
				in.load();
				g2d.drawImage(in.getImagem(), in.getX(), in.getY(), this);
			}
		} else {
			g2d.drawImage(newgame, 0, 0, null);
//			g2d.drawString("CLIQUE PARA INICIAR", 50, 340);
//			ImageIcon fimJogo = new ImageIcon("res\\gameover.png");
//			g2d.drawImage(fimJogo.getImage(), 0, 0, null);
		}

		g.dispose();
	}
	
	public void exibirPontuacao(Graphics2D g2d) {
		Font fonte = new Font("Comic Sans MS", Font.BOLD, 30);
		g2d.setFont(fonte);
		g2d.drawString("Pontuação: " + this.pontuacao, 5, 28);
		g2d.drawString("Aproveitamento: " + dfGerman.format(aproveitamento) + "%", 5, 60);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (emJogo == true) {
			ator.update();
			
			///// Erro está abaixo
			for (int o = 0; o < enemy1.size(); o++) {
				Enemy1 in = enemy1.get(o);
				
				if (in.isVisivel()) {
					in.update();
				} else {
					enemy1.remove(o);
				}
			}
			
			try {
				checarColisoes();
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
				e1.printStackTrace();
			}
			repaint();	
		}
	}
	
	public void checarColisoes() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		Rectangle formaAtor = ator.getBounds();
		Rectangle formaEnemy1;
		
		for (int i = 0; i < enemy1.size(); i++) {
			Enemy1 tempEnemy1 = enemy1.get(i);
			formaEnemy1 = tempEnemy1.getBounds();
			
			if (formaAtor.intersects(formaEnemy1)) {
				ator.setVisivel(true);
				tempEnemy1.setVisivel(false);
				emJogo = true;
				this.pontuacao += 1;
				this.aproveitamento = (((double) pontuacao / (double) this.qtdDeInimigos) * (double) 100);
				tocaSomPonto();
			}
		}
	}
	
	private class TecladoAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			ator.keyPressed(e);
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
			ator.keyRelease(e);
		}
	}
	
	public void tocaTrilhaSonora() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		File arquivo = new File("sons\\FranticLevel.wav");
		AudioInputStream audioStream = AudioSystem.getAudioInputStream(arquivo);
		Clip clip = AudioSystem.getClip();
		clip.open(audioStream);
		
		clip.start();
		clip.loop(clip.LOOP_CONTINUOUSLY);
	}
	
	public void tocaSomPonto() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		File arquivo = new File("sons\\ponto.wav");
		AudioInputStream audioStream = AudioSystem.getAudioInputStream(arquivo);
		Clip clip = AudioSystem.getClip();
		clip.open(audioStream);
		
		clip.start();
	}
	
	DecimalFormat dfGerman = new DecimalFormat("#,###.##",
			new DecimalFormatSymbols(Locale.GERMAN));
}
