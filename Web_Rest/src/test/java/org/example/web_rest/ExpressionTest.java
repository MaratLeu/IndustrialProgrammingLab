package org.example.web_rest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ExpressionTest {
    @Test
    void testEvaluate() {
        ArrayList<String> expressions = new ArrayList<>();
        expressions.add("2 / (1 + 1) * 3 + 5");
        expressions.add("10 + 20 - a * b");
        expressions.add("a = 5");
        expressions.add("b = 5");

        ArrayList<String> expected_results = new ArrayList<>();
        expected_results.add("8");
        expected_results.add("5");

        ArrayList<String> real_results = Expression.evaluateLines(expressions);
        assert expected_results.equals(real_results);
    }

    @Test
    void testLexemeBuffer() {
        List<Expression.Lexeme> lexemes = new ArrayList<>();
        lexemes.add(new Expression.Lexeme(Expression.LexemeType.NUMBER, "5"));
        lexemes.add(new Expression.Lexeme(Expression.LexemeType.PLUS, "+"));
        lexemes.add(new Expression.Lexeme(Expression.LexemeType.NUMBER, "3"));

        Expression.LexemeBuffer buffer = new Expression.LexemeBuffer(lexemes);

        assertEquals("5", buffer.next().lexeme);
        assertEquals(1, buffer.getPos());
        assertEquals("+", buffer.next().lexeme);
        buffer.back();
        assertEquals("+", buffer.next().lexeme);
        assertEquals("3", buffer.next().lexeme);
    }

    @Test
    void testAnalyseValidExpression() {
        String expression = "3 + 5 * (2 - 8)";
        List<Expression.Lexeme> lexemes = Expression.Analyse(expression);

        assertEquals(7, lexemes.size());
        assertEquals(Expression.LexemeType.NUMBER, lexemes.get(0).type);
        assertEquals("3", lexemes.get(0).lexeme);
        assertEquals(Expression.LexemeType.PLUS, lexemes.get(1).type);
        assertEquals(Expression.LexemeType.OPEN_BRACKET, lexemes.get(2).type);
        assertEquals(Expression.LexemeType.NUMBER, lexemes.get(3).type);
        assertEquals("2", lexemes.get(3).lexeme);
    }

    @Test
    void testAnalyseInvalidCharacter() {
        String expression = "3 + 5 & 2";

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            Expression.Analyse(expression);
        });

        assertEquals("Unexpected character: '&'", thrown.getMessage());
    }

    @Test
    void testEvaluateExpression() {
        List<Expression.Lexeme> lexemes = Expression.Analyse("3 + 5 * (2 - 8)");
        Expression.LexemeBuffer buffer = new Expression.LexemeBuffer(lexemes);

        int result = Expression.expr(buffer);
        assertEquals(-27, result);
    }

    @Test
    void testDivisionByZero() {
        RuntimeException thrown = assertThrows(IllegalArgumentException.class, () -> {
            Expression.evaluate_with_regex(new ArrayList<>(List.of("10 / 0")));
        });

        assertEquals("Division by zero", thrown.getMessage());
    }

    @Test
    void testEvaluateWithRegex() {
        ArrayList<String> expressions = new ArrayList<>();
        expressions.add("2 / 1 + 1 * 3 + 5");
        expressions.add("10 + 20 - a * b");
        expressions.add("a = 5");
        expressions.add("b = 5");

        ArrayList<String> expected_results = new ArrayList<>();
        expected_results.add("10.0");
        expected_results.add("5.0");

        ArrayList<String> real_results = Expression.evaluate_with_regex(expressions);
        assert expected_results.equals(real_results);
    }

    @Test
    void testEvaluateWithLibrary() {
        ArrayList<String> expressions = new ArrayList<>();
        expressions.add("2 / 1 + 1 * 3 + 5");
        expressions.add("10 + 20 - a * b");
        expressions.add("a = 5");
        expressions.add("b = 5");

        ArrayList<String> expected_results = new ArrayList<>();
        expected_results.add("10.0");
        expected_results.add("5.0");

        ArrayList<String> real_results = Expression.evaluate_with_library(expressions);
        assert expected_results.equals(real_results);
    }
}
