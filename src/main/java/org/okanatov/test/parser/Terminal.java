package org.okanatov.test.parser;

public enum Terminal implements GrammarSymbols {
    F;

    public boolean isTerminal() {
        return true;
    }
}
