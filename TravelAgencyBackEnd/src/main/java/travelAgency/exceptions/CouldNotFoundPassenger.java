package travelAgency.exceptions;

public class CouldNotFoundPassenger extends RuntimeException {

    public CouldNotFoundPassenger(String message) {
        super(message);
    }

    public static CouldNotFoundPassenger withPassengerId(String passengerId)
            throws CouldNotFoundPassenger
    {
        return new CouldNotFoundPassenger("Could not found passenger " +
                "with passenger number: "+passengerId);
    }
}
