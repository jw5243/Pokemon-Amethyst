package com.horse.pokemon;

import java.util.Random;

public class RandomNumberGenerator {
    public static int generateNumber(int minimum, int maximum) {
        return new Random().nextInt(maximum - minimum + 1) + minimum;
    }
}