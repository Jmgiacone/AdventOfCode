package com.github.jmgiacone.AdventOfCode.day14;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Part2
{
    private static final int BIT_LENGTH = 36;
    public static void main(String[] args) throws IOException
    {
        Map<Long, Long> memory = new LinkedHashMap<>();
        Map<Integer, Character> currentMask = new HashMap<>(BIT_LENGTH);
        List<String> file = Files.readAllLines(Paths.get("2020/src/main/resources/day14/part1/input.txt"));

        for(String instruction : file)
        {
            String[] tokens = instruction.split(" = ");

            if("mask".equalsIgnoreCase(tokens[0]))
            {
                updateMask(currentMask, tokens[1].toCharArray());
            }
            else
            {
                long memoryLoc = Long.parseLong(tokens[0].substring(4, tokens[0].indexOf(']')));
                long value = Long.parseLong(tokens[1]);
                char[] binary = decimalToBinary(memoryLoc);
                Set<Long> addresses = applyMask(binary, currentMask);

                addresses.forEach(address -> memory.put(address, value));
            }
        }

        long sum = memory.values().stream().reduce(0L, Long::sum);
        System.out.println("sum = " + sum);
    }

    private static void updateMask(Map<Integer, Character> currentMask, char[] newMask)
    {
        currentMask.clear();

        for(int i = BIT_LENGTH - 1; i >= 0; i--)
        {
            char bit = newMask[i];
            if(bit != '0')
            {
                currentMask.put(i, bit);
            }
        }
    }

    private static char[] decimalToBinary(final long decimal)
    {
        char[] binary = new char[BIT_LENGTH];

        for(int i = 0; i < BIT_LENGTH; i++)
        {
            binary[i] = '0';
        }

        long quotient = decimal;

        for(int index = BIT_LENGTH - 1; quotient != 0 && index >= 0; index--)
        {
            binary[index] = Character.forDigit((int)(quotient % 2), 2);
            quotient /= 2;
        }

        return binary;
    }

    private static long binaryToDecimal(final char[] binary)
    {
        long total = 0;
        for(int i = BIT_LENGTH - 1; i >= 0; i--)
        {
            total += Integer.parseInt(binary[i] + "") * (1L << (BIT_LENGTH - 1 - i));
        }

        return total;
    }

    private static Set<Long> applyMask(char[] binary, Map<Integer, Character> mask)
    {
        mask.forEach((k, v) -> binary[k] = v);

        return generatePossibilities(binary);
    }

    private static Set<Long> generatePossibilities(char[] binary)
    {
        List<Integer> wildcardPositions = new LinkedList<>();
        for(int i = BIT_LENGTH - 1; i >= 0; i--)
        {
            if(binary[i] == 'X')
            {
                wildcardPositions.add(i);
            }
        }

        Set<Long> possibilities = new HashSet<>();

        if(!wildcardPositions.isEmpty())
        {
            int iterations = 1 << wildcardPositions.size();
            for (int i = 0; i < iterations; i++)
            {
                char[] values = decimalToBinary(i);

                char[] possibility = Arrays.copyOf(binary, binary.length);
                int index = BIT_LENGTH - 1;
                for(Integer position : wildcardPositions)
                {
                    possibility[position] = values[index];
                    index--;
                }

                possibilities.add(binaryToDecimal(possibility));
            }
        }
        else
        {
            return Set.of(binaryToDecimal(binary));
        }

        return possibilities;
    }
}