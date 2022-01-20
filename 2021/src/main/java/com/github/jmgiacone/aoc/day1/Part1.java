package com.github.jmgiacone.aoc.day1;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

/**
 * https://adventofcode.com/2021/day/1
 */
public class Part1
{
    public static void main(String[] args) throws Exception
    {
        List<Long> measurements = Files.lines(Path.of("2021/src/main/resources/day1/input.txt"))
                .map(String::trim)
                .map(Long::parseLong)
                .collect(Collectors.toList());

        Long prevMeasurement = null;
        int count = 0;
        for(Long measurement : measurements)
        {
            if(prevMeasurement == null)
            {
                prevMeasurement = measurement;
                continue;
            }

            if(measurement > prevMeasurement)
            {
                count++;
            }

            prevMeasurement = measurement;
        }

        System.out.println("count = " + count);
    }
}
