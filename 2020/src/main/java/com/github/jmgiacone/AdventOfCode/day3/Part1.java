package com.github.jmgiacone.AdventOfCode.day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Part1
{
    private static final Map.Entry<Integer, Integer> DELTA = new AbstractMap.SimpleEntry<>(3, 1);

    public static void main(String[] args) throws IOException
    {
        List<String> lines = Files.readAllLines(Paths.get("2020/src/main/resources/day3/part1/input.txt"));
        int totalLines = lines.size();
        int iterations = (int)Math.ceil(totalLines / (double)DELTA.getValue());

        int row = 0;
        int column = 0;
        int treeCount = 0;
        while(row < totalLines)
        {
            String line = lines.get(row);
            char position = line.charAt(column % line.length());

            if(position == '#')
            {
                treeCount++;
            }

            row += DELTA.getValue();
            column += DELTA.getKey();
//            String line = lines.get(row);
//
//            //Move X to the right and check for tree
//            char position = line.charAt(column % line.length());
//
//            if(position == '#')
//            {
//                treeCount++;
//            }
//
//            row += DELTA.getValue();
//
//            if(row < lines.size())
//            {
//                position = lines.get(row).charAt(column % lines.get(row).length());
//
//                if(position == '#')
//                {
//                    treeCount++;
//                }
//            }
//
//            column += DELTA.getKey();
        }

        System.out.println("Tree count: " + treeCount);
//        AtomicInteger lineNumber = new AtomicInteger(1);
//        Map<String, Boolean> results = Files.readAllLines(Paths.get("2020/src/main/resources/day3/part1/input.txt"))
//                .stream()
//                .collect(Collectors.toMap(line -> lineNumber.getAndIncrement() + ": " + line,
//                        line -> {
//                            //1-3 a: password
//                            //0 234567890
//                            int start, end;
//
//                            String[] tokens = line.split(" ");
//                            String[] bounds = tokens[0].split("-");
//
//                            try
//                            {
//                                start = Integer.parseInt(bounds[0]);
//                                end = Integer.parseInt(bounds[1]);
//                            }
//                            catch (NumberFormatException e)
//                            {
//                                throw e;
//                            }
//
//                            char requiredChar = tokens[1].charAt(0);
//                            String password = tokens[2];
//
//                            return passwordMeetsRequirement(start, end, requiredChar, password);
//                        }));
//
//        System.out.println("Test cases passed: " + results.values().stream().filter(b -> b).count());
//        System.out.println(results);
    }

    private static boolean passwordMeetsRequirement(int start, int end, char c, String password)
    {
        Map<Character, Long> frequencyMap = password.chars()
                .mapToObj(ch -> (char)ch)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        Long charFreq = frequencyMap.getOrDefault(c, 0L);
        return charFreq >= start && charFreq <= end;
    }
}
