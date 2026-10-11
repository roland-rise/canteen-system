package ug.ac.vu.canteen.model;

// Staff order: charge is the items total plus a 10% service charge, no discount.
public class StaffOrder extends Order {

    private static final double SERVICE_CHARGE_RATE = 0.10;

    public StaffOrder(String customerName, double itemsTotal)
            throws InvalidOrderException {
        super(customerName, itemsTotal);
    }

    @Override
    public double calculateCharge() {
        return getItemsTotal() + getItemsTotal() * SERVICE_CHARGE_RATE;
    }

    @Override
    public String getType() {
        return "Staff";
    }
}
