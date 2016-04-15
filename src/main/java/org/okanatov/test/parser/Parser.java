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
    private Map<NonTerminal, Map<Character, Integer>> table = new HashMap<>();

    public Parser(String sourceExpression) throws IOException {
        this.sourceExpression = new StringReader(sourceExpression);
        stack.push(NonTerminal.E);

        this.lookahead = this.sourceExpression.read();
        currentSymbol = stack.peek();

        Map<Character, Integer> e = new HashMap<>();
        e.put('0', 0);
        e.put('1', 0);
        e.put('2', 0);
        e.put('3', 0);
        e.put('4', 0);
        e.put('5', 0);
        e.put('6', 0);
        e.put('7', 0);
        e.put('8', 0);
        e.put('9', 0);
        table.put(NonTerminal.E, e);

        Map<Character, Integer> e_ = new HashMap<>();
        e_.put('+', 1);
        e_.put((char) -1, 5);
        table.put(NonTerminal._E, e_);

        Map<Character, Integer> t = new HashMap<>();
        t.put('0', 2);
        t.put('1', 2);
        t.put('2', 2);
        t.put('3', 2);
        t.put('4', 2);
        t.put('5', 2);
        t.put('6', 2);
        t.put('7', 2);
        t.put('8', 2);
        t.put('9', 2);
        table.put(NonTerminal.T, t);

        Map<Character, Integer> t_ = new HashMap<>();
        t_.put('+', 3);
        t_.put('*', 4);
        t_.put((char) -1, 6);
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

                    if (Character.isDigit(lookahead))
                        operands.push((char) lookahead);
                    else
                        operators.push((char) lookahead);

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
                        case 3: // T'->e
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
                        case 5: // E'->e
                            stack.pop();
                            break;
                        case 6: // T'->e
                            stack.pop();
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
