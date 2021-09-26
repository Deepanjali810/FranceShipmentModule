package com.discount;

import java.util.Map;

public class TotalDiscountPerMonthConstraint implements ConstraintRule {
    @Override
    public double applyConstraint(double discount, String inputLine, Map<String, Map<String, Double>> pricesByProvider, Map<String, Object> extraData) {
        String[] inputs = inputLine.split(" ");
        String date = inputs[0];
        String size = inputs[1];
        String provider = inputs[2];
        Map<String, Double> totalDiscountSoFarByMonth = (Map<String, Double>) extraData.get("totalDiscountSoFarByMonth");
        String month = date.substring(0, 7);
        if (totalDiscountSoFarByMonth.get(month) == null) {
            return Math.min(10d, discount);
        } else {
            return Math.min(10 - totalDiscountSoFarByMonth.get(month), discount);
        }
    }
}
