package org.okanatov.test.parser;

import java.io.IOException;

class A implements States {

    public static final A INSTANCE = new A();

    @Override
    public void handle_id(Parser parser) {
        System.out.println("Error in state " + this.getClass().getName() + " on id receive.");
    }

    @Override
    public void handle_plus(Parser parser) {
        action(parser);
    }

    @Override
    public void handle_minus(Parser parser) {
        action(parser);
    }

    @Override
    public void action(Parser parser) {
        Integer op1 = parser.operands.pop();
        Character operator = parser.operators.pop();

        if (operator == 'u') {
            parser.operands.push(-op1);
        } else {
            Integer op2 = parser.operands.pop();

            switch (operator) {
                case '+':
                    parser.operands.push(op1 + op2);
                    break;
                case '-':
                    parser.operands.push(op2 - op1);
                    break;
                case '*':
                    parser.operands.push(op1 * op2);
                    break;

                case '/':
                    parser.operands.push(op2 / op1);
                    break;
            }
        }
    }

    @Override
    public void handle_empty(Parser parser) {
        action(parser);
    }

    @Override
    public void handle_multiply(Parser parser) throws IOException {
        action(parser);
    }

    @Override
    public void handle_division(Parser parser) throws IOException {
        action(parser);
    }

    @Override
    public void handle_left_brace(Parser parser) throws IOException {
        System.out.println("Error in state " + this.getClass().getName() + " on left brace receive.");
    }

    @Override
    public void handle_right_brace(Parser parser) throws IOException {
        action(parser);
    }
}
