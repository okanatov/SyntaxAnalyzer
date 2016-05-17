package org.okanatov.test.parser;

import java.io.IOException;
import java.io.StringReader;
import java.util.Stack;

public class Parser {
    private StringReader sourceExpression;
    private States state;

    int lookahead;
    Stack<States> stack;
    Stack<Integer> operands = new Stack<>();
    Stack<Character> operators = new Stack<>();

    public Parser(String sourceExpression) throws IOException {
        this.sourceExpression = new StringReader(sourceExpression);
        this.lookahead = this.sourceExpression.read();
        this.state = E.INSTANCE;
        this.stack = new Stack<>();
        this.stack.push(state);
    }

    public int expr() throws IOException {
        while (!stack.empty()) {
            stack.pop();
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
                    state.handle_id(this);
                    break;
                case '+':
                    state.handle_plus(this);
                    break;
                case '-':
                    state.handle_minus(this);
                    break;
                case '*':
                    state.handle_multiply(this);
                    break;
                case '/':
                    state.handle_division(this);
                    break;
                case '(':
                    state.handle_left_brace(this);
                    break;
                case ')':
                    state.handle_right_brace(this);
                    break;
                case -1:
                    state.handle_empty(this);
                    break;
                default:
                    System.out.println("Error: unknown character.");
                    break;
            }
            if (!stack.empty())
                state = stack.peek();
        }
        return operands.peek();
    }

    void match(int c) throws IOException {
        if (lookahead == c) lookahead = sourceExpression.read();
        else throw new Error("Error: syntax error.");
    }
}