public class Road  {
	protected Player owner;
	protected int length;
	protected City start;
	protected City end;
	protected String color;
	protected String name,name2;
	public Road(City s, City e, String col, int l)
	{
		start =s;
		end = e;
		color = col;
		length = l;
		owner = null;
		name = s.getName() + "-" + e.getName();
		name2 = e.getName() + "-" + s.getName();
	}
	public String getName2() {
		return name2;
	}
	public void setName2(String name2) {
		this.name2 = name2;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player players) {
		this.owner = players;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public City getStart() {
		return start;
	}

	public void setStart(City start) {
		this.start = start;
	}

	public City getEnd() {
		return end;
	}

	public void setEnd(City end) {
		this.end = end;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	public String toString(){
		return start.getName() + "-"+end.getName()+ " "+length;
	}
	public boolean equals(Object arg0)
	{
		Road a = (Road)arg0;
		if(a.getName().equals(getName()) || a.getName().equals(getName2()) )
			return true;
		return false;
	}
}

