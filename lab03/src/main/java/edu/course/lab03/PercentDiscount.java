package edu.course.lab03;

public class PercentDiscount implements DiscountPrivacy{
    private final double percent;
    PercentDiscount(double percent){
        if (percent > 100 || percent < 0){
            throw new IllegalArgumentException("percent should be between 0 and 100");
        }
        this.percent = percent;
    }
    @Override
    public double apply(double price) {
        return price * (1 - this.percent / 100);
    }

    double getPercent() { return this.percent; }
}
