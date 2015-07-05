package org.okanatov.test.parser;

import java.io.IOException;
import java.io.StringReader;

public class Parser {
    private StringReader sourceExpression;
    private int lookahead;

    public Parser(String sourceExpression) throws IOException {
        this.sourceExpression = new StringReader(sourceExpression);
        this.lookahead = this.sourceExpression.read();
    }

    public int expr() throws IOException {
        int left = term();
        int right;

        while (true) {
            switch (lookahead) {
                case '+':
                    match('+'); right = term();
                    left += right;
                    break;
                case '-':
                    match('-'); right = term();
                    left -= right;
                    break;
                default:
                    return left;
            }
        }
    }

    private int term() throws IOException {
        int left = factor();
        int right;

        while (true) {
            switch (lookahead) {
                case '*':
                    match('*'); right = factor();
                    left *= right;
                    break;
                case '/':
                    match('/'); right = factor();
                    left /= right;
                    break;
                default:
                    return left;
            }
        }
    }

    private int factor() throws IOException {
        int result;

        switch ((char) lookahead) {
            case '0': case '1': case '2': case '3': case '4':
            case '5': case '6': case '7': case '8': case '9':
                result = Character.digit(lookahead, 10);
                match(lookahead);
                break;
            case '(':
                match('('); result = expr(); match(')');
                break;
            case '-':
                match('-'); result = -factor();
                break;
            default:
                throw new Error("syntax error");
        }

        return result;
    }

    private void match(int c) throws IOException {
        if (lookahead == c) lookahead = sourceExpression.read();
        else throw new Error("syntax error");
    }
}
