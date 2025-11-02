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

    //--------------------------------------------------------------------------------------------------------------------------------------
    //--------------------------------------------------------------------------------------------------------------------------------------
    //--------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Take in a string and evaluate it as a postfix expression.
     * @param postFix A string that represents a valid postfix expression
     * @return An Integer value that results from the proper evaluation of the give postfix
     */
    public static Integer evaluatePostfix(String postFix){
        
        //Stack will hold operands as they are encountered
        ResizableArrayStack<Integer> valueStack = new ResizableArrayStack<>(postFix.length());
        int index = 0;

        //Loop the span of the string and check each character
        while(index < postFix.length()){
            char nextCharacter = postFix.charAt(index);

            //If a character is an integer push it to the stack
            if(Character.isDigit(nextCharacter)){
                valueStack.push(Character.getNumericValue(nextCharacter));
            }
            //If a character is a known operator perform that operation on the top two stack items
            else if(nextCharacter == '+' ||nextCharacter == '-'||nextCharacter == '*'
                    || nextCharacter == '/' || nextCharacter == '^'){

                Integer operandTwo = valueStack.pop();
                Integer operandOne = valueStack.pop();
                Integer result = operate(operandOne, operandTwo, nextCharacter);
                valueStack.push(result);
            }
            //If a parenthesis open is found assume a multi-digit operand is being input.
            else if( nextCharacter == '('){
                int closeIndex = index;
                //Find the substring leading to the paren close
                while (postFix.charAt(closeIndex) != ')') {
                    closeIndex++; 
                }

                //Add the muti digit integer to the stack and continue
                valueStack.push(Integer.valueOf(postFix.substring(index+1, closeIndex)));
                index = closeIndex;
                continue;
            }//Ignore this character in the case that no condition is hit
            index++;
        }
        return valueStack.peek();
    }

    /**
     * Performs a mathematical operaion given the operator and it's two operands with the structure:
     * (Operand 1, Operator, Operand 2)
     * @param operand1 first operand of expression
     * @param operand2 second operand of expression
     * @param operation the operation to be performed
     * @return result of evaluating the expression
     * @throws IllegalArgumentException If the given operator is not recognized.
     */
    private static Integer operate(Integer operand1, Integer operand2, char operation) throws IllegalArgumentException{
        if(operation == '+'){
            return operand1 + operand2;

        } else if (operation == '-') {
            return operand1 - operand2;

        } else if (operation == '*') {
            return operand1 * operand2;

        } else if (operation == '/') {
            return operand1 / operand2;

        } else if (operation == '^') {
           
            return (int) Math.pow(operand1, operand2);
        } 

       throw new IllegalArgumentException("Unknown operator: " + operation);    
    }
}