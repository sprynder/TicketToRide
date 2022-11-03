import javax.swing.*;
import java.awt.*;
import java.util.*;
public class RoadsGraphics {
	protected Road r;
	protected Polygon a;
	public RoadsGraphics(Road road,int[] x, int[] y){
		r = road;
		a = new Polygon(x,y,x.length);
	}
	public void Paint(Graphics g)
	{
		getColor(r.getColor());
		g.drawPolygon(a);
	}
	public Road getR() {
		return r;
	}
	public void setR(Road r) {
		this.r = r;
	}
	public Polygon getA() {
		return a;
	}
	public void setA(Polygon a) {
		this.a = a;
	}
	public void getColor(String col)
	{
	}
}
