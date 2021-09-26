package com.discount;


import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Driver {
    public static void main(String[] args) throws IOException {
        File file = new File("src/com/discount/inputFile.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String inputLine;
        Map<String, Map<String, Double>> prices = new HashMap<>();
        Map<String, Double> lpPriceMap = new HashMap<>();
        lpPriceMap.put("S", 1.5);
        lpPriceMap.put("M", 4.9);
        lpPriceMap.put("L", 6.9);
        prices.put("LP", lpPriceMap);
        Map<String, Double> mrPriceMap = new HashMap<>();
        mrPriceMap.put("S", 2d);
        mrPriceMap.put("M", 3d);
        mrPriceMap.put("L", 4d);
        prices.put("MR", mrPriceMap);

        DiscountRule rule1 = new DiscountRule1();
        DiscountRule rule2 = new DiscountRule2();

        List<DiscountRule> rules = new ArrayList<>();
        rules.add(rule1);
        rules.add(rule2);

        ConstraintRule constraintRule1 = new TotalDiscountPerMonthConstraint();

        List<ConstraintRule> constraintRules = new ArrayList<>();
        constraintRules.add(constraintRule1);
        Map<String, Object> extraData = new HashMap<>();

        HashMap<String, Double> totalDiscountSoFarByMonth = new HashMap<>();
        HashMap<String, Integer> lShipmentByMonthCount = new HashMap<>();
        extraData.put("totalDiscountSoFarByMonth", totalDiscountSoFarByMonth);
        extraData.put("lShipmentByMonthCount", lShipmentByMonthCount);

        FileWriter myWriter = new FileWriter("src/com/discount/outputFile.txt");

        while ((inputLine = br.readLine()) != null) {
            System.out.println(inputLine);
            String[] inputs = inputLine.split(" ");
            if (!isValidInput(inputs, prices)) {
                myWriter.write(inputLine + " ignored");
                myWriter.write("\n");
                continue;
            }
            double discount = 0;
            for (DiscountRule rule : rules) {
                discount = rule.applyDiscount(inputLine, prices, extraData);
                if (discount > 0) {
                    break;
                }
            }
            // 10 per month
            // 4 per week
            // day1: 4
            // day2: 0.5: NOT
            // day8: 0.5: YES

            String date = inputs[0];
            String size = inputs[1];
            String provider = inputs[2];
            double originalPrice = prices.get(provider).get(size);
            if (discount > 0) {
                double discountAfterConstraints = discount;
                for (ConstraintRule constraintRule : constraintRules) {
                    discountAfterConstraints = Math.min(discountAfterConstraints, constraintRule.applyConstraint(discount, inputLine, prices, extraData));
                }

                String month = date.substring(0, 7);
                if (totalDiscountSoFarByMonth.get(month) == null) {
                    totalDiscountSoFarByMonth.put(month, discountAfterConstraints);
                } else {
                    totalDiscountSoFarByMonth.put(month, totalDiscountSoFarByMonth.get(month) + discountAfterConstraints);
                }
                System.out.println(discountAfterConstraints);
                myWriter.write(inputLine + " " + Math.round(100*(originalPrice - discountAfterConstraints))/100d+ " " + Math.round(100*discountAfterConstraints)/100d);
            } else {
                myWriter.write(inputLine + " " + (originalPrice) + " -");
                System.out.println("---");
            }
            myWriter.write("\n");
        }
        myWriter.close();

    }

    private static boolean isValidInput(String[] inputs, Map<String, Map<String, Double>> prices) {
        if (inputs.length != 3) {
            return false;
        }
        String size = inputs[1];
        String provider = inputs[2];
        if (!size.equalsIgnoreCase("s") && !size.equalsIgnoreCase("m") && !size.equalsIgnoreCase("l")) {
            return false;
        }
        if (!prices.containsKey(provider)) {
            return false;
        }
        return true;
    }
}
