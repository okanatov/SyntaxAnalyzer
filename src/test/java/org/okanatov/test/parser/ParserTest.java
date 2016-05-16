package org.okanatov.test.parser;

import org.junit.Before;
import org.junit.Ignore;
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
    public void testPlus() throws IOException {
        parser = new Parser("1+2+3");
        assertEquals(6, parser.expr());
    }

    @Ignore
    public void testEasy() throws IOException {
        parser = new Parser("1+2*3");
        assertEquals(7, parser.expr());
    }
    @Ignore
    public void testEasy2() throws IOException {
        parser = new Parser("2*3+1");
        assertEquals(7, parser.expr());
    }

    @Ignore
    public void testEasy3() throws IOException {
        parser = new Parser("2*3*1");
        assertEquals(6, parser.expr());
    }

    @Ignore
    public void testEasy4() throws IOException {
        parser = new Parser("2+3+1");
        assertEquals(6, parser.expr());
    }

    @Ignore
    public void testEasy5() throws IOException {
        parser = new Parser("4-3");
        assertEquals(1, parser.expr());
    }

    @Ignore
    public void testEasy6() throws IOException {
        parser = new Parser("-2+3");
        assertEquals(1, parser.expr());
    }

    @Ignore
    public void testEasy7() throws IOException {
        parser = new Parser("-2");
        assertEquals(-2, parser.expr());
    }

    @Ignore
    public void testEasy8() throws IOException {
        parser = new Parser("-2*6");
        assertEquals(-12, parser.expr());
    }

    @Ignore
    public void testEasy9() throws IOException {
        parser = new Parser("6*-2");
        assertEquals(-12, parser.expr());
    }

    @Ignore
    public void testEasy10() throws IOException {
        parser = new Parser("(6)");
        assertEquals(6, parser.expr());
    }

    @Ignore
    public void testEasy11() throws IOException {
        parser = new Parser("(1+2)*3");
        assertEquals(9, parser.expr());
    }

    @Ignore
    public void testEasy12() throws IOException {
        parser = new Parser("2*(3+1)");
        assertEquals(8, parser.expr());
    }

    @Ignore
    public void testEasy13() throws IOException {
        parser = new Parser("9-2+5");
        assertEquals(12, parser.expr());
    }

    @Ignore
    public void testMul() throws IOException {
        parser = new Parser("3*2+5");
        assertEquals(11, parser.expr());
    }

    @Ignore
    public void testComplex() throws IOException {
        parser = new Parser("8-2+4/2+5*2");
        assertEquals(18, parser.expr());
    }

    @Ignore
    public void testBraces() throws IOException {
        parser = new Parser("3*(2+5)");
        assertEquals(21, parser.expr());
    }

    @Ignore
    public void testUnary() throws IOException {
        parser = new Parser("-3+5)");
        assertEquals(2, parser.expr());
    }

    @Ignore
    public void testSuperComplex() throws IOException {
        parser = new Parser("5+(-3)*5/3");
        assertEquals(0, parser.expr());
    }
}
