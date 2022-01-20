package com.github.jmgiacone.AdventOfCode.day10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Map.entry;

public class Part2
{
    private static Map<Map.Entry<Integer, Integer>, Long> CACHE = new HashMap<>();
    public static void main(String[] args) throws IOException
    {
        //Read in the input
        List<Long> input = Files.readAllLines(Paths.get("2020/src/main/resources/day10/part1/input.txt")).stream().map(Long::parseLong).sorted(Comparator.naturalOrder()).collect(Collectors.toList());

        //Generate graph of possibilities
        Map<Integer, Set<Integer>> graph = new HashMap<>();

        input.add(0, 0L);
        for(int i = 0; i < input.size(); i++)
        {
            //Check the numbers ahead and see if anything is 1-3 away
            long value = input.get(i);

            //This is the last number. It has no neighbors
            if(i == input.size() - 1)
            {
                graph.put(i, new HashSet<>());
            }

            int j = i + 1;
            while(j < input.size() && input.get(j) - value <= 3)
            {
                Set<Integer> neighbors = graph.getOrDefault(i, new HashSet<>());
                neighbors.add(j);
                graph.put(i, neighbors);

                j++;
            }
        }

        Map<Integer, Set<Integer>> parents = graph.keySet().stream()
                .map(node -> {
                    Set<Integer> parentSet = graph.entrySet()
                            .stream()
                            .filter(entry -> entry.getValue().contains(node))
                            .map(Map.Entry::getKey)
                            .collect(Collectors.toSet());

                    return entry(node, parentSet);
        }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));


        long paths = numPaths(input.size() - 1, 0, parents);
        System.out.println("paths = " + paths);
        System.out.println();
        System.out.println("CACHE = " + CACHE);
    }

    private static long numPaths(int srcIndex, int destIndex, Map<Integer, Set<Integer>> parentMap)
    {
        Map.Entry<Integer, Integer> entry = entry(srcIndex, destIndex);
        if(CACHE.containsKey(entry))
        {
            System.out.println(String.format("Cache hit - %d -> %d = %d", srcIndex, destIndex, CACHE.get(entry)));
            return CACHE.get(entry);
        }

        int currentIndex = srcIndex;
        Set<Integer> parents = parentMap.get(currentIndex);
        while(parents.size() == 1 && currentIndex != destIndex)
        {
            currentIndex = parentMap.get(currentIndex).iterator().next();

            if(currentIndex == destIndex)
            {
                CACHE.put(entry, 1L);
                return 1;
            }

            parents = parentMap.get(currentIndex);
        }

        long count = 0;

        for(Integer parent : parents)
        {
            if(parent == destIndex)
            {
                count++;
            }
            else if(parent > destIndex)
            {
                count += numPaths(parent, destIndex, parentMap);
            }
        }

        CACHE.put(entry, count);
        return count;

//        return parents.stream()
//                .map(parent -> numPaths(parent, destIndex, parentMap))
//                .reduce(0L, Long::sum);

//        if(parentMap.get(currentIndex).size() > 1)
//        {
//            return parentMap.get(currentIndex).stream()
//                    .map(parent -> numPaths(parent, destIndex, parentMap))
//                    .reduce(0L, Long::sum);
//        }
//
//        return 1;
    }
}
