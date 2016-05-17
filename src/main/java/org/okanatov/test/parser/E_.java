package org.okanatov.test.parser;

import java.io.IOException;

class E_ implements States {
    @Override
    public void handle_id(Parser parser) {
        System.out.println("Error in state " + this.getClass().getName() + " on id receive.");
    }

    @Override
    public void handle_plus(Parser parser) throws IOException {
        parser.stack.push(new E_());
        parser.stack.push(new A());
        parser.stack.push(new T());
        parser.operators.push('+');
        parser.match('+');
    }

    @Override
    public void handle_minus(Parser parser) throws IOException {
        parser.stack.push(new E_());
        parser.stack.push(new A());
        parser.stack.push(new T());
        parser.operators.push('-');
        parser.match('-');
    }

    @Override
    public void action(Parser parser) {
        System.out.println("Error in state " + this.getClass().getName() + " on action receive.");
    }

    @Override
    public void handle_empty(Parser parser) {
        // empty implementation
    }

    @Override
    public void handle_multiply(Parser parser) {
        System.out.println("Error in state " + this.getClass().getName() + " on multiply receive.");
        throw new Error();
    }

    @Override
    public void handle_division(Parser parser) {
        System.out.println("Error in state " + this.getClass().getName() + " on division receive.");
    }

    @Override
    public void handle_left_brace(Parser parser) {
        System.out.println("Error in state " + this.getClass().getName() + " on left_brace receive.");
    }

    @Override
    public void handle_right_brace(Parser parser) throws IOException {
        // empty implementation
    }
}
