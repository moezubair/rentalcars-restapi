package com.aaroncameron.rentalcars.comparators;

import com.aaroncameron.rentalcars.core.Car;

import java.util.Comparator;

public class PriceComparator implements Comparator<Car> {

    @Override
    public int compare(Car carOne, Car carTwo) {

        Double carOnePrice = carOne.getPrice();
        Double carTwoPrice = carTwo.getPrice();

        return carOnePrice > carTwoPrice ? +1 : carOnePrice < carTwoPrice ? -1 : 0;
    }
}
