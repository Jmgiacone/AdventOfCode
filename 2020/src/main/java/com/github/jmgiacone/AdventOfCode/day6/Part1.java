package com.github.jmgiacone.AdventOfCode.day6;

import com.github.jmgiacone.AdventOfCode.day5.Part2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Part1
{
    public static void main(String[] args) throws IOException
    {
        AtomicInteger count = new AtomicInteger(1);
        Map<Integer, Integer> results = Arrays.stream(Files.readString(Paths.get("2020/src/main/resources/day6/part1/input.txt")).split("\r\n\r\n"))
                .collect(Collectors.toMap(answers -> count.getAndIncrement(),
                                          answers -> Arrays.stream(answers.split("\r\n")).map(String::chars).flatMap(chars -> chars.mapToObj(c -> (char)c)).collect(Collectors.toSet()).size()
                                          )
                );

        System.out.println("results = " + results);
        System.out.println("Sum: " + results.values().stream().reduce(0, Integer::sum));
    }
}
