import java.lang.Character;

/**
 * A calculator class that converts infix expressions to postfix notation
 * and evaluates postfix expressions using stack-based algorithms.
 */
public class Calculator 
{  
    /**
     * Main method to test the calculator functionality.
     * Demonstrates infix to postfix conversion and postfix evaluation.
     * @param args command line arguments
     */
    public static void main(String[] args)
    {
        //Test infix to postfix conversion with a complex expression
        System.out.println("The infix conversion results in " +
                           convertToPostfix("a*b/(c-a)+d*e"));
        //Test postfix evaluation with a numeric expression
        System.out.println("The evaluation of the postfix expression (23*42-/56*+) is " +
                           evaluatePostfix("23*42-/56*+"));  
    }
    
    /**
     * Converts an infix expression to postfix notation.
     * Handles operator precedence, associativity, and parentheses properly.
     * Supports operators: +, -, *, /, ^ and parentheses ( ).
     * @param infix the infix expression to convert
     * @return the equivalent postfix expression
     */
    public static String convertToPostfix(String infix)
    {
        StackInterface<Character> operatorStack = new LinkedStack<>();
        StringBuilder postfix = new StringBuilder();
        char topOperator;
        int index = 0;

        while(index < infix.length())
        {
            char nextChar = infix.charAt(index);

            //Ignore whitespace
            if(Character.isWhitespace(nextChar))
            {
                index++;
                continue;
            }

            switch (nextChar)
            {
                //Case 1: Operand
                default: 
                if(Character.isLetterOrDigit(nextChar))
                {
                    postfix.append(nextChar);
                }
                break;

                //Case 2: Exponent(right-associative)
                case '^' :
                while (!operatorStack.isEmpty() &&  
                precedence(nextChar) < precedence(operatorStack.peek()))
                {
                    postfix.append(operatorStack.pop());
                }
                operatorStack.push(nextChar);
                break; 

                //Case 3: Operators
                case '+' : case '-' : case '*' : case '/' : 
                while (!operatorStack.isEmpty() &&  
                precedence(nextChar) <= precedence(operatorStack.peek()))
                {
                    postfix.append(operatorStack.pop());
                }
                operatorStack.push(nextChar);
                break;

                //Case 4: Left Parenthesis
                case '(' :
                operatorStack.push(nextChar);
                break;

                //Case 5: Right Parenthesis
                case ')' : //Stack is not empty if infix expression is valid 
                topOperator = operatorStack.pop();
                while (topOperator != '(')
                {
                    postfix.append(topOperator);
                    topOperator = operatorStack.pop();
                }
                break;
            }
            index++;
        }

        //Pop remaining operators from stack
        while (!operatorStack.isEmpty())
        {
            topOperator = operatorStack.pop();
            postfix.append(topOperator);
        }
        return postfix.toString();       
    }

    /**
     * Determines the precedence level of an operator.
     * Higher numbers indicate higher precedence.
     * Used to determine operator evaluation order in infix to postfix conversion.
     * @param operator the operator character to evaluate (+, -, *, /, ^)
     * @return the precedence level
     */
    private static int precedence(char operator)
    {
        switch(operator)
        {
            case '^':
            return 3;
            case '*':
            case '/':
            return 2;
            case '+':
            case '-':
            return 1;
            default: // For non-operators
            return 0;
        }
    }

    public static Integer evaluatePostfix(String postFix){
    
        ResizableArrayStack<Integer> valueStack = new ResizableArrayStack<>(postFix.length());
        int index = 0;
        while(index < postFix.length()){
            char nextCharacter = postFix.charAt(index);
            if(Character.isDigit(nextCharacter)){
                //Issue: Integer.valueOf(Character.toString(nextCharacter)) is unnecessarily complex 
                //and could cause issues. Should use Character.getNumericValue(nextCharacter) to 
                //directly convert char digit to int value.
                System.out.println("Pushing to stack: " + Integer.valueOf(Character.toString(nextCharacter)));
                valueStack.push(Integer.valueOf(Character.toString(nextCharacter)));
            }
            else if(nextCharacter == '+' ||nextCharacter == '-'||nextCharacter == '*'
                    || nextCharacter == '/' || nextCharacter == '^'){

                Integer operandTwo = valueStack.pop();
                Integer operandOne = valueStack.pop();
                System.out.println("Val1: " + operandOne + " Val2: " + operandTwo + " " +nextCharacter);

                Integer result = operate(operandOne, operandTwo, nextCharacter);
                valueStack.push(result);
                System.out.println(result);
            }
            index++;
        }
        return valueStack.peek();
    }

    public static  Integer operate(Integer operand1, Integer operand2, char operation){
        if(operation == '+'){
            return operand1 + operand2;

        } else if (operation == '-') {
            return operand1 - operand2;

        } else if (operation == '*') {
            return operand1 * operand2;

        } else if (operation == '/') {
            return operand1 / operand2;

        } else if (operation == '^') {
            //Issue: ^ is bitwise XOR in Java, not exponentiation. This returns wrong results 
            //(e.g., 2^3 = 1 instead of 8). Solution: use (int) Math.pow(operand1, operand2) for 
            //mathematical exponentiation.
            return operand1^operand2;
        } 

        //Issue: Returning 999999 for unknown operations is poor error handling. 
        //Solution: throw IllegalArgumentException("Unknown operator: " + operation) 
        //for better error reporting.
        return Integer.valueOf(999999);
    }
}