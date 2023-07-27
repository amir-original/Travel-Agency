package travelAgency.model.flight;

public class InvalidFlightCapacity extends IllegalArgumentException {

    public InvalidFlightCapacity() {
    }

    public InvalidFlightCapacity(String s) {
        super(s);
    }

    public static InvalidFlightCapacity becauseItIsNegative()
    throws InvalidFlightCapacity{
        return new InvalidFlightCapacity("Capacity must be a positive integer.");
    }
}
