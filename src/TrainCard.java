public class TrainCard 
{
	private String color;
	public TrainCard(String c) 
	{
		setColor(c);
	}
	public String getColor() 
	{
		return color;
	}
	public void setColor(String color) 
	{
		this.color = color;
	}
	public int getPoint()
	{
		if(color.equals("Any"))
			return 2;
		else 
			return 1;
	}

}

