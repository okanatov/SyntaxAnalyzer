package org.okanatov.test.parser;

import java.util.Stack;

public enum SemanticAction implements Grammar {
    A();

    public void action(Stack<Integer> operands, Stack<Character> operators) {
        Integer op1 = operands.pop();
        Character operator = operators.pop();

        if (operator == 'u') {
            operands.push(-op1);
        } else {
            Integer op2 = operands.pop();

            switch (operator) {
                case '+':
                    operands.push(op1 + op2);
                    break;
                case '-':
                    operands.push(op2 - op1);
                    break;
                case '*':
                    operands.push(op1 * op2);
                    break;

                case '/':
                    operands.push(op2 / op1);
                    break;
            }
        }
    }
}
