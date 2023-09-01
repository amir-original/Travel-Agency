package travelAgency.infrastructure.persistence.jdbc_mysql.reservation;

public class CouldNotFoundReservation extends RuntimeException{

    public CouldNotFoundReservation() {
    }

    public CouldNotFoundReservation(String message) {
        super(message);
    }

    public static CouldNotFoundReservation withReservationNumber(String reservationNumber)
    throws CouldNotFoundReservation{
        return new CouldNotFoundReservation("Could not found reservation" +
                " with reservation number: "+reservationNumber);
    }

    public static CouldNotFoundReservation withFlightNumber(String flightNumber)
    throws CouldNotFoundReservation
    {
        return new CouldNotFoundReservation("Could not found reservation" +
                " with flight number: "+flightNumber);
    }
}
