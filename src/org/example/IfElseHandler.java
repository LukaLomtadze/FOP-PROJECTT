package org.example;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IfElseHandler {

    private static final String IF_STATEMENT_REGEX = "^\\s*if\\s+(.*)\\s*:\\s*$";
    private static final String ELIF_STATEMENT_REGEX = "^\\s*elif\\s+(.*)\\s*:\\s*$";
    private static final String ELSE_STATEMENT_REGEX = "^\\s*else\\s*:\\s*$";

    public int handleIfElse(String[] lines, int currentIndex, Map<String, Integer> variables, Evaluating evaluator) {
        boolean conditionMatched = false;
        int currentLineIndex = currentIndex;

        while (currentLineIndex < lines.length) {
            String line = lines[currentLineIndex].trim();

            if (line.isEmpty()) {
                currentLineIndex++;
                continue;
            }

            Matcher ifMatcher = Pattern.compile(IF_STATEMENT_REGEX).matcher(line);
            Matcher elifMatcher = Pattern.compile(ELIF_STATEMENT_REGEX).matcher(line);
            Matcher elseMatcher = Pattern.compile(ELSE_STATEMENT_REGEX).matcher(line);

            if (ifMatcher.matches() || elifMatcher.matches()) {

                if (conditionMatched) {
                    currentLineIndex = skipBlock(lines, currentLineIndex);
                    continue;
                }


                String conditionExpression = ifMatcher.matches() ? ifMatcher.group(1).trim() : elifMatcher.group(1).trim();
                if (evaluator.evaluateExpression(conditionExpression) != 0) {
                    conditionMatched = true;
                    currentLineIndex = executeBlock(lines, currentLineIndex + 1, variables, evaluator);
                } else {
                    currentLineIndex = skipBlock(lines, currentLineIndex);
                }
            } else if (elseMatcher.matches()) {

                if (!conditionMatched) {
                    conditionMatched = true;
                    currentLineIndex = executeBlock(lines, currentLineIndex + 1, variables, evaluator);
                } else {
                    currentLineIndex = skipBlock(lines, currentLineIndex);
                }
            } else {

                break;
            }
        }


        return currentLineIndex;
    }


    private int executeBlock(String[] lines, int startIndex, Map<String, Integer> variables, Evaluating evaluator) {
        StringBuilder block = new StringBuilder();
        int currentIndex = startIndex;

        while (currentIndex < lines.length && lines[currentIndex].startsWith("    ")) {
            block.append(lines[currentIndex].substring(4)).append("\n");
            currentIndex++;
        }

        evaluator.eval(block.toString());
        return currentIndex;

    }

    private int skipBlock(String[] lines, int startIndex) {
        int currentIndex = startIndex + 1;

        while (currentIndex < lines.length && lines[currentIndex].startsWith("    ")) {
            currentIndex++;
        }

        return currentIndex;
    }
}