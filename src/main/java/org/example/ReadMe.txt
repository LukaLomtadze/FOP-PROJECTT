This project provides an evaluator for mathematical expressions and a handler for Python-like for loops in Java.

Usage Prerequisites Java 8 or above.


1. How to Run
Structure Your Code: Pass a string of code to the eval() method of the Evaluating class.

Variable Assignment:
x = 5
y = x
z = x + y + 3

1.1 For Loop:

You can Copy this code and paste in main method
for i in range(0, 3):
    x = i * 2
    print(x)

Example Code:


You can Copy this code and paste in main method
1.2 String code = """
    x = 10
    y = 20
    z = x + y
    print(z)
    for i in range(0, 3):
        x = i * 2
        print(x)
""";

2. Evaluating evaluator = new Evaluating();
evaluator.eval(code);
Run: Compile and run the main() method to execute the code.

3. Supported Operators
+, -, *, / (Division by zero prints an error message)
How It Works
Assignments: Assign values to variables with arithmetic expressions.
For Loops: Runs loops in the format for <var> in range(<start>, <end>).



For the algorthms U can try following

1. Sum of N th numbers

N = 10
sum = 0

for i in range(1, N):
    sum = sum + i

print(sum) // 55

N = 11
sum = 0
i = 1

while i < N:
    sum = sum + i
    i = i + 1

print(sum) // 55

2. Fibonacci

a = 0
b = 1
print(a)
print(b)
for i in range(2, 10):
    c = a + b
    print(c)
    a = b
    b = c // fibonacci sequence 0 1 1 2 3 5 8 13 21 34 55


3. Factorial

n = 5
result = 1
for i in range(1, n):
    result = result * i

print(result) // 120

x = 5
y = 1

while x > 1:
    y = y * x
    x = x - 1
print(y) // 120


4. multiplication table

N = 5
i = 1
t = 1
while i <= 10:
    t = N * i
    print(t)
    i =i+ 1

5. sum digits

num = 1234
sum = 0

while num > 0:
    digit = num % 10
    sum = sum + digit
    num = num / 10

print(sum)

6. largest digits

num = 3947
largest = 0

while num > 0:
    digit = num % 10
    if largest < digit:
        largest = digit
    num = num / 10

(largest)  # Output: 9


7. reverseing number

num = 1234
reversed = 0

while num > 0:
    digit = num % 10
    reversed = reversed * 10 + digit
    num = num / 10

print(reversed)


8.GCD of numbers

a = 48
b = 18

while b > 0:
    temp = b
    b = a % b
    a = temp

print(a)