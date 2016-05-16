package org.okanatov.test.parser;

import java.io.IOException;
import java.io.StringReader;
import java.util.Stack;

public class Parser {
    private StringReader sourceExpression;
    private int lookahead;
    private States state;
    private Stack<States> stack;
    private Stack<Integer> operands = new Stack<>();
    private Stack<Character> operators = new Stack<>();

    public Parser(String sourceExpression) throws IOException {
        this.sourceExpression = new StringReader(sourceExpression);
        this.lookahead = this.sourceExpression.read();
        this.state = new E();
        this.stack = new Stack<>();
        this.stack.push(state);
    }

    public int expr() throws IOException {
        while (!stack.empty()) {
            switch (lookahead) {
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    stack.pop();
                    state.handle_id(this, stack, operands, operators, lookahead);
                    break;
                case '+':
                    stack.pop();
                    state.handle_plus(this, stack, operands, operators);
                    break;
                case '-':
                    state.handle_minus(stack, operands, operators);
                    match('-');
                    break;
                case -1:
                    stack.pop();
                    state.handle_empty(stack, operands, operators);
                    break;
                default:
                    System.out.println("Error");
                    break;
            }
            if (!stack.empty())
                state = stack.peek();
        }
        return operands.peek();
    }

    public void match(int c) throws IOException {
        if (lookahead == c) lookahead = sourceExpression.read();
        else throw new Error("syntax error");
    }
}