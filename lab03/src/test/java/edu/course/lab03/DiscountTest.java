package edu.course.lab03;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DiscountTest {
    // NO DISCOUNT
    @Test
    void returnsPrice(){
        NoDiscount obj = new NoDiscount();

        assertEquals(100, obj.apply(100));
    }

    @Test
    void priceNegative(){
        NoDiscount obj = new NoDiscount();

        assertThrows(IllegalArgumentException.class, () -> obj.apply(-10));
    }

    // PERCENT DISCOUNT
    @Test
    void returnsPriceWithDiscount(){
        PercentDiscount obj = new PercentDiscount(20);

        assertEquals(80, obj.apply(100));
    }

    @Test
    void wrongDiscount(){
        assertThrows(IllegalArgumentException.class, () -> new PercentDiscount(-20));
    }

}
