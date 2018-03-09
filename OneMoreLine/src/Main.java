import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.Timer;

public class Main extends JApplet implements ActionListener {

	private Game game;
	private Timer timer;
	@Override
	public void init(){
		super.init();
		game = new Game();
		timer = new Timer(20,this);
		setLayout(new BorderLayout());
		add(game, BorderLayout.CENTER );
		setSize(800,600);
	}

	@Override
	public void start() {
		super.start();
		timer.start();
	}

	@Override
	public void stop() {
		super.stop();
		timer.stop();
		System.out.println("Game Over");
	}

	@Override
	public void destroy() {
		super.destroy();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(game.end == 1)
			this.stop();
		game.updateGame();
	}
}
