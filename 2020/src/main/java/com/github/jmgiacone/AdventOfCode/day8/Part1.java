package com.github.jmgiacone.AdventOfCode.day8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Map.entry;

public class Part1
{
    enum Instruction
    {
        ACC, JMP, NOP;
    }

    public static void main(String[] args) throws IOException
    {
        List<Map.Entry<Instruction, Integer>> program = Files.readAllLines(Paths.get("2020/src/main/resources/day8/part1/input.txt"))
                .stream()
                .map(String::toUpperCase)
                .map(line -> {
                    String[] tokens = line.split(" ");
                    int value = Integer.parseInt(tokens[1]);

                    return entry(Instruction.valueOf(tokens[0]), value);
                })
                .collect(Collectors.toList());

        int accumulator = 0;
        int lineNumber = 0;
        Set<Integer> executedInstructions = new HashSet<>(program.size());

        do
        {
            Map.Entry<Instruction, Integer> instruction = program.get(lineNumber);
            executedInstructions.add(lineNumber);

            switch(instruction.getKey())
            {
                case ACC:
                    accumulator += instruction.getValue();
                    lineNumber++;
                    break;
                case JMP:
                    lineNumber += instruction.getValue();
                    break;
                case NOP:
                default:
                    lineNumber++;
                    break;
            }
        }
        while(!executedInstructions.contains(lineNumber) && lineNumber < program.size());
        System.out.println("Line #" + lineNumber + " already executed! Set = " + executedInstructions);
        System.out.println("accumulator = " + accumulator);
    }
}
