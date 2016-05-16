package org.okanatov.test.parser;

import java.util.Stack;

public class E implements States {
    @Override
    public void handle_id(Parser parser, Stack<States> stack, Stack<Integer> operands, Stack<Character> operators, int lookahead) {
        stack.push(new E_());
        stack.push(new F());
    }

    @Override
    public void handle_plus(Parser parser, Stack<States> stack, Stack<Integer> operands, Stack<Character> operators) {
        System.out.println("Error in state " + this.getClass().getName() + " on plus receive");
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
        System.out.println("Error in state " + this.getClass().getName() + " on empty receive");
    }
}
