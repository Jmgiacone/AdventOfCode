package com.github.jmgiacone.AdventOfCode.day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Map.entry;

public class Part1
{
    //byr (Birth Year)
    //iyr (Issue Year)
    //eyr (Expiration Year)
    //hgt (Height)
    //hcl (Hair Color)
    //ecl (Eye Color)
    //pid (Passport ID)
    //cid (Country ID)
    private static final Set<String> REQUIRED_FIELDS = Set.of(
            "byr",
            "iyr",
            "eyr",
            "hgt",
            "hcl",
            "ecl",
            "pid");

    public static void main(String[] args) throws IOException
    {
        List<String> passports = List.of(Files.readString(Paths.get("2020/src/main/resources/day4/part1/input.txt")).split("\r\n\r\n"));

        AtomicInteger count = new AtomicInteger(1);
        Map<Integer, Boolean> results = passports
                .stream()
                .collect(
                        Collectors.toMap(passport -> count.getAndIncrement(),
                                         Part1::isValid)
                );

        System.out.println("results = " + results);
        System.out.println("Valid passports: " + results.values().stream().filter(Boolean::booleanValue).count());
    }

    private static boolean isValid(String passport)
    {
        Set<String> json = Arrays.stream(passport.split("\r\n")).map(line -> line.split(" ")).flatMap(Arrays::stream).map(token -> token.split(":")[0]).collect(Collectors.toSet());

        return json.containsAll(REQUIRED_FIELDS);
    }
}
