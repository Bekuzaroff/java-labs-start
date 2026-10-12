package edu.course.lab03;

public class NoDiscount implements DiscountPrivacy{
    @Override
    public double apply(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("price can't be negative");
        }
        return price;
    }
}
