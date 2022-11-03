
import java.awt.*;
import java.io.IOException;

import javax.swing.JFrame;

public class GraphicsFrame extends JFrame {
	private GraphicsPanel gPanel;

	public static void main(String[] args) throws IOException {
		GraphicsFrame thing = new GraphicsFrame("Ticket to Ride");
	}

	public GraphicsFrame(String str) throws IOException {
		super(str);
		getContentPane().add(new GraphicsPanel());
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);	
		setVisible(true);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}