import java.util.ArrayList;
import java.util.Stack;

import java.util.ArrayList;
import java.util.Stack;


public class BinaryCardTree {

    private BinaryCountNode root;

    public BinaryCardTree()
    {
        root = null;
    }

    public void add(BinaryCountNode x) 
    {
        int cnt = contains(x);
        if (cnt == 0)
        {
            root = addRecursive(root, x);
        } 
        else 
        {
            addNodeCount(root, x);
        }
    }

    public void addNodeCount(BinaryCountNode tree, BinaryCountNode x) 
    {
        if (tree != null)
        {
            int dirTest = x.compareTo(tree);
            if (dirTest == 0)
            {
                tree.increaseCount();
            }
            else if (dirTest < 0) 
            {
                addNodeCount(tree.getLeft(), x);
            } 
            else if (dirTest > 0) 
            {
                addNodeCount(tree.getRight(), x);
            }
        }

    }

    public BinaryCountNode addRecursive(BinaryCountNode tree, BinaryCountNode x)
    {
        if (tree == null) 
        {
            tree = x; //new BinaryCountNode(x);
        } 
        else 
        {
            if (x.getCard().compareTo(tree.getCard()) < 0) 
            {
                tree.setLeft(addRecursive(tree.getLeft(), x));
            } 
            else if (x.getCard().compareTo(tree.getCard()) > 0)
            {
                tree.setRight(addRecursive(tree.getRight(), x));
            }
            return tree;
        }
        return tree;
    }

    public ArrayList<Comparable> getAllCards() 
    {
        ArrayList<Comparable> allCards = new ArrayList<>();
        allCards = inOrder(root, allCards);
        return allCards;

    }

    public ArrayList<Comparable> inOrder(BinaryCountNode tree, ArrayList<Comparable> arrayCards)
    {
        if (tree != null) 
        {
            inOrder(tree.getLeft(), arrayCards);
            for (int i = 0; i < tree.getCount(); i++) 
            {
                arrayCards.add(tree.getCard());
            }
            inOrder(tree.getRight(), arrayCards);
        }
        return arrayCards;
    }

    public int numCardsInHand() 
    {
        ArrayList<Comparable> cardList = getAllCards();
        return cardList.size();
    }

    public int contains(Comparable x) 
    {
        // Call search to find the node and its count
        return search(x, root);
    }

    public int search(Comparable x, BinaryCountNode tree) 
    {
        if (tree == null) 
        {
            return 0;
        } 
        else 
        {
            int dirTest = x.compareTo(tree);
            if (dirTest == 0) 
            {
                return tree.getCount();
            } 
            else if (dirTest < 0) 
            {
                return search(x, tree.getLeft());
            } 
            else if (dirTest > 0) 
            {
                return search(x, tree.getRight());
            }
        }
        return 0;
    }

    public BinaryCountNode findNode(Comparable x, BinaryCountNode tree) {
        if (tree == null) 
        {
            return tree;
        } 
        else 
        {
            int dirTest = x.compareTo(tree);
            if (dirTest == 0) 
            {
                return tree;
            } else if (dirTest < 0)
            {
                return findNode(x, tree.getLeft());
            } else if (dirTest > 0) 
            {
                return findNode(x, tree.getRight());
            }
        }
        return null;
    }

    public void searchMostColorCard(BinaryCountNode tree, ArrayList<Comparable> arrayMostColorCards, Stack<Integer> maxCnt) 
    {
        if (tree != null) 
        {
            searchMostColorCard(tree.getLeft(), arrayMostColorCards, maxCnt);
            searchMostColorCard(tree.getRight(), arrayMostColorCards, maxCnt);

            int cnt = maxCnt.peek();
            if (cnt < tree.getCount())
            {
                arrayMostColorCards.clear();
                arrayMostColorCards.add(tree.getCard());
                maxCnt.pop();
                maxCnt.push(tree.getCount());
                
            } 
            else if (cnt == tree.getCount()) 
            {
                arrayMostColorCards.add(tree.getCard());                    
            }
                   
        }
    }
    
    public ArrayList<Comparable> getMostColorCard() 
    {
        ArrayList<Comparable> mostColorCards = new ArrayList<>();
        Stack<Integer> maxCnt = new Stack<>();
        mostColorCards.add(root);
        maxCnt.push(root.getCount());
        
        searchMostColorCard(root, mostColorCards, maxCnt);
        return mostColorCards;
    }

    public void searchLeastColorCard(BinaryCountNode tree, ArrayList<Comparable> arrayLeastColorCards, Stack<Integer> minCnt) 
    {
        if (tree != null) 
        {
            searchLeastColorCard(tree.getLeft(), arrayLeastColorCards, minCnt);
            searchLeastColorCard(tree.getRight(), arrayLeastColorCards, minCnt);


            int cnt = minCnt.peek();
            if (cnt > tree.getCount())
            {
                arrayLeastColorCards.clear();
                arrayLeastColorCards.add(tree.getCard());
                minCnt.pop();
                minCnt.push(tree.getCount());
                
            } 
            else if (cnt == tree.getCount()) 
            {
                arrayLeastColorCards.add(tree.getCard());                    
            }

        }
        
    }
        
    public ArrayList<Comparable> getLeastColorCard() 
    {
        ArrayList<Comparable> leastColorCards = new ArrayList<>();
        Stack<Integer> minCnt = new Stack<>();
        //minCnt.push(1);
        leastColorCards.add(root);
        minCnt.push(root.getCount());
        searchLeastColorCard(root, leastColorCards, minCnt);
        return leastColorCards;
    }

    
    public BinaryCountNode getLargestNode (BinaryCountNode tree)
    {
        if (tree.getRight() == null)
            return tree;
        return getLargestNode(tree.getRight());
    }

        
    public ArrayList<Comparable> remove(Comparable target, int num) 
    { 
        ArrayList<Comparable> s = new ArrayList<>();   
        removeRecursive(root, target, num, s);
        return s;
    }

    public void removeRecursive(BinaryCountNode tree, Comparable target, int num, ArrayList<Comparable> s)
    {
        if (tree != null)
        {
            //removeRecursive(tree.getLeft(), target, num, s);
            if (target.equals(tree.getCard()))
            {
                if (num < tree.getCount())
                {
                    tree.setCount(tree.getCount()-num);
                    for (int i = 0; i < num; i++)
                        s.add(target);
                                    }
                else if (num == tree.getCount())
                {
                    //  Delete Node
                    Stack<BinaryCountNode> parentStack = new Stack<>();
                    parentStack.push(null);
                    BinaryCountNode nodeDeleted = deleteNode(root, tree.getCard(), parentStack);

                    for (int i =0; i < num; i++)
                        s.add(target);
                    
                }
                else if (num > tree.getCount())
                {
                    BinaryCountNode wildNodeToSearch = new BinaryCountNode("wild", 1, null, null);
                    BinaryCountNode wildNode = findNode(wildNodeToSearch, root);
                    if (wildNode == null)
                        return;
                    else
                    {
                        if (wildNode.getCount() + tree.getCount() == num)
                        {
                            // Delete Node
                            Stack<BinaryCountNode> parentStack = new Stack<>();
                            parentStack.push(null);
                            BinaryCountNode nodeDeleted = deleteNode(root, tree.getCard(), parentStack);
                            
                            // Delete Wild
                            parentStack.clear();
                            parentStack.push(null);
                            nodeDeleted = deleteNode(root, "wild", parentStack);
                            
                            for (int i = 0; i < tree.getCount(); i++)
                                s.add(target);
                            for (int j = 0; j < wildNode.getCount(); j++)
                                s.add(wildNode.getCard());
                          
                        }
                        else if (wildNode.getCount() + tree.getCount() > num)
                        {
                            // Delete Node
                            Stack<BinaryCountNode> parentStack = new Stack<>();
                            parentStack.push(null);
                            BinaryCountNode nodeDeleted = deleteNode(root, tree.getCard(), parentStack);
                            
                            wildNode.setCount(wildNode.getCount() - num + tree.getCount());
                            for (int i = 0; i < tree.getCount(); i++)
                                s.add(target);
                            for (int j = 0; j < num-tree.getCount(); j++)
                                s.add(wildNode.getCard());                         
                        }
                    }
                }
            }
            else
            {
                if (target.compareTo(tree.getCard()) < 0)
                    removeRecursive(tree.getLeft(), target, num, s);
                if (target.compareTo(tree.getCard()) > 0)
                    removeRecursive(tree.getRight(), target, num, s);
            }
        }
    }
    
    
    public BinaryCountNode deleteNode(BinaryCountNode tree, String val, Stack<BinaryCountNode> parentNodes)
    {
        if (tree == null)
            return tree;
        
        if (tree.getCard().equals(val))
        {
            if (tree.getLeft() == null && tree.getRight() == null)
            {
                BinaryCountNode parent = parentNodes.pop();
                if (parent == null)
                    root = null;
                else
                {
                    parent.setLeft(null);
                    parent.setRight(null);
                }
                tree.setLeft(null);
                tree.setRight(null);                                        
                return tree;
            }
            else if (tree.getLeft() == null || tree.getRight() == null)
            {
                    BinaryCountNode parent = parentNodes.pop();
                    if (parent == null)
                    {
                        if (tree.getLeft() != null)
                            root = tree.getLeft();
                        if (tree.getRight() != null)
                            root = tree.getRight();
                    }
                    else
                    {                        
                        if (tree.getLeft() == null)
                        {
                            if (parent.getCard().compareTo(val)>0) 
                                parent.setLeft(tree.getRight()); 
                            else
                                parent.setRight(tree.getRight());
                        }
                        else if (tree.getRight() == null)
                        {
                            if (parent.getCard().compareTo(val)<0)
                                parent.setRight(tree.getLeft());
                            else
                                parent.setLeft(tree.getLeft());
                        }
                    }
                    
                    tree.setLeft(null);
                    tree.setRight(null);

            }
            else
            {
                BinaryCountNode suc = getLargestNode(tree.getLeft());
                suc.setRight(tree.getRight());
                BinaryCountNode parent = parentNodes.pop();
                
                if (parent == null) 
                {
                    //BinaryCountNode temp = new BinaryCountNode(tree);
                    root = tree.getLeft();
                    //tree = tree.getLeft();
                    //temp.setLeft(null);
                    //temp.setRight(null);               
                    //parentNodes.push(temp);
                }
                else
                {
                    //BinaryCountNode parent = parentNodes.pop();
                    //if (parent.getCard().compareTo(val)<0) 
                    if (parent.getRight() == tree)
                        parent.setRight(tree.getLeft());
                    //else if (parent.getCard().compareTo(val)>0)
                    else if (parent.getLeft() == tree)
                        parent.setLeft(tree.getLeft());
                }
                    tree.setLeft(null);
                    tree.setRight(null);
                    //parentNodes.push(tree);
                
            }
            return tree;    
        }
        else
        {
            parentNodes.pop();
            parentNodes.push(tree);
            if (tree.getCard().compareTo(val) > 0) 
            {
                //parentNodes.push(tree);
                tree = deleteNode(tree.getLeft(), val, parentNodes);
            }
            if (tree.getCard().compareTo(val) < 0)
            {
                //parentNodes.push(tree);
                tree = deleteNode(tree.getRight(), val, parentNodes);
            }
        }
        return tree;
    }
    
    

    @Override
    public String toString() 
    {
        ArrayList<Comparable> cardListAdded = getAllCards();

        String cardInTree = "hand -- > " + cardListAdded;

        return cardInTree;
    }
}

