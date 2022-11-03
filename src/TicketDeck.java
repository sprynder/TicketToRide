import java.util.ArrayList;
import java.util.Collections;

public class TicketDeck {
	private ArrayList<TicketCard> tickets;
	private ArrayList<TicketCard> topFour;

	public TicketDeck() {
		tickets = new ArrayList<TicketCard>();
		topFour = new ArrayList<TicketCard>();
	}

	public ArrayList<TicketCard> getTickets() {
		return tickets;
	}

	public void setTickets(TicketCard a) {
		tickets.add(a);
	}

	public ArrayList<TicketCard> getTopFour() {
		return topFour;
	}

	public void fillTopFour() {
		/*
		 * int b; if (tickets.size() < 4) b = tickets.size(); else b = 4;
		 */
		shuffle();
		int i = 0;
		while (i < 3) {
			if (!tickets.isEmpty()) {
				topFour.add(tickets.remove(0));
				i++;
			} else {
				topFour.add(null);
				i++;
			}
		}
	}
	public void fillTopFive() {
		/*
		 * int b; if (tickets.size() < 4) b = tickets.size(); else b = 4;
		 */
		shuffle();
		int i = 0;
		while (i < 5) {
			if (!tickets.isEmpty()) {
				topFour.add(tickets.remove(0));
				i++;
			} else {
				topFour.add(null);
				i++;
			}
		}
	}

	public void addToBottom(ArrayList<TicketCard> a) {
		for (TicketCard item : a) {
			tickets.add(item);
		}
		//fillTopFour();
	}

	public void shuffle() {
		Collections.shuffle(tickets);
	}

	public void setTickets(ArrayList<TicketCard> tickets) {
		this.tickets = tickets;
	}

	public void setTopFour(ArrayList<TicketCard> topFour) {
		this.topFour = topFour;
	}
}
