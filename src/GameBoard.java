import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class GameBoard
{
	private HashMap<City, ArrayList<Road>> map;
	
	public GameBoard()
	{
		map = new HashMap<City, ArrayList<Road>>();
	}
	
	public void addCities(City a, City b)
	{
		if(!map.containsKey(a))
		{
			map.put(a, new ArrayList<Road>());
		}
		if(!map.containsKey(b))
		{
			map.put(b, new ArrayList<Road>());
		}
	}
	
	public void addRoad(Road road)
	{
	    map.get(road.getEnd()).add(road);
		map.get(road.getStart()).add(road);
	}
	public void addDRoad(DoubleRoad r)
	{
		Road road = new Road(r.getStart(), r.getEnd(), r.getColor(), r.getLength());
		map.get(road.getEnd()).add(road);
		map.get(road.getStart()).add(road);
	}
	public HashMap<City, ArrayList<Road>> getMap() {
		return map;
	}

	public void setMap(HashMap<City, ArrayList<Road>> map) {
		this.map = map;
	}

	public void addRoadCity(City c, Road r)
	{
	}
}


