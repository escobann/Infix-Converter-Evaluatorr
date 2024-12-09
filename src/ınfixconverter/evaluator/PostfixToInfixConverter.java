/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ınfixconverter.evaluator;

/**
 *
 * @author esma
 */
import java.util.Stack;
import java.util.Scanner;

public class PostfixToInfixConverter {

    private static final String OPERATORS = "+-*/"; // Supported operators

    // Custom exception class for syntax errors
    public static class SyntaxErrorException extends Exception {
        public SyntaxErrorException(String message) {
            super(message); // Pass error message to the base Exception class
        }
    }

    // Helper method: Checks if a character is an operator
    private static boolean isOperator(char ch) {
        return OPERATORS.indexOf(ch) != -1; // Returns true if the character is in OPERATORS
    }

    // Method to convert postfix expression to infix
    public static String toInfix(String expression) throws SyntaxErrorException {
        Stack<String> stack = new Stack<>(); // Stack to hold operands and partial expressions
        Scanner scan = new Scanner(expression); // Scanner to tokenize the input
        String nextToken; // To store the current token

        // Process each token in the expression
        while ((nextToken = scan.findInLine("\\d+|[-+/\\*]")) != null) {//regular expression regex
            if (Character.isDigit(nextToken.charAt(0))) {
                // If the token is a number (operand), push it to the stack
                stack.push(nextToken);
            } else if (isOperator(nextToken.charAt(0))) {
                // If the token is an operator, pop two operands from the stack
                String rhs = stack.pop(); // Right-hand side operand
                String lhs = stack.pop(); // Left-hand side operand
                // Create infix expression with parentheses
                String infixExpr = "(" + lhs + " " + nextToken + " " + rhs + ")";
                stack.push(infixExpr); // Push the result back to the stack
            } else {
                // If the token is invalid, throw a syntax error
                throw new SyntaxErrorException("Invalid character found");
            }
        }

        // At the end, the stack should have exactly one element (the complete infix expression)
        if (stack.size() != 1) {
            throw new SyntaxErrorException("Syntax Error: Invalid postfix expression");
        }

        return stack.pop(); // Return the final infix expression
    }

    // Main method to test the conversion
    public static void main(String[] args) {
        try {
            
            
            String postfixExpression = "5 1 2 + 4 * + 3 -"; // ((5 + ((1 + 2) * 4)) - 3)
            String infixExpression = toInfix(postfixExpression);
            System.out.println("Infix Expression : " + infixExpression);

       
            String postfixExpression2 = "8 2 /"; // (8 / 2)
            String infixExpression2 = toInfix(postfixExpression2);
            System.out.println("Infix Expression : " + infixExpression2);

            // Invalid postfix expression (error)
            //String postfixExpression4 = "90 +"; // Invalid expression
            //String infixExpression4 = toInfix(postfixExpression4);
           // System.out.println("Infix Expression 4: " + infixExpression4);
        } catch (SyntaxErrorException e) {
            // Catch and print syntax errors
            System.err.println("Error: " + e.getMessage());
       
        }
    }
}

