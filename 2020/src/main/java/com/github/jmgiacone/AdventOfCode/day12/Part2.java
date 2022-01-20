package com.github.jmgiacone.AdventOfCode.day12;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Part2
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
        int waypointNorthSouth = 1;
        int waypointEastWest = 10;
        int northSouth = 0;
        int eastWest = 0;

        System.out.printf("START\tShip: (%d, %d)\twaypoint: (%d, %d)\n", northSouth, eastWest, waypointNorthSouth, waypointEastWest);
        for(String line : Files.readAllLines(Paths.get("2020/src/main/resources/day12/part1/input.txt")))
        {
            System.out.print(line + " -> ");
            char command = line.charAt(0);
            int value = Integer.parseInt(line.substring(1));

            switch(command)
            {
                case 'L':
                case 'R':
                    //a + bi * e^it -> rotate
                    //e^it = cos(t) + i*sin(t)
                    //(a + bi) * (cos(t) + i*sin(t)) = a*cos(t) + a*i*sin(t) + b*i*cos(t) + b*i*i*sin(t)
                    // = (a*cos(t) - b*sin(t)) + (a*sin(t) + b*cos(t))i
                    int multiplier = command == 'R' ? -1 : 1;
                    value *= multiplier;

                    double radians = Math.toRadians(value);
                    double cosine = Math.cos(radians);
                    double sine = Math.sin(radians);

                    int nsTmp = waypointNorthSouth;
                    int ewTmp = waypointEastWest;
                    waypointEastWest = (int)Math.round(ewTmp * cosine - nsTmp * sine);
                    waypointNorthSouth = (int)Math.round(ewTmp * sine + nsTmp * cosine);
                    break;
                case 'N':
                case 'S':
                case 'E':
                case 'W':
                    Direction d = Direction.fromCode(command);
                    waypointNorthSouth += value * d.getNorthSouth();
                    waypointEastWest += value * d.getEastWest();
                    break;
                case 'F':
                    northSouth += value * waypointNorthSouth;
                    eastWest += value * waypointEastWest;
                    break;
            }
            System.out.printf("Ship: (%d, %d)\twaypoint: (%d, %d)\n", northSouth, eastWest, waypointNorthSouth, waypointEastWest);
        }

        System.out.printf("Ship: (%d, %d)\twaypoint: (%d, %d)\tvalue = %d\n", northSouth, eastWest, waypointNorthSouth, waypointEastWest, Math.abs(northSouth) + Math.abs(eastWest));
//        System.out.println("Final position = (" + northSouth + ", " + eastWest + ") -> " + (Math.abs(northSouth) + Math.abs(eastWest)));
    }

    static int rotate(int a, int b, int degrees)
    {
        double radians = Math.toRadians(degrees);
        double cosine = Math.cos(radians);
        double sine = Math.sin(radians);
        int tmpA = a;
        a = (int)(a * cosine - b * sine);
        b = (int)(tmpA * sine + b * cosine);

        return 0;
    }
}