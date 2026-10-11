package ug.ac.vu.canteen.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import ug.ac.vu.canteen.model.InvalidOrderException;
import ug.ac.vu.canteen.model.Order;
import ug.ac.vu.canteen.model.OrderNotFoundException;

// Keeps all the orders and does the work on them: add, find, remove, totals, sorted list.
public class OrderManager {

    private final ArrayList<Order> orders;

    public OrderManager() {
        orders = new ArrayList<>();
    }

    public void addOrder(Order order) throws InvalidOrderException {
        if (order == null) {
            throw new InvalidOrderException("Order cannot be empty");
        }
        orders.add(order);
    }

    public Order findById(String id) throws OrderNotFoundException {
        if (id != null) {
            for (Order order : orders) {
                if (order.getOrderId().equalsIgnoreCase(id.trim())) {
                    return order;
                }
            }
        }
        throw new OrderNotFoundException(id);
    }

    public void removeById(String id) throws OrderNotFoundException {
        Order order = findById(id);
        orders.remove(order);
    }

    public int getOrderCount() {
        return orders.size();
    }

    public int getDiscountedCount() {
        int count = 0;
        for (Order order : orders) {
            if (order.isDiscountApplied()) {
                count++;
            }
        }
        return count;
    }

    public double getTotalCharges() {
        double total = 0;
        for (Order order : orders) {
            total += order.calculateCharge();
        }
        return total;
    }

    public double getTotalDiscount() {
        double total = 0;
        for (Order order : orders) {
            total += order.getDiscountAmount();
        }
        return total;
    }

    // a read-only copy, so no other class can change the private list
    public List<Order> getOrders() {
        return List.copyOf(orders);
    }

    // a sorted copy, so the real list keeps the order the orders were added in
    public List<Order> getSortedOrders() {
        ArrayList<Order> copy = new ArrayList<>(orders);
        // report order for A = 4: highest charge first, equal charges by ID
        Comparator<Order> byChargeHighest =
                Comparator.comparingDouble(Order::calculateCharge).reversed();
        copy.sort(byChargeHighest.thenComparing(Order::getOrderId));
        return copy;
    }
}
