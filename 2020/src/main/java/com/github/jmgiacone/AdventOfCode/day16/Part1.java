package com.github.jmgiacone.AdventOfCode.day16;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Map.entry;

public class Part1
{
    private static final String MY_TICKET = "your ticket:";
    public static void main(String[] args) throws IOException
    {
        Map<String, Set<Map.Entry<Integer, Integer>>> rules = new HashMap<>();
        List<String> input = Files.readAllLines(Paths.get("2020/src/main/resources/day16/part1/input.txt"))
                .stream()
                .filter(line -> !"".equalsIgnoreCase(line))
                .collect(Collectors.toList());

        //Input is in three parts
        // - Rules
        // - My ticket
        // - Nearby tickets

        int i = 0;
        do
        {
            String line = input.get(i);
            String[] tokens = line.split(":");

            String ruleName = tokens[0];
            Set<Map.Entry<Integer, Integer>> values = parseValues(tokens[1]);

            rules.put(ruleName, values);

            i++;
        }
        while(!MY_TICKET.equalsIgnoreCase(input.get(i)));

        Set<Integer> myTicket = parseTicket(input.get(i + 1));
        List<Set<Integer>> otherTickets = input.subList(i + 3, input.size()).stream().map(Part1::parseTicket).collect(Collectors.toList());

        System.out.println("rules = " + rules);
        System.out.println("myTicket = " + myTicket);
        System.out.println("otherTickets = " + otherTickets);

        long sum = otherTickets.stream().map(ticket -> isValid(ticket, rules)).flatMap(Set::stream).reduce(0, Integer::sum);
        System.out.println("sum = " + sum);
    }

    private static Set<Integer> isValid(Set<Integer> ticket, Map<String, Set<Map.Entry<Integer, Integer>>> rules)
    {
        return ticket.stream().filter(value -> rules.values().stream().noneMatch(rule -> matchesRule(value, rule))).collect(Collectors.toSet());
    }

    private static boolean matchesRule(Integer value, Set<Map.Entry<Integer, Integer>> rule)
    {
        for(Map.Entry<Integer, Integer> bound : rule)
        {
            int lower = bound.getKey();
            int upper = bound.getValue();

            if(value >= lower && value <= upper)
            {
                return true;
            }
        }

        return false;
    }

    private static Set<Integer> parseTicket(String ticket)
    {
        return Arrays.stream(ticket.split(",")).map(Integer::parseInt).collect(Collectors.toSet());
    }

    private static Set<Map.Entry<Integer, Integer>> parseValues(String rules)
    {
        return Arrays.stream(rules.trim().split(" or "))
                .map(pair -> {
                    int indexOfHyphen = pair.indexOf('-');
                    int num1 = Integer.parseInt(pair.substring(0, indexOfHyphen));
                    int num2 = Integer.parseInt(pair.substring(indexOfHyphen + 1));

                    return entry(num1, num2);
                })
                .collect(Collectors.toSet());
    }
}