import java.lang.Character;
public class Calculator {
    



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