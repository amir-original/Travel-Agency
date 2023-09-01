package travelAgency.infrastructure.persistence.jdbc_mysql.reservation;

import static java.lang.String.format;

public class CouldNotCancelReservation extends RuntimeException{

    public CouldNotCancelReservation(String message) {
        super(message);
    }

    public static CouldNotCancelReservation becauseReservationNumberIsWrong(String reservationNumber) {
        final String message = format("Could not Cancel reservation because" +
                " reservation number [%s] is not exist or wrong!", reservationNumber);
        return new CouldNotCancelReservation(message);
    }
}
