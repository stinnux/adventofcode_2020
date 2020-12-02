package de.stinnux.aoc.day1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day1 {
    private List<Integer> integerList = new ArrayList<>();


    public Day1() throws FileNotFoundException {
        try (BufferedReader br = new BufferedReader(new FileReader("src/day1.txt"))) {
            String line = br.readLine();
            while (line != null) {
                integerList.add(Integer.parseInt(line));
                line = br.readLine();
            }
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    public void find2020() {
        for (int i=0; i < integerList.size(); i++) {
            int value1 = integerList.get(i);
            Integer value2 = findRest(value1, i);
            if (value2 != null) {
                System.out.println(value1 * value2);
                return;
            }
        }
    }

    public void find2020_three() {
        for (int i = 0; i < integerList.size(); i++) {
            int first = integerList.get(i);
            findSecond(first, i);
        }
    }

    protected Integer findRest(int first, int start) {
        for (int i=start; i < integerList.size(); i++) {
            if (first + integerList.get(i) == 2020) {
                return integerList.get(i);
            }
        }
        return null;
    }

    protected Integer findSecond(int first, int start) {
        for (int i=start; i < integerList.size(); i++) {
            Integer second = integerList.get(i);
            if (first + second < 2020) {
                Integer third = findRest(first + second, i);
                if (third != null) {
                    System.out.println(first * second * third);
                }
            }
        }
        return null;
    }
}
