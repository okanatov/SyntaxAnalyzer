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

}
