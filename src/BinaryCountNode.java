public class BinaryCountNode implements Comparable<BinaryCountNode>
{
    private String card;
    private int count;
    public BinaryCountNode leftNode;
    public BinaryCountNode rightNode;
    
    public BinaryCountNode(String newCard, int newCount, BinaryCountNode left, BinaryCountNode right)
    {
        card = newCard;
        count = newCount;
        leftNode = left;
        rightNode = right;
    }
    
    public BinaryCountNode (BinaryCountNode node)
    {
        this.card = node.card;
        this.count = node.count;
        this.leftNode = node.leftNode;
        this.rightNode = node.rightNode;
    }
    public String getCard()
    {
        return card;
    }
    
    public int getCount()
    {
        return count;
    }
    
    public void setCount(int newCnt)
    {
        count = newCnt;
    }
    
    public BinaryCountNode getLeft()
    {
        return leftNode;
    }
    
    public void setLeft(BinaryCountNode node)
    {   
        leftNode = node;
    }
    
    public BinaryCountNode getRight()
    {
        return rightNode;
    }
    
    public void setRight(BinaryCountNode node)
    {
        rightNode = node;
    }
    
    public void increaseCount()
    {
        count = count +1;
        
    }
    
    public void decreaseCount()
    {
        count = count -1;
    }
     
    @Override
    public int compareTo(BinaryCountNode x) {
        
        return this.card.compareTo(x.getCard());
    }
}

