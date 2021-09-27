# FranceShipmentModule


## Approach:
1. We categorised all the rules into two types. **DiscountRules** and **ConstraintRules**
2. **DiscountRules**: Rules which provides discount on a shipment. eg. Rule1, Rule2
3. **ConstraintRules**: Rules which limits discount on a shipment. eg. Rule3(which limits the discounts in a given calander month)


DiscountRules
```
public interface DiscountRule {
    double applyDiscount(String inputLine, Map<String, Map<String, Double>> pricesByProvider, Map<String, Object> extraData);
}
```

ConstraintRule
```
public interface ConstraintRule {
    double applyConstraint(double discount, String inputLine, Map<String, Map<String, Double>> pricesByProvider, Map<String, Object> extraData);
}
```




## Assumptions:
1. For a shipment, multiple DiscountRules are not applicable. in other words, Only one/zero DiscountRule is applicable on a shipment.
2. Multiple ConstraintRules can limit the discount amount, and the final discount after all the ConstraintRules will be the minimum of permited discounts by all ConstraintRules
    
