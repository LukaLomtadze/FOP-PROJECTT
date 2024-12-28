package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Evaluating {
    private final Map<String, Integer> variables = new HashMap<>();
    ForLoopHandler forLoopHandler = new ForLoopHandler();
    WhileLoopHandler whileLoopHandler = new WhileLoopHandler();

    public void eval(String code) {
        String[] lines = code.split("\n");
        int currentLineIndex = 0;

        while (currentLineIndex < lines.length) {
            String line = lines[currentLineIndex].trim();
            if (line.isEmpty()) {
                currentLineIndex++;
                continue;
            }

            if (line.contains("=")) {
                handleAssignment(line);
            } else if (line.startsWith("for")) {
                currentLineIndex = forLoopHandler.handleForLoop(lines, currentLineIndex, variables, this);
            } else if (line.startsWith("while")) {
                currentLineIndex = whileLoopHandler.handleWhileLoop(lines, currentLineIndex, variables, this);
            } else if (line.contains("print")) {
                handlePrint(line);
            }

            currentLineIndex++;
        }
    }

    public void handleAssignment(String line) {
        String[] parts = line.split("=");
        variables.put(parts[0].trim(), evaluateExpression(parts[1].trim()));
    }

    public int evaluateExpression(String expression) {
        expression = expression.trim();


        if (expression.matches("\\d+")) {
            return Integer.parseInt(expression);
        }

        if (expression.matches("[a-zA-Z]+")) {
            return variables.getOrDefault(expression, 0);
        }

        // Handle comparison expressions
        if (expression.contains(">") || expression.contains("<") || expression.contains("==") || expression.contains("!=")) {
            return evaluateComparisonExpression(expression);
        }

        // Handle arithmetic expressions (without comparison)
        Stack<Integer> values = new Stack<>();
        Stack<Character> operators = new Stack<>();
        String[] tokens = expression.split("(?<=\\d)(?=[+-/*])|(?<=[+-/*])(?=\\d)|\\s+");

        for (String token : tokens) {
            token = token.trim();
            if (token.matches("\\d+")) {
                values.push(Integer.parseInt(token));
            } else if (token.matches("[a-zA-Z]+")) {
                values.push(variables.getOrDefault(token, 0));
            } else if ("+-/*".contains(token)) {
                while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(token.charAt(0))) {
                    values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
                }
                operators.push(token.charAt(0));
            }
        }

        while (!operators.isEmpty()) {
            values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
        }
        return values.pop();
    }

    // New method to handle comparison operators
    private int evaluateComparisonExpression(String expression) {
        String[] parts = expression.split("\\s+");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid comparison expression: " + expression);
        }

        int left = evaluateExpression(parts[0]);
        int right = evaluateExpression(parts[2]);
        String operator = parts[1];

        switch (operator) {
            case ">":
                return left > right ? 1 : 0;
            case "<":
                return left < right ? 1 : 0;
            case "==":
                return left == right ? 1 : 0;
            case "!=":
                return left != right ? 1 : 0;
            default:
                throw new IllegalArgumentException("Unknown comparison operator: " + operator);
        }
    }

    private int precedence(char operator) {
        return switch (operator) {
            case '+', '-' -> 1;
            case '*', '/' -> 2;
            default -> 0;
        };
    }

    private int applyOperator(char operator, int b, int a) {
        return switch (operator) {
            case '+' -> a + b;
            case '-' -> a - b;
            case '*' -> a * b;
            case '/' -> a / b;
            default -> 0;
        };
    }

    public void handlePrint(String line) {
        String varName = line.substring(line.indexOf("(") + 1, line.indexOf(")")).trim();
        System.out.println(variables.getOrDefault(varName, 0));
    }
}
