package com.github.jmgiacone.AdventOfCode.day9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Map.entry;

public class Part1
{
    public static void main(String[] args) throws IOException
    {
        int preambleSize = 25;

        //Read in the input
        List<Long> input = Files.readAllLines(Paths.get("2020/src/main/resources/day9/part1/input.txt")).stream().map(Long::parseLong).collect(Collectors.toList());

        Map<Long, Set<Long>> sums = new HashMap<>(preambleSize);
        for(int i = 0; i < input.size(); i++)
        {
            //Read in the initial preamble
            long num = input.get(i);

            if(i < preambleSize)
            {
                sums.put(num, new HashSet<>());

                if(sums.size() == preambleSize)
                {
                    //Generate the initial triangle. This runs once
                    for(int j = 0; j < preambleSize - 1; j++)
                    {
                        for(int k = 0; k < preambleSize; k++)
                        {
                            long jNum = input.get(j);
                            long kNum = input.get(k);

                            sums.get(jNum).add(jNum + kNum);
                        }
                    }
                }

                continue;
            }

            //Check if this number is a sum of the past <preambleSize> numbers
            boolean isSum = false;
            for(Set<Long> sumSet : sums.values())
            {
                if(sumSet.contains(num))
                {
                    isSum = true;
                    break;
                }
            }

            if(!isSum)
            {
                System.out.println("num = " + num);
                break;
            }

            //Tick the window one unit forward. This removes the oldest row entirely
            sums.remove(input.get(i - preambleSize));

            //Then adds a new column
            Set<Long> newSums = sums.keySet().stream().map(n -> num + n).collect(Collectors.toSet());
            sums.put(num, new HashSet<>(newSums));
        }


//        Map<Integer, Set<Integer>> sums = new HashMap<>(preambleSize);
//        Files.readAllLines(Paths.get("2020/src/main/resources/day9/part1/example.txt")).stream()
//                .map(Integer::parseInt)
//                .map(num -> {
//                    //Read in the initial preamble
//                    if(sums.size() < preambleSize)
//                    {
//                        sums.put(num, new HashSet<>());
//                    }
//
//                    //Preamble is read, generate the triangle
//                        }
//                );

//        System.out.println("Line #" + lineNumber + " already executed! Set = " + executedInstructions);
//        System.out.println("accumulator = " + accumulator);
    }
}
