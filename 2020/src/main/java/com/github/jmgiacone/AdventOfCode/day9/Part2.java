package com.github.jmgiacone.AdventOfCode.day9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Part2
{
    public static void main(String[] args) throws IOException
    {
        int preambleSize = 25;

        //Read in the input
        List<Long> input = Files.readAllLines(Paths.get("2020/src/main/resources/day9/part1/input.txt")).stream().map(Long::parseLong).collect(Collectors.toList());

        Map<Long, Set<Long>> sums = new HashMap<>(preambleSize);
        Long invalidNum = null;
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
                invalidNum = num;
                break;
            }

            //Tick the window one unit forward. This removes the oldest row entirely
            sums.remove(input.get(i - preambleSize));

            //Then adds a new column
            Set<Long> newSums = sums.keySet().stream().map(n -> num + n).collect(Collectors.toSet());
            sums.put(num, new HashSet<>(newSums));
        }
        System.out.println("num = " + invalidNum);

        //Now search the whole input looking for a contiguous set numbers that sum to invalidNum
        for(int i = 0; i < input.size(); i++)
        {
            int end = i + 1;

            long sum = input.get(i);
            for(; end < input.size() && sum < invalidNum; end++)
            {
                sum += input.get(end);
            }

            if(sum == invalidNum)
            {
                System.out.println(String.format("start = %d\tend = %d", i, end - 1));
                List<Long> sublist = input.subList(i, end);
                long min = sublist.stream().min(Long::compareTo).get();
                long max = sublist.stream().max(Long::compareTo).get();

                System.out.println(String.format("min = %d\tmax = %d\tsum = %d", min, max, min + max));
//                System.out.println("start = " + i + " end = " + end + " sum = " + (input.get(i) + input.get(end)));
                break;
            }
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
