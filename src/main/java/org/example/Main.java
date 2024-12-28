package org.example;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Evaluating evaluating = new Evaluating();


        // Your code goes inside """        """
        String code = """
                x = 6
                y = 1
                
                for ucha in range(1, x):
                    y = y + ucha
                    print(y)
                
                """;

        evaluating.eval(code);
    }
}