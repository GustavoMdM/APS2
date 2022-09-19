package Jogo;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import Jogo.Modelo.Fase;

public class Container extends JFrame {
	//janela do jogo
	public Container() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		add(new Fase());
		setTitle("Acerte o lixo");
		setSize(750, 680); //(largura, altura);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}
	
	public static void main (String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		new Container();
	}
}