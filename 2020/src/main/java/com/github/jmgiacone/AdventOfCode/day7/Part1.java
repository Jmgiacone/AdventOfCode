package com.github.jmgiacone.AdventOfCode.day7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Map.entry;

public class Part1
{
    public static void main(String[] args) throws IOException
    {
        //Message format
        //light red bags contain 1 bright white bag, 2 muted yellow bags.
        //  0    1   2      3     4   5     6    7    8   9    10     11
        Map<String, Map<String, Long>> allRules = Files.readAllLines(Paths.get("2020/src/main/resources/day7/part1/input.txt"))
                .stream()
                .map(line -> line.split(" "))
                .map(tokens -> {
                    String parentBagName = tokens[0] + " " + tokens[1];
                    Map<String, Long> rules = new HashMap<>(5);

                    for(int i = 4; i < tokens.length; i += 4)
                    {
                        if(tokens[i].equals("no"))
                        {
                            break;
                        }

                        long numBags = Long.parseLong(tokens[i]);
                        String childBagName = tokens[i + 1] + " " + tokens[i + 2];
                        rules.put(childBagName, numBags);
                    }

                    return entry(parentBagName, rules);
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        System.out.println("rules = " + allRules);

        Map<String, Boolean> results = allRules.keySet().stream().collect(Collectors.toMap(Function.identity(), bag -> bagCanContain(bag, "shiny gold", allRules)));
        System.out.println("results = " + results);
        System.out.println("Num bags: " + results.values().stream().filter(b -> b).count());
    }

    private static boolean bagCanContain(String bag, String targetBag,  Map<String, Map<String, Long>> rules)
    {
        Set<String> expandedBags = new HashSet<>();

        Set<String> currentBags = Set.of(bag);

        do
        {
            Set<String> current = new HashSet<>();
            for(String key : currentBags)
            {
                current.addAll(rules.get(key).keySet());
                expandedBags.add(key);
            }

            currentBags = current;

            if(currentBags.contains(targetBag))
            {
                return true;
            }
        }
        while(!expandedBags.containsAll(currentBags));

        return currentBags.contains(targetBag);
    }
}
