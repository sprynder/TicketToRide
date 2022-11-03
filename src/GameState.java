import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.*;

public class GameState {
	private int playerCounter, actionCounter, finishingTurn;
	private TrainDeck trainDeck;
	private TicketDeck ticketDeck;
	private Player[] players;
	private int state;
	private Road sRoad;
	private DoubleRoad dRoad;
	private int lr;
	private ArrayList<TicketCard> add;
	private ArrayList<ArrayList<Player>> place;
	private GameBoard board;
	private ArrayList<Road> roads;
	private ArrayList<City> cities;
	private Map<Integer, Integer> pointValues;
	private String rCol;
	private ArrayList<Player> most;

	public ArrayList<City> getCities() {
		return cities;
	}

	public void setCities(ArrayList<City> cities) {
		this.cities = cities;
	}

	public ArrayList<Road> getRoads() {
		return roads;
	}

	public void setRoads(ArrayList<Road> roads) {
		this.roads = roads;
	}

	public GameState() throws IOException {
		place = new ArrayList<ArrayList<Player>>();
		playerCounter = -1;
		finishingTurn = -4;
		players = new Player[4];
		players[0] = new Player("Blue");
		players[1] = new Player("Green");
		players[2] = new Player("Red");
		players[3] = new Player("Yellow");
		pointValues = new HashMap<Integer, Integer>();
		pointValues.put(1, 1);
		pointValues.put(2, 2);
		pointValues.put(3, 4);
		pointValues.put(4, 7);
		pointValues.put(5, 10);
		pointValues.put(6, 13);
		board = new GameBoard();
		actionCounter = 0;
		roads = new ArrayList<Road>();
		cities = new ArrayList<City>();
		makeCities();
		makeRoads();
		trainDeck = new TrainDeck();
		String[] str = new String[] { "Pink", "White", "Blue", "Yellow", "Orange", "Black", "Red", "Green" };
		for (int i = 0; i < 8; i++) {
			for (int a = 0; a < 12; a++) {
				trainDeck.getDeck().add(new TrainCard(str[i]));
			}
		}
		for (int i = 0; i < 14; i++) {
			trainDeck.getDeck().add(new TrainCard("Any"));
		}
		Collections.shuffle((trainDeck.getDeck()));
		Collections.shuffle((trainDeck.getDeck()));
		ticketDeck = new TicketDeck();
		 ClassLoader classLoader = getClass().getClassLoader();
	        File file = new File(classLoader.getResource("Files/tickets.txt").getFile());
	        Scanner sc = new Scanner(file);
		//Scanner sc = new Scanner(new File("tickets.txt"));
		while (sc.hasNextLine()) {
			String[] strr = sc.nextLine().split("\\|");
			ticketDeck.getTickets()
					.add(new TicketCard(Integer.parseInt(strr[0]), findCities(strr[1]), findCities(strr[2])));
		}
		Collections.shuffle(ticketDeck.getTickets());
		state = -1;
		sRoad = null;
		dRoad = null;
		add = new ArrayList<TicketCard>();
		place = new ArrayList<ArrayList<Player>>();
		// startGame();
		setBoard();
		rCol = "Any";
		turn(0, "a", 1, null, null, "a", 1);
		turn(-1, "a", 1, null, null, "a", 1);
		// System.out.println(ticketDeck.getTickets().size()+"ticket");
	}

	public void startGame() {
		for (Player a : players) {
			for (int i = 0; i < 4; i++) {

				a.getCards().get(trainDeck.getDeck().get(0).getColor()).add(trainDeck.getDeck().remove(0));
			}
		}
		trainDeck.fillDisplay();
	}

	public void setBoard() {
		for (City a : cities) {
			board.getMap().put(a, new ArrayList<Road>());
		}
		for (Road a : roads) {
			board.getMap().get(a.getStart()).add(a);
			board.getMap().get(a.getEnd()).add(a);
		}
	}

	public void makeCities() throws IOException {
	    ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("Files/city.txt").getFile());
        Scanner sc = new Scanner(file);
		//Scanner sc = new Scanner(new File("city.txt"));
		while (sc.hasNextLine()) {
			cities.add(new City(sc.nextLine()));
		}
	}

	public City findCities(String name) {
		// System.out.println(name);
		for (int i = 0; i < cities.size(); i++) {
			if (cities.get(i).getName().equalsIgnoreCase(name)) {

				return cities.get(i);
			}
		}
		return null;
	}

	public void makeRoads() throws IOException {
	    ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("Files/roads.txt").getFile());
        Scanner sc = new Scanner(file);
		while (sc.hasNextLine()) {
			String[] str = sc.nextLine().split(" \\| ");
			for (int i = 0; i < str.length; i++) {
				str[i] = str[i].replaceAll("\\s+$", "");
			}
			String c1 = str[0].substring(0, str[0].indexOf("-"));
			String c2 = str[0].substring(str[0].indexOf("-") + 1, str[0].length());
			if (str.length == 4) {
				roads.add(new Road(findCities(c1), findCities(c2), str[2], Integer.parseInt(str[3])));
			} else {
				roads.add(new DoubleRoad(findCities(c1), findCities(c2), str[2].substring(4, str[2].length()),
						Integer.parseInt(str[4]), str[3].substring(4, str[3].length())));
			}
		}
	}

	public Road findRoad(String name) {
		for (int i = 0; i < roads.size(); i++) {
			if (roads.get(i).getName().equalsIgnoreCase(name))
				return roads.get(i);
			else if (roads.get(i).getName2().equalsIgnoreCase(name))
				return roads.get(i);
		}
		// System.out.println("\n\n\n poop");
		return null;
	}

	public void stopTurn() {
		state = 0;
		while (!players[playerCounter % 4].getsCards().isEmpty()) {
			players[playerCounter % 4].getCards().get(players[playerCounter % 4].getsCards().get(0).getColor())
					.add(players[playerCounter % 4].getsCards().remove(0));
		}
		sRoad = null;
		dRoad = null;
		lr = -1;
	}

	public boolean AvengersEndgame() {
		for (Player a : players) {
			if (a.getTrains() <= 2) {
				return true;
			}
		}
		return false;
	}

	public void organizePlayers() {
		ArrayList<Player> peeps = new ArrayList<Player>();
		place = new ArrayList<ArrayList<Player>>();
		for (Player a : players) {
			peeps.add(a);
		}
		while (!peeps.isEmpty()) {
			Player max = peeps.get(0);
			int rm = 0;
			int p = 0;
			for (Player a : peeps) {
				if (a.compareTo(max) == 1) {
					max = a;
					rm = p;
				}
				p++;
			}
			peeps.remove(rm);
			ArrayList<Player> places = new ArrayList<Player>();
			places.add(max);
			for (int i = 0; i < peeps.size(); i++) {
				if (peeps.get(i).compareTo(max) == 0) {
					places.add(peeps.remove(i));
					i--;
				}
			}
			// System.out.println(peeps);
			place.add(place.size(), null);
			place.set(place.size() - 1, places);
			places = new ArrayList<Player>();
		}
		for (int i = 0; i < place.size(); i++) {
			for (Player a : place.get(i)) {
				a.setPlace(i + 1);
				// System.out.println(a.getColor()+" "+a.getPlace() +"
				// "+a.getPoints()+" "+a.getCompletedTickets().size()+"
				// "+a.getMaxlength());
			}
		}
	}

	public void turn(int st, String a, int index, Road p, DoubleRoad pp, String e, int l) {
		// System.out.println(state);

		if (playerCounter == -1) {
			startGame();
			playerCounter++;
			return;
		}
		if ((state != 0 && st != state) || (state != -1 && playerCounter < 3))
			return;
		if (state == 0)
			state = st;

		// ********************************************
		boolean nonNullElemExist = false;
		for (TrainCard s : trainDeck.getDisplay()) {
			if (s != null) {
				nonNullElemExist = true;
				break;
			}
		}
		if (trainDeck.getDeck().isEmpty() && nonNullElemExist == false && !trainDeck.getDiscard().isEmpty()) {
			trainDeck.resetDeck();
		}
		if (playerCounter < 4) {
			state = -1;
		}
		if (state == -1) {
			// System.out.println(actionCounter);
			if (actionCounter == 0) {
				ticketDeck.fillTopFive();
				// System.out.println(ticketDeck.getTopFour());
				actionCounter++;
				return;
			}
			if (index == 6 && add.size() < 3) {
				return;
			}
			if (index != 6) {
				if ((index < ticketDeck.getTopFour().size()) && add.contains(ticketDeck.getTopFour().get(index))) {
					add.remove(ticketDeck.getTopFour().get(index));
				} else if ((index < ticketDeck.getTopFour().size()) && ticketDeck.getTopFour().get(index) != null)
					add.add(ticketDeck.getTopFour().get(index));
			}
			if (index == 6 && add.size() >= 3) {
				players[playerCounter % 4].getTickets().addAll(add);
				ticketDeck.getTopFour().removeAll(add);
				ticketDeck.addToBottom(ticketDeck.getTopFour());
				ticketDeck.setTopFour(new ArrayList<TicketCard>());
				// System.out.println(playerCounter + " p");
				if (playerCounter < 3)
					state = -1;
				else
					state = 0;
				actionCounter = 0;

				players[playerCounter % 4].settCount(players[playerCounter % 4].gettCount() + add.size());
				playerCounter++;
				add = new ArrayList<TicketCard>();
				if (state != 0)
					turn(-1, "a", 1, null, null, "a", 1);
			}
			return;
		}
		players[playerCounter % 4].checkContracts();
		players[(playerCounter) % 4].longestPath();
		if (state == 1) {
			if (index < 5) {
				if (trainDeck.getDisplay().get(index) != null
						&& actionCounter + trainDeck.getDisplay().get(index).getPoint() < 3) {
					TrainCard selected = trainDeck.getDisplay().remove(index);

					players[playerCounter % 4].getCards().get(selected.getColor()).add(selected);
					players[playerCounter % 4].setTtCount(players[playerCounter % 4].getTtCount() + 1);
					if (selected.getColor().equals("Any"))
						actionCounter++;
					actionCounter++;
					trainDeck.refillDisplay(index);
				}
			} else {
				if (!trainDeck.getDeck().isEmpty()) {
					players[playerCounter % 4].getCards().get(trainDeck.getDeck().get(0).getColor())
							.add(trainDeck.getDeck().remove(0));
					players[playerCounter % 4].setTtCount(players[playerCounter % 4].getTtCount() + 1);
					actionCounter++;
				}
			}
			if (trainDeck.getDeck().isEmpty() && nonNullElemExist == false && !trainDeck.getDiscard().isEmpty()) {
				trainDeck.resetDeck();
			}
			if (actionCounter == 2) {
				state = 0;
				actionCounter = 0;
				playerCounter++;
			}
			int nullCount = 0;
			int o = 0;
			int y = -1;
			int notnull = -1;
			nonNullElemExist = false;
			for (TrainCard s : trainDeck.getDisplay()) {
				if (s != null) {
					nonNullElemExist = true;
					y = o;
				} else
					nullCount++;
				o++;
			}
			boolean wilds = true;
			for (TrainCard s : trainDeck.getDisplay()) {
				if (s != null && !s.getColor().equals("Any")) {
					wilds = false;
					break;
				}
			}
			if ((actionCounter == 1 && nonNullElemExist == false)
					|| (actionCounter == 1 && nullCount == 4 && trainDeck.getDisplay().get(y).getColor().equals("Any"))
					|| (actionCounter == 1 && wilds)) {
				state = 0;
				actionCounter = 0;
				playerCounter++;
			}
			// if (actionCounter < 2) {
			// return;
			// }
		}
		// System.out.println("ac" + actionCounter);
		// System.out.println(trainDeck.getDisplay());
		// *****************************************************************
		if (state == 2) {
			if (actionCounter == 0) {
				ticketDeck.fillTopFour();
				actionCounter++;
				return;
			}
			if (index == 5 && add.size() < 1) {
				return;
			}
			if (index != 5) {
				if ((index < ticketDeck.getTopFour().size()) && add.contains(ticketDeck.getTopFour().get(index))) {
					add.remove(ticketDeck.getTopFour().get(index));
				} else if ((index < ticketDeck.getTopFour().size()) && ticketDeck.getTopFour().get(index) != null)
					add.add(ticketDeck.getTopFour().get(index));
			}
			if (index == 5 && add.size() >= 1) {
				players[playerCounter % 4].getTickets().addAll(add);
				ticketDeck.getTopFour().removeAll(add);
				ticketDeck.addToBottom(ticketDeck.getTopFour());
				ticketDeck.setTopFour(new ArrayList<TicketCard>());
				// System.out.println(ticketDeck.getTickets().size());
				// System.out.println(players[playerCounter%4].getTickets().size());
				state = 0;
				actionCounter = 0;
				players[playerCounter % 4].settCount(players[playerCounter % 4].gettCount() + add.size());
				playerCounter++;
				add = new ArrayList<TicketCard>();
			}

		}
		// *********************************

		if (state == 3) {
			int max = 0;
			for (String tr : players[playerCounter % 4].getCards().keySet()) {
				if (!tr.equals("Any"))
					max = Math.max(max, players[playerCounter % 4].getCards().get(tr).size());
			}
			// System.out.println(max);
			if (sRoad == null && p != null && dRoad == null) {
				sRoad = p;
				if (sRoad.getOwner() != null || (players[playerCounter % 4].getTrains() - sRoad.length < 0)
						|| (!sRoad.getColor().equals("Any")
								&& players[playerCounter % 4].getCards().get(sRoad.getColor()).size()
										+ players[playerCounter % 4].getCards().get("Any").size() < sRoad.getLength())
						|| (sRoad.getColor().equals("Any")
								&& max + players[playerCounter % 4].getCards().get("Any").size() < sRoad.getLength())) {
					sRoad = null;
					state = 0;
					return;
				}
			} else if (dRoad == null && pp != null && sRoad == null && l == 0) {
				dRoad = pp;
				lr = l;
				if (dRoad.getOwner() != null || (players[playerCounter % 4].getTrains() - dRoad.length < 0)
						|| (!dRoad.getColor().equals("Any")
								&& players[playerCounter % 4].getCards().get(dRoad.getColor()).size()
										+ players[playerCounter % 4].getCards().get("Any").size() < dRoad.getLength())
						|| (dRoad.getColor().equals("Any")
								&& max + players[playerCounter % 4].getCards().get("Any").size() < dRoad.getLength())) {
					dRoad = null;
					lr = -1;
					state = 0;
					return;
				}
			} else if (dRoad == null && pp != null && sRoad == null && l == 1) {
				dRoad = pp;
				lr = l;
				if (dRoad.getOwnerR() != null || (players[playerCounter % 4].getTrains() - dRoad.length < 0)
						|| (!dRoad.getColorR().equals("Any")
								&& players[playerCounter % 4].getCards().get(dRoad.getColorR()).size()
										+ players[playerCounter % 4].getCards().get("Any").size() < dRoad.getLength())
						|| (dRoad.getColorR().equals("Any")
								&& max + players[playerCounter % 4].getCards().get("Any").size() < dRoad.getLength())) {
					dRoad = null;
					lr = -1;
					state = 0;
					return;
				}
			}
			if (dRoad != null && !dRoad.checkOpposite(players[playerCounter % 4])) {
				dRoad = null;
				state = 0;
				lr = -1;
				return;
			}
			if (sRoad != null && !sRoad.getColor().equals("Any"))
				rCol = sRoad.getColor();
			else if (dRoad != null && lr == 0 && !dRoad.getColor().equals("Any"))
				rCol = dRoad.getColor();
			else if (dRoad != null && lr == 1 && !dRoad.getColorR().equals("Any"))
				rCol = dRoad.getColorR();
			if (e != null && !e.equals("10")) {
				if (sRoad != null && sRoad.getColor().equals("Any")
						&& ((players[playerCounter % 4].getsCards().isEmpty())
								|| containsOnly(players[playerCounter % 4].getsCards(), "Any"))) {
					if (!players[playerCounter % 4].getCards().get(e).isEmpty()) {
						players[playerCounter % 4].getsCards()
								.add(players[playerCounter % 4].getCards().get(e).remove(0));
						rCol = e;
						if (players[playerCounter % 4].getsCards().size() != sRoad.getLength())
							return;
					}
				} else if (dRoad != null && dRoad.getColor().equals("Any")
						&& ((players[playerCounter % 4].getsCards().isEmpty())
								|| containsOnly(players[playerCounter % 4].getsCards(), "Any"))) {
					if (!players[playerCounter % 4].getCards().get(e).isEmpty()) {
						players[playerCounter % 4].getsCards()
								.add(players[playerCounter % 4].getCards().get(e).remove(0));
						rCol = e;
						if (players[playerCounter % 4].getsCards().size() != dRoad.getLength())
							return;
					}
				}
				if ((sRoad != null && players[playerCounter % 4].getsCards().size() < sRoad.getLength())
						|| (dRoad != null && players[playerCounter % 4].getsCards().size() < dRoad.getLength())) {
					if (index != 10 && (e != null && !e.equals("10"))
							&& !players[playerCounter % 4].getCards().get(e).isEmpty() && sRoad != null) {
						if (e.equals(rCol) || e.equals("Any"))
							players[playerCounter % 4].getsCards()
									.add(players[playerCounter % 4].getCards().get(e).remove(0));
					} else if (index != 10 && (e != null && !e.equals("10"))
							&& !players[playerCounter % 4].getCards().get(e).isEmpty() && dRoad != null && lr == 0) {
						if (e.equals(rCol) || e.equals("Any"))
							players[playerCounter % 4].getsCards()
									.add(players[playerCounter % 4].getCards().get(e).remove(0));
					} else if (index != 10 && (e != null && !e.equals("10"))
							&& !players[playerCounter % 4].getCards().get(e).isEmpty() && dRoad != null && lr == 1) {
						if (e.equals(rCol) || e.equals("Any"))
							players[playerCounter % 4].getsCards()
									.add(players[playerCounter % 4].getCards().get(e).remove(0));
					}
				}
			}
			if (e != null && e.equals("10")) {
				if (sRoad != null && players[playerCounter % 4].getsCards().size() == sRoad.getLength()) {
					sRoad.setOwner(players[playerCounter % 4]);
					players[playerCounter % 4]
							.setPoints(players[playerCounter % 4].getPoints() + pointValues.get(sRoad.getLength()));
					players[playerCounter % 4].setTrains(players[playerCounter % 4].getTrains() - sRoad.getLength());
					trainDeck.getDiscard().addAll(players[playerCounter % 4].getsCards());
					players[playerCounter % 4].setsCards(new ArrayList<TrainCard>());
					state = 0;
					actionCounter = 0;
					players[playerCounter % 4].setTtCount(players[playerCounter % 4].getTtCount() - sRoad.getLength());
					players[playerCounter % 4].getpMap().addCities(sRoad.getStart(), sRoad.getEnd());
					players[playerCounter % 4].getpMap().addRoad(sRoad);
					playerCounter++;
					sRoad = null;

				} else if (dRoad != null && players[playerCounter % 4].getsCards().size() == dRoad.getLength()
						&& lr == 0) {
					dRoad.setOwner(players[playerCounter % 4]);
					players[playerCounter % 4]
							.setPoints(players[playerCounter % 4].getPoints() + pointValues.get(dRoad.getLength()));
					players[playerCounter % 4].setTrains(players[playerCounter % 4].getTrains() - dRoad.getLength());
					trainDeck.getDiscard().addAll(players[playerCounter % 4].getsCards());
					players[playerCounter % 4].setsCards(new ArrayList<TrainCard>());
					state = 0;
					actionCounter = 0;
					players[playerCounter % 4].setTtCount(players[playerCounter % 4].getTtCount() - dRoad.getLength());
					players[playerCounter % 4].getpMap().addCities(dRoad.getStart(), dRoad.getEnd());
					players[playerCounter % 4].getpMap().addDRoad(dRoad);
					playerCounter++;
					dRoad = null;
					lr = -1;

				} else if (dRoad != null && players[playerCounter % 4].getsCards().size() == dRoad.getLength()
						&& lr == 1) {
					dRoad.setOwnerR(players[playerCounter % 4]);
					players[playerCounter % 4].setTrains(players[playerCounter % 4].getTrains() - dRoad.getLength());
					players[playerCounter % 4]
							.setPoints(players[playerCounter % 4].getPoints() + pointValues.get(dRoad.getLength()));
					trainDeck.getDiscard().addAll(players[playerCounter % 4].getsCards());
					players[playerCounter % 4].setsCards(new ArrayList<TrainCard>());
					state = 0;
					players[playerCounter % 4].setTtCount(players[playerCounter % 4].getTtCount() - dRoad.getLength());
					players[playerCounter % 4].getpMap().addCities(dRoad.getStart(), dRoad.getEnd());
					players[playerCounter % 4].getpMap().addDRoad(dRoad);
					actionCounter = 0;
					playerCounter++;
					dRoad = null;
					lr = -1;

				}
			}
			players[playerCounter % 4].checkContracts();
			players[(playerCounter) % 4].longestPath();
		}
		organizePlayers();
		// *****************************************************************
		if (state == 4) {
			if (actionCounter == 0) {
				ArrayList<Player> longestT = longest();
				most = most();
				for (Player k : most) {
					k.setPoints(k.getPoints() + 15);
				}
				for (Player k : longestT) {
					k.setPoints(k.getPoints() + 10);
				}
			}
			playerCounter++;
			actionCounter++;
		}
		if (trainDeck.getDeck().isEmpty() && nonNullElemExist == false && !trainDeck.getDiscard().isEmpty()) {
			trainDeck.resetDeck();
		}
		if (AvengersEndgame() == true && finishingTurn == -4) {
			finishingTurn = playerCounter + 4;
		}
		if (finishingTurn == playerCounter) {
			state = 4;
			return;
		}
		players[playerCounter % 4].checkContracts();
		players[(playerCounter) % 4].longestPath();
		// System.out.println(players[(playerCounter-1)%4].getColor());
		// System.out.println(players[(playerCounter-1)%4].getMaxlength());
	}

	public ArrayList<Player> getMost() {
		return most;
	}

	public void setMost(ArrayList<Player> most) {
		this.most = most;
	}

	public ArrayList<Player> longest() {
		Player most = players[0];
		for (Player a : players) {
			if (a.getMaxlength() > most.getMaxlength())
				most = a;
		}
		ArrayList<Player> wow = new ArrayList<Player>();
		for (Player a : players) {
			if (a.getMaxlength() == most.getMaxlength())
				wow.add(a);
		}
		return wow;

	}

	public ArrayList<Player> most() {
		for (Player a : players) {
			int count = 0;
			for (TicketCard p : a.getTickets()) {
				if (p.isCompleted()) {
					a.setPoints(a.getPoints() + p.getValue());
					count++;
				} else
					a.setPoints(a.getPoints() - p.getValue());
			}
			a.setcCount(count);
		}

		Player most = players[0];
		for (Player a : players) {
			if (a.getcCount() > most.getcCount())
				most = a;
		}
		ArrayList<Player> wow = new ArrayList<Player>();
		for (Player a : players) {
			if (a.getcCount() == most.getcCount())
				wow.add(a);
		}
		return wow;
	}

	public int getLr() {
		return lr;
	}

	public void setLr(int lr) {
		this.lr = lr;
	}

	public DoubleRoad getdRoad() {
		return dRoad;
	}

	public void setdRoad(DoubleRoad dRoad) {
		this.dRoad = dRoad;
	}

	public String getrCol() {
		return rCol;
	}

	public void setrCol(String rCol) {
		this.rCol = rCol;
	}

	public int getPlayerCounter() {
		return playerCounter;
	}

	public void setPlayerCounter(int playerCounter) {
		this.playerCounter = playerCounter;
	}

	public int getActionCounter() {
		return actionCounter;
	}

	public void setActionCounter(int actionCounter) {
		this.actionCounter = actionCounter;
	}

	public int getFinishingTurn() {
		return finishingTurn;
	}

	public void setFinishingTurn(int finishingTurn) {
		this.finishingTurn = finishingTurn;
	}

	public TrainDeck getTrainDeck() {
		return trainDeck;
	}

	public void setTrainDeck(TrainDeck trainDeck) {
		this.trainDeck = trainDeck;
	}

	public TicketDeck getTicketDeck() {
		return ticketDeck;
	}

	public void setTicketDeck(TicketDeck ticketDeck) {
		this.ticketDeck = ticketDeck;
	}

	public Player[] getPlayers() {
		return players;
	}

	public void setPlayers(Player[] players) {
		this.players = players;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Road getsRoad() {
		return sRoad;
	}

	public void setsRoad(Road sRoad) {
		this.sRoad = sRoad;
	}

	public ArrayList<TicketCard> getAdd() {
		return add;
	}

	public void setAdd(ArrayList<TicketCard> add) {
		this.add = add;
	}

	public ArrayList<ArrayList<Player>> getPlace() {
		return place;
	}

	public void setPlace(ArrayList<ArrayList<Player>> place) {
		this.place = place;
	}

	public GameBoard getBoard() {
		return board;
	}

	public void setBoard(GameBoard board) {
		this.board = board;
	}

	public Map<Integer, Integer> getPointValues() {
		return pointValues;
	}

	public void setPointValues(Map<Integer, Integer> pointValues) {
		this.pointValues = pointValues;
	}

	public boolean containsOnly(ArrayList<TrainCard> cards, String blah) {
		boolean t = true;
		for (TrainCard a : cards) {
			if (!a.getColor().equals(blah)) {
				t = false;
				break;
			}
		}
		return t;
	}
}
