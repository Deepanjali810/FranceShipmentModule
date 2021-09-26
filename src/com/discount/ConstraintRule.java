package com.discount;

import java.util.Map;

public interface ConstraintRule {
    double applyConstraint(double discount, String inputLine, Map<String, Map<String, Double>> pricesByProvider, Map<String, Object> extraData);
}
