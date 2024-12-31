package org.example;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Evaluating evaluating = new Evaluating();

        String code = """
                a = 36
                b = 12
                
                while b > 0:
                    temp = b
                    b = a % b
                    a = temp
                
                print(a)
                
                """;

        evaluating.eval(code);
    }
}