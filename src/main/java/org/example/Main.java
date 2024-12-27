package org.example;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Evaluating interpreter = new Evaluating();

        String code = """
                x = 6 + 2 * 5
                print(x)
                
                y = 6
                x = y
                print(x)
                """;
        interpreter.eval(code);
    }
}