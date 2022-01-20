package com.github.jmgiacone.AdventOfCode.day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Part2
{
    private static final Long TARGET_SUM = 2020L;

    public static void main(String[] args) throws IOException
    {
        List<Long> numbers = Files.readAllLines(Paths.get("2020/src/main/resources/day1/part1/input.txt"))
                .stream()
                .map(Long::parseLong)
                .collect(Collectors.toList());

        //brute force that bitch
        for(int i = 0; i < numbers.size(); i++)
        {
            for(int j = 0; j < numbers.size(); j++) {
                for (int k = 0; k < numbers.size(); k++) {
                    if (!(i == k || j == i || k == j)) {
                        long num1 = numbers.get(i);
                        long num2 = numbers.get(j);
                        long num3 = numbers.get(k);

                        if (num1 + num2 + num3 == TARGET_SUM) {
                            System.out.println(num1 * num2 * num3);
                            break;
                        }
                    }
                }
            }
        }
    }
}
