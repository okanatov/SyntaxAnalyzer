package org.okanatov.test.lexer;

public class Word extends Token {
    public final String lexeme;
    public Word(Tag tag, String string) {
        super(tag);
        lexeme = string;
    }
}
