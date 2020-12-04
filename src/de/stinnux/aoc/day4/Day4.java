package de.stinnux.aoc.day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day4 {
    private final List<Passport> passports = new ArrayList<>();

    public Day4() throws IOException {
        Passport p = new Passport();
        Iterator<String> it = Files.readAllLines(Paths.get("src/day4.txt")).iterator();
        while (it.hasNext()) {
            String line = it.next();
            if (line.length() > 0) {
                readLine(p, line);
            } else {
                passports.add(p);
                p = new Passport();
            }
        }
        passports.add(p);
        countValidRange();
    }

    private void readLine(Passport p, String line) {
        String[] attrbutes = line.split("\\s");
        for (String a : attrbutes) {
            String[] fv = a.split(":");
            p.addAttribute(fv[0], fv[1]);
        }
    }


    public void countValid() {
        System.out.println(this.passports.stream().filter(p -> p.isValid()).count());
    }

    public void countValidRange() {
        List<Passport> validPassports = this.passports.stream().filter(p -> p.isValidRange()).collect(Collectors.toList());
        System.out.println(this.passports.stream().filter(p -> p.isValidRange()).count());
    }

    private class Passport {
        Pattern haircolor = Pattern.compile("^#[0-9a-f]{6}$");
        Pattern passportid = Pattern.compile("^[0-9]{9}$");
        private final Map<String, String> attributes = new HashMap<>();
        private final String[] requiredAttributes = {"byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"};
        private final String[] allowedColors = {"amb", "blu", "brn", "gry", "grn", "hzl", "oth"};
        private boolean isValid;

        public void addAttribute(String key, String value) {
            attributes.put(key, value);
        }

        public boolean isValid() {
            for (String a : requiredAttributes) {
                if (!attributes.containsKey(a)) {
                    return false;
                }
            }
            return true;
        }

        public boolean isValidRange() {
            if (!isValid()) {
                return false;
            }
            try {
                Integer byr = Integer.parseInt(attributes.get("byr"));
                if (byr < 1920 || byr > 2002) {
                    return false;
                }
                Integer iyr = Integer.parseInt(attributes.get("iyr"));
                if (iyr < 2010 || iyr > 2020) {
                    return false;
                }
                Integer eyr = Integer.parseInt(attributes.get("eyr"));
                if (eyr < 2020 || eyr > 2030) {
                    return false;
                }
                String hgt = attributes.get("hgt");
                String unit = hgt.substring(hgt.length() - 2);
                Integer value = Integer.parseInt(hgt.substring(0, hgt.length() - 2));
                if (unit.equals("cm")) {
                    if (value < 150 || value > 193) {
                        return false;
                    }
                } else if (unit.equals("in")) {
                    if (value < 59 || value > 76) {
                        return false;
                    }
                } else {
                    return false;
                }
                if (!haircolor.matcher(attributes.get("hcl")).matches()) {
                    return false;
                }

                boolean colorok = false;
                for (String clr : allowedColors) {
                    if (attributes.get("ecl").equals(clr)) {
                        colorok = true;
                        break;
                    }
                }
                if (!colorok) {
                    return false;
                }

                if (!passportid.matcher(attributes.get("pid")).matches()) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }

            this.isValid = true;
            return true;
        }
    }
}
