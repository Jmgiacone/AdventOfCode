package com.github.jmgiacone.aoc.day1;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

/**
 * https://adventofcode.com/2021/day/1
 */
public class Part2
{
    public static void main(String[] args) throws Exception
    {
        List<Long> measurements = Files.lines(Path.of("2021/src/main/resources/day1/input.txt"))
                .map(String::trim)
                .map(Long::parseLong)
                .collect(Collectors.toList());

        Long prevMeasurement = null;
        int count = 0;

        long rollingA = 0;
        long rollingB = 0;
        for(int i = 0; i < measurements.size() - 3; i++)
        {
            //Start off the rolling sum at the beginning of the list
            if(i == 0)
            {
                rollingA = measurements.get(i) + measurements.get(i + 1) + measurements.get(i + 2);
                rollingB = measurements.get(i + 1) + measurements.get(i + 2) + measurements.get(i + 3);
            }
            //Now that the rolling sum has started, all we have to do is remove the first and add the last
            // to keep the correct sum
            else
            {
                rollingA = rollingA - measurements.get(i - 1) + measurements.get(i + 2);
                rollingB = rollingB - measurements.get(i) + measurements.get(i + 3);
            }

            if(rollingB > rollingA)
            {
                count++;
            }
        }

        System.out.println("count = " + count);
    }
}
