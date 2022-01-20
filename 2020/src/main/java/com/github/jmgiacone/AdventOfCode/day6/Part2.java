package com.github.jmgiacone.AdventOfCode.day6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Part2
{
    public static void main(String[] args) throws IOException
    {
        AtomicInteger count = new AtomicInteger(1);
        Map<Integer, Long> results = Arrays.stream(Files.readString(Paths.get("2020/src/main/resources/day6/part1/input.txt")).split("\r\n\r\n"))
                .collect(Collectors.toMap(body -> count.getAndIncrement(),
                                            body -> {
                                                String[] answers = body.split("\r\n");
                                                Map<Character, Long> frequencyMap = Arrays.stream(answers).map(String::chars).flatMap(chars -> chars.mapToObj(c -> (char)c)).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
                                                return frequencyMap.values().stream().filter(value -> value == answers.length).count();
                                            }
                                          )
                );

        System.out.println("results = " + results);
        System.out.println("Sum: " + results.values().stream().reduce(0L, Long::sum));
    }
}
