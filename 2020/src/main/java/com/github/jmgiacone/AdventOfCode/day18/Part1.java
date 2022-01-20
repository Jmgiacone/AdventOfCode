package com.github.jmgiacone.AdventOfCode.day18;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Part1
{
    private static final BiFunction<Long, Long, Long> PRODUCT = (a, b) -> a * b;
    public static void main(String[] args) throws IOException
    {
        List<String> inputExpressions = Files.readAllLines(Paths.get("2020/src/main/resources/day18/part1/input.txt"))
                .stream()
                .map(expression -> expression.replaceAll(" ", ""))
                .collect(Collectors.toList());

        long sum = 0;
        for(String expression : inputExpressions)
        {
            long answer = evaluateExpression(expression.toCharArray());
            System.out.println("answer = " + answer);
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