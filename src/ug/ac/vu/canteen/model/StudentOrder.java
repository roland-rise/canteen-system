package ug.ac.vu.canteen.model;

// Student order: charge is the items total, less 11% when the total is above 20,000.
public class StudentOrder extends Order {

    private static final double DISCOUNT_RATE = 0.11;
    private static final double DISCOUNT_THRESHOLD = 20_000;

    public StudentOrder(String customerName, double itemsTotal)
            throws InvalidOrderException {
        super(customerName, itemsTotal);
    }

    @Override
    public double calculateCharge() {
        return getItemsTotal() - getDiscountAmount();
    }

    @Override
    public double getDiscountAmount() {
        if (isDiscountApplied()) {
            return getItemsTotal() * DISCOUNT_RATE;
        }
        return 0;
    }

    @Override
    public String getType() {
        return "Student";
    }

    // must be above 20,000, so exactly 20,000 gets no discount
    @Override
    protected boolean discountApplies() {
        return getItemsTotal() > DISCOUNT_THRESHOLD;
    }
}
