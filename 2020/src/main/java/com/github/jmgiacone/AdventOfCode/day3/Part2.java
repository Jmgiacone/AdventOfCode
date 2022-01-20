package com.github.jmgiacone.AdventOfCode.day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Part2
{
    private static List<Map.Entry<Integer, Integer>> DELTAS = List.of(
            Map.entry(1, 1),
            Map.entry(3, 1),
            Map.entry(5, 1),
            Map.entry(7, 1),
            Map.entry(1, 2)
    );
    public static void main(String[] args) throws IOException
    {
        List<String> lines = Files.readAllLines(Paths.get("2020/src/main/resources/day3/part1/input.txt"));

        Map<Map.Entry<Integer, Integer>, Integer> results = DELTAS.stream()
                .collect(
                        Collectors.toMap(Function.identity(),
                                entry -> countTrees(lines, 0, 0, entry)));

        System.out.println("results = " + results);
        System.out.println("Product = " + results.values().stream().reduce(1, (a, b) -> a * b));
    }

    private static int countTrees(List<String> lines, int row, int column, Map.Entry<Integer, Integer> delta)
    {
        int totalLines = lines.size();

        int treeCount = 0;
        while (row < totalLines)
        {
            String line = lines.get(row);
            char position = line.charAt(column % line.length());

            if(position == '#')
            {
                treeCount++;
            }

            row += delta.getValue();
            column += delta.getKey();

        }

        return treeCount;
    }
}
