package com.github.jmgiacone.AdventOfCode.day13;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Map.entry;

public class Part2
{
    public static void main(String[] args) throws IOException
    {
        List<String> file = Files.readAllLines(Paths.get("2020/src/main/resources/day13/part1/input.txt"));

        int mod = 0;
        Map<Integer, Integer> modulusMap = new HashMap<>();
        Integer incrementValue = null;
        for(String token : file.get(1).split(","))
        {
            if(token.equalsIgnoreCase("x"))
            {
                mod++;
                continue;
            }

            int busId = Integer.parseInt(token);

            if(incrementValue == null)
            {
                incrementValue = busId;
                modulusMap.put(busId, mod);
                System.out.printf("t %% %d = %d\n", busId, mod);
            }
            else
            {
                modulusMap.put(busId, busId - (mod % busId));
                System.out.printf("t %% %d = %d (%d)\n", busId, busId - (mod % busId), mod);
            }

            mod++;
        }

        System.out.println("modulusMap = " + modulusMap);
        System.out.println("findSolution(modulusMap) = " + findSolution(modulusMap));
    }

    private static long findSolution(Map<Integer, Integer> modulusMap)
    {
        long product = modulusMap.keySet().stream().map(x -> (long)x).reduce(1L, (a, b) -> a * b);
        long answer = 0;

        for(Map.Entry<Integer, Integer> entry : modulusMap.entrySet())
        {
            long mod = entry.getKey();
            long rem = entry.getValue();

            long nBar = product / mod;
            long u = modularDivision(1, nBar, mod);

            answer += rem * nBar * u;
        }

        return answer % product;
    }

    private static long modularDivision(long a, long b, long mod)
    {
        a %= mod;
        long inverse = modInverse(b, mod);
        if(inverse == -1)
        {
            return -1;
        }

        return (inverse * a) % mod;
    }

    private static long modInverse(long a, long mod)
    {
        long[] retVal = extendedGCD(a, mod);
        long gcd = retVal[0];
        long x = retVal[1];
        long y = retVal[2];

        if(gcd != 1)
        {
            return -1;
        }
        else
        {
            return (x % mod + mod) % mod;
        }
    }

    private static long[] extendedGCD(long a, long b)
    {
        if(a == 0)
        {
            return new long[] {b, 0, 1};
        }

        long x1, y1;
        long[] retVal = extendedGCD(b % a, a);
        x1 = retVal[1];
        y1 = retVal[2];

        retVal[1] = y1 - (b / a) * x1;
        retVal[2] = x1;

        return retVal;
    }
}