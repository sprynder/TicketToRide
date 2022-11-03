import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.awt.Polygon;
import java.awt.Rectangle;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.*;
import java.io.*;

public class GraphicsPanel extends JPanel implements MouseListener, MouseMotionListener {
	private int a = 0;
	private ArrayList<RoadsGraphics> roads;
	private ArrayList<DoubleRoadsGraphics> dRoads;
	private GameState game;
	private RoadsGraphics hR;
	private DoubleRoadsGraphics dHR;
	private int lr;
	private PlayerGraphics pg;
	private TrainCardGraphics decko;
	private HashMap<Integer, String> color;
	private TicketCardGraphics tg;
	private endGraphics eg;

	public GraphicsPanel() throws IOException {
		setBackground(Color.decode("#8B0000"));
		setPreferredSize(new Dimension(1920, 1080));
		addMouseListener(this);
		addMouseMotionListener(this);
		roads = new ArrayList<RoadsGraphics>();
		dRoads = new ArrayList<DoubleRoadsGraphics>();
		game = new GameState();
		makeRoads();
		lr = -1;
		pg = new PlayerGraphics();
		decko = new TrainCardGraphics();
		eg = new endGraphics();
		// game.turn(0, "a", 1, null, null, "a", 1);
		color = new HashMap<Integer, String>();
		tg = new TicketCardGraphics();
		color.put(0, "Black");
		color.put(1, "Blue");
		color.put(2, "Green");
		color.put(3, "Orange");
		color.put(4, "Pink");
		color.put(5, "Red");
		color.put(6, "White");
		color.put(7, "Yellow");
		color.put(8, "Any");
	}

	public void makeRoads() throws IOException {
	    ClassLoader classLoader = getClass().getClassLoader();
	    File file = new File(classLoader.getResource("Files/gRoads.txt").getFile());
		Scanner sc = new Scanner(file);
		while (sc.hasNextLine()) {
			String name = sc.nextLine();
			String num = sc.nextLine();
			String x = sc.nextLine();
			String y = sc.nextLine();
			String blank = sc.nextLine();
			String names[] = name.split("-");
			x = x.substring(3, x.length() - 1);
			y = y.substring(3, y.length() - 1);
			String xx[] = x.split(", ");
			String yy[] = y.split(", ");
			int[] xxx = new int[xx.length];
			int[] yyy = new int[yy.length];
			for (int i = 0; i < xx.length; i++) {
				xxx[i] = Integer.parseInt(xx[i]);
			}
			for (int i = 0; i < yy.length; i++) {
				yyy[i] = Integer.parseInt(yy[i]) - 14;
			}
			if (names.length == 3) {
				DoubleRoad a = (DoubleRoad) game.findRoad(names[0] + "-" + names[1]);
				if (names[2].equalsIgnoreCase("right")) {
					if (findDRoad(a.getName()) == null) {
						dRoads.add(new DoubleRoadsGraphics(null, null, xxx, yyy, a));
					} else
						findDRoad(a.getName()).setRight(new Polygon(xxx, yyy, xxx.length));
				} else {
					if (findDRoad(a.getName()) == null) {
						dRoads.add(new DoubleRoadsGraphics(xxx, yyy, null, null, a));
					} else
						findDRoad(a.getName()).setLeft(new Polygon(xxx, yyy, xxx.length));

				}
			} else {
				roads.add(new RoadsGraphics(game.findRoad(name), xxx, yyy));
			}
		}
	}

	public DoubleRoadsGraphics findDRoad(String name) {
		for (int i = 0; i < dRoads.size(); i++) {
			if (dRoads.get(i).getR().getName().equalsIgnoreCase(name))
				return dRoads.get(i);
			else if (dRoads.get(i).getR().getName2().equalsIgnoreCase(name))
				return dRoads.get(i);
		}
		return null;
	}

	public void paint(Graphics g) {
		Image img = null;
		try {
			// img = ImageIO.read(new File("TTR Diagram(Choose Train
			// Cards).jpeg"));
			img = ImageIO.read(ClassLoader.getSystemResource("Files/ttr_board.jpg"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		super.paint(g);
		g.drawImage(img, 272, 5, 1392, 831, null);
		g.setColor(Color.BLACK);
		// roads
		for (RoadsGraphics a : roads) {
			g.setColor(findColor(a.getR().getColor()));
			if (a.equals(hR)) {
				g.setColor(Color.CYAN);
				hR = null;
			}
			if (a.getR().getOwner() == null)
				g.drawPolygon(a.getA());
			else {
				g.setColor(findColor(a.getR().getOwner().getColor()));
				g.fillPolygon(a.getA());
			}
		}
		for (DoubleRoadsGraphics a : dRoads) {
			g.setColor(findColor(a.getdRoad().getColor()));
			if (a.equals(dHR) && lr == 0) {
				g.setColor(Color.cyan);
				dHR = null;
				lr = -1;
			}
			if (a.getR().getOwner() == null)
				g.drawPolygon(a.getLeft());
			else {
				g.setColor(findColor(a.getR().getOwner().getColor()));
				g.fillPolygon(a.getLeft());
			}
			g.setColor(findColor(a.getdRoad().getColorR()));
			if (a.equals(dHR) && lr == 1 && a.getR().getOwnerR() == null) {
				g.setColor(Color.cyan);
				dHR = null;
				lr = -1;
			}
			if (a.getR().getOwnerR() == null)
				g.drawPolygon(a.getRight());
			else {
				g.setColor(findColor(a.getR().getOwnerR().getColor()));
				g.fillPolygon(a.getRight());
			}
		}
		if (game.getState() != -1) {
			decko.paint(g, game.getTrainDeck().getDisplay(), game.getTrainDeck(), game.getActionCounter(),
					game.getTrainDeck().getDeck().isEmpty(), game.getState());
			g.setColor(Color.white);
			if (game.getsRoad() != null)
				g.drawString("Claiming: " + game.getsRoad().getName(), 1693, 14);
			if (game.getdRoad() != null && game.getLr() == 0)
				g.drawString("Claiming: " + game.getdRoad().getName() + " left", 1693, 14);
			if (game.getdRoad() != null && game.getLr() == 1)
				g.drawString("Claiming: " + game.getdRoad().getName() + " right", 1693, 14);
			g.setColor(Color.BLACK);
			tg.paint(g, game);
		} else {
			tg.paintS(g, game);
		}
		int irdk = game.getPlayerCounter() % 4;
		pg.paint(g, game, irdk);
		if (game.AvengersEndgame() == true) {
			g.setColor(Color.white);
			g.drawString("We're in the endgame now", 1680, 47);
		}
		if (game.getState() == 4) {
			eg.paint(g, game);
		}
		// roads
	}

	public Color findColor(String a) {
		if (a.equalsIgnoreCase("Blue"))
			return new Color(30, 144, 255);
		if (a.equals("White"))
			return Color.WHITE;
		if (a.equals("Green"))
			return Color.GREEN;
		if (a.equals("Yellow"))
			return Color.YELLOW;
		if (a.equals("Orange"))
			return Color.ORANGE;
		if (a.equals("Any"))
			return Color.GRAY;
		if (a.equals("Pink"))
			return Color.PINK;
		if (a.equals("Red"))
			return Color.RED;
		return Color.BLACK;
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		int t = e.getX();
		int u = e.getY();
		for (RoadsGraphics a : roads) {
			if (a.getA().contains(t, u)) {
				hR = a;
			}
		}
		for (DoubleRoadsGraphics a : dRoads) {
			if (a.getLeft().contains(t, u)) {
				lr = 0;
				dHR = a;
			}
			if (a.getRight().contains(t, u)) {
				lr = 1;
				dHR = a;
			}
		}
		for (int i = 0; i < decko.getRects().size(); i++) {
			if (decko.getRects().get(i).contains(t, u)) {
				decko.setOutline(i);
			}
		}
		for (int i = 0; i < pg.getRects().length; i++) {
			if (pg.getRects()[i].contains(t, u)) {
				pg.setOutline(i + 1);
			}
		}
		if (game.getState() == 3) {
			if (pg.getCon().contains(t, u)) {
				pg.setOutline(10);
			}
			if (pg.getBack().contains(t, u))
				pg.setOops(1);
		}
		if (game.getState() == 2) {
			for (int i = 0; i < tg.getRects().size(); i++) {
				if (tg.getRects().get(i).contains(t, u))
					tg.setOutline(i);
			}
		}
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int t = e.getX();
		int u = e.getY();
		// System.out.println(t + ", " + u);
		for (RoadsGraphics a : roads) {
			if (a.getA().contains(t, u)) {
				game.turn(3, null, 0, a.getR(), null, null, 0);
			}
		}
		for (DoubleRoadsGraphics a : dRoads) {
			if (a.getLeft().contains(t, u)) {
				game.turn(3, null, 0, null, a.getdRoad(), null, 0);
			} else if (a.getRight().contains(t, u)) {
				game.turn(3, null, 0, null, a.getdRoad(), null, 1);
			}
		}
		for (int i = 0; i < decko.getRects().size(); i++) {
			if (decko.getRects().get(i).contains(t, u)) {
				if (game.getState() == 1 || game.getState() == 0)
					game.turn(1, null, i, null, null, null, 0);
			}
		}
		for (int i = 0; i < pg.getRects().length; i++) {
			if (pg.getRects()[i].contains(t, u) && game.getState() == 3) {
				game.turn(3, null, i, null, null, color.get(i), game.getLr());
				// System.out.println(color.get(i));
			}
			// System.out.println(game.getPlayers()[game.getPlayerCounter()%4].getsCards());
		}
		if (tg.getSel().contains(t, u) && game.getState() == 0) {
			game.turn(2, null, -1, null, null, null, -1);
		}
		if (game.getState() == 2) {
			for (int i = 0; i < tg.getRects().size(); i++) {
				if (tg.getRects().get(i).contains(t, u))
					game.turn(2, null, i, null, null, null, -1);
			}
		}
		if (game.getState() == 2 && tg.getCon().contains(t, u)) {
			game.turn(2, null, 5, null, null, null, -1);

		}

		if (game.getState() == -1) {
			for (int i = 0; i < tg.getRects2().size(); i++) {
				if (tg.getRects2().get(i).contains(t, u))
					game.turn(-1, null, i, null, null, null, -1);
			}
		}
		if (game.getState() == -1 && tg.getCon().contains(t, u)) {
			game.turn(-1, null, 6, null, null, null, -1);

		}

		if (game.getState() == 3) {
			if (pg.getCon().contains(t, u)) {
				game.turn(3, null, 0, null, null, "10", game.getLr());
			}
			if (pg.getBack().contains(t, u)) {
				game.stopTurn();
				game.setState(0);
			}
		}
		if (game.getState() == 4) {
			if (eg.getMove().contains(t, u))
				game.turn(4, null, -1, null, null, null, -1);
		}
		repaint();
		for (int i = 0; i < pg.getUpdown().length; i++) {
			if (pg.getUpdown()[i].contains(t, u)) {
				if (i == 0) {
					pg.settC(pg.gettC() + 1);
				} else {
					pg.settC(pg.gettC() - 1);
				}
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

	}

}
