package org.okanatov.test.parser;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Parser {
    private StringReader sourceExpression;
    private int lookahead;
    private Grammar currentSymbol;
    private Stack<Grammar> stack = new Stack<>();
    private Map<Grammar, Map<Character, Integer>> table = new HashMap<>();

    public Parser(String sourceExpression) throws IOException {
        this.sourceExpression = new StringReader(sourceExpression);
        this.lookahead = this.sourceExpression.read();

        Map<Character, Integer> e = new HashMap<>();

        for (int i = 0; i < 10; i++)
            e.put(Character.forDigit(i, 10), 0);
        e.put('-', 6);

        table.put(NonTerminal.E, e);

        Map<Character, Integer> e_ = new HashMap<>();
        e_.put('+', 1);
        e_.put('-', 5);
        e_.put(Character.MAX_VALUE, 3);
        table.put(NonTerminal._E, e_);

        Map<Character, Integer> t = new HashMap<>();

        for (int i = 0; i < 10; i++)
            t.put(Character.forDigit(i, 10), 2);

        table.put(NonTerminal.T, t);

        Map<Character, Integer> t_ = new HashMap<>();
        t_.put('+', 3);
        t_.put('-', 3);
        t_.put('*', 4);
        t_.put(Character.MAX_VALUE, 3);
        table.put(NonTerminal._T, t_);
    }

    public int expr() throws IOException {
        stack.push(NonTerminal.E);
        Stack<Integer> operands = new Stack<>();
        Stack<Character> operators = new Stack<>();

        currentSymbol = stack.peek();

        while (!stack.empty()) {
            if (currentSymbol instanceof SemanticAction) {
                ((SemanticAction) currentSymbol).action(operands, operators);
                stack.pop();
            } else {
                if (currentSymbol instanceof Terminal) {
                    if (Character.isDigit((char) lookahead)) {
                        stack.pop();
                        operands.push(Character.digit(lookahead, 10));
                        match(lookahead);
                    } else if (lookahead == '-') {
                        stack.pop();
                        stack.push(SemanticAction.A);
                        stack.push(Terminal.F);
                        operators.push('u');
                        match('-');
                    } else {
                        System.out.println("Error");
                    }
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
                        case 5: // E'->-TE'
                            stack.pop();
                            stack.push(NonTerminal._E);
                            stack.push(SemanticAction.A);
                            stack.push(NonTerminal.T);
                            operators.push('-');
                            match('-');
                            break;
                        case 6: // -E->-TE'
                            stack.pop();
                            stack.push(NonTerminal._E);
                            stack.push(SemanticAction.A);
                            stack.push(NonTerminal.T);
                            operators.push('u');
                            match('-');
                        default:
                            System.out.println("Error");
                    }
                }
            }
            if (!stack.empty())
                currentSymbol = stack.peek();
        }

        return operands.peek();
    }

    private void match(int c) throws IOException {
        if (lookahead == c) lookahead = sourceExpression.read();
        else throw new Error("syntax error");
    }
}