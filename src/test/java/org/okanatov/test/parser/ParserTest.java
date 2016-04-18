package org.okanatov.test.parser;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ParserTest {
    private Parser parser;

    @Before
    public void setUp() {
        parser = null;
    }

    @Test
    public void testEasy() throws IOException {
        parser = new Parser("1+2*3");
        assertEquals(7, parser.expr());
    }
    @Test
    public void testEasy2() throws IOException {
        parser = new Parser("2*3+1");
        assertEquals(7, parser.expr());
    }

    @Test
    public void testEasy3() throws IOException {
        parser = new Parser("2*3*1");
        assertEquals(6, parser.expr());
    }

    @Test
    public void testEasy4() throws IOException {
        parser = new Parser("2+3+1");
        assertEquals(6, parser.expr());
    }

    @Test
    public void testEasy5() throws IOException {
        parser = new Parser("4-3");
        assertEquals(1, parser.expr());
    }

    @Test
    public void testEasy6() throws IOException {
        parser = new Parser("-2+3");
        assertEquals(1, parser.expr());
    }

    @Test
    public void testEasy7() throws IOException {
        parser = new Parser("-2");
        assertEquals(-2, parser.expr());
    }

    @Test
    public void testEasy8() throws IOException {
        parser = new Parser("-2*6");
        assertEquals(-12, parser.expr());
    }
    @Test
    public void testEasy9() throws IOException {
        parser = new Parser("6*-2");
        assertEquals(-12, parser.expr());
    }
}
