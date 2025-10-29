import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CalculatorTest 
{
    //TESTING for convertToPostfix
    @Test
    public void convertToPostfix()
    {     
        //Basic operator precedence test
        String infix1 = "a+b*c";
        String expected1 = "abc*+";
        assertEquals(expected1, Calculator.convertToPostfix(infix1));
        
        //Test with parentheses
        String infix2 = "(a+b)*c";
        String expected2 = "ab+c*";
        assertEquals(expected2, Calculator.convertToPostfix(infix2));

        //Test with subtraction and division
        String infix3 = "a+b/c-a";
        String expected3 = "abc/+a-";
        assertEquals(expected3, Calculator.convertToPostfix(infix3));   

        //Test with a complex infix expression
        String infix4 = "a*b/(c-a)+d*e";
        String expected4 = "ab*ca-/de*+";
        assertEquals(expected4, Calculator.convertToPostfix(infix4));
    } 

    @Test
    public void convertToPostfixWithExponents()
    {
        String infix = "a^b^c";
        String expected = "abc^^";
        assertEquals(expected, Calculator.convertToPostfix(infix));     
    }

    @Test
    public void convertToPostfixWithWhitespace()
    {
        String infix = "a + b * c";
        String expected = "abc*+";
        assertEquals(expected, Calculator.convertToPostfix(infix));
    }

    @Test
    public void convertToPostfixWithNestedParentheses()
    {
        String infix = "((a+b)*c)+d";
        String expected = "ab+c*d+";
        assertEquals(expected, Calculator.convertToPostfix(infix));
    } 

    @Test
    public void convertToPostfixWithSingleOperand()
    {
        String infix = "a";
        String expected = "a";
        assertEquals(expected, Calculator.convertToPostfix(infix));
    }

    @Test
    public void convertToPostfixWithNumbers()
    {
        String infix = "1+2*3";
        String expected = "123*+";
        assertEquals(expected, Calculator.convertToPostfix(infix));
    }

    //TESTING for evaluatePostfix
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
}