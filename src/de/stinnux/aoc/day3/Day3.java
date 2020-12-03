package de.stinnux.aoc.day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Day3 {
    private String[] rows;

    private enum Typ {
        TREE, FREE
    }

    public void walk() {
        int w1_1 = walk(1, 1);
        int w3_1 = walk(3, 1);
        int w5_1 = walk(5, 1);
        int w7_1 = walk(7, 1);
        int w1_2 = walk(1, 2);

        System.out.println(w1_1 * w3_1 * w5_1 * w7_1 * w1_2);
    }

    public int walk(int r, int d) {
        int x = 0;
        int y = 0;
        int trees = 0;
        while (y < rows.length) {
            if (hasTree(x, y)) {
                trees++;
            }
            x += r;
            y += d;
        }
        return trees;
    }

    private boolean hasTree(int x, int y) {
        // Pattern repeats to the right
        if (x >= rows[y].length()) {
            x = x % rows[y].length();
        }
        return rows[y].charAt(x) == '#';
    }

    public Day3() throws IOException {
        final int line = 0;
        final int column = 0;
        rows = Files.readAllLines(Paths.get("src/day3.txt")).toArray(new String[]{});
        walk();
    }
}
