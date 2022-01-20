package com.github.jmgiacone.AdventOfCode.day5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Part1
{
    public static void main(String[] args) throws IOException
    {
        List<String> tickets = Files.readAllLines(Paths.get("2020/src/main/resources/day5/part1/input.txt"));

        AtomicInteger count = new AtomicInteger(1);
        Map<String, Long> results = tickets
                .stream()
                .collect(
                        Collectors.toMap(ticket -> count.getAndIncrement() + ": " + ticket,
                                         Part2::calculateId)
                );

        System.out.println("results = " + results);
        System.out.println("Max id: " + results.values().stream().max(Comparator.naturalOrder()).get());
    }

    public static long calculateId(String ticket)
    {
        AtomicInteger start = new AtomicInteger(0);
        AtomicInteger end = new AtomicInteger(127);

        ticket.substring(0, 7)
                .chars()
                .mapToObj(c -> (char)c)
                .forEach(instruction -> {
                    int s = start.get();
                    int e = end.get();
                    int mid = (int)Math.floor((e + s) / 2.0);

                    switch(instruction)
                    {
                        case 'F':
                            end.set(mid);
                            break;
                        case 'B':
                            start.set(mid + 1);
                            break;
                    }
                });

        long result = start.get() * 8;

        start.set(0);
        end.set(7);
        ticket.substring(7)
                .chars()
                .mapToObj(c -> (char)c)
                .forEach(instruction -> {
                    int s = start.get();
                    int e = end.get();
                    int mid = (int)Math.floor((e + s) / 2.0);

                    switch(instruction)
                    {
                        case 'L':
                            end.set(mid);
                            break;
                        case 'R':
                            start.set(mid + 1);
                            break;
                    }
                });

        result += start.get();

        return result;
    }
}
