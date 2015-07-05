package org.okanatov.test.lexer;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class LexerTest {
    private Lexer lexer;
    private Token token;

    @Before
    public void setUp() throws Exception {
        lexer = new Lexer("2 + 3");
    }

    @Test
    public void testSimple() throws IOException {
        token = lexer.scan();
        assertEquals(Tag.NUM, token.tag);

        token = lexer.scan();
        assertEquals(Tag.PLUS, token.tag);

        token = lexer.scan();
        assertEquals(Tag.NUM, token.tag);
    }
}