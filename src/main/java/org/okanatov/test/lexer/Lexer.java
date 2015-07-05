package org.okanatov.test.lexer;

import java.io.IOException;
import java.io.StringReader;

public class Lexer {
    private StringReader sourceExpression;
    private int lineCount = 0;
    private char peek = ' ';

    public Lexer(String string) {
        sourceExpression = new StringReader(string);
    }

    public Token scan() throws IOException {
        for (;; peek = (char) sourceExpression.read()) {
            if (peek == ' ' || peek == '\t') continue;
            else if (peek == '\n') ++lineCount;
            else break;
        }

        if (Character.isDigit(peek)) {
            int val = 0;
            do {
                val = 10 * val + Character.digit(peek, 10);
                peek = (char) sourceExpression.read();
            } while (Character.isDigit(peek));
            return new Num(val);
        } else if (Character.isLetter(peek)) {
            return new Word(Tag.NUM, "word");
        } else if (peek == '+') {
            peek = ' ';
            return new Token(Tag.PLUS);
        } else if (peek == '-') {
            peek = ' ';
            return new Token(Tag.MINUS);
        } else if (peek == '*') {
            peek = ' ';
            return new Token(Tag.MUL);
        } else if (peek == '/') {
            peek = ' ';
            return new Token(Tag.DIV);
        }

        throw new Error("Lexer error");
    }
}
