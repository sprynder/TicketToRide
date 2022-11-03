public class DoubleRoad extends Road {

	private String colorR;
	private Player OwnerR;

	public Player getOwnerR() {
		return OwnerR;
	}
	public DoubleRoad(City s, City e, String col, int l, String colorR) {
		super(s, e, col, l);
		this.colorR = colorR;
		OwnerR = null;
	}
	public void setOwnerR(Player players) {
		OwnerR = players;
	}

	public String getColorR() {
		return colorR;
	}

	public void setColorR(String colorR) {
		this.colorR = colorR;
	}
	public boolean checkOpposite(Player play)
	{
		if(play.equals(owner) || play.equals(OwnerR))
		{
			return false;
		}
		return true;
	}

}

