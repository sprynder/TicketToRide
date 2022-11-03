import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;

public class PlayerGraphics {
	private Image black, blue, green, orange, purple, red, white, yellow, deck, any, upArrow, downArrow;
	private Rectangle[] rects;
	private int outline;
	private HashMap<Integer, int[]> map;
	private Rectangle con;
	private Rectangle back;
	private int tC;
	private Rectangle[] updown;
	private Image[] tics;
	private int Oops;
	private HashMap<String, Integer> nums;

	public Image[] getTics() {
		return tics;
	}

	public void setTics(Image[] tics) {
		this.tics = tics;
	}

	public int getOops() {
		return Oops;
	}

	public void setOops(int oops) {
		Oops = oops;
	}

	public int gettC() {
		return tC;
	}

	public void settC(int tC) {
		this.tC = tC;
	}

	public PlayerGraphics() {
		tC = 200;
		tics = new Image[2];
		try {
			black = ImageIO.read(ClassLoader.getSystemResource("Files/black_train_card.jpg"));
			blue = ImageIO.read(ClassLoader.getSystemResource("Files/blue_train_card.jpg"));
			green = ImageIO.read(ClassLoader.getSystemResource("Files/green_train_card.jpg"));
			orange = ImageIO.read(ClassLoader.getSystemResource("Files/orange_train_card.jpg"));
			purple = ImageIO.read(ClassLoader.getSystemResource("Files/purple_train_card.jpg"));
			red = ImageIO.read(ClassLoader.getSystemResource("Files/red_train_card.jpg"));
			white = ImageIO.read(ClassLoader.getSystemResource("Files/white_train_card.jpg"));
			yellow = ImageIO.read(ClassLoader.getSystemResource("Files/yellow_train_card.jpg"));
			// deck = ImageIO.read(new File("card_back.jpg"));
			any = ImageIO.read(ClassLoader.getSystemResource("Files/locomotive_train_card.png"));
			tics[0] = ImageIO.read(ClassLoader.getSystemResource("Files/60207782_2151274458496116_7182466975807832064_n.jpg"));
			tics[1] = ImageIO.read(ClassLoader.getSystemResource("Files/60339637_2262638610496862_6828586323729711104_n.png"));
			upArrow = ImageIO.read(ClassLoader.getSystemResource("Files/1024px-Red_Arrow_Up.svg.png"));
			downArrow = ImageIO.read(ClassLoader.getSystemResource("Files/768px-Red_Arrow_Down.svg.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		con = new Rectangle(1610, 845, 50, 50);
		rects = new Rectangle[9];
		map = new HashMap<Integer, int[]>();
		map.put(1, new int[] { 1, 1 });
		map.put(2, new int[] { 1, 2 });
		map.put(3, new int[] { 1, 3 });
		map.put(4, new int[] { 2, 1 });
		map.put(5, new int[] { 2, 2 });
		map.put(6, new int[] { 2, 3 });
		map.put(7, new int[] { 3, 1 });
		map.put(8, new int[] { 3, 2 });
		map.put(9, new int[] { 3, 3 });
		nums = new HashMap<String, Integer>();
		nums.put("Black", 0);
		nums.put("Blue", 1);
		nums.put("Green", 2);
		nums.put("Orange", 3);
		nums.put("Pink", 4);
		nums.put("Red", 5);
		nums.put("White", 6);
		nums.put("Yellow", 7);
		nums.put("Any", 8);

		outline = -1;
		updown = new Rectangle[2];
		updown[1] = new Rectangle(800, 960, 20, 20);
		updown[0] = new Rectangle(800, 860, 20, 20);
		Oops = 0;
	}

	public void paint(Graphics g, GameState game, int index) {
		HashMap color = new HashMap<Integer, String>();
		back = new Rectangle(1610, 945, 50, 50);

		color.put(0, "Black");
		color.put(1, "Blue");
		color.put(2, "Green");
		color.put(3, "Orange");
		color.put(4, "Pink");
		color.put(5, "Red");
		color.put(6, "White");
		color.put(7, "Yellow");
		color.put(8, "Any");

		int width = 75;
		int length = 40;
		int xCoord = 970;
		int yCoord = 847;
		g.setColor(Color.decode("#D2B48C"));
		g.fillRect(272, 839, 1392, 155);

		drawTickets(g, tC, game);
		g.setColor(Color.BLACK);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
		g.drawString("Player " + game.getPlayers()[game.getPlayerCounter() % 4].getColor(), 300, 880);
		g.drawString("Points: " + game.getPlayers()[game.getPlayerCounter() % 4].getPoints(), 300, 920);
		g.drawString("Trains: " + game.getPlayers()[game.getPlayerCounter() % 4].getTrains(), 300, 960);
		g.setColor(Color.cyan);
		if (outline != -1) {
			if (outline != 10 && outline != 11
					&& game.getPlayers()[game.getPlayerCounter() % 4].getCards().get(color.get(outline - 1)).isEmpty())
				g.setColor(Color.RED);
			else if (outline != 10 && outline != 11
					&& !game.getPlayers()[game.getPlayerCounter() % 4].getCards().get(color.get(outline - 1)).isEmpty())
				g.setColor(Color.CYAN);
			if (outline == 10
			/*
			 * && ((game.getsRoad() != null &&
			 * game.getPlayers()[game.getPlayerCounter() % 4].getsCards()
			 * .size() == game.getsRoad().getLength())) || game.getdRoad() !=
			 * null && game.getPlayers()[game.getPlayerCounter() %
			 * 4].getsCards() .size() == game.getdRoad().getLength()
			 */) {
				g.setColor(Color.red);
				if (game.getsRoad() != null && game.getPlayers()[game.getPlayerCounter() % 4].getsCards().size() == game
						.getsRoad().getLength())
					g.setColor(Color.green);
				if (game.getdRoad() != null && game.getPlayers()[game.getPlayerCounter() % 4].getsCards().size() == game
						.getdRoad().getLength())
					g.setColor(Color.green);
				g.fillRect(1610, 845, 50, 50);
			} else if (outline < 10 && outline >= 0)
				g.fillRect(xCoord - 5 + 246 * ((map.get(outline))[0] - 1),
						yCoord - 5 + 45 * ((map.get(outline))[1] - 1), width + 10, length + 10);
			outline = -1;
		}
		if (Oops == 1) {
			g.setColor(Color.red);
			g.fillRect(1610, 945, 50, 50);
			Oops = 0;
		}

		g.setColor(Color.BLACK);
		// g.drawLine(926, 854, 926, 1013);
		int x = 926;
		int y1 = 854, y2 = 1013;
		/*
		 * for(int i = 0; i < 2; i++) { x += 246; g.drawLine(x, y1,x, y2); }
		 */
		Player[] players = game.getPlayers();
		g.drawImage(black, xCoord, yCoord, width, length, null);
		g.drawImage(blue, xCoord, yCoord + 45, width, length, null);
		g.drawImage(green, xCoord, yCoord + 90, width, length, null);
		rects[0] = new Rectangle(xCoord, yCoord, width, length);
		rects[2] = new Rectangle(xCoord, yCoord + 90, width, length);
		rects[1] = new Rectangle(xCoord, yCoord + 45, width, length);
		xCoord = 1217;
		g.drawImage(orange, xCoord, yCoord, width, length, null);
		g.drawImage(purple, xCoord, yCoord + 45, width, length, null);
		g.drawImage(red, xCoord, yCoord + 90, width, length, null);
		rects[3] = new Rectangle(xCoord, yCoord, width, length);
		rects[4] = new Rectangle(xCoord, yCoord + 45, width, length);
		rects[5] = new Rectangle(xCoord, yCoord + 90, width, length);
		xCoord = 1463;
		g.drawImage(white, xCoord, yCoord, width, length, null);
		g.drawImage(yellow, xCoord, yCoord + 45, width, length, null);
		g.drawImage(any, xCoord, yCoord + 90, width, length, null);
		rects[6] = new Rectangle(xCoord, yCoord, width, length);
		rects[7] = new Rectangle(xCoord, yCoord + 45, width, length);
		rects[8] = new Rectangle(xCoord, yCoord + 90, width, length);
		Player p = game.getPlayers()[index];
		HashMap<String, ArrayList<TrainCard>> cards = p.getCards();
		int numBlack = cards.get("Black").size();
		int numBlue = cards.get("Blue").size();
		int numGreen = cards.get("Green").size();
		int numOrange = cards.get("Orange").size();
		int numPink = cards.get("Pink").size();
		int numRed = cards.get("Red").size();
		int numWhite = cards.get("White").size();
		int numYellow = cards.get("Yellow").size();
		int numAny = cards.get("Any").size();
		g.setColor(Color.BLACK);
		g.setFont(new Font("Serif", Font.PLAIN, 24));
		xCoord = 1075;
		yCoord = 878;
		g.drawString("" + numBlack, xCoord, yCoord);
		g.drawString("" + numBlue, xCoord, yCoord + 45);
		g.drawString("" + numGreen, xCoord, yCoord + 90);
		xCoord = 1332;
		g.drawString("" + numOrange, xCoord, yCoord);
		g.drawString("" + numPink, xCoord, yCoord + 45);
		g.drawString("" + numRed, xCoord, yCoord + 90);
		xCoord = 1568;
		g.drawString("" + numWhite, xCoord, yCoord);
		g.drawString("" + numYellow, xCoord, yCoord + 45);
		g.drawString("" + numAny, xCoord, yCoord + 90);
		String str = null;
		int str1 = 0;
		int an = 0;
		for (TrainCard a : p.getsCards()) {
			if (a.getColor().equals("Any")) {
				an++;
			} else {
				if (str == null) {
					str1++;
					str = a.getColor();
				} else
					str1++;
			}
		}
		xCoord = 970;
		yCoord = 847;
		//System.out.println("" + str1);
		//System.out.println(str);
		if (game.getState() == 3) {
			g.setColor(Color.white);
			if (an > 0)
				g.drawString("" + an, 1568 - 125, 878 + 91);
			if (str1 > 0 && str != null)
				g.drawString("" + str1, (map.get(nums.get(str) + 1)[0] - 1) * 246 + xCoord - 20,
						(map.get(nums.get(str) + 1)[1] - 1) * 45 + yCoord+27);
		}
		g.setColor(Color.black);
		if (game.getState() == 3) {
			g.setFont(new Font("TimesRoman", Font.PLAIN, 14));
			g.drawRect(1610, 845, 50, 50);
			g.drawString("Done", 1620, 870);
			g.drawRect(1610, 945, 50, 50);
			g.drawString("Cancel", 1615, 970);

		}
		drawLeaderBoard(g, game);
	}

	public void drawLeaderBoard(Graphics g, GameState game) {
		for (int i = 0; i < 4; i++) {
			if (i == game.getPlayerCounter() % 4) {
				g.setColor(Color.LIGHT_GRAY);
				g.fillRect(25, 5 + 250 * i, 210, 210);
			}
			setColor(game.getPlayers()[i].getPlace(), g);
			g.fillRect(30, 10 + 250 * i, 200, 200);
			g.setColor(Color.BLACK);
			g.setFont(new Font("Serif", Font.PLAIN, 21));
			g.drawString(game.getPlayers()[i].getColor(), 35, 30 + 250 * i);
			g.drawString("Points: " + game.getPlayers()[i].getPoints(), 35, 60 + 250 * i);
			g.drawString("Trains: " + game.getPlayers()[i].getTrains(), 35, 90 + 250 * i);
			g.drawString("Number of Tickets: " + game.getPlayers()[i].gettCount(), 35, 120 + 250 * i);
			g.setFont(new Font("Serif", Font.PLAIN, 18));
			g.drawString("Number of TrainCards: " + game.getPlayers()[i].getTtCount(), 35, 150 + 250 * i);

		}
	}

	public void setColor(int i, Graphics g) {
		if (i == 1) {
			g.setColor(Color.decode("#FFD700"));
		} else if (i == 2)
			g.setColor(Color.decode("#DCDCDC"));
		else if (i == 3)
			g.setColor(Color.decode("#F4A460"));
		else
			g.setColor(Color.white);
	}

	public HashMap<Integer, int[]> getMap() {
		return map;
	}

	public void drawTickets(Graphics g, int t, GameState game) {
		Image check = null;
		try {
			check = ImageIO.read(ClassLoader.getSystemResource("Files/check-mark-icon-transparent-background-21.jpg"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if (game.getPlayers()[game.getPlayerCounter() % 4].getTickets().size() != 0) {
			g.setColor(Color.black);
			g.drawImage(tics[0], 575, 850, 220, 140, null);
			g.drawImage(tics[1], 575, 850, 220, 140, null);
			if (game.getPlayers()[game.getPlayerCounter() % 4].getTickets()
					.get(t % game.getPlayers()[game.getPlayerCounter() % 4].getTickets().size()).isCompleted()) {
				// g.setColor(Color.green);
				g.drawImage(check, 800, 901, 20, 20, null);
				g.drawString("Completed", 805, 932);
			}
			g.drawString("" + game.getPlayers()[game.getPlayerCounter() % 4].getTickets()
					.get(t % game.getPlayers()[game.getPlayerCounter() % 4].getTickets().size()).getStart().getName()
					+ " - "
					+ game.getPlayers()[game.getPlayerCounter() % 4].getTickets()
							.get(t % game.getPlayers()[game.getPlayerCounter() % 4].getTickets().size()).getEnd()
							.getName(),
					600, 875);
			g.setColor(Color.BLACK);
			g.drawString(
					"" + game.getPlayers()[game.getPlayerCounter() % 4].getTickets()
							.get(t % game.getPlayers()[game.getPlayerCounter() % 4].getTickets().size()).getValue(),
					753, 960);
			g.setColor(Color.WHITE);
			g.fillRect(800, 860, 20, 20);
			g.drawImage(upArrow, 800, 860, 20, 20, null);
			g.fillRect(800, 960, 20, 20);
			g.drawImage(downArrow, 800, 960, 20, 20, null);

		}
	}

	public Rectangle[] getUpdown() {
		return updown;
	}

	public void setUpdown(Rectangle[] updown) {
		this.updown = updown;
	}

	public void setMap(HashMap<Integer, int[]> map) {
		this.map = map;
	}

	public Rectangle getBack() {
		return back;
	}

	public void setBack(Rectangle back) {
		this.back = back;
	}

	public Image getBlack() {
		return black;
	}

	public void setBlack(Image black) {
		this.black = black;
	}

	public Image getBlue() {
		return blue;
	}

	public void setBlue(Image blue) {
		this.blue = blue;
	}

	public Image getGreen() {
		return green;
	}

	public void setGreen(Image green) {
		this.green = green;
	}

	public Image getOrange() {
		return orange;
	}

	public void setOrange(Image orange) {
		this.orange = orange;
	}

	public Image getPurple() {
		return purple;
	}

	public void setPurple(Image purple) {
		this.purple = purple;
	}

	public Image getRed() {
		return red;
	}

	public void setRed(Image red) {
		this.red = red;
	}

	public Image getWhite() {
		return white;
	}

	public void setWhite(Image white) {
		this.white = white;
	}

	public Image getYellow() {
		return yellow;
	}

	public void setYellow(Image yellow) {
		this.yellow = yellow;
	}

	public Image getDeck() {
		return deck;
	}

	public void setDeck(Image deck) {
		this.deck = deck;
	}

	public Image getAny() {
		return any;
	}

	public void setAny(Image any) {
		this.any = any;
	}

	public Rectangle[] getRects() {
		return rects;
	}

	public void setRects(Rectangle[] rects) {
		this.rects = rects;
	}

	public int getOutline() {
		return outline;
	}

	public void setOutline(int outline) {
		this.outline = outline;
	}

	public Rectangle getCon() {
		return con;
	}

	public void setCon(Rectangle con) {
		this.con = con;
	}

}