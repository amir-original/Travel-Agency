package travelAgency.exceptions;

public class CouldNotBookReservation extends RuntimeException {

    public CouldNotBookReservation(String message) {
        super(message);
    }

    public static CouldNotBookReservation becauseItIsDuplicate()
    throws CouldNotBookReservation
    {
        return new CouldNotBookReservation("Could not book reservation because it is duplicate");
    }
}
