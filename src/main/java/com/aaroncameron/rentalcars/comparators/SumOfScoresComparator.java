package com.aaroncameron.rentalcars.comparators;

import com.aaroncameron.rentalcars.core.Car;

import java.util.Comparator;

public class SumOfScoresComparator implements Comparator<Car> {

    @Override
    public int compare(Car carOne, Car carTwo) {

        double carOneSumOfScores = carOne.getSumOfScores();
        double carTwoSumOfScores = carTwo.getSumOfScores();

        return carTwoSumOfScores > carOneSumOfScores ? +1 : carTwoSumOfScores < carOneSumOfScores ? -1 : 0;
    }
}
