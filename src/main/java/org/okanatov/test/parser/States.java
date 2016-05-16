package org.okanatov.test.parser;

import java.io.IOException;
import java.util.Stack;

public interface States {
    void handle_id(Parser parser, Stack<States> stack, Stack<Integer> operands, Stack<Character> operators, int lookahead) throws IOException;
    void handle_plus(Parser parser, Stack<States> stack, Stack<Integer> operands, Stack<Character> operators) throws IOException;
    void handle_minus(Stack<States> stack, Stack<Integer> operands, Stack<Character> operators);
    void action(Stack<Integer> operands, Stack<Character> operators);
    void handle_empty(Stack<States> stack, Stack<Integer> operands, Stack<Character> operators);
}
