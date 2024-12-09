/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ınfixconverter.evaluator;

/**
 *
 * @author esma
 */
import ınfixconverter.evaluator.PostfixToInfixConverter.SyntaxErrorException;
import java.util.Stack;

public class InfixEvaluator {

    // Define supported operators
    private static final String OPERATORS = "+-*/";  // A string containing the supported operators

    // Helper method: Evaluates the operation between two operands
    private static int evalOp(int lhs, int rhs, char op) {
        switch (op) {
            case '+': return lhs + rhs;  // Addition operation
            case '-': return lhs - rhs;  // Subtraction operation
            case '*': return lhs * rhs;  // Multiplication operation
            case '/': return lhs / rhs;  // Division operation
            default: throw new IllegalArgumentException("unsupported operator: " + op);  // If operator is not recognized
        }
    }

    // Evaluates an infix expression and returns the result
    public static int evaluateInfix(String expression) throws SyntaxErrorException {
        Stack<Integer> values = new Stack<>();  // Stack to hold the values (numbers)
        Stack<Character> ops = new Stack<>();   // Stack to hold the operators

        for (int i = 0; i < expression.length(); i++) {//Length of the string 
            char currentChar = expression.charAt(i);  // Get the current character in the expression

            // Skip spaces
            if (currentChar == ' ') continue;  // Ignore spaces

            // If the current character is a digit, form the number
            if (Character.isDigit(currentChar)) {
                int num = 0;  // Initialize a variable to store the number
                // While the next character is also a digit, build the full number
                while (i < expression.length() && Character.isDigit(expression.charAt(i))) {
                    num = num * 10 + (expression.charAt(i) - '0');  // Build the number by multiplying and adding digits
                    i++;  // Move to the next character
                }
                i--; // Decrement to go back to the last character of the number
                values.push(num);  // Push the formed number onto the stack
            }
            // If the character is an opening parenthesis, push it to the operator stack
            else if (currentChar == '(') {
                ops.push(currentChar);  // Opening parenthesis signifies a new sub-expression
            }
            // If the character is a closing parenthesis, evaluate the expression inside the parentheses
            else if (currentChar == ')') {
                // While the stack contains operators and the top of the stack isn't '('
                while (!ops.isEmpty() && ops.peek() != '(') {
                    int rhs = values.pop();  // Pop the right-hand side value
                    int lhs = values.pop();  // Pop the left-hand side value
                    char op = ops.pop();     // Pop the operator
                    values.push(evalOp(lhs, rhs, op));  // Evaluate and push the result
                }
                ops.pop();  // Pop the opening parenthesis '('
            }
            // If the character is an operator, process it based on operator precedence
            else if (OPERATORS.indexOf(currentChar) != -1) {
                // While the operator stack isn't empty and the top operator has greater or equal precedence
                while (!ops.isEmpty() && precedence(ops.peek()) >= precedence(currentChar)) {
                    int rhs = values.pop();  // Pop the right-hand side value
                    int lhs = values.pop();  // Pop the left-hand side value
                    char op = ops.pop();     // Pop the operator
                    values.push(evalOp(lhs, rhs, op));  // Evaluate and push the result
                }
                ops.push(currentChar);  // Push the current operator onto the stack
            }
        }

        // After processing the entire expression, evaluate any remaining operations
        while (!ops.isEmpty()) {
            int rhs = values.pop();  // Pop the right-hand side value
            int lhs = values.pop();  // Pop the left-hand side value
            char op = ops.pop();     // Pop the operator
            values.push(evalOp(lhs, rhs, op));  // Evaluate and push the result
        }

        return values.pop();  // Return the final evaluated result from the stack
    }

    // Helper method: Determines the precedence of operators
    private static int precedence(char op) {
        if (op == '+' || op == '-') return 1;  // Addition and subtraction have lower precedence
        if (op == '*' || op == '/') return 2;  // Multiplication and division have higher precedence
        return 0;  // Default for invalid operators
    }

    public static void main(String[] args) {
        try {
            String infixExpression = "(78+2)-4/4 + (9*4)";  // Example expression to evaluate
            int result = evaluateInfix(infixExpression);  // Call the evaluation method
            System.out.println("Infix result: " + result);  // Print the result
        } catch (SyntaxErrorException e) {
            System.err.println("!ERROR: " + e.getMessage());  // If there's a syntax error, print the error message
        }
    }
}

