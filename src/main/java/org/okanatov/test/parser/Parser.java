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
        e.put('(', 9);

        table.put(NonTerminal.E, e);

        Map<Character, Integer> e_ = new HashMap<>();
        e_.put('+', 1);
        e_.put('-', 5);
        e_.put(')', 3);
        e_.put(Character.MAX_VALUE, 3);
        table.put(NonTerminal._E, e_);

        Map<Character, Integer> t = new HashMap<>();

        for (int i = 0; i < 10; i++)
            t.put(Character.forDigit(i, 10), 2);
        t.put('(', 10);

        table.put(NonTerminal.T, t);

        Map<Character, Integer> t_ = new HashMap<>();
        t_.put('+', 3);
        t_.put('-', 3);
        t_.put('*', 4);
        t_.put(')', 3);
        t_.put(Character.MAX_VALUE, 3);
        table.put(NonTerminal._T, t_);

        Map<Character, Integer> f = new HashMap<>();
        for (int i = 0; i < 10; i++)
            f.put(Character.forDigit(i, 10), 7);
        f.put('-', 8);
        f.put('(', 11);
        f.put(')', 12);
        table.put(Terminal.F, f);
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
                        break;
                    case 7: // F->id
                        stack.pop();
                        operands.push(Character.digit(lookahead, 10));
                        match(lookahead);
                        break;
                    case 8: // F->-id
                        stack.pop();
                        stack.push(SemanticAction.A);
                        stack.push(Terminal.F);
                        operators.push('u');
                        match('-');
                        break;
                    case 9: // E->(TE')
                        stack.pop();
                        stack.push(NonTerminal._E);
                        stack.push(NonTerminal.T);
                        break;
                    case 10: // T->(FT')
                        stack.pop();
                        stack.push(NonTerminal._T);
                        stack.push(Terminal.F);
                        break;
                    case 11: // F->(E)
                        stack.pop();
                        match('(');
                        stack.push(Terminal.F);
                        stack.push(NonTerminal.E);
                        break;
                    case 12: // E'->) | T'->) | F->)
                        stack.pop();
                        match(')');
                        break;
                    default:
                        System.out.println("Error");
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