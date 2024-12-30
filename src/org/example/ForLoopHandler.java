package org.example;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForLoopHandler {

    // for loopis syntax python
    private static final String FOR_LOOP_REGEX =
            "^\\s*for\\s+(\\w+)\\s+in\\s+range\\(([^,]+),\\s*([^\\)]+)\\)\\s*:\\s*$";

    public int handleForLoop(String[] lines, int loopIndex, Map<String, Integer> variables, Evaluating evaluator) {
        String loopLine = lines[loopIndex].trim();


        Pattern pattern = Pattern.compile(FOR_LOOP_REGEX);
        Matcher matcher = pattern.matcher(loopLine);

        if (matcher.matches()) {
            String loopVariable = matcher.group(1);
            String startExpression = matcher.group(2).trim();
            String endExpression = matcher.group(3).trim();


            int start = evaluator.evaluateExpression(startExpression);
            int end = evaluator.evaluateExpression(endExpression);

            //System.out.println("Executing for-loop from " + start + " to " + (end - 1)); // Debugging


            int currentLineIndex = loopIndex + 1;
            StringBuilder loopBody = new StringBuilder();
            while (currentLineIndex < lines.length && lines[currentLineIndex].startsWith("    ")) {
                loopBody.append(lines[currentLineIndex].substring(4)).append("\n");
                currentLineIndex++;
            }


            for (int i = start; i < end; i++) {
                variables.put(loopVariable, i);

                evaluator.eval(loopBody.toString());
            }

            return currentLineIndex;
        } else {
            throw new IllegalArgumentException("Invalid for-loop syntax: " + loopLine);
        }
    }
}