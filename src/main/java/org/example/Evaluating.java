package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Evaluating {
    private final Map<String, Integer> variables = new HashMap<>();
    ForLoopHandler forLoopHandler = new ForLoopHandler();
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

    private int applyOpeartorV2(char operator, int a, int b) {
        int result = 0;

        if(operator == '+') result = a + b;
        else if(operator == '-') result = a - b;
        else if(operator == '*') result = a * b;
        else if(operator == '/') {
            if(b == 0){
                System.out.println("Division by zero  detected nigger");
            }
            result = a / b;
        }

        return result;
    }

    public void handlePrint(String line) {
        String varName = line.substring(line.indexOf("(") + 1, line.indexOf(")")).trim();
        System.out.println(variables.getOrDefault(varName, 0));
    }
}
