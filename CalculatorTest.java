import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;


public class CalculatorTest 
{
    //Testing for converToPostfix

    //Testing for evaluatePostfix
    @Test
    public void evaluatePostfixTesting1(){
        //Basic test on given expression 
        String postfix = "23*42-/56*+";
        Integer expected = 33;
        assertEquals(expected, Calculator.evaluatePostfix(postfix));
    }

    @Test
    public void evaluatePostfixTesting2(){
        //Test for ignoring unexpected values
        String postfix = "23*42qwertyuiopasdfghjklzxcvbnm<>?;':\"[]{}|\\!@#$%&~`-/56*+";
        Integer expected = 33;
        assertEquals(expected, Calculator.evaluatePostfix(postfix));
    }

    @Test 
    public void evaluatePostfixTesting3(){
        //Test for evaluating mutlti digit input
        String postfix = "(12)2*";
        Integer expected = 24;
        assertEquals(expected, Calculator.evaluatePostfix(postfix) );
    }


    
    
}