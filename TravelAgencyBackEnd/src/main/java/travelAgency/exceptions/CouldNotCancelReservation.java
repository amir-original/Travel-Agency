package travelAgency.exceptions;

public class CouldNotCancelReservation extends RuntimeException{

    public CouldNotCancelReservation(String message) {
        super(message);
    }

    public static CouldNotCancelReservation becauseFlightIsDeparted() {
        return new CouldNotCancelReservation("Could not Cancel reservation because flight is departed!");
    }
}
