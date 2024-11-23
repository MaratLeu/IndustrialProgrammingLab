package org.example.web_rest;

import net.objecthunter.exp4j.ExpressionBuilder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Expression {
    public static ArrayList<String> evaluateLines(ArrayList<String> lines) {
        ArrayList<String> results = new ArrayList<>();
        for (String line : lines) {
            List<Lexeme> lexemes = Analyse(line);
            LexemeBuffer lexemeBuffer = new LexemeBuffer(lexemes);
            results.add(String.valueOf(expr(lexemeBuffer)));
        }
        return results;
    }

    public static String evaluate_line(String line) {
        List<Lexeme> lexemes = Analyse(line);
        LexemeBuffer lexemeBuffer = new LexemeBuffer(lexemes);
        return String.valueOf(expr(lexemeBuffer));
    }

    public static ArrayList<String> transform_to_arithmetic(ArrayList<String> lines) {
        ArrayList<String> expressions = new ArrayList<>();
        Map<String, Integer> values = new HashMap<>();
        for (String line : lines) {
            if (line.contains("=")) {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    String variable = parts[0].trim();
                    int value = Integer.parseInt(parts[1].trim());
                    values.put(variable, value);
                }
            }
        }
        for (String line : lines) {
            if (!line.contains("=")) {
                String transformedLine = line;
                for (Map.Entry<String, Integer> entry : values.entrySet()) {
                    transformedLine = transformedLine.replace(entry.getKey(), String.valueOf(entry.getValue()));
                }
                expressions.add(transformedLine);
            }
        }
        return expressions;
    }

    public static Set<String> extractVariables(String expression) {
        Set<String> variables = new HashSet<>();
        Pattern pattern = Pattern.compile("[a-z]");
        Matcher matcher = pattern.matcher(expression);
        while (matcher.find()) {
            variables.add(matcher.group());
        }
        return variables;
    }

    public enum LexemeType {
        OPEN_BRACKET, CLOSE_BRACKET,
        PLUS, MINUS,
        MULTIPLY, DIVIDE,
        NUMBER, EOF
    }

    public static class Lexeme {
        LexemeType type;
        String lexeme;

        public Lexeme(LexemeType type, String lexeme) {
            this.type = type;
            this.lexeme = lexeme;
        }

        public Lexeme(LexemeType type, Character lexeme) {
            this.type = type;
            this.lexeme = lexeme.toString();
        }

        public String toString() {
            return "Lexeme{" + "type=" + type + ", lexeme='" + lexeme + '\'' + '}';
        }
    }

    public static class LexemeBuffer {
        private int pos;

        public List<Lexeme> lexemes;

        public LexemeBuffer(List<Lexeme> lexemes) {
            this.lexemes = lexemes;
        }

        public Lexeme next() {
            return lexemes.get(pos++);
        }

        public void back() {
            pos--;
        }

        public int getPos() {
            return pos;
        }
    }

    public static List<Lexeme> Analyse(String expression) {
        ArrayList<Lexeme> lexemes = new ArrayList<>();
        int pos = 0;
        while (pos != expression.length()) {
            char c = expression.charAt(pos);
            switch (c) {
                case '(':
                    lexemes.add(new Lexeme(LexemeType.OPEN_BRACKET, c));
                    pos++;
                    continue;
                case ')':
                    lexemes.add(new Lexeme(LexemeType.CLOSE_BRACKET, c));
                    pos++;
                    continue;
                case '+':
                    lexemes.add(new Lexeme(LexemeType.PLUS, c));
                    pos++;
                    continue;
                case '-':
                    lexemes.add(new Lexeme(LexemeType.MINUS, c));
                    pos++;
                    continue;
                case '*':
                    lexemes.add(new Lexeme(LexemeType.MULTIPLY, c));
                    pos++;
                    continue;
                case '/':
                    lexemes.add(new Lexeme(LexemeType.DIVIDE, c));
                    pos++;
                    continue;
                default:
                    if (c >= '0' && c <= '9') {
                        StringBuilder sb = new StringBuilder();
                        do {
                            sb.append(c);
                            pos++;
                            if (pos >= expression.length()) {
                                break;
                            }
                            c = expression.charAt(pos);
                        } while(c >= '0' && c <= '9');
                        lexemes.add(new Lexeme(LexemeType.NUMBER, sb.toString()));
                    }
                    else {
                        if (c != ' ') {
                            throw new RuntimeException("Unexpected character: '" + c + "'");
                        }
                        pos++;
                    }
            }
        }
        lexemes.add(new Lexeme(LexemeType.EOF, ""));
        return lexemes;
    }

    public static int expr(LexemeBuffer lexemes) {
        Lexeme lexeme = lexemes.next();
        if (lexeme.type == LexemeType.EOF) {
            return 0;
        }
        else {
            lexemes.back();
            return plusminus(lexemes);
        }
    }

    public static int plusminus(LexemeBuffer lexemes) {
        int value = multdiv(lexemes);
        while (true) {
            Lexeme lexeme = lexemes.next();
            switch (lexeme.type) {
                case PLUS:
                    value += multdiv(lexemes);
                    break;
                case MINUS:
                    value -= multdiv(lexemes);
                    break;
                default:
                    lexemes.back();
                    return value;
            }
        }
    }

    public static int multdiv(LexemeBuffer lexemes) {
        int value = factor(lexemes);
        while (true) {
            Lexeme lexeme = lexemes.next();
            switch (lexeme.type) {
                case MULTIPLY:
                    value *= factor(lexemes);
                    break;
                case DIVIDE:
                    value /= factor(lexemes);
                    break;
                default:
                    lexemes.back();
                    return value;
            }
        }
    }

    public static int factor(LexemeBuffer lexemes) {
        Lexeme lexeme = lexemes.next();
        switch (lexeme.type) {
            case NUMBER:
                return Integer.parseInt(lexeme.lexeme);
            case OPEN_BRACKET:
                int value = expr(lexemes);
                lexeme = lexemes.next();
                if (lexeme.type != LexemeType.CLOSE_BRACKET) {
                    throw new RuntimeException("Unexpected token: " + lexeme.lexeme + " at position " + lexemes.getPos());
                }
                return value;
            default:
                throw new RuntimeException("Unexpected token: " + lexeme.lexeme + " at position " + lexemes.getPos());
        }
    }

    // Подсчет с помощью регулярных выражений
    private static final Map<String, Integer> operatorPrecedence = new HashMap<>() {{
        put("*", 2);
        put("/", 2);
        put("+", 1);
        put("-", 1);
    }};

    private static double regex(String expression) {
        expression = expression.replaceAll("\s+", "");
        Deque<Double> operands = new ArrayDeque<>();
        Deque<String> operators = new ArrayDeque<>();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (Character.isDigit(c)) {
                int j = i + 1;
                while (j < expression.length() && (Character.isDigit(expression.charAt(j)) || expression.charAt(j) == '.')) {
                    j++;
                }
                operands.push(Double.parseDouble(expression.substring(i, j)));
                i = j - 1;
            } else if (c == '(') {
                operators.push("(");
            } else if (c == ')') {
                while (!operators.isEmpty() && !operators.peek().equals("(")) {
                    processOperator(operands, operators);
                }
                operators.pop(); // Remove '('
            } else if (operatorPrecedence.containsKey(String.valueOf(c))) {
                while (!operators.isEmpty() && operatorPrecedence.get(String.valueOf(c)) <= operatorPrecedence.get(operators.peek())) {
                    processOperator(operands, operators);
                }
                operators.push(String.valueOf(c));
            }
        }

        while (!operators.isEmpty()) {
            processOperator(operands, operators);
        }

        return operands.pop();
    }

    private static void processOperator(Deque<Double> operands, Deque<String> operators) {
        String operator = operators.pop();
        double num2 = operands.pop();
        double num1 = operands.pop();
        double result = performOperation(num1, operator, num2);
        operands.push(result);
    }

    private static double performOperation(double num1, String operator, double num2) {
        return switch (operator) {
            case "+" -> num1 + num2;
            case "-" -> num1 - num2;
            case "*" -> num1 * num2;
            case "/" -> {
                if (num2 == 0) {
                    throw new IllegalArgumentException("Division by zero");
                }
                yield num1 / num2;
            }
            default -> throw new UnsupportedOperationException("Operator not supported: " + operator);
        };
    }

    public static ArrayList<String> evaluate_with_regex(ArrayList<String> expressions) {
        ArrayList<String> results = new ArrayList<>();
        for (String expression : expressions) {
            var result = regex(expression);
            results.add(String.valueOf(result));
        }
        return results;
    }

    // Подсчет с помощью библиотеки
    public static ArrayList<String> evaluate_with_library(ArrayList<String> expressions) {
        ArrayList<String> results = new ArrayList<>();
        for (String expression : expressions) {
            net.objecthunter.exp4j.Expression exp = new ExpressionBuilder(expression).build();
            var result = exp.evaluate();
            results.add(String.valueOf(result));
        }
        return results;
    }
}
