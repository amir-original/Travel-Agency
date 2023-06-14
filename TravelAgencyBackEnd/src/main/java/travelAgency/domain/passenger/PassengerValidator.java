package travelAgency.domain.passenger;

import travelAgency.exceptions.*;

import java.util.regex.Pattern;

public class PassengerValidator {

    private final Passenger passenger;

    public PassengerValidator(Passenger passenger) {
        this.passenger = passenger;
    }

    public void validatePassengerInformation() {
        if (passenger.zipcode().isBlank())
            throw new IllegalArgumentException("the zipcode must not be null or empty!");
        if (passenger.address().isBlank())
            throw new IllegalArgumentException("the address must not be null or empty!");
    }

}
