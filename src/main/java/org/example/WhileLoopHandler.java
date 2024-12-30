 package org.example;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

    public class WhileLoopHandler {

        private static final String WHILE_LOOP_REGEX =
                "^\\s*while\\s+(.*)\\s*:\\s*$";

        public int HandleWhileLoop(String[] lines, int loopIndex, Map<String, Integer> variables, Evaluating evaluator) {
            String loopLine = lines[loopIndex].trim();

            Pattern pattern = Pattern.compile(WHILE_LOOP_REGEX);
            Matcher matcher = pattern.matcher(loopLine);

            if (matcher.matches()) {
                String conditionExpression = matcher.group(1).trim();

                int currentLineIndex = loopIndex + 1;
                StringBuilder LoopBody = new StringBuilder();
                while (currentLineIndex < lines.length && lines[currentLineIndex].startsWith("    ")) {
                    LoopBody.append(lines[currentLineIndex].substring(4)).append("\n");
                    currentLineIndex++;
                }

                while (evaluator.evaluateExpression(conditionExpression) != 0) {
                    evaluator.eval(LoopBody.toString());
                }

                return currentLineIndex - 1;
            } else {
                throw new IllegalArgumentException("Invalid While-loop syntax: " + loopLine);
            }
        }
    }

