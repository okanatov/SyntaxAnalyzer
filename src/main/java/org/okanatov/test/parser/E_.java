package org.okanatov.test.parser;

import java.io.IOException;
import java.util.Stack;

public class E_ implements States {

    @Override
    public void handle_id(Parser parser, Stack<States> stack, Stack<Integer> operands, Stack<Character> operators, int lookahead) {
        System.out.println("Error in state " + this.getClass().getName() + " on id receive");
    }

    @Override
    public void handle_plus(Parser parser, Stack<States> stack, Stack<Integer> operands, Stack<Character> operators) throws IOException {
        stack.push(new E_());
        stack.push(new A());
        stack.push(new F());
        operators.push('+');
        parser.match('+');
    }

    @Override
    public void handle_minus(Stack<States> stack, Stack<Integer> operands, Stack<Character> operators) {
        System.out.println("Error in state " + this.getClass().getName() + " on minus receive");
    }

    @Override
    public void action(Stack<Integer> operands, Stack<Character> operators) {
        System.out.println("Error in state " + this.getClass().getName() + " on action receive");
    }

    @Override
    public void handle_empty(Stack<States> stack, Stack<Integer> operands, Stack<Character> operators) {
        // empty implementation
    }
}
