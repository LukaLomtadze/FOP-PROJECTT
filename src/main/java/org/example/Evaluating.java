package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Evaluating {
    private final Map<String, Integer> variables = new HashMap<>();
    ForLoopHandler forLoopHandler = new ForLoopHandler();
    WhileLoopHandler whileLoopHandler = new WhileLoopHandler();
    IfElseHandler ifElseHandler = new IfElseHandler();

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
                currentLineIndex++;
            } else if (line.startsWith("for")) {
                currentLineIndex = forLoopHandler.handleForLoop(lines, currentLineIndex, variables, this);
            } else if (line.startsWith("while")) {
                currentLineIndex = whileLoopHandler.handleWhileLoop(lines, currentLineIndex, variables, this);
            } else if (line.startsWith("if") || line.startsWith("elif") || line.startsWith("else")) {
                currentLineIndex = ifElseHandler.handleIfElse(lines, currentLineIndex, variables, this);
            } else if (line.contains("print")) {
                handlePrint(line);
                currentLineIndex++;
            } else {
                currentLineIndex++;
            }
        }
    }





    public void handleAssignment(String line) {
        String[] parts = line.split("=");
        variables.put(parts[0].trim(), evaluateExpression(parts[1].trim()));
    }

    public int evaluateExpression(String expression) {
        expression = expression.trim();

        // Directly return if it's a numeric literal
        if (expression.matches("\\d+")) {
            return Integer.parseInt(expression);
        }

        // Directly return if it's a single variable
        if (expression.matches("[a-zA-Z]+")) {
            return variables.getOrDefault(expression, 0);
        }

        // Handle comparison operators like == and !=
        if (expression.contains(">") || expression.contains("<") || expression.contains("==") || expression.contains("!=")) {
            return evaluateComparisonExpression(expression);
        }

        // Handle logical expressions like && and ||
        if (expression.contains("and") || expression.contains("or")) {
            return evaluateLogicalExpression(expression);
        }

        // Arithmetic operations (+, -, *, /)
        Stack<Integer> values = new Stack<>();
        Stack<Character> operators = new Stack<>();
        String[] tokens = expression.split("(?<=[-+*/()%])|(?=[-+*/()%])|\\s+");

        for (String token : tokens) {
            token = token.trim();
            if (token.isEmpty()) {
                continue; // Skip empty tokens
            }
            if (token.matches("\\d+")) {
                values.push(Integer.parseInt(token));
            } else if (token.matches("[a-zA-Z]+")) {
                values.push(variables.getOrDefault(token, 0));
            } else if ("+-*/%".contains(token)) {
                while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(token.charAt(0))) {
                    values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
                }
                operators.push(token.charAt(0));
            } else {
                throw new IllegalArgumentException("Invalid token: " + token);
            }
        }


        while (!operators.isEmpty()) {
            values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
        }
        return values.pop();
    }



    private int evaluateComparisonExpression(String expression) {
        String[] operators = {"==", "!=", ">", "<"};
        for (String operator : operators) {
            int operatorIndex = expression.indexOf(operator);
            if (operatorIndex != -1) {
                String left = expression.substring(0, operatorIndex).trim();
                String right = expression.substring(operatorIndex + operator.length()).trim();

                int leftValue = evaluateExpression(left);
                int rightValue = evaluateExpression(right);

                return switch (operator) {
                    case "==" -> leftValue == rightValue ? 1 : 0;
                    case "!=" -> leftValue != rightValue ? 1 : 0;
                    case ">" -> leftValue > rightValue ? 1 : 0;
                    case "<" -> leftValue < rightValue ? 1 : 0;
                    default -> throw new IllegalArgumentException("Unknown operator: " + operator);
                };
            }
        }
        throw new IllegalArgumentException("Invalid comparison expression: " + expression);
    }



    private int evaluateLogicalExpression(String expression) {
        // Handle logical AND (&&) and OR (||)
        if (expression.contains("and")) {
            String[] parts = expression.split("and");
            for (String part : parts) {
                if (evaluateExpression(part.trim()) == 0) {
                    return 0;
                }
            }
            return 1;
        } else if (expression.contains("or")) {
            String[] parts = expression.split("\\|\\|");
            for (String part : parts) {
                if (evaluateExpression(part.trim()) != 0) {
                    return 1;
                }
            }
            return 0;
        } else {
            throw new IllegalArgumentException("Invalid logical expression: " + expression);
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
            case '%' -> a % b;
            default -> 0;
        };
    }


    public void handlePrint(String line) {
        try {
            int startIndex = line.indexOf("(") + 1;
            int endIndex = line.lastIndexOf(")");
            if (startIndex > 0 && endIndex > startIndex) {
                String varName = line.substring(startIndex, endIndex).trim();
                if (variables.containsKey(varName)) {
                    System.out.println(variables.get(varName));
                } else {
                    System.out.println("Undefined variable: " + varName);
                }
            } else {
                throw new IllegalArgumentException("Invalid print statement: " + line);
            }
        } catch (StringIndexOutOfBoundsException e) {
            System.err.println("Error in handlePrint: Invalid format in line: " + line);
        }
    }

}
