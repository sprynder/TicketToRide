import java.util.ArrayList;
import java.util.HashMap;

public class Player implements Comparable {
	private String color;
	private int trains;
	private int points;
	private GameBoard pMap;
	private ArrayList<TicketCard> tickets;
	private ArrayList<TicketCard> completedTickets;
	private int cCount;

	public int getcCount() {
		return cCount;
	}

	public void setcCount(int cCount) {
		this.cCount = cCount;
	}

	public boolean getComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}

	private int tCount;
	private int ttCount;
	private ArrayList<TrainCard> sCards;
	private HashMap<String, ArrayList<TrainCard>> cards;
	private BinaryCardTree trainCards;
	private int Maxlength;
	private int place;
	private boolean complete;

	public Player(String col) {
		complete = false;
		;
		ttCount = 4;
		place = 1;
		color = col;
		points = 0;
		trains = 5;
		Maxlength = 0;
		tickets = new ArrayList<TicketCard>();
		completedTickets = new ArrayList<TicketCard>();
		sCards = new ArrayList<TrainCard>();
		cards = new HashMap<String, ArrayList<TrainCard>>();
		cards.put("Black", new ArrayList<TrainCard>());
		cards.put("Blue", new ArrayList<TrainCard>());
		cards.put("Red", new ArrayList<TrainCard>());
		cards.put("Green", new ArrayList<TrainCard>());
		cards.put("Pink", new ArrayList<TrainCard>());
		cards.put("Yellow", new ArrayList<TrainCard>());
		cards.put("White", new ArrayList<TrainCard>());
		cards.put("Orange", new ArrayList<TrainCard>());
		cards.put("Any", new ArrayList<TrainCard>());
		trainCards = new BinaryCardTree();
		sCards = new ArrayList<TrainCard>();
		pMap = new GameBoard();
		tCount = 0;
	}

	public int getTtCount() {
		return ttCount;
	}

	public void setTtCount(int ttCount) {
		this.ttCount = ttCount;
	}

	public int gettCount() {
		return tCount;
	}

	public void settCount(int tCount) {
		this.tCount = tCount;
	}

	public int getPlace() {
		return place;
	}

	public void setPlace(int place) {
		this.place = place;
	}

	public Player(String color, int trains, int points, GameBoard pMap, ArrayList<TicketCard> tickets,
			ArrayList<TicketCard> completedTickets, ArrayList<TrainCard> sCards,
			HashMap<String, ArrayList<TrainCard>> count, BinaryCardTree trainCards, int length) {
		super();
		this.color = color;
		this.trains = trains;
		this.points = points;
		this.pMap = pMap;
		this.tickets = tickets;
		this.completedTickets = completedTickets;
		this.sCards = sCards;
		this.cards = count;
		this.trainCards = trainCards;
		this.Maxlength = length;
	}

	public void longestPath() {
		for (City blah : pMap.getMap().keySet()) {
			for (City poop : pMap.getMap().keySet()) {
				for (Road a : pMap.getMap().get(blah)) {
					int length = 0;
					ArrayList<Road> track = new ArrayList<Road>();
					length = travelPath(a, poop, track, length, blah);
					Maxlength = Math.max(length, Maxlength);
				}
			}
		}
		complete = false;
	}

	public void checkContracts() {
		// System.out.println("start");
		for (int i = 0; i < tickets.size(); i++/* TicketCard p : tickets */) {
			if (pMap.getMap().get(tickets.get(i).getStart()) != null) {
				// System.out.println(tickets.get(i).getStart().getName());
				for (Road a : pMap.getMap().get(tickets.get(i).getStart())) {
					int length = -1;
					ArrayList<Road> track = new ArrayList<Road>();
					// track.add(a);
					length = travelPath(a, tickets.get(i).getEnd(), track, length, tickets.get(i).getStart());
					// System.out.println(complete);
					if (complete == true) {
						tickets.get(i).setCompleted(true);
						complete = false;
						continue;
					}
				}
			}
		}
	}

	// travel both roads and their cities(check if previous road cities ==
	// current)
	public int travelPath(Road start, City dest, ArrayList<Road> traveled, int length, City sta) {
		traveled.add(start);
		// System.out.println(traveled);
		length += start.getLength();
		City go = null;
		if (start.getStart().getName().equals(sta.getName()))
			go = start.getEnd();
		else if (start.getEnd().getName().equals(sta.getName()))
			go = start.getStart();
		if (go.getName().equals(dest.getName())) {
			// System.out.println(traveled);
			int total = 0;
			for (Road a : traveled) {
				total += a.getLength();
			}
			traveled.remove(start);
			Maxlength = Math.max(total, Maxlength);
			length = total;
			complete = true;
			for (Road a : pMap.getMap().get(go)) {
				if (!traveled.contains(a)) {
					length = travelPath(a, dest, traveled, length, go);
				}
			}
			return length;
		}
		if (!go.getName().equals(dest.getName())) {
			if (pMap.getMap().get(go).isEmpty() || traveled.containsAll(pMap.getMap().get(go))) {
				traveled.remove(start);
				length = -1;
				return length;
			} else
				for (Road a : pMap.getMap().get(go)) {
					if (!traveled.contains(a)) {
						length = travelPath(a, dest, traveled, length, go);
					}
				}
		}
		return length;

	}

	public int getMaxlength() {
		return Maxlength;
	}

	public void setMaxlength(int maxlength) {
		Maxlength = maxlength;
	}

	public void addToHandTickets(TicketCard t) {
		tickets.add(t);
	}

	public void addToHandTrains(TrainCard t) {
		trainCards.add(new BinaryCountNode(t.getColor(), 1, null, null));
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getTrains() {
		return trains;
	}

	public void setTrains(int trains) {
		this.trains = trains;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public GameBoard getpMap() {
		return pMap;
	}

	public void setpMap(GameBoard pMap) {
		this.pMap = pMap;
	}

	public ArrayList<TicketCard> getTickets() {
		return tickets;
	}

	public void setTickets(ArrayList<TicketCard> tickets) {
		this.tickets = tickets;
	}

	public ArrayList<TicketCard> getCompletedTickets() {
		return completedTickets;
	}

	public void setCompletedTickets(ArrayList<TicketCard> completedTickets) {
		this.completedTickets = completedTickets;
	}

	public ArrayList<TrainCard> getsCards() {
		return sCards;
	}

	public void setsCards(ArrayList<TrainCard> sCards) {
		this.sCards = sCards;
	}

	public HashMap<String, ArrayList<TrainCard>> getCards() {
		return cards;
	}

	public void setCards(HashMap<String, ArrayList<TrainCard>> cards) {
		this.cards = cards;
	}

	public BinaryCardTree getTrainCards() {
		return trainCards;
	}

	public void setTrainCards(BinaryCardTree trainCards) {
		this.trainCards = trainCards;
	}

	public int getLength() {
		return Maxlength;
	}

	public void setLength(int length) {
		this.Maxlength = length;
	}

	@Override
	public int compareTo(Object arg0) {
		Player comp = (Player) arg0;
		if (this.getPoints() > comp.getPoints())
			return 1;
		else if (this.getPoints() < comp.getPoints())
			return -1;
		else {
			if (this.getcCount() > comp.getcCount())
				return 1;
			else if (this.getcCount() > comp.getcCount())
				return -1;
			else {
				if (this.getMaxlength() > comp.getMaxlength())
					return 1;
				else if (this.getMaxlength() < comp.getLength())
					return -1;
				else
					return 0;
			}
		}
	}

}
