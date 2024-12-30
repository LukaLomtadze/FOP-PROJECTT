package org.example;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Evaluating evaluating = new Evaluating();

        String code = """
                x = 5
                y=6
                z = 7
                if x > 8:
                    print(x)
                elif x < y:
                    print(z)
                else:
                    print(y)
                    
                    
                for i in range(1, z):
                    x = x + 1
                    print(x)
                """;

        evaluating.eval(code);//7
    }
}