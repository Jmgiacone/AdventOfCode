package com.github.jmgiacone.AdventOfCode.day14;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Map.entry;

public class Part1
{
    private static final int BIT_LENGTH = 36;
    public static void main(String[] args) throws IOException
    {
        Map<Long, Long> memory = new LinkedHashMap<>();
        Map<Integer, Integer> currentMask = new HashMap<>(BIT_LENGTH);
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
                int[] binary = decimalToBinary(Long.parseLong(tokens[1]));
                applyMask(binary, currentMask);
                long maskedValue = binaryToDecimal(binary);

                memory.put(memoryLoc, maskedValue);
            }
        }

        long sum = memory.values().stream().reduce(0L, Long::sum);
        System.out.println("sum = " + sum);
    }

    private static void updateMask(Map<Integer, Integer> currentMask, char[] newMask)
    {
        currentMask.clear();

        for(int i = BIT_LENGTH - 1; i >= 0; i--)
        {
            char bit = newMask[i];
            if(bit != 'X')
            {
                currentMask.put(i, Integer.parseInt(bit + ""));
            }
        }
    }

    private static int[] decimalToBinary(final long decimal)
    {
        int[] binary = new int[BIT_LENGTH];
        long quotient = decimal;

        for(int index = BIT_LENGTH - 1; quotient != 0 && index >= 0; index--)
        {
            binary[index] = (int)(quotient % 2);
            quotient /= 2;
        }

        return binary;
    }

    private static long binaryToDecimal(final int[] binary)
    {
        long total = 0;
        for(int i = BIT_LENGTH - 1; i >= 0; i--)
        {
            total += binary[i] * (1L << (BIT_LENGTH - 1 - i));
        }

        return total;
    }

    private static void applyMask(int[] binary, Map<Integer, Integer> mask)
    {
        mask.forEach((k, v) -> binary[k] = v);
    }
}