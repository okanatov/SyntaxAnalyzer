package org.okanatov.test.parser;

import java.io.IOException;

class F implements States {
    @Override
    public void handle_id(Parser parser) throws IOException {
        parser.operands.push(Character.digit(parser.lookahead, 10));
        parser.match(parser.lookahead);
    }

    @Override
    public void handle_plus(Parser parser) {
        System.out.println("Error in state " + this.getClass().getName() + " on plus receive.");
    }

    @Override
    public void handle_minus(Parser parser) throws IOException {
        parser.stack.push(new A());
        parser.stack.push(new F());
        parser.operators.push('u');
        parser.match('-');
    }

    @Override
    public void action(Parser parser) {
        System.out.println("Error in state " + this.getClass().getName() + " on action receive.");
    }

    @Override
    public void handle_empty(Parser parser) {
        System.out.println("Error in state " + this.getClass().getName() + " on empty receive.");
    }

    @Override
    public void handle_multiply(Parser parser) throws IOException {
        System.out.println("Error in state " + this.getClass().getName() + " on multiply receive.");
    }

    @Override
    public void handle_division(Parser parser) throws IOException {
        System.out.println("Error in state " + this.getClass().getName() + " on division receive.");
    }

    @Override
    public void handle_left_brace(Parser parser) throws IOException {
        parser.stack.push(new F());
        parser.stack.push(new E());
        parser.match('(');
    }

    @Override
    public void handle_right_brace(Parser parser) throws IOException {
        parser.match(')');
    }
}
