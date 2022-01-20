package com.github.jmgiacone.AdventOfCode.day10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Part1
{
    public static void main(String[] args) throws IOException
    {
        //Read in the input
        List<Long> input = Files.readAllLines(Paths.get("2020/src/main/resources/day10/part1/input.txt")).stream().map(Long::parseLong).collect(Collectors.toList());
        input.sort(Comparator.naturalOrder());
        Map<Long, Integer> frequencyMap = new HashMap<>(5);

        long prevRating = 0;
        for(int i = 0; i < input.size(); i++)
        {
            long rating = input.get(i);
            long diff = rating - prevRating;
            increment(diff, frequencyMap);
            prevRating = rating;
        }
        increment(3, frequencyMap);
        System.out.println(frequencyMap);
    }

    private static void increment(long diff, Map<Long, Integer> map)
    {
        int diffFreq = map.getOrDefault(diff, 0);
        map.put(diff, diffFreq + 1);
    }
}
