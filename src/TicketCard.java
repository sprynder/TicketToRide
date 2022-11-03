
public class TicketCard 
{
	private int value;
	private City start;
	private City end;
	private boolean completed;
	public TicketCard(int val, City s, City e) 
	{
		setValue(val);
		setStart(s);
		setEnd(e);
		completed = false;
	}
	public int getValue() 
	{
		return value;
	}
	public void setValue(int value) 
	{
		this.value = value;
	}
	public City getStart() 
	{
		return start;
	}
	public void setStart(City start) 
	{
		this.start = start;
	}
	public City getEnd() 
	{
		return end;
	}
	public void setEnd(City end) 
	{
		this.end = end;
	}
	public boolean isCompleted() 
	{
		return completed;
	}
	public void setCompleted(boolean completed) 
	{
		this.completed = completed;
	}

}


