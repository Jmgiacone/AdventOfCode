package com.github.jmgiacone.AdventOfCode.day13;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Map.entry;

public class Part1
{
    public static void main(String[] args) throws IOException
    {
        List<String> file = Files.readAllLines(Paths.get("2020/src/main/resources/day13/part1/input.txt"));
        long earliestDepartureTime = Long.parseLong(file.get(0));
        Set<Long> busIds = Arrays.stream(file.get(1).split(",")).filter(token -> !token.equalsIgnoreCase("x")).map(Long::parseLong).collect(Collectors.toSet());
        System.out.printf("Earliest time = %d\tbusIds = %s\n", earliestDepartureTime, busIds);

        Map.Entry<Long, Long> idDiff = busIds.stream().map(id -> entry(id, (int)Math.ceil(earliestDepartureTime / (double)id) * id - earliestDepartureTime)).min(Map.Entry.comparingByValue()).get();

        long id = idDiff.getKey();
        long diff = idDiff.getValue();
        System.out.printf("Take bus %d at %d. Product = %d * %d = %d\n", id, (int)Math.ceil(earliestDepartureTime / (double)id) * id, id, diff, id * diff);
//        System.out.println("Take bus " + idDiff.getKey() + " at " + (int)Math.ceil(earliestDepartureTime / (double)id) * id + ". Product = ");
    }
}