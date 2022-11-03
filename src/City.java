
public class City
{
	private String name;
	public City(String n) 
	{
		name = n;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name=name;
	}
	public boolean equals(Object arg0)
	{
		City a = (City)arg0;
		if(a.name.equals(name))
		{
			return true;
		}
		return false;
	}
	public String toString()
	{
		return name;
	}
}


