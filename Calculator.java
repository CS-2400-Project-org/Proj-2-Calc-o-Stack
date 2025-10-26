import java.lang.Character;

public class Calculator 
{
    public String convertToPostfix(String infix)
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
                // Case 1: Operand
                default: 
                if(Character.isLetterOrDigit(nextChar))
                {
                    postfix.append(nextChar);
                }
                break;

                // Case 2: Exponent (right-associative)
                case '^' :
                while (!operatorStack.isEmpty() &&  
                precedence(nextChar) < precedence(operatorStack.peek()))
                {
                    postfix.append(operatorStack.pop());
                }
                operatorStack.push(nextChar);
                break; 

                // Case 3: Operators
                case '+' : case '-' : case '*' : case '/' : 
                while (!operatorStack.isEmpty() &&  
                precedence(nextChar) <= precedence(operatorStack.peek()))
                {
                    postfix.append(operatorStack.pop());
                }
                operatorStack.push(nextChar);
                break;

                // Case 4: Left Parenthesis
                case '(' :
                operatorStack.push(nextChar);
                break;

                // Case 5: Right Parenthesis
                case ')' : // Stack is not empty if infix expression is valid 
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
        while (!operatorStack.isEmpty())
        {
            topOperator = operatorStack.pop();
            postfix.append(topOperator);
        }

        return postfix.toString();       
    }

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

    public Integer evaluatePostfix(String postFix){
    
        ResizableArrayStack<Integer> valueStack = new ResizableArrayStack<>(postFix.length());
        int index = 0;
        while(index < postFix.length()){
            char nextCharacter = postFix.charAt(index);
            if(Character.isDigit(nextCharacter)){

                valueStack.push(Integer.valueOf(nextCharacter));
            }
            else if(nextCharacter == '+' ||nextCharacter == '-'||nextCharacter == '*'
                    || nextCharacter == '/' || nextCharacter == '^'){

                Integer operandTwo = valueStack.pop();
                Integer operandOne = valueStack.pop();

                Integer result = operate(operandOne, operandTwo, nextCharacter);
                valueStack.push(result);
            }
            index++;
        }

        return valueStack.peek();
    }

    public Integer operate(Integer operand1, Integer operand2, char operation){
        if(operation == '+'){
            return operand1 + operand2;

        } else if (operation == '-') {
            return operand1 - operand2;

        } else if (operation == '*') {
            return operand1 * operand2;

        } else if (operation == '/') {
            return operand1 / operand2;

        } else if (operation == '^') {
            return operand1^operand2;
        } 

        return Integer.valueOf(999999);
    }
}