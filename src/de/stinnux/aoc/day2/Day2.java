package de.stinnux.aoc.day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day2 {
    private List<Password> passwordList = new ArrayList<>();
    private Pattern p = Pattern.compile("(\\d+)\\-(\\d+) (\\p{Lower}): (\\p{Lower}+)");

    private class Password {
        public String original;
        public int min;
        public int max;
        public int actual;
        public char c;
        public String password;
        public boolean isValid;

        public Password(String original, int min, int max, char c, String password) {
            this.original = original;
            this.min = min;
            this.max = max;
            this.c = c;
            this.password = password;

            checkSecond();
        }

        @Override
        public String toString() {
            return "Password{" +
                    "original='" + original + '\'' +
                    ", min=" + min +
                    ", max=" + max +
                    ", actual=" + actual +
                    ", c=" + c +
                    ", password='" + password + '\'' +
                    ", isValid=" + isValid +
                    '}';
        }

        private void checkFirst() {
            this.actual = countCharacter();
            this.isValid = actual >= min && actual <= max;
        }

        private void checkSecond() {
            this.isValid = (this.password.charAt(this.min - 1) == this.c && this.password.charAt(this.max - 1) != this.c) ||
                    (this.password.charAt(this.min - 1) != this.c && this.password.charAt(this.max - 1) == this.c);
        }

        private int countCharacter() {
            int count = 0;
            for (int i = 0; i < password.length(); i++) {
                if (password.charAt(i) == c) {
                    count++;
                }
            }
            return count;
        }

    }

    private void addElement(String datarow) {
        Matcher m = p.matcher(datarow);
        if (m.find()) {
            this.passwordList.add(new Password(datarow, Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)), m.group(3).charAt(0), m.group(4)));
        }
    }


    public Day2() throws IOException {
        Files.readAllLines(Paths.get("src/day2.txt")).stream().forEach(str -> this.addElement(str));
        System.out.println(passwordList.stream().filter(password -> password.isValid).count());
    }
}
