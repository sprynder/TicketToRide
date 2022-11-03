import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class TrainCardGraphics {
	private Image black, blue, green, orange, purple, red, white, yellow, deck, any;
	private ArrayList<Rectangle> rects;
	private int outline;

	public TrainCardGraphics() {
		try {
			black = ImageIO.read(ClassLoader.getSystemResource("Files/black_train_card.jpg"));
			blue = ImageIO.read(ClassLoader.getSystemResource("Files/blue_train_card.jpg"));
			green = ImageIO.read(ClassLoader.getSystemResource("Files/green_train_card.jpg"));
			orange = ImageIO.read(ClassLoader.getSystemResource("Files/orange_train_card.jpg"));
			purple = ImageIO.read(ClassLoader.getSystemResource("Files/purple_train_card.jpg"));
			red = ImageIO.read(ClassLoader.getSystemResource("Files/red_train_card.jpg"));
			white = ImageIO.read(ClassLoader.getSystemResource("Files/white_train_card.jpg"));
			yellow = ImageIO.read(ClassLoader.getSystemResource("Files/yellow_train_card.jpg"));
			deck = ImageIO.read(ClassLoader.getSystemResource("Files/card_back.jpg"));
			any = ImageIO.read(ClassLoader.getSystemResource("Files/locomotive_train_card.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		rects = new ArrayList<Rectangle>();
		for (int i = 0; i < 6; i++) {
			int x = 1685;
			int y = 170 + 120 * (i - 1);
			int width = 180;
			int length = 113;
			rects.add(new Rectangle(x, y, width, length));
		}
		outline = -1;
	}

	public void paint(Graphics g, ArrayList<TrainCard> trains, TrainDeck decks, int a, boolean p, int b) {
		if (!trains.isEmpty() && outline != -1 && (outline == 5 || trains.get(outline) != null)) {
			if (outline != 5 && a + trains.get(outline).getPoint() >= 3 || b == 3)
				g.setColor(Color.RED);
			else if (outline == 5 || a + trains.get(outline).getPoint() < 3)
				g.setColor(Color.CYAN);
			if (outline != 5)
				g.fillRect(1685, 170 + 120 * (outline - 1), 185, 115);
			else if (!p)
				g.fillRect(1685, 170 + 120 * (outline - 1), 185, 115);
			outline = -1;
		}
		for (int i = 0; i < trains.size(); i++) {
			if (trains.get(i) != null)
				drawImage(trains.get(i).getColor(), i, g);
		}
		if (!decks.getDeck().isEmpty())
			g.drawImage(deck, 1690, 175 + 120 * 4, 175, 108, null);

	}

	public int getOutline() {
		return outline;
	}

	public void setOutline(int outline) {
		this.outline = outline;
	}

	public ArrayList<Rectangle> getRects() {
		return rects;
	}

	public void setRects(ArrayList<Rectangle> rects) {
		this.rects = rects;
	}

	public void drawImage(String col, int num, Graphics g) {
		int x = 1690;
		int y = 175 + 120 * (num - 1);
		int width = 175;
		int length = 108;
		if (col.equalsIgnoreCase("black"))
			g.drawImage(black, x, y, width, length, null);
		if (col.equalsIgnoreCase("blue"))
			g.drawImage(blue, x, y, width, length, null);
		if (col.equalsIgnoreCase("green"))
			g.drawImage(green, x, y, width, length, null);
		if (col.equalsIgnoreCase("orange"))
			g.drawImage(orange, x, y, width, length, null);
		if (col.equalsIgnoreCase("pink"))
			g.drawImage(purple, x, y, width, length, null);
		if (col.equalsIgnoreCase("red"))
			g.drawImage(red, x, y, width, length, null);
		if (col.equalsIgnoreCase("white"))
			g.drawImage(white, x, y, width, length, null);
		if (col.equalsIgnoreCase("yellow"))
			g.drawImage(yellow, x, y, width, length, null);
		if (col.equalsIgnoreCase("any"))
			g.drawImage(any, x, y, width, length, null);
	}
}
