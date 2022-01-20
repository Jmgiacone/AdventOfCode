package com.github.jmgiacone.AdventOfCode.day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.regex.Pattern.matches;

public class Part2
{
    enum Key
    {
        BYR(value -> verifyDigits(value, 4) && verifyBounds(value, 1920, 2002)),
        IYR(value -> verifyDigits(value, 4) && verifyBounds(value, 2010, 2020)),
        EYR(value -> verifyDigits(value, 4) && verifyBounds(value, 2020, 2030)),
        HGT(value -> verifyHeight(value, 150, 193, 59, 76)),
        HCL(value -> matches("#[0-9a-f]{6}", value)),
        ECL(Set.of("amb", "blu", "brn", "gry", "grn", "hzl", "oth")::contains),
        PID(value -> matches("[0-9]{9}", value)),
        CID(value -> true);

        static Set<String> KEY_NAMES = Arrays.stream(values()).filter(key -> key != CID).map(Enum::name).map(String::toLowerCase).collect(Collectors.toSet());
        private Predicate<String> verificationMethod;

        Key(Predicate<String> verificationMethod)
        {
            this.verificationMethod = verificationMethod;
        }

        public Predicate<String> getVerificationMethod() {
            return verificationMethod;
        }

        public void setVerificationMethod(Predicate<String> verificationMethod) {
            this.verificationMethod = verificationMethod;
        }

        public boolean verify(String key)
        {
            return verificationMethod.test(key);
        }

        private static boolean verifyHeight(String value, int cmMin, int cmMax, int inMin, int inMax)
        {
            int length = value.length();
            int valueInt = Integer.parseInt(value.substring(0, length - 2));
            String unit = value.substring(length - 2 , length);

            switch(unit)
            {
                case "cm":
                    return verifyBounds(valueInt, cmMin, cmMax);
                case "in":
                    return verifyBounds(valueInt, inMin,inMax);
                default:
                    return false;
            }
        }

        private static boolean verifyDigits(String value, int numDigits)
        {
            return value.length() == numDigits;
        }

        private static boolean verifyBounds(String value, int min, int max)
        {
            int valueInt = Integer.parseInt(value);

            return verifyBounds(valueInt, min, max);
        }

        private static boolean verifyBounds(int value, int min, int max)
        {
            return value >= min && value <= max;
        }
    }

    //byr (Birth Year)
    //iyr (Issue Year)
    //eyr (Expiration Year)
    //hgt (Height)
    //hcl (Hair Color)
    //ecl (Eye Color)
    //pid (Passport ID)
    //cid (Country ID)

    public static void main(String[] args) throws IOException
    {
        List<String> passports = List.of(Files.readString(Paths.get("2020/src/main/resources/day4/part1/input.txt")).split("\r\n\r\n"));

        AtomicInteger count = new AtomicInteger(1);
        Map<Integer, Boolean> results = passports
                .stream()
                .collect(
                        Collectors.toMap(passport -> count.getAndIncrement(),
                                         Part2::isValid)
                );

        System.out.println("Valid passports: " + results.values().stream().filter(Boolean::booleanValue).count());
        System.out.println("results = " + results);

        passports.forEach(System.out::println);
    }

    private static boolean isValid(String passport)
    {
        Map<String, String> json = Arrays.stream(passport.split("\r\n")).map(line -> line.split(" ")).flatMap(Arrays::stream).collect(Collectors.toMap(token -> token.split(":")[0], token -> token.split(":")[1]));//.map(token -> token.split(":")[0]).collect(Collectors.toSet());

        return json.keySet().containsAll(Key.KEY_NAMES) &&
                json.entrySet()
                        .stream()
                        .allMatch(
                                entry -> Key.valueOf(entry.getKey().toUpperCase()).verify(entry.getValue())
                        );
    }
}
