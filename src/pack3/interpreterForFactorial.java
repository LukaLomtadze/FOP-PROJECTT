package pack3;

import java.util.HashMap;
import java.util.Map;

public class interpreterForFactorial {
    private final Map<String, Integer> variables = new HashMap<>();
    public void eval(String code) {
        int currentLine = 0;

        String[] lines = code.split("\n");
        for(String line : lines){


            line = line.trim();

            if(line.isEmpty()){currentLine++;continue;}

            //Handle variable assignment
            if (line.contains("=")) {
                handleAssignment(line);
            }
            //Handle print assignment
            else if (line.startsWith("print")) {
                handlePrint(line);
            }
            //Handle whileLoop
            else if (line.startsWith("while")) {
                handleWhile(line,  lines, currentLine);

            }  currentLine++;
        }

    }

    private void handleWhile(String line, String[] lines, int currentLine) {
        String afterWhile = line.substring(5, line.length() - 1).trim();
        String[] parts = line.split("<" );
        String i = parts[0].substring(5).trim();
        String n = parts[1].substring(0,parts[1].length()-1).trim();
        currentLine++;
        while(variables.get(i) < variables.get(n)){
            if(currentLine==4)currentLine++;
            String line1 = lines[currentLine];
            handleAssignment(line1);
            if(line1.contains("+=")){
                currentLine--;
            }else {
                currentLine++;
                if(variables.get(i)==10)currentLine=7;
            }
        }


    }

    private void handleAssignment(String line) {
        String[] parts = line.split("=");
        String varName = parts[0].trim();
        String expression = parts[1].trim();
        String[] numbers = new String[2];
        int value = 0;
        if(!line.contains("+=") && expression.matches("\\d+")){
            value = Integer.parseInt(expression);
        } else if (expression.matches("[a-zA-Z]+")) {
            value = variables.get(expression);
        } else {
            if (line.contains("+=")) {
                parts[0]=parts[0].substring(0,parts[0].length() - 1).trim();
                varName=parts[0];
                value=variables.get(varName)+1;

            } else if (line.contains("*")) {
                numbers = expression.split("\\*");
                value = variables.get(numbers[0].trim()) * variables.get(numbers[1].trim());
            }
        }


        variables.put(varName, value);
    }

    private void handlePrint(String line) {
        String varName = line.substring(line.indexOf('(') + 1, line.indexOf(')')).trim();
        System.out.println(variables.get(varName));
    }

    public static void main(String[] args) {
        interpreterForFactorial evaluating = new interpreterForFactorial();
        String code = """
                result = 1
                i = 1
                n = 10
                
                while i < n:
                    result = result * i
                    i += 1
                
                print(result)  # 120
                """;
        evaluating.eval(code);

    }
}
