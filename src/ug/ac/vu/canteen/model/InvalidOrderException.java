package ug.ac.vu.canteen.model;

// Thrown when an order is given a value that breaks the canteen rules.
// It is checked, because the menu can recover by asking the user again.
public class InvalidOrderException extends Exception {

    public InvalidOrderException(String message) {
        super(message);
    }
}
