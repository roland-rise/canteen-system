package ug.ac.vu.canteen.model;

// Parent of StudentOrder and StaffOrder, holds what every order shares.
public abstract class Order implements Chargeable {

    private static final String ID_PREFIX = "MR461-";
    private static final double MAX_ITEMS_TOTAL = 10_000_000;
    private static final int MAX_NAME_LENGTH = 25;
    private static int nextNumber = 1;

    private final String orderId;
    private String customerName;
    private double itemsTotal;
    private boolean discountApplied;

    public Order(String customerName, double itemsTotal) throws InvalidOrderException {
        setCustomerName(customerName);
        setItemsTotal(itemsTotal);
        // the ID number is only used up after both values passed their checks
        // %03d pads the number with zeros to 3 digits, so 1 becomes 001
        this.orderId = ID_PREFIX + String.format("%03d", nextNumber);
        nextNumber++;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getItemsTotal() {
        return itemsTotal;
    }

    public boolean isDiscountApplied() {
        return discountApplied;
    }

    // final so a subclass cannot replace these checks, the constructor also uses them
    public final void setCustomerName(String customerName) throws InvalidOrderException {
        if (customerName == null || customerName.isBlank()) {
            throw new InvalidOrderException("Customer name cannot be blank");
        }
        String cleaned = customerName.trim();
        if (cleaned.length() > MAX_NAME_LENGTH) {
            throw new InvalidOrderException("Customer name cannot be longer than "
                    + MAX_NAME_LENGTH + " characters");
        }
        // a name made only of digits or symbols is almost always a typing mistake
        if (!cleaned.matches(".*\\p{L}.*")) {
            throw new InvalidOrderException("Customer name must contain at least one letter");
        }
        this.customerName = cleaned;
    }

    public final void setItemsTotal(double itemsTotal) throws InvalidOrderException {
        // isNaN is needed: parseDouble accepts the word NaN and it passes <= 0
        if (Double.isNaN(itemsTotal) || itemsTotal <= 0) {
            throw new InvalidOrderException("Items total must be more than zero");
        }
        if (itemsTotal > MAX_ITEMS_TOTAL) {
            throw new InvalidOrderException("Items total cannot be more than UGX "
                    + String.format("%,.0f", MAX_ITEMS_TOTAL));
        }
        this.itemsTotal = itemsTotal;
        // updated here, so the flag can never be out of date after the total changes
        this.discountApplied = discountApplies();
    }

    @Override
    public abstract double calculateCharge();

    public abstract String getType();

    // a subclass says when its discount rule is met, the default is never
    protected boolean discountApplies() {
        return false;
    }

    // no discount unless a subclass says otherwise
    public double getDiscountAmount() {
        return 0;
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s, items UGX %,.2f, discount UGX %,.2f, "
                + "charge UGX %,.2f", orderId, customerName, getType(), itemsTotal,
                getDiscountAmount(), calculateCharge());
    }
}
