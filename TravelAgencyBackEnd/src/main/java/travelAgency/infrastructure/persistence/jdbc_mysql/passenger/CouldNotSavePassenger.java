package travelAgency.infrastructure.persistence.jdbc_mysql.passenger;

public class CouldNotSavePassenger extends RuntimeException {

    public CouldNotSavePassenger(String message) {
        super(message);
    }

    public static CouldNotSavePassenger becauseItIsDuplicate()
            throws CouldNotSavePassenger
    {
        return new CouldNotSavePassenger("Could not save Passenger " +
                "because it is duplicate");
    }
}
