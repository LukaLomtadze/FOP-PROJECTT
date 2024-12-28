package pack2;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IfElseHandler {
    private static final String IF_STATEMENT_REGEX = "^\\s*if\\s+(.+)\\s*:\\s*$";
    private static final String ELSE_STATEMENT_REGEX = "^\\s*else\\s*:\\s*$";

    public int handleIfElse(String[] lines, int currentIndex, Map<String, Integer> variables, Evaluating evaluator) {
        String ifLine = lines[currentIndex].trim();

        Pattern ifPattern = Pattern.compile(IF_STATEMENT_REGEX);
        Matcher ifMatcher = ifPattern.matcher(ifLine);

        if (ifMatcher.matches()) {
            String condition = ifMatcher.group(1).trim();
            boolean conditionResult = evaluator.evaluateCondition(condition);

            int currentLineIndex = currentIndex + 1;
            StringBuilder ifBody = new StringBuilder();
            StringBuilder elseBody = new StringBuilder();

            boolean isElseBlock = false;

            while (currentLineIndex < lines.length && (lines[currentLineIndex].startsWith("    ") || lines[currentLineIndex].trim().isEmpty())) {
                if (lines[currentLineIndex].trim().matches(ELSE_STATEMENT_REGEX)) {
                    isElseBlock = true;
                    currentLineIndex++;
                    continue;
                }

                if (isElseBlock) {
                    elseBody.append(lines[currentLineIndex].substring(4)).append("\n");
                } else {
                    ifBody.append(lines[currentLineIndex].substring(4)).append("\n");
                }

                currentLineIndex++;
            }

            if (conditionResult) {
                evaluator.eval(ifBody.toString());
            } else if (isElseBlock) {
                evaluator.eval(elseBody.toString());
            }

            return currentLineIndex - 1;
        } else {
            throw new IllegalArgumentException("Invalid if-else syntax: " + ifLine);
        }
    }
}
