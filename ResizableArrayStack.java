import java.util.Arrays;
import java.util.EmptyStackException;

public class ResizableArrayStack<T> implements StackInterface<T> {
    private T[] stack;
    private int topIndex;
    private final static int DEFAULT_CAPACITY = 50;
    private final static int MAX_CAPACITY = 10000;
    private boolean integrityOk;
    
    
    public ResizableArrayStack(){
        this(DEFAULT_CAPACITY);
    }

    public ResizableArrayStack(int initialCapacity){
        integrityOk = false;
        checkCapacity(initialCapacity);

        @SuppressWarnings("unchecked")
        T[] tempStack = (T[])new Object[initialCapacity];
        stack=tempStack;
        topIndex=-1;
        integrityOk = true;
    }

    public void push(T newEntry){
        checkIntegrity();
        ensureCapacity();

        stack[++topIndex] = newEntry;
    }

    public T pop(){
        checkIntegrity();
        if(isEmpty()){
            throw new EmptyStackException();
        }

        T result = stack[topIndex];
        stack[topIndex--] = null;
        return result;

    }

    public T peek(){
        checkIntegrity();
        if (isEmpty()){
            throw new EmptyStackException();
        }
        return stack[topIndex];

    }

    public void clear(){
        checkIntegrity();

        while (topIndex > -1){
            stack[topIndex--] = null;
        }
    }


    public boolean isEmpty(){
        if(topIndex == -1) return true;
        return false;

    }

    private void checkIntegrity(){
        if(!integrityOk) throw(new SecurityException("Stack is corrupt and cannot be used."));
    }

    private void checkCapacity(int checkVal){
        if(checkVal>MAX_CAPACITY){
            throw new IllegalStateException("Attempt to create a stack whose"+
                                            " capacity exceeds maximul allotment of " +
                                            MAX_CAPACITY);
        }
    }
    private void ensureCapacity(){
        if(topIndex==stack.length-1){
            int newLength = stack.length * 2;
            checkCapacity(newLength);
            stack = Arrays.copyOf(stack, newLength);
        }
    }
}
