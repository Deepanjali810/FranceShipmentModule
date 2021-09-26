package com.discount;

import java.util.Map;

public class DiscountRule2 implements DiscountRule {
    @Override
    public double applyDiscount(String inputLine, Map<String, Map<String, Double>> pricesByProvider, Map<String, Object> extraData) {
        String[] inputs = inputLine.split(" ");
        String date = inputs[0];
        String size = inputs[1];
        String provider = inputs[2];
        if (size.equalsIgnoreCase("l") && provider.equalsIgnoreCase("lp")) {
            Map<String, Integer> lShipmentByMonthCount = (Map<String, Integer>) extraData.get("lShipmentByMonthCount");
            String month = date.substring(0, 7);
            if (lShipmentByMonthCount.get(month) == null) {
                lShipmentByMonthCount.put(month, 1);
            } else {
                lShipmentByMonthCount.put(month, lShipmentByMonthCount.get(month) + 1);
            }
            if (lShipmentByMonthCount.get(month) == 3) {
                return pricesByProvider.get(provider).get(size);
            }
            return -1;
        } else {
            return -1;
        }
    }
}
