package org.okanatov.test.parser;

import java.io.IOException;

class E implements States {
    @Override
    public void handle_id(Parser parser) {
        parser.stack.push(new E_());
        parser.stack.push(new T());
    }

    @Override
    public void handle_plus(Parser parser) {
        System.out.println("Error in state " + this.getClass().getName() + " on plus receive.");
    }

    @Override
    public void handle_minus(Parser parser) throws IOException {
        parser.stack.push(new E_());
        parser.stack.push(new A());
        parser.stack.push(new T());
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
    public void handle_multiply(Parser parser) {
        System.out.println("Error in state " + this.getClass().getName() + " on multiply receive.");
    }

    @Override
    public void handle_division(Parser parser) {
        System.out.println("Error in state " + this.getClass().getName() + " on division receive.");
    }

    @Override
    public void handle_left_brace(Parser parser) {
        parser.stack.push(new E_());
        parser.stack.push(new T());
    }

    @Override
    public void handle_right_brace(Parser parser) {
        System.out.println("Error in state " + this.getClass().getName() + " on right brace receive.");
    }
}
