package com.github.jmgiacone.AdventOfCode.day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Part2
{
    public static void main(String[] args) throws IOException
    {
        AtomicInteger lineNumber = new AtomicInteger(1);
        Map<String, Boolean> results = Files.readAllLines(Paths.get("2020/src/main/resources/day2/part1/input.txt"))
                .stream()
                .collect(Collectors.toMap(line -> lineNumber.getAndIncrement() + ": " + line,
                        line -> {
                            //1-3 a: password
                            //0 234567890
                            int start, end;

                            String[] tokens = line.split(" ");
                            String[] bounds = tokens[0].split("-");

                            try
                            {
                                start = Integer.parseInt(bounds[0]);
                                end = Integer.parseInt(bounds[1]);
                            }
                            catch (NumberFormatException e)
                            {
                                throw e;
                            }

                            char requiredChar = tokens[1].charAt(0);
                            String password = tokens[2];

                            return passwordMeetsRequirement(start, end, requiredChar, password);
                        }));

        System.out.println("Test cases passed: " + results.values().stream().filter(b -> b).count());
        System.out.println(results);
    }

    private static boolean passwordMeetsRequirement(int start, int end, char c, String password)
    {
        return password.charAt(start - 1) == c ^ password.charAt(end - 1) == c;
    }
}
