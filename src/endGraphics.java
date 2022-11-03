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

public class endGraphics {
	private Rectangle move;

	public endGraphics() {
		move = new Rectangle(1680, 5, 220, 989);
	}

	public Rectangle getMove() {
		return move;
	}

	public void setMove(Rectangle move) {
		this.move = move;
	}

	public void paint(Graphics g, GameState game) {

		if (game.getActionCounter() < 5) {
			g.setColor(Color.black);
			g.fillRect(1680, 5, 220, 989);
			g.setColor(Color.white);
			g.fillRect(1690, 15, 200, 969);
			g.setColor(Color.BLACK);
			// g.drawRect(1690, 175 + 120 * 5, 175, 200);
			g.drawString(game.getPlayers()[game.getPlayerCounter() % 4].getColor(), 1695, 100);
			g.drawString("" + "Longest Path: " + game.getPlayers()[game.getPlayerCounter() % 4].getMaxlength(), 1695,
					150);
			if (game.longest().contains(game.getPlayers()[game.getPlayerCounter() % 4])) {
				g.drawString("+10(LR)", 1695+120, 150);
			}
			g.setFont(new Font("TimesRoman", Font.PLAIN, 14));
			g.drawString("" + "Completed Tickets: " + game.getPlayers()[game.getPlayerCounter() % 4].getcCount(), 1695,
					200);
			if (game.getMost().contains(game.getPlayers()[game.getPlayerCounter() % 4])) {
				g.drawString("+15(GT)", 1695+145, 200);
			}
			int o = 0;
			for (int i = 0; i < game.getPlayers()[game.getPlayerCounter() % 4].getTickets().size(); i++) {
				TicketCard blah = game.getPlayers()[game.getPlayerCounter() % 4].getTickets().get(i);
				if (blah.isCompleted()) {
					g.setColor(Color.decode("#007615"));
					g.drawString(blah.getStart() + "-" + blah.getEnd() + " +" + blah.getValue(), 1695, 250 + i * 25);
				} else {
					g.setColor(Color.decode("#970303"));
					g.drawString(blah.getStart() + "-" + blah.getEnd() + " -" + blah.getValue(), 1695, 250 + i * 25);
				}
			}
			// g.drawString("" + " " + game.getPlayers()[game.getPlayerCounter()
			// % 4].getcCount(), 1695, 250);
		} else {
			g.setColor(Color.white);
			g.fillRect(1680, 5, 220, 989);
			g.setColor(Color.BLACK);
			g.drawString("Leaderboard", 1695, 100);
			for (int i = 0; i < game.getPlace().size(); i++) {
				String str = "" + (i + 1) + ": ";
				for (int b = 0; b < game.getPlace().get(i).size(); b++) {
					str += game.getPlace().get(i).get(b).getColor() + "(" + game.getPlace().get(i).get(b).getPoints()
							+ ") ";
					if (game.getMost().contains(game.getPlace().get(i).get(b))) {
						str += "(GT) ";
					}
					if (game.longest().contains(game.getPlace().get(i).get(b))) {
						str += "(LR) ";
					}
					g.setColor(Color.BLACK);
					g.drawString(str, 1695, 200 + i * 200);
				}
			}
		}
	}
}
