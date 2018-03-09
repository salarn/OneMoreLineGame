import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class Zone {
	Ellipse2D.Double zone;
	Color c;
	public int radius(){
		return 15;
	}
	public Zone(){
		zone = new Ellipse2D.Double(200-15,-100, 2*this.radius(), 2*this.radius());
		c = new Color( (int)(Math.random()*55.0+200), (int)(Math.random()*55.0+200), (int)(Math.random()*55.0+200),(int)255);
	}
	public void setLocation(double x,double y){
		zone.x = x;
		zone.y = y;
	}
	public void changeY(double y){
		zone.y += y;
	}
	public double getX(){
		return zone.x;
	}
	public double getY(){
		return zone.y;
	}
	public int centerX(){
		return (int)zone.x+this.radius();
	}
	public int centerY(){
		return (int)zone.y+this.radius();
	}

	public void render( Graphics2D g){
		g.setColor(c);
		g.fill(zone);
	}
}
