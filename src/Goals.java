public class Goals implements Comparable<Goals>
{
    private String card;
    private int count;
    
    public Goals(String c, int cnt)
    {
        card = c;
        count = cnt;
    }
    
    public String getCard()
    {
        return card;
    }
            
    public int getCount()
    {
        return count;
    }
    
    
    @Override
    public int compareTo(Goals x) 
    {
        if (getCount() > x.getCount())
            return 1;
        else if (getCount () == x.getCount())
            return 0;
        else
            return -1;
    }
    
    
    @Override
    public String toString() 
    {
        return Integer.toString(count) + "-" + getCard();
    }
}

