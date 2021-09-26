package com.discount;

import java.util.Map;

public class DiscountRule1 implements DiscountRule {
    @Override
    public double applyDiscount(String inputLine, Map<String, Map<String, Double>> pricesByProvider, Map<String, Object> extraData) {
        String[] inputs = inputLine.split(" ");
        String date = inputs[0];
        String size = inputs[1];
        String provider = inputs[2];
        if (size.equalsIgnoreCase("s")) {
            double currentPrice = pricesByProvider.get(provider).get(size);
            double minPrice = currentPrice;
            for (String providerCode : pricesByProvider.keySet()) {
                minPrice = Math.min(minPrice, pricesByProvider.get(providerCode).get(size));
            }
            if ((currentPrice - minPrice) > 0) {
                return currentPrice - minPrice;
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }
}
