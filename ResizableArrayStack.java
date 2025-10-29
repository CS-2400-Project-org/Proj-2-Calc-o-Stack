import java.util.Arrays;
import java.util.EmptyStackException;


/**
 * A class implementing a stack structure using an array that increases in size as necessary.
 * The size of the array cannot be decreased.
 * @param stack The stack that will hold all entries
 * @param topIndex The index of the entry at the top of the stack
 * @param DEFAULT_CAPACITY The default value that will be used for stack size if one is not given
 * @param MAX_CAPACITY The maximum value a stack can reach before the array can no longer resize
 * @param integrityOk Set to true on successful construction to indicate non-corrupt stack
 */
public class ResizableArrayStack<T> implements StackInterface<T> {
    private T[] stack;
    private int topIndex;
    private final static int DEFAULT_CAPACITY = 50;
    private final static int MAX_CAPACITY = 10000;
    private boolean integrityOk;
    
    /** 
     * Default stack constructor, will initialize with a size of 50
     */ 
    public ResizableArrayStack(){
        this(DEFAULT_CAPACITY);
    }

    /**
     * Constructor that allows the user to select the initial size of the stack
     * @param initialCapacity The size of the stack on initialization
     */
    public ResizableArrayStack(int initialCapacity){
        integrityOk = false;
        checkCapacity(initialCapacity);

        @SuppressWarnings("unchecked")
        T[] tempStack = (T[])new Object[initialCapacity];
        stack=tempStack;
        topIndex=-1;
        integrityOk = true;
    }
    
    /**
     * Adds an entry to the top of the stack
     * @param newEntry the item which will be added to the top of the stack
     */

    public void push(T newEntry){
        checkIntegrity();
        ensureCapacity();

        stack[++topIndex] = newEntry;
    }

    /**
     * Removes an item from the top of the stack
     * @return The item that was located at the top of the stack
     */
    public T pop(){
        checkIntegrity();
        if(isEmpty()){
            throw new EmptyStackException();
        }

        T result = stack[topIndex];
        stack[topIndex--] = null;
        return result;

    }

    /**
     * Returns the item at the top of the stack without removing it
     * @return The item at the top of the stack
     */
    public T peek(){
        checkIntegrity();
        if (isEmpty()){
            throw new EmptyStackException();
        }
        return stack[topIndex];

    }

    /**
     * Removes every item from the stack.
     */
    public void clear(){
        checkIntegrity();

        while (topIndex > -1){
            stack[topIndex--] = null;
        }
    }

    /**
     * Determined if the stack has any entries
     * @return true if the stack is empty, false if there are entries.
     */
    public boolean isEmpty(){
        checkIntegrity();
        if(topIndex == -1) return true;
        return false;

    }

    /**
     * Is called in all public methods to ensure the stack is not corrupt
     * @throws SecurityException In the event that initialization failed
     */
    private void checkIntegrity(){
        if(!integrityOk) throw(new SecurityException("Stack is corrupt and cannot be used."));
    }

    /**
     * Ebsures that the size of the array is not greater than the maximum allowed size
     * @param checkVal The value to be checked against MAX_CAPACITY
     * @throws IllegalStateException In the event that the checkVal exceeds MAX_CAPACITY
     */
    private void checkCapacity(int checkVal){
        if(checkVal>MAX_CAPACITY){
            throw new IllegalStateException("Attempt to create a stack whose"+
                                            " capacity exceeds maximul allotment of " +
                                            MAX_CAPACITY);
        }
    }
    /**
     * Called in push in order to resize the array if the current capacity has been reached
     */
    private void ensureCapacity(){
        if(topIndex==stack.length-1){
            int newLength = stack.length * 2;
            checkCapacity(newLength);
            stack = Arrays.copyOf(stack, newLength);
        }
    }
}
