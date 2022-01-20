package com.github.jmgiacone.AdventOfCode.day12;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Part1
{
    public static final Direction STARTING_DIRECTION = Direction.EAST;

    enum Direction
    {
        NORTH(1, 0),
        WEST(0, -1),
        SOUTH(-1, 0),
        EAST(0, 1);

        private int northSouth;
        private int eastWest;

        Direction(int northSouth, int eastWest)
        {
            this.northSouth = northSouth;
            this.eastWest = eastWest;
        }

        public int getNorthSouth()
        {
            return northSouth;
        }

        public int getEastWest()
        {
            return eastWest;
        }

        Direction rotate(char dir, int value)
        {
            int rotations = (value / 90) % 4;
            rotations *= (dir == 'R' ? -1 : 1);

            int x = ordinal() + rotations;
            int index = x < 0 ? values().length + x : x % values().length;
            return values()[index];
        }

        static Direction fromCode(char code)
        {
            char upper = Character.toUpperCase(code);
            return Arrays.stream(values()).filter(direction -> direction.name().charAt(0) == upper).findFirst().get();
        }
    }

    public static void main(String[] args) throws IOException
    {
        Direction currentHeading = STARTING_DIRECTION;
        int northSouth = 0;
        int eastWest = 0;
        for(String line : Files.readAllLines(Paths.get("2020/src/main/resources/day12/part1/input.txt")))
        {
            System.out.print(line + " -> ");
            char command = line.charAt(0);
            int value = Integer.parseInt(line.substring(1));

            switch(command)
            {
                case 'L':
                case 'R':
                    currentHeading = currentHeading.rotate(command, value);
                    break;
                case 'N':
                case 'S':
                case 'E':
                case 'W':
                    Direction d = Direction.fromCode(command);
                    northSouth += value * d.getNorthSouth();
                    eastWest += value * d.getEastWest();
                    break;
                case 'F':
                    northSouth += value * currentHeading.getNorthSouth();
                    eastWest += value * currentHeading.getEastWest();
                    break;
            }
            System.out.printf("(%d, %d)\n", northSouth, eastWest);
        }

        System.out.println("Final position = (" + northSouth + ", " + eastWest + ") -> " + (Math.abs(northSouth) + Math.abs(eastWest)));
    }
}