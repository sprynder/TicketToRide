import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class TicketCardGraphics {
	private Image black;
	private Rectangle sel;
	private int outline;
	private Rectangle con;
	private Image[] tics;

	public int getOutline() {
		return outline;
	}

	public void setOutline(int outline) {
		this.outline = outline;
	}

	public Image getBlack() {
		return black;
	}

	public void setBlack(Image black) {
		this.black = black;
	}

	public Rectangle getSel() {
		return sel;
	}

	public void setSel(Rectangle sel) {
		this.sel = sel;
	}

	public ArrayList<Rectangle> getRects() {
		return rects;
	}

	public void setRects(ArrayList<Rectangle> rects) {
		this.rects = rects;
	}

	public Rectangle getCon() {
		return con;
	}

	public void setCon(Rectangle con) {
		this.con = con;
	}

	private ArrayList<Rectangle> rects;
	private ArrayList<Rectangle> rects2;


	public TicketCardGraphics() {
		tics = new Image[2];
		try {
			black = ImageIO.read(ClassLoader.getSystemResource("Files/ticket_card_back.jpg"));
			tics[0]=ImageIO.read(ClassLoader.getSystemResource("Files/60207782_2151274458496116_7182466975807832064_n.jpg"));
			tics[1]=ImageIO.read(ClassLoader.getSystemResource("Files/60339637_2262638610496862_6828586323729711104_n.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		sel = new Rectangle(1680, 175 + 120 * 5, 175, 200);
		//g.drawRect(1680, 15 +180 * 5, 220, 50);
		con = new Rectangle(1680, 15 + 180 * 5, 220, 50);
		rects = new ArrayList<Rectangle>();
		for (int i = 0; i < 3; i++) {
			int x = 1680;
			int y = 240 + 175 * (i - 1);
			int width = 220;
			int length = 140;
			rects.add(new Rectangle(x, y, width, length));
		}
		rects2 = new ArrayList<Rectangle>();
		/*
		 * g.drawRect(1680, 15 + 180 * (i), 175, 140);
						g.drawImage(tics[0],1680, 15 + 180 * (i), 220, 140,null);
						g.drawImage(tics[1],1680, 15 + 180 * (i), 220, 140,null);
						g.setColor(Color.black);
						g.drawString(
								game.getTicketDeck().getTopFour().get(i).getStart().getName() + " - "
										+ game.getTicketDeck().getTopFour().get(i).getEnd().getName()+" " + game.getTicketDeck().getTopFour().get(i).getValue(),
								1700, 45+ 180* (i));
		 * 
		 */
		for (int i = 0; i < 5; i++) {
			int x = 1680;
			int y = 15 + 180 * (i );
			int width = 220;
			int length = 140;
			rects2.add(new Rectangle(x, y, width, length));
		}
	}

	public ArrayList<Rectangle> getRects2() {
		return rects2;
	}

	public void setRects2(ArrayList<Rectangle> rects2) {
		this.rects2 = rects2;
	}

	public void paint(Graphics g, GameState game) {
		if (game.getState() == 0 && !game.getTicketDeck().getTickets().isEmpty()) {
			g.drawImage(black, 1690, 175 + 120 * 5, 175, 200, null);
		}
		if (game.getState() == 2) {
			g.setColor(Color.decode("#8B0000"));
			g.fillRect(1680, 5, 195, 1000);
			if(game.getAdd().size()<1)
				g.setColor(Color.red);
			else
				g.setColor(Color.green);
			g.fillRect(1680, 15 +180 * 5, 220, 50);
			g.setColor(Color.BLACK);
			g.drawString("Confirm", 1690, 15+180*5+20);
			for (int i = 0; i < game.getTicketDeck().getTopFour().size(); i++) {
				if (game.getTicketDeck().getTopFour().get(i) != null) {
					if (game.getAdd().contains(game.getTicketDeck().getTopFour().get(i)))
						g.setColor(Color.green);
					else
						g.setColor(Color.red);
					if (game.getTicketDeck().getTopFour().get(i) != null) {
						g.fillRect(1670, 5 + 180 * (i), 240, 160);
						g.drawImage(tics[0],1680, 15 + 180 * (i), 220, 140,null);
						g.drawImage(tics[1],1680, 15 + 180 * (i), 220, 140,null);
						g.setColor(Color.black);
						g.drawString(
								game.getTicketDeck().getTopFour().get(i).getStart().getName() + " - "
										+ game.getTicketDeck().getTopFour().get(i).getEnd().getName(),
								1715, 40+ 180* (i));
						//g.setFont(new Font("TimesRoman", Font.PLAIN, 14));
						g.drawString(""+game.getTicketDeck().getTopFour().get(i).getValue(), 1858, 125+180*i);
					}
				}
			}
		}
	}
	public void paintS(Graphics g, GameState game) {
		if (game.getState() == -1) {
			g.setColor(Color.decode("#8B0000"));
			g.fillRect(1680, 5, 195, 1000);
			
			if(game.getAdd().size()<3)
				g.setColor(Color.red);
			else
				g.setColor(Color.green);
			g.fillRect(1680, 15 +180 * 5, 220, 50);
			g.setColor(Color.BLACK);
			g.setFont(new Font("TimesRoman", Font.BOLD, 14));
			g.drawString("Confirm", 1765, 15+180*5+25);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 14));
			for (int i = 0; i < game.getTicketDeck().getTopFour().size(); i++) {
				if (game.getTicketDeck().getTopFour().get(i) != null) {
					if (game.getAdd().contains(game.getTicketDeck().getTopFour().get(i)))
						g.setColor(Color.green);
					else
						g.setColor(Color.red);
					if (game.getTicketDeck().getTopFour().get(i) != null) {
						g.fillRect(1670, 5 + 180 * (i), 240, 160);
						g.drawImage(tics[0],1680, 15 + 180 * (i), 220, 140,null);
						g.drawImage(tics[1],1680, 15 + 180 * (i), 220, 140,null);
						g.setColor(Color.black);
						g.drawString(
								game.getTicketDeck().getTopFour().get(i).getStart().getName() + " - "
										+ game.getTicketDeck().getTopFour().get(i).getEnd().getName(),
								1715, 40+ 180* (i));
						g.drawString(""+game.getTicketDeck().getTopFour().get(i).getValue(), 1858, 125+180*i);
					}
				}
			}
		}
	}
}
