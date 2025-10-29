import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;


public class CalculatorTest 
{


    @Test
    public void evaluatePostfixTesting1(){
        String postfix = "23*42-/56*+";
        Integer expected = 33;
        assertEquals(expected, Calculator.evaluatePostfix(postfix));
    }
    
}