package com.github.jmgiacone.AdventOfCode.day17;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Part1
{
    static class SetBoard
    {
        private static String toKey(int plane, int row, int column)
        {
            return String.format("%d,%d,%d", plane, row, column);
        }

        public static final char ACTIVE_CHARACTER = '#';
        public static final char INACTIVE_CHARACTER = '.';
        private static final int PLANE = 0;
        private static final int ROW = 1;
        private static final int COLUMN = 2;

        private Set<String> active;
        private int[] minCoords;
        private int[] maxCoords;

        public SetBoard(char[][] inputGrid)
        {
            active = new HashSet<>();
            minCoords = new int[]{0, Integer.MAX_VALUE, Integer.MAX_VALUE}; //plane, row, column
            maxCoords = new int[]{0, -1, -1}; //plane, row, column

            for(int row = 0; row < inputGrid.length; row++)
            {
                for(int column = 0; column < inputGrid[row].length; column++)
                {
                    if(inputGrid[row][column] == ACTIVE_CHARACTER)
                    {
                        minCoords[ROW] = Math.min(row, minCoords[ROW]);
                        minCoords[COLUMN] = Math.min(column, minCoords[COLUMN]);

                        maxCoords[ROW] = Math.max(row, maxCoords[ROW]);
                        maxCoords[COLUMN] = Math.max(column, maxCoords[COLUMN]);

                        active.add(toKey(0, row, column));
                    }
                }
            }
        }

        public void cycle()
        {
            Set<String> nextState = new HashSet<>(active);

            int pMin = minCoords[PLANE];
            int rMin = minCoords[ROW];
            int cMin = minCoords[COLUMN];
            
            int pMax = maxCoords[PLANE];
            int rMax = maxCoords[ROW];
            int cMax = maxCoords[COLUMN];
            resetMinMax();
            
            for(int plane = pMin - 1; plane <= pMax + 1; plane++)
            {
                for(int row = rMin - 1; row <= rMax + 1; row++)
                {
                    for(int column = cMin - 1; column <= cMax + 1; column++)
                    {
                        String key = toKey(plane, row, column);
                        int neighbors = numNeighbors(plane, row, column);

                        if(active.contains(key))
                        {
                            if(!(neighbors == 2 || neighbors == 3))
                            {
                                nextState.remove(key);
                            }
                            else
                            {
                                updateMinMax(plane, row, column);
                            }
                        }
                        else
                        {
                            if(neighbors == 3)
                            {
                                nextState.add(key);
                                updateMinMax(plane, row, column);
                            }
                        }
                    }
                }
            }

            active = nextState;
        }

        public int numActive()
        {
            return active.size();
        }

        public int numNeighbors(int plane, int row, int column)
        {
            int count = 0;
            for(int p = plane - 1; p <= plane + 1; p++)
            {
                for(int r = row - 1; r <= row + 1; r++)
                {
                    for(int c = column - 1; c <= column + 1; c++)
                    {
                        if(!(p == plane && r == row && c == column) && active.contains(toKey(p, r, c)))
                        {
                            count++;
                        }
                    }
                }
            }

            return count;
        }

        private void resetMinMax()
        {
            minCoords[PLANE] = Integer.MAX_VALUE;
            minCoords[ROW] = Integer.MAX_VALUE;
            minCoords[COLUMN] = Integer.MAX_VALUE;

            maxCoords[PLANE] = Integer.MIN_VALUE;
            maxCoords[ROW] = Integer.MIN_VALUE;
            maxCoords[COLUMN] = Integer.MIN_VALUE;
        }
        
        private void updateMinMax(int plane, int row, int column)
        {
            minCoords[PLANE] = Math.min(plane, minCoords[PLANE]);
            minCoords[ROW] = Math.min(row, minCoords[ROW]);
            minCoords[COLUMN] = Math.min(column, minCoords[COLUMN]);

            maxCoords[PLANE] = Math.max(plane, maxCoords[PLANE]);
            maxCoords[ROW] = Math.max(row, maxCoords[ROW]);
            maxCoords[COLUMN] = Math.max(column, maxCoords[COLUMN]);
        }

        @Override
        public String toString()
        {
            StringBuilder builder = new StringBuilder();
            for(int plane = minCoords[PLANE]; plane <= maxCoords[PLANE]; plane++)
            {
                builder.append("z=").append(plane).append("\n");
                for(int row = minCoords[ROW]; row <= maxCoords[ROW]; row++)
                {
                    for(int column = minCoords[COLUMN]; column <= maxCoords[COLUMN]; column++)
                    {
                        builder.append(active.contains(toKey(plane, row, column)) ? ACTIVE_CHARACTER : INACTIVE_CHARACTER);
                    }

                    builder.append("\n");
                }

                if(plane != maxCoords[PLANE])
                {
                    builder.append("\n");
                }
            }

            return builder.toString();
        }
    }

    public static final int ITERATIONS = 6;
    public static void main(String[] args) throws IOException
    {
        Map<String, Set<Map.Entry<Integer, Integer>>> rules = new HashMap<>();
        char[][] inputGrid = Files.readAllLines(Paths.get("2020/src/main/resources/day17/part1/input.txt"))
                .stream()
                .map(String::toCharArray)
                .toArray(char[][]::new);

        SetBoard board = new SetBoard(inputGrid);

        System.out.println("Before any cycles:");
        System.out.println(board);
        for(int i = 0; i < ITERATIONS; i++)
        {
            board.cycle();
            System.out.printf("After %d cycle%s:\n", i + 1, i == 0 ? "" : "s");
            System.out.println(board);
        }
        System.out.println("board.numActive() = " + board.numActive());

        //Board is a 3D cube that is infinite in all three directions
        //It doesn't really matter where this grid (on the face) goes as it's infinite
        //The indices in this board will really just represent offsets from a specific point

        //According to the example, it looks like the cube
    }
}