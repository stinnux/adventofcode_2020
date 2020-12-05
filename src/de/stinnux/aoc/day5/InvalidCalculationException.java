package de.stinnux.aoc.day5;

public class InvalidCalculationException extends Throwable {
    public InvalidCalculationException(String format) {
        super(format);
    }
}
