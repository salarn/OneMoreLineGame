import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class Ball {

	Ellipse2D.Double ball;
	public int radius(){
		return 15;
	}
	public Ball() { 
		ball = new Ellipse2D.Double(400-this.radius() , 600-200, 2*this.radius(), 2*this.radius());
	}
	public double getX(){
		return ball.x;
	}
	public double getY(){
		return ball.y;
	}
	public void setLocation ( double x,  double y ) {
		ball.x = x;
		ball.y = y;
	}
	public void changeX(double x){
		ball.x += x;
		//ball.x = Math.min(ball.x, 600-30-3.5);
		//ball.x = Math.max(ball.x, 200);
	}
	public void changeY(double x){
		ball.y += x;
		//ball.x = Math.min(ball.x, 600-30-3.5);
		//ball.x = Math.max(ball.x, 200);
	}
	public int centerX(){
		return (int)ball.x+this.radius();
	}
	public int centerY(){
		return (int)ball.y+this.radius();
	}
	public double dis(Zone z){
		double disX = this.centerX() - z.centerX();
		double disY = this.centerY() - z.centerY();
		return Math.sqrt( disX*disX + disY*disY );
	}
	public void render ( Graphics2D g ){
		g.setColor(Color.RED);
		g.fill(ball);
	}
}
