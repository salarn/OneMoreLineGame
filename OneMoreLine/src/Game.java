import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class Game extends JPanel {
	
	public int end = 0;
	public double speed = 6;
	public double score = 0;
	public double mxscore = 0;
	private double dirX = 0 , dirY = -speed;
	private Ball ball;
	private ArrayList< Zone > zone = new ArrayList< Zone >();
	int cnt = 4;
	int connect = -1;
	int nearestZone;
	double teta;double change;
	boolean press = false;
	double r;
	JLabel label = new JLabel();
	JLabel label2 = new JLabel("Game Over" , SwingConstants.CENTER);
	//Font fFont = new Font("Serif", , 14);
	public Game() {
		ball = new Ball();
		Timer orbit = new Timer(20,new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				teta += change;
				int oldX = ball.centerX();
				int oldY = ball.centerY();
				int newX = (int) ((double)r*Math.cos(teta)+(int)zone.get(nearestZone).centerX());
				int newY = (int) ((double)-r*Math.sin(teta)+(int)zone.get(nearestZone).centerY());
				dirX = newX-oldX;
				dirY = newY-oldY;
			}
			
		});
		setFocusable(true);
		addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if(press == true || end == 1 || e.getKeyCode() != KeyEvent.VK_SPACE)
					return;
				press = true;
				double mntool = 10000.0;
				int kodam = -1;
				for(int i = 0;i < zone.size(); i++)
					if(ball.dis(zone.get(i)) < mntool && zone.get(i).getY() >= 0.0){
						mntool = ball.dis(zone.get(i));
						kodam = i;
					}
				nearestZone = kodam;
				if(nearestZone != -1){
					connect = nearestZone;					
					double deltaY = ball.centerY()-zone.get(nearestZone).centerY();
					double deltaX = ball.centerX()-zone.get(nearestZone).centerX();
					if(deltaX == 0){
						if(deltaY >= 0)
							teta = 3.0/2.0*Math.PI;
						else
							teta = Math.PI/2.0;
					}
					else{
						double tanTeta = deltaY/deltaX;
						if(tanTeta <= 0){
							if(deltaX <= 0)
								teta = Math.PI - Math.atan(tanTeta) ;
							else
								teta = -Math.atan(tanTeta);
						}
						else{
							if(deltaX <= 0)
								teta = Math.PI - Math.atan(tanTeta);
							else
								teta = -Math.atan(tanTeta);
						}
					}
					double Ax = deltaX;
					double Ay = deltaY;
					r = ball.dis(zone.get(nearestZone));
					if(Ax*dirY - dirX*Ay >= 0)
						change = -(speed/r);
					else
						change = +(speed/r);
					orbit.start();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				press = false;
				connect = -1;
				orbit.stop();
			}
			
		});
	}
	public boolean check(){
		for(int i = 0;i < zone.size(); i++)
			if( ball.dis(zone.get(i)) <= ball.radius()+zone.get(i).radius() )
				return false;
		if(connect == -1 && (ball.getX() <= 200+7 || ball.getX() >= 600-ball.radius()*2-7))
			return false;
		return true;
	}
	public String Parse(int x){
		String ans1 = "";
		while(x != 0){
			char c = (char) ('0'+ (x%10));
			ans1 += c;
			x /= 10;
		}
		String ans2 ="";
		for(int i = ans1.length()-1;i >=0 ; i--)
			ans2 += ans1.charAt(i);
		return ans2;
	}
	
	@Override
	protected void paintComponent(Graphics g2) {
		super.paintComponent(g2);
		Graphics2D g = (Graphics2D) g2;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		/////Back Ground
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.gray);
		g.fillRect(0, 0, 200, 600);
		g.fillRect(600, 0, 200, 600);
		g.setColor(Color.orange);
		g.setStroke(new BasicStroke(11));
		g.drawLine(200, 0, 200, 600);
		g.drawLine(600-1,0,600-1,600);
		if(connect != -1){
			g.setColor(Color.green);
			g.setStroke(new BasicStroke(3));
			g.drawLine((int)ball.centerX(), (int)ball.centerY(), (int)zone.get(connect).centerX(),(int)zone.get(connect).centerY());
		}
		label.setText("Score : " + Parse((int)mxscore/100));
		label.setBounds(650,0,200,100);
		this.add(label);
		label.setBackground(Color.black);
		label.setForeground(Color.black);
		label.setFont(new Font("Serif", Font.PLAIN, 30));
		////Loss
		if(end == 1){
			label2.setBounds(200,200,400,200);
			label2.setBackground(Color.black);
			label2.setForeground(Color.lightGray);
			label2.setFont(new Font("Serif", Font.PLAIN, 60));
			this.add(label2);
		}
		//label.setOpaque(true);
		/////balls
		ball.render(g);
			for(int i = 0;i < zone.size(); i++)
			zone.get(i).render(g);
	}
	public void updateGame(){
		//zone.down(speed);
		if(zone.size() == 0 || zone.get(zone.size()-1).getY() > 60){
			Zone z = new Zone();
			z.setLocation(Math.random()*(400-2*z.radius()-30) + 212, -50);
			zone.add(z);
		}
		if(check() == false)
			end = 1;
		if(end != 1){
			ball.changeX((int)dirX);
			for(int i = 0;i < zone.size(); i++)
				zone.get(i).changeY((int)-dirY);
		}
		score += -dirY;
		if((int)score/100 > (int)mxscore/100)
			speed += 0.1;
		mxscore = Math.max(mxscore, score);
		repaint();
	}
	
}