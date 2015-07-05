package org.okanatov.test.lexer;

public class Num extends Token {
    public final int value;
    public Num(int tag, int value) {
        super(Tag.NUM);
        this.value = value;
    }
}
