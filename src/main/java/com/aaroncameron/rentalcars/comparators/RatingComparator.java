package com.aaroncameron.rentalcars.comparators;

import com.aaroncameron.rentalcars.core.Car;

import java.util.Comparator;

public class RatingComparator implements Comparator<Car>{

    @Override
    public int compare(Car carOne, Car carTwo) {

        double carOneRating = carOne.getRating();
        double carTwoRating = carTwo.getRating();

        return carTwoRating > carOneRating ? +1 : carTwoRating < carOneRating ? -1 : 0;
    }
}
