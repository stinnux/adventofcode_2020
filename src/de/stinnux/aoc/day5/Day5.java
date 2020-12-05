package de.stinnux.aoc.day5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Day5 {
    private final List<Ticket> ticketList = new ArrayList<>();


    private class Ticket implements Comparable {
        public String location;
        public int row;
        public int seat;
        public int checksum;

        public Ticket(String location, int row, int seat) {
            this.location = location;
            this.row = row;
            this.seat = seat;
            calculateCheckSum();
        }

        private void calculateCheckSum() {
            this.checksum = row * 8 + seat;
        }

        public int compareTo(Object o) {
            Ticket t = (Ticket) o;
            if (this.row == t.row) {
                return this.seat - t.seat;
            }
            return this.row - t.row;
        }
    }

    private void pathLine(String line) throws InvalidDirectionException, InvalidCalculationException {
        int minRow = 0;
        int maxRow = 127;
        for (int i = 0; i <= 6; i++) {
            if (line.charAt(i) == 'F') {
                maxRow = maxRow - (int) Math.ceil((maxRow - minRow) / 2.0);
            } else if (line.charAt(i) == 'B') {
                minRow = minRow + (int) Math.ceil((maxRow - minRow) / 2.0);
            } else {
                throw new InvalidDirectionException(String.format("line '%s' position '%d' contains invalid character '%s'" ,
                        line,
                        i,
                        line.charAt(i)));
            }
        }
        if (minRow != maxRow) {
            throw new InvalidCalculationException(String.format("minRow is %d while maxRow is %d for line '%s'" , minRow, maxRow, line));
        }

        int minSeat = 0;
        int maxSeat = 7;
        for (int i = 7; i <= 9; i++) {
            if (line.charAt(i) == 'R') {
                minSeat = minSeat + (int) Math.ceil((maxSeat - minSeat) / 2.0);
            } else if (line.charAt(i) == 'L') {
                maxSeat = maxSeat - (int) Math.ceil((maxSeat - minSeat) / 2.0);
            } else {
                throw new InvalidDirectionException(String.format("line '%s' position '%d' contains invalid character '%s'" ,
                        line,
                        i,
                        line.charAt(i)));
            }
        }
        if (minSeat != maxSeat) {
            throw new InvalidCalculationException(String.format("minRow is %d while maxRow is %d for line '%s'" , minRow, maxRow, line));
        }

        this.ticketList.add(new Ticket(line, minRow, minSeat));

    }

    public Day5() throws IOException, InvalidDirectionException, InvalidCalculationException {
        for (String s : Files.readAllLines(Paths.get("src/day5.txt"))) {
            pathLine(s);
        }

        part2();
    }

    private void part1() {
        ticketList.sort(Comparator.comparingInt(o -> o.checksum));
        Ticket last = ticketList.get(ticketList.size() - 1);

        System.out.println(last.checksum);
    }

    private void part2() {
        Collections.sort(ticketList);

        // Create HashMap of ids
        HashMap<Integer, Ticket> hashedTickets = new HashMap<>();
        for (Ticket t : ticketList) {
            hashedTickets.put(t.checksum, t);
        }

        int nextseat = -1;
        for (Ticket t : ticketList) {
            //intialize nextseat
            if (nextseat == -1) nextseat = t.seat;
            // Doesn't work if the first seat is missing ?! Because we get the row from the ticketList?!
            // But answer is correct, so fine for now
            if (t.seat != nextseat) {
                int checksum_missing = t.row * 8 + nextseat;
                if (hashedTickets.get(checksum_missing - 1) != null && hashedTickets.get(checksum_missing + 1) != null) {
                    System.out.println(String.format("Empty seat is at row %d and seat %d, checksum %d" , t.row, nextseat, (t.row * 8) + nextseat));
                    nextseat++;
                    if (nextseat > 7) nextseat = 0;
                }
            }
            nextseat++;
            if (nextseat > 7) nextseat = 0;
        }
    }

    public static void main(String args[]) throws InvalidDirectionException, IOException, InvalidCalculationException {
        Day5 d5 = new Day5();
    }
}
