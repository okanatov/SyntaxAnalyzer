package org.okanatov.test.parser;

public enum SemanticAction implements Grammar {
    A;

    public void action() {
        Character op1 = Parser.operands.pop();
        Character op2 = Parser.operands.pop();
        Character oper = Parser.operators.pop();

        switch (oper) {
            case '+':
                Parser.operands.push(Character.forDigit((Character.digit(op1, 10) + Character.digit(op2, 10)), 10));
                break;
            case '*':
                Parser.operands.push(Character.forDigit((Character.digit(op1, 10) * Character.digit(op2, 10)), 10));
                break;
        }
    }
}
