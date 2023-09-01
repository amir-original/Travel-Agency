package travelAgency.infrastructure.persistence.jdbc_mysql.reservation;

public class CouldNotLoadReservations extends RuntimeException{


    private CouldNotLoadReservations(String message) {
        super(message);
    }

    public static CouldNotLoadReservations becauseOf(String reason)
        throws CouldNotLoadReservations{
        return new CouldNotLoadReservations(reason);
    }
}
