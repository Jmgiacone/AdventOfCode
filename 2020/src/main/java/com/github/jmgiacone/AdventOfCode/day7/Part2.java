package com.github.jmgiacone.AdventOfCode.day7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Map.entry;

public class Part2
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

        Map<String, Long> results = allRules.keySet().stream().collect(Collectors.toMap(Function.identity(), key -> totalBags(key, allRules)));

        System.out.println("shiny gold = " + results.get("shiny gold"));
        System.out.println("results = " + results);
    }

    private static long totalBags(String targetBag, Map<String, Map<String, Long>> rules)
    {
        Map<String, Long> rule = rules.get(targetBag);

        if(rule.isEmpty())
        {
            return 0L;
        }

        return rule.entrySet()
                .stream()
                .map(entry -> entry.getValue() + entry.getValue() * totalBags(entry.getKey(), rules))
                .reduce(0L, Long::sum);
    }
}
