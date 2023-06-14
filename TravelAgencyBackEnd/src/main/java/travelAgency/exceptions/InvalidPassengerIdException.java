package travelAgency.exceptions;

public class InvalidPassengerIdException extends IllegalArgumentException {
    public InvalidPassengerIdException(String s) {
        super(s);
    }

    public InvalidPassengerIdException() {
    }
}
