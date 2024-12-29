package org.example;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Evaluating evaluating = new Evaluating();


        // Your code goes inside """        """
        String code = """
                x = 5
                y= 1
               \s
                while x > 1:
                    y = y * x
                    x = x - 1
                   \s
                print(y)
               \s""";

        evaluating.eval(code);
    }
}