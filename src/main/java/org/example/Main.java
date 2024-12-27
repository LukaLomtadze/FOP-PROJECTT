package org.example;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Evaluating evaluating = new Evaluating();

        // Python-like code for summing the first N natural numbers using range
        String code = """
                x = 1
                y = 50
                
                for i in range(1, y):
                    x = x + i
                print(x)
                """;

        evaluating.eval(code);
    }
}