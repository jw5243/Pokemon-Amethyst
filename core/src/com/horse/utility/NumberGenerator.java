package com.horse.utility;

import java.util.Random;

public class NumberGenerator {
    public static int generateNumber(int minimum, int maximum) {
        return new Random().nextInt(maximum - minimum + 1) + minimum;
    }
}