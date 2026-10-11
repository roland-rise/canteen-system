package ug.ac.vu.canteen.app;

import java.util.List;
import ug.ac.vu.canteen.model.InvalidOrderException;
import ug.ac.vu.canteen.model.Order;
import ug.ac.vu.canteen.model.OrderNotFoundException;
import ug.ac.vu.canteen.model.StaffOrder;
import ug.ac.vu.canteen.model.StudentOrder;
import ug.ac.vu.canteen.service.OrderManager;

// The menu of the canteen system, main is here so the program starts from this class.
public class CanteenMenu {

    private static final String FULL_NAME = "Mande Roland";
    private static final String REG_NUMBER = "VU-BBC-2511-0461-DAY";

    private final OrderManager manager;
    private final InputHelper input;

    public CanteenMenu() {
        manager = new OrderManager();
        input = new InputHelper();
    }

    public static void main(String[] args) {
        CanteenMenu menu = new CanteenMenu();
        menu.run();
    }

    public void run() {
        System.out.println("=====================================");
        System.out.println("  School Canteen Charging System");
        System.out.println("  " + FULL_NAME);
        System.out.println("  Reg No: " + REG_NUMBER);
        System.out.println("=====================================");

        int choice;
        do {
            showMenu();
            choice = input.readInt("Choose an option: ", 0, 6);
            switch (choice) {
                case 1 -> addOrder(true);
                case 2 -> addOrder(false);
                case 3 -> showReport();
                case 4 -> findOrder();
                case 5 -> removeOrder();
                case 6 -> showSummary();
                case 0 -> System.out.println("Goodbye.");
            }
        } while (choice != 0);
    }

    private void showMenu() {
        System.out.println();
        System.out.println("1. Add student order");
        System.out.println("2. Add staff order");
        System.out.println("3. Show report (highest charge first)");
        System.out.println("4. Find an order");
        System.out.println("5. Remove an order");
        System.out.println("6. Show summary");
        System.out.println("0. Exit");
    }

    private void addOrder(boolean isStudent) {
        boolean saved = false;
        while (!saved) {
            String name = input.readText("Customer name: ");
            double total = input.readDouble("Total of items (UGX): ");
            try {
                Order order;
                if (isStudent) {
                    order = new StudentOrder(name, total);
                } else {
                    order = new StaffOrder(name, total);
                }
                manager.addOrder(order);
                System.out.println("Order saved: " + order);
                saved = true;
            } catch (InvalidOrderException e) {
                System.out.println("Not saved: " + e.getMessage());
                if (!input.readYesNo("Try again? (y/n): ")) {
                    return;
                }
            }
        }
    }

    private void showReport() {
        List<Order> sorted = manager.getSortedOrders();
        if (sorted.isEmpty()) {
            System.out.println("No orders yet.");
            return;
        }
        System.out.printf("%-3s %-9s %-25s %-8s %14s %12s %14s%n",
                "No.", "ID", "Customer", "Type", "Items total", "Discount", "Charge");
        int position = 1;
        // each order answers calculateCharge in its own way, that is polymorphism
        for (Order order : sorted) {
            System.out.printf("%-3d %-9s %-25s %-8s %,14.2f %,12.2f %,14.2f%n",
                    position, order.getOrderId(), order.getCustomerName(),
                    order.getType(), order.getItemsTotal(),
                    order.getDiscountAmount(), order.calculateCharge());
            position++;
        }
    }

    private void findOrder() {
        String id = input.readText("Enter order ID: ");
        try {
            System.out.println(manager.findById(id));
        } catch (OrderNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void removeOrder() {
        String id = input.readText("Enter ID of the order to remove: ");
        try {
            Order order = manager.findById(id);
            System.out.println(order);
            if (input.readYesNo("Remove this order? (y/n): ")) {
                manager.removeById(id);
                System.out.println("Order removed.");
            } else {
                System.out.println("Nothing was removed.");
            }
        } catch (OrderNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void showSummary() {
        System.out.println("---- Summary ----");
        System.out.println("Orders recorded: " + manager.getOrderCount());
        System.out.println("Orders with discount: " + manager.getDiscountedCount());
        System.out.printf("Total charges: UGX %,.2f%n", manager.getTotalCharges());
        System.out.printf("Total discount given: UGX %,.2f%n",
                manager.getTotalDiscount());
    }
}
