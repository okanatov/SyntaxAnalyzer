package org.okanatov.test.parser;

import java.util.Stack;

public class A implements States {
    @Override
    public void handle_id(Parser parser, Stack<States> stack, Stack<Integer> operands, Stack<Character> operators, int lookahead) {
        System.out.println("Error in state " + this.getClass().getName() + " on id receive");
    }

    @Override
    public void handle_plus(Parser parser, Stack<States> stack, Stack<Integer> operands, Stack<Character> operators) {
        action(operands, operators);
    }

    @Override
    public void handle_minus(Stack<States> stack, Stack<Integer> operands, Stack<Character> operators) {
        System.out.println("Error in state " + this.getClass().getName() + " on minus receive");
    }

    @Override
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

    @Override
    public void handle_empty(Stack<States> stack, Stack<Integer> operands, Stack<Character> operators) {
        action(operands, operators);
    }
}
