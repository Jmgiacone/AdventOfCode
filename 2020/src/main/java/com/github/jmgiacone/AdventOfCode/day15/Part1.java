package com.github.jmgiacone.AdventOfCode.day15;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Part1
{
    public static void main(String[] args) throws IOException
    {
        List<Integer> startingNumbers = Arrays.stream(
                Files.readAllLines(Paths.get("2020/src/main/resources/day15/part1/input.txt")).get(0).split(",")
                )
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        int[] history = new int[2020];

        int turn = 0;

        //Initialize the map with our known numbers
        Map<Integer, List<Integer>> numberMap = new HashMap<>();
        for(Integer num : startingNumbers)
        {
            if(!numberMap.containsKey(num))
            {
                numberMap.put(num, new LinkedList<>(List.of(turn)));
            }
            history[turn] = num;
            turn++;
        }

        while(turn < history.length)
        {
            int prevNum = history[turn - 1];
            int currentNum = 0;

            List<Integer> turns = numberMap.get(prevNum);
            if(turns.size() == 2)
            {
                currentNum = turns.get(1) - turns.get(0);
            }

            turns = numberMap.getOrDefault(currentNum, new LinkedList<>());

            if(turns.size() == 2)
            {
                turns.remove(0);
            }

            turns.add(turn);

            history[turn] = currentNum;
            numberMap.put(currentNum, turns);

            turn++;
        }

        System.out.println("history[history.length - 1] = " + history[history.length - 1]);
    }
}