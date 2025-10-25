import java.util.EmptyStackException;

/**
   A class of stacks whose entries are stored in a chain of nodes
   @author Frank M. Carrano
*/
public class LinkedStack<T> implements StackInterface<T> 
{
    private Node topNode;//reference to first node in the chain

    public LinkedStack()
    {
        topNode = null;
    }//End default constructor
    
    /** Adds a new entry to the top of this stack.
    @param newEntry  An object to be added to the stack. */
    public void push(T newEntry)
    {
        Node newNode = new Node(newEntry, topNode);
        topNode = newNode;
    } //End push

    /** Removes and returns this stack's top entry.
    @return  The object at the top of the stack. 
    @throws  EmptyStackException if the stack is empty before the operation. */
    public T pop()
    {
        T top = peek();
        topNode = topNode.getNextNode();
        return top;
    } // end pop
    
    /** Retrieves this stack's top entry.
    @return  The object at the top of the stack.
    @throws  EmptyStackException if the stack is empty. */
    public T peek()
    {
        if (isEmpty())
        {
            throw new EmptyStackException();
        }
        else
        {
            return topNode.getData();
        }
    } // end peek
    
    /** Detects whether this stack is empty.
    @return  True if the stack is empty. */
    public boolean isEmpty()
    {
        return topNode == null;
    }
    
    /** Removes all entries from this stack. */
    public void clear()
    {
        topNode = null;
    }

    private class Node
    {
        private T data;//Entry in stack
        private Node next;//Link to next node

        public Node(T dataSection)
        {
            this.data = dataSection;
            this.next = null;
        }//End default constructor

        public Node(T dataSection, Node nextNode)
        {
            this.data = dataSection;
            this.next = nextNode;
        }

        /** Sets the data for this node. */
        public void setData(T dataSection)
        {
            this.data = dataSection;
        }
        /** Sets the next node reference for this node. */
        public void setNext(Node nextNode)
        {
            this.next = nextNode;
        }

        /** Gets the data stored in this node. */
        public T getData() 
        {
            return data;
        }
        /** Gets the next node reference. */
        public Node getNextNode()
        {
            return next;
        }
    }//End Node

}//End LinkedStack