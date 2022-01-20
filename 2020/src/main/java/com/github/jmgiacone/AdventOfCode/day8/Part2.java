package com.github.jmgiacone.AdventOfCode.day8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Map.entry;

public class Part2
{
    enum Instruction
    {
        ACC, JMP, NOP;
    }

    static class Program
    {
        private List<Map.Entry<Instruction, Integer>> instructions;

        public Program(List<Map.Entry<Instruction, Integer>> instructions)
        {
            this.instructions = new LinkedList<>(instructions);
        }

        public List<Map.Entry<Instruction, Integer>> getInstructions() {
            return instructions;
        }

        public void setInstructions(List<Map.Entry<Instruction, Integer>> instructions) {
            this.instructions = instructions;
        }
    }

    public static void main(String[] args) throws IOException
    {
       List<Map.Entry<Instruction, Integer>> originalProgram = Files.readAllLines(Paths.get("2020/src/main/resources/day8/part1/input.txt"))
                .stream()
                .map(String::toUpperCase)
                .map(line -> {
                    String[] tokens = line.split(" ");
                    int value = Integer.parseInt(tokens[1]);

                    return entry(Instruction.valueOf(tokens[0]), value);
                })
                .collect(Collectors.toList());

        //Map of index -> NEW instruction
        Map<Integer, Instruction> replacementMap = new HashMap<>();

        for(int i = 0; i < originalProgram.size(); i++)
        {
            Map.Entry<Instruction, Integer> line = originalProgram.get(i);

            switch(line.getKey())
            {
                case ACC:
                    break;
                case JMP:
                    replacementMap.put(i, Instruction.NOP);
                    break;
                case NOP:
                    replacementMap.put(i, Instruction.JMP);
                    break;
            }
        }

        //Generate new programs by flipping every NOP -> JMP and JMP -> NOP one-at-a-time
        List<Program> alteredPrograms = new LinkedList<>(List.of(new Program(originalProgram)));
        alteredPrograms.addAll(replacementMap
                .entrySet()
                .stream()
                .map(entry -> {
                    int lineNumber = entry.getKey();
                    Instruction replacement = entry.getValue();
                    List<Map.Entry<Instruction, Integer>> alteredProgram = new LinkedList<>(originalProgram);

                    Map.Entry<Instruction, Integer> instruction = alteredProgram.get(lineNumber);
                    instruction = entry(replacement, instruction.getValue());
                    alteredProgram.set(lineNumber, instruction);

                    return alteredProgram;
                 })
                .map(Program::new)
                .collect(Collectors.toList())
        );

        for(Program program : alteredPrograms)
        {
            Map.Entry<Boolean, Integer> result = terminates(program.getInstructions());

            if(result.getKey())
            {
                System.out.println("accumulator = " + result.getValue());
                break;
            }
        }
    }

    private static Map.Entry<Boolean, Integer> terminates(List<Map.Entry<Instruction, Integer>> program)
    {
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

        return entry(lineNumber == program.size(), accumulator);
    }
}
