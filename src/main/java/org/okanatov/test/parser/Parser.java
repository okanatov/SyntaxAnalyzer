package org.okanatov.test.parser;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Stack;
import java.util.function.Consumer;

public class Parser {
    private StringReader sourceExpression;
    int lookahead;
    Stack<States> stack = new Stack<>();
    private ArrayList<Transition> transitions = new ArrayList<>();
    private States currentState;

    public Parser(String sourceExpression) throws IOException {
        this.sourceExpression = new StringReader(sourceExpression);
        this.lookahead = this.sourceExpression.read();

        addTransition(States.E, (Parser parser) -> {
            System.out.println((char) parser.lookahead);
            System.out.println(parser.stack);
        } );
    }

    public int expr() throws IOException {
        stack.push(States.E);
        currentState = stack.peek();

        while (!stack.empty()) {
            transitions.forEach(value -> { if (value.isStatesEqual(currentState)) value.action(this); });
            stack.pop();

            if (!stack.empty())
                currentState = stack.peek();
        }

        return 0;
    }

    private void match(int c) throws IOException {
        if (lookahead == c) lookahead = sourceExpression.read();
        else throw new Error("syntax error");
    }

    private void addTransition(States state, Consumer<Parser> action) {
        transitions.add(new Transition(state, action));
    }
}

enum States {
    E, E_, T, T_, F
}

class Transition {
    private States state;
    private Consumer<Parser> action;

    public Transition(States state, Consumer<Parser> action) {
        this.state = state;
        this.action = action;
    }

    public boolean isStatesEqual(States state) {
        return this.state.getClass() == state.getClass();
    }

    public void action(Parser parser) {
        action.accept(parser);
    }
}