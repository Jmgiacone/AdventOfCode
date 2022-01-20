package com.github.jmgiacone.AdventOfCode.day18;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class Part2
{
    private static final BiFunction<Long, Long, Long> PRODUCT = (a, b) -> a * b;
    public static void main(String[] args) throws IOException
    {
        List<String> inputExpressions = Files.readAllLines(Paths.get("2020/src/main/resources/day18/part1/example.txt"))
                .stream()
                .filter(line -> !line.startsWith("//"))
                .map(expression -> expression.replaceAll(" ", ""))
                .collect(Collectors.toList());

        long sum = 0;
        for(String expression : inputExpressions)
        {
            long answer = evaluateExpression(expression.toCharArray());
            System.out.println(expression + " = " + answer);
            sum += answer;
        }
        System.out.println("sum = " + sum);
    }

    private static long evaluateExpression(char[] expression)
    {
        BiFunction<Long, Long, Long> x = null;
        Long answer = null;
        Long value = null;
        for(int i = 0; i < expression.length; i++)
        {
            switch(expression[i])
            {
                case '+':
                    x = Long::sum;
                    break;
                case '*':
                    x = PRODUCT;
                    break;
                case '(':
                    int delta = -1;
                    i++;
                    int subexpressionStart = i;
                    while(delta != 0 && i < expression.length)
                    {
                        switch(expression[i])
                        {
                            case ')':
                                delta++;
                                break;
                            case '(':
                                delta--;
                                break;
                        }

                        i++;
                    }

                    i--;

                    value = evaluateExpression(Arrays.copyOfRange(expression, subexpressionStart, i));
                    break;
                default:
                    value = Long.parseLong(expression[i] + "");

                    //If:
                    //  - There are at least two chars remaining
                    //  - AND the next char is an add

                    while(i < expression.length - 2 && expression[i + 1] == '+')
                    {
                        i+= 2;

                        switch(expression[i])
                        {
                            //The second operand is an expression
                            case '(':
                                int parity = -1;
                                i++;

                                int start = i;
                                while(i < expression.length && parity != 0)
                                {
                                    switch(expression[i])
                                    {
                                        case ')':
                                            parity--;
                                            break;
                                        case '(':
                                            parity++;
                                            break;
                                    }
                                    i++;
                                }
                                i--;
                                value += evaluateExpression(Arrays.copyOfRange(expression, start, i));
                                break;
                            default:
                                value += Long.parseLong(expression[i] + "");
                                break;
                        }
                    }

//                    if(i < expression.length - 2 && expression[i + 1] == '+')
//                    {
//                        //Character after the multiply is another number
//                        if(expression[i + 2] != '(')
//                        {
//                            do
//                            {
//                                value += Long.parseLong(expression[i + 2] + "");
//                                i += 2;
//                            }
//                            while(i < expression.length - 2 && );
//                        }
//                        else
//                        {
//                            i += 3;
//                            int expressionStart = i;
//                            int d = -1;
//
//                            //Two cases:
//                            //  - Character after the multiply is another number
//                            //  - Character after multiply is an open paren
//                            i++;
//                            while(i < expression.length && d != 0)
//                            {
//                                switch(expression[i])
//                                {
//                                    case ')':
//                                        d++;
//                                        break;
//                                    case '(':
//                                        d--;
//                                        break;
//                                }
//
//                                i++;
//                            }
//                            i--;
//
//                            value += evaluateExpression(Arrays.copyOfRange(expression, expressionStart, i));
//                        }
//                    }
                    break;
            }

            if(answer == null && value != null)
            {
                answer = value;
                value = null;
            }

            if(x != null && value != null)
            {
                answer = x.apply(value, answer);
                x = null;
                value = null;
            }
        }

        return answer;
    }
}