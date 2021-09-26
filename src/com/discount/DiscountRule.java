package com.discount;

import java.util.Map;

public interface DiscountRule {
    double applyDiscount(String inputLine, Map<String, Map<String, Double>> pricesByProvider, Map<String, Object> extraData);
}
