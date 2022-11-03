import java.util.ArrayList;
import java.util.Collections;

public class TrainDeck {
	private ArrayList<TrainCard> deck;
	private ArrayList<TrainCard> display;
	private ArrayList<TrainCard> discard;

	public TrainDeck() {
		deck = new ArrayList<TrainCard>();
		display = new ArrayList<TrainCard>();
		discard = new ArrayList<TrainCard>();
	}

	public ArrayList<TrainCard> getDiscard() {
		return discard;
	}

	public void setDiscard(ArrayList<TrainCard> discard) {
		this.discard = discard;
	}

	public void setDeck(ArrayList<TrainCard> deck) {
		this.deck = deck;
	}

	public void setDisplay(ArrayList<TrainCard> display) {
		this.display = display;
	}

	public ArrayList<TrainCard> getDeck() {
		return deck;
	}

	public void setDeck(TrainCard a) {
		deck.add(a);
	}

	public void resetDeck() {
		display.clear();
		deck.addAll(discard);
		Collections.shuffle(deck);
		discard.clear();
		fillDisplay();
	}

	public ArrayList<TrainCard> getDisplay() {
		return display;
	}

	public void fillDisplay() {
		int i = 0;
			while (i < 5) {
				if (!deck.isEmpty())
					display.add(deck.remove(0));
				else
					display.add(null);
				i++;
			}
		resetDisplay();
	}

	public void resetDisplay() {
		if (checkForTriples() == true) {
			discard.addAll(display);
			display.clear();
			fillDisplay();
		}

	}

	public void refillDisplay(int i) {
		if (deck.isEmpty() == false) {
			display.add(i, deck.remove(0));
		} else {
			display.add(i, null);
		}
		boolean nonNullElemExist= false;
		for (TrainCard s: display) {
		  if (s != null) {
		     nonNullElemExist = true;
		     break;
		  }
		}
		if(!nonNullElemExist){
			display.clear();
			Collections.shuffle(discard);
			resetDeck();}
		resetDisplay();
	}

	public boolean checkForTriples() {
		if (countWildCardsInDisplay() >= 3) {
			return true;
		}
		return false;
	}

	public int countWildCardsInDisplay() {
		int count = 0;
		for (TrainCard item : display) {
			if (item != null && item.getColor().equalsIgnoreCase("any")) {
				count++;
			}
		}
		return count;
	}
}
