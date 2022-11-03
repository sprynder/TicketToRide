import javax.swing.*;
import java.awt.*;
import java.util.*;
public class DoubleRoadsGraphics {
	protected Polygon left;
	protected Polygon right;
	protected DoubleRoad dRoad;
	public DoubleRoadsGraphics(int[] x, int[] y, int[] xr, int[] yr, DoubleRoad d) {
		if(x!=null)
		left = new Polygon(x,y,x.length);
		if(xr!=null)
		right = new Polygon(xr,yr,xr.length);
		dRoad = d;
	}
	public Polygon getLeft() {
		return left;
	}
	public void setLeft(Polygon left) {
		this.left = left;
	}
	public DoubleRoad getdRoad() {
		return dRoad;
	}
	public Polygon getRight() {
		return right;
	}
	public void setRight(Polygon right) {
		this.right = right;
	}
	public DoubleRoad getR() {
		return dRoad;
	}
	public void setdRoad(DoubleRoad dRoad) {
		this.dRoad = dRoad;
	}

}
