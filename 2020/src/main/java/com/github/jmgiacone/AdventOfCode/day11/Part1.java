package com.github.jmgiacone.AdventOfCode.day11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Part1
{
    public static void main(String[] args) throws IOException
    {
        //Read in the input
        char[][] board = Files.readAllLines(Paths.get("2020/src/main/resources/day11/part1/input.txt"))
                .stream()
                .map(String::toCharArray)
                .toArray(char[][]::new);
        char[][] prevBoard;
        int iteration = 0;
        int numChanged;
        int numOccupied;

        do
        {
            System.out.println("iteration = " + iteration);
            iteration++;
//            displayBoard(board);
            prevBoard = new char[board.length][];
            for(int i = 0; i < board.length; i++)
            {
                prevBoard[i] = Arrays.copyOf(board[i], board[i].length);
            }

            numChanged = 0;
            numOccupied = 0;
            for(int i = 0; i < board.length; i++)
            {
                for(int j = 0; j < board[i].length; j++)
                {
                    char occupation = prevBoard[i][j];

                    if(occupation == '.')
                    {
                        continue;
                    }

                    int neighbors = numNeighbors(i, j, prevBoard);
                    if(occupation == 'L' && neighbors == 0)
                    {
                        numChanged++;
                        board[i][j] = '#';
                    }
                    else if(occupation == '#' && neighbors >= 4)
                    {
                        numChanged++;
                        board[i][j] = 'L';
                    }

                    if(board[i][j] == '#')
                    {
                        numOccupied++;
                    }
                }
            }
        }
        while(numChanged != 0);

        System.out.println("Final board");
        displayBoard(board);
        System.out.println("numOccupied = " + numOccupied);
//        System.out.println("Total occupied: " + numOccupied(board));
    }

    public static int numOccupied(char[][] board)
    {
        int count = 0;
        for(char[] chars : board)
        {
            for(char aChar : chars)
            {
                if(aChar == '#')
                {
                    count++;
                }
            }
        }

        return count;
    }

    public static int numNeighbors(int i, int j, char[][] board)
    {
        int xStart = Math.max(0, i - 1);
        int yStart = Math.max(0, j - 1);
        int xEnd = Math.min(board.length, i + 2);
        int yEnd = Math.min(board[0].length, j + 2);

        int count = 0;
        for(int x = xStart; x < xEnd; x++)
        {
            for(int y = yStart; y < yEnd; y++)
            {
                if(!(x == i && y == j) && board[x][y] == '#')
                {
                    count++;
                }
            }
        }

        return count;
    }

    public static void displayBoard(char[][] board)
    {
        for(char[] chars : board)
        {
            StringBuilder builder = new StringBuilder();
            for(char aChar : chars)
            {
                builder.append(aChar);
            }
            System.out.println(builder);
        }
        System.out.println();
    }

    public static int boardDiff(char[][] board1, char[][] board2)
    {
        int count = 0;
        for(int i = 0; i < board1.length; i++)
        {
            for(int j = 0; j < board1[i].length; j++)
            {
                if(board1[i][j] != board2[i][j])
                {
                    count++;
                }
            }
        }

        return count;
    }
}
