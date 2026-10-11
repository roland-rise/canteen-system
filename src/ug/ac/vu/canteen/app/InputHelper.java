package ug.ac.vu.canteen.app;

import java.util.Scanner;

// Reads the keyboard and keeps asking until the input can be used.
public class InputHelper {

    private final Scanner scanner;

    public InputHelper() {
        scanner = new Scanner(System.in);
    }

    public String readText(String prompt) {
        System.out.print(prompt);
        return readLine().trim();
    }

    public int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String line = readLine().trim();
            try {
                int value = Integer.parseInt(line);
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.println("Please enter a number from " + min
                        + " to " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("That is not a whole number, try again.");
            }
        }
    }

    public double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            // people type 25,000 so the commas are taken out first
            String line = readLine().trim().replace(",", "");
            try {
                double value = Double.parseDouble(line);
                // parseDouble also accepts the words NaN and Infinity
                if (Double.isNaN(value) || Double.isInfinite(value)) {
                    System.out.println("That is not a valid amount, try again.");
                } else {
                    return value;
                }
            } catch (NumberFormatException e) {
                System.out.println("That is not a number. Type digits only, like 25000.");
            }
        }
    }

    public boolean readYesNo(String prompt) {
        while (true) {
            System.out.print(prompt);
            String answer = readLine().trim();
            if (answer.equalsIgnoreCase("y") || answer.equalsIgnoreCase("yes")) {
                return true;
            }
            if (answer.equalsIgnoreCase("n") || answer.equalsIgnoreCase("no")) {
                return false;
            }
            System.out.println("Please type y or n.");
        }
    }

    private String readLine() {
        // hasNextLine is false when the input is closed (Ctrl+D), nextLine would fail
        if (!scanner.hasNextLine()) {
            System.out.println();
            System.out.println("Input closed, the program will stop.");
            System.exit(0);
        }
        return scanner.nextLine();
    }
}
