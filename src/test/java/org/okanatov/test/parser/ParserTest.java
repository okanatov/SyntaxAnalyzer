package org.okanatov.test.parser;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class ParserTest {
    private Parser parser;

    @Before
    public void setUp() {
        parser = null;
    }

    @Test
    public void testEasy() throws IOException {
        parser = new Parser("9-2+5");
        assertEquals(12, parser.expr());
    }

    @Test
    public void testMul() throws IOException {
        parser = new Parser("3*2+5");
        assertEquals(11, parser.expr());
    }

    @Test
    public void testComplex() throws IOException {
        parser = new Parser("8-2+4/2+5*2");
        assertEquals(18, parser.expr());
    }

    @Test
    public void testBraces() throws IOException {
        parser = new Parser("3*(2+5)");
        assertEquals(21, parser.expr());
    }

    @Test
    public void testUnary() throws IOException {
        parser = new Parser("-3+5)");
        assertEquals(2, parser.expr());
    }

    @Test
    public void testSuperComplex() throws IOException {
        parser = new Parser("5+(-3)*5/3");
        assertEquals(0, parser.expr());
    }

}
