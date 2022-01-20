package com.github.jmgiacone.AdventOfCode.day16;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Map.entry;

public class Part2
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

        List<Integer> myTicket = parseTicket(input.get(i + 1));
        Set<List<Integer>> otherTickets = input.subList(i + 3, input.size()).stream().map(Part2::parseTicket).collect(Collectors.toSet());

        System.out.println("rules = " + rules);
        System.out.println("myTicket = " + myTicket);
        System.out.println("otherTickets = " + otherTickets);

        otherTickets.removeIf(ticket -> !isValid(ticket, rules).isEmpty());

        List<String> answer = determineFields(rules, otherTickets);
        System.out.println("answer = " + answer);

        long product = 1;
        for(int index = 0; index < answer.size(); index++)
        {
            if(answer.get(index).startsWith("departure"))
            {
                product *= myTicket.get(index);
            }
        }

        System.out.println("product = " + product);
    }

    private static List<String> determineFields(Map<String, Set<Map.Entry<Integer, Integer>>> rules, Set<List<Integer>> tickets)
    {
        Map<Integer, Set<String>> determinedFields = new HashMap<>(rules.size());

        for(List<Integer> ticket : tickets)
        {
            Map<Integer, Set<String>> candidateFields = determineCandidates(rules, ticket);

            System.out.println("candidateFields = " + candidateFields);

            for(int index : candidateFields.keySet())
            {
                if(!determinedFields.containsKey(index))
                {
                    determinedFields.put(index, candidateFields.get(index));
                }
                else
                {
                    Set<String> candidates = determinedFields.get(index);
                    candidates.retainAll(candidateFields.get(index));
                    determinedFields.put(index, candidates);
                }
            }
        }

        List<Integer> sortedIndices = determinedFields.entrySet().stream().sorted(Comparator.comparing(entry -> entry.getValue().size())).map(Map.Entry::getKey).collect(Collectors.toList());

        for(int index : sortedIndices)
        {
            Set<String> indexSet = determinedFields.get(index);

            for(int i : determinedFields.keySet())
            {
                if(i != index)
                {
                    determinedFields.get(i).removeAll(indexSet);
                }
            }
        }

        return determinedFields.keySet().stream().sorted().map(determinedFields::get).flatMap(Set::stream).collect(Collectors.toList());
    }

    private static Map<Integer, Set<String>> determineCandidates(Map<String, Set<Map.Entry<Integer, Integer>>> rules, List<Integer> ticket)
    {
        Map<Integer, Set<String>> matchedRules = new HashMap<>(ticket.size());
        for(int i = 0; i < ticket.size(); i++)
        {
            int value = ticket.get(i);
            Set<String> candidateRules = rules.entrySet()
                    .stream()
                    .filter(entry -> matchesRule(value, entry.getValue()))
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toSet());

            matchedRules.put(i, candidateRules);
        }

        return matchedRules;
    }

    private static Set<Integer> isValid(List<Integer> ticket, Map<String, Set<Map.Entry<Integer, Integer>>> rules)
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

    private static List<Integer> parseTicket(String ticket)
    {
        return Arrays.stream(ticket.split(",")).map(Integer::parseInt).collect(Collectors.toList());
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