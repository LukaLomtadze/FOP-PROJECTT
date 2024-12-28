package org.example;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Evaluating evaluating = new Evaluating();

        // Python-like code for summing the first N natural numbers using range
        String code = """
                x = 5
                
                while x > 0:
                    print(x)
                    x = x - 1
                """;

        evaluating.eval(code);
    }
}