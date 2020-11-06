package model;

import java.util.HashMap;
import java.util.Map;

public class OrderBy {

    private Map<String, Boolean> orders = new HashMap<>();

    public void appendOrder(String key, boolean isDesc) {
        orders.put(key, isDesc);
    }

    public Map<String, Boolean> getOrders() {
        return orders;
    }
}
