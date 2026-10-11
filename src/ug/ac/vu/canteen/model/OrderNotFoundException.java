package ug.ac.vu.canteen.model;

// Thrown when an order ID is searched for or removed but no such order exists.
public class OrderNotFoundException extends Exception {

    private final String orderId;

    public OrderNotFoundException(String orderId) {
        super("No order found with ID " + orderId);
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }
}
