package org.okanatov.test.parser;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Parser {
    private StringReader sourceExpression;
    private int lookahead;
    private Stack<Grammar> stack = new Stack<>();
    private Grammar currentSymbol;
    public static Stack<Character> operands = new Stack<>();
    public static Stack<Character> operators = new Stack<>();
    private Map<Grammar, Map<Character, Integer>> table = new HashMap<>();

    public Parser(String sourceExpression) throws IOException {
        this.sourceExpression = new StringReader(sourceExpression);
        stack.push(NonTerminal.E);

        this.lookahead = this.sourceExpression.read();
        currentSymbol = stack.peek();

        Map<Character, Integer> e = new HashMap<>();

        for (int i = 0; i < 10; i++)
            e.put(Character.forDigit(i, 10), 0);

        table.put(NonTerminal.E, e);

        Map<Character, Integer> e_ = new HashMap<>();
        e_.put('+', 1);
        e_.put((char) -1, 3);
        table.put(NonTerminal._E, e_);

        Map<Character, Integer> t = new HashMap<>();

        for (int i = 0; i < 10; i++)
            t.put(Character.forDigit(i, 10), 2);

        table.put(NonTerminal.T, t);

        Map<Character, Integer> t_ = new HashMap<>();
        t_.put('+', 3);
        t_.put('*', 4);
        t_.put((char) -1, 3);
        table.put(NonTerminal._T, t_);
    }

    public int expr() throws IOException {
        while (!stack.empty()) {
            if (currentSymbol instanceof SemanticAction) {
                ((SemanticAction) currentSymbol).action();
                stack.pop();
            } else {
                if (((GrammarSymbols) currentSymbol).isTerminal()) {
                    stack.pop();
                    operands.push((char) lookahead);
                    match(lookahead);
                } else {
                    switch (table.get(currentSymbol).get((char) lookahead)) {
                        case 0: // E->TE'
                            stack.pop();
                            stack.push(NonTerminal._E);
                            stack.push(NonTerminal.T);
                            break;
                        case 1: // E'->+TE'
                            stack.pop();
                            stack.push(NonTerminal._E);
                            stack.push(SemanticAction.A);
                            stack.push(NonTerminal.T);
                            operators.push('+');
                            match('+');
                            break;
                        case 2: // T->FT'
                            stack.pop();
                            stack.push(NonTerminal._T);
                            stack.push(Terminal.F);
                            break;
                        case 3: // T'->e | E'->e
                            stack.pop();
                            break;
                        case 4: // T'->*FT'
                            stack.pop();
                            stack.push(NonTerminal._T);
                            stack.push(SemanticAction.A);
                            stack.push(Terminal.F);
                            operators.push('*');
                            match('*');
                            break;
                        default:
                            System.out.println("Error");
                    }
                }
            }
            if (!stack.empty())
                currentSymbol = stack.peek();
        }

        return Character.digit(operands.peek(), 10);
    }

    private void match(int c) throws IOException {
        if (lookahead == c) lookahead = sourceExpression.read();
        else throw new Error("syntax error");
    }
}