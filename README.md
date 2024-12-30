# Factorial Branch

## Overview
This branch includes the implementation of a class designed to calculate the factorial of a given number `n`. The purpose of this class is to provide a robust solution for computing factorials using an iterative approach line by line.

## Functionality
- **Purpose**: To calculate the factorial of a given number `n`.
- **Current Issues**: The class works correctly for some values of `n`, but it does not handle all possible input values as expected. Specifically, there are issues with handling large values of `n` and negative inputs.

## Subset of Python Language Used
The implementation of the factorial calculation is inspired by the following Python language constructs:
- **While Loop**: Used to iterate and calculate the factorial.
- **Variable Assignment**: Used to initialize and update variables within the loop.
- **Arithmetic Operations**: Used to perform multiplication and increment operations.

### Example Pseudocode
The logic for the factorial calculation follows this pseudocode structure:
```python
result = 1
i = 1
n = 10

while i < n:
    result = result * i
    i += 1

print(result)  # Example output: 120
