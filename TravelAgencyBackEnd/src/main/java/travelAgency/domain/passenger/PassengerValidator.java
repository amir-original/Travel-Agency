package travelAgency.domain.passenger;

import travelAgency.exceptions.*;

import java.util.regex.Pattern;

public class PassengerValidator {

    private final Passenger passenger;

    public PassengerValidator(Passenger passenger) {
        this.passenger = passenger;
    }

    public void validatePassengerInformation() {
        if (isNameBlank())
            throw new IllegalArgumentException("the name must not be null or empty!");
        if (passenger.zipcode().isBlank())
            throw new IllegalArgumentException("the zipcode must not be null or empty!");
        if (passenger.address().isBlank())
            throw new IllegalArgumentException("the address must not be null or empty!");
        if (passenger.phoneNumber().isBlank())
            throw new IllegalArgumentException("the phoneNumber must not be null or empty!");
        if (isNotValidPhoneNumberFormat())
            throw new InvalidPhoneNumberException();
    }

    private boolean isNotValidPhoneNumberFormat() {
        return !Pattern.matches("^((\\+98)|(0)|(98))9\\d{9}$", passenger.phoneNumber());
    }

    private boolean isNameBlank() {
        return passenger.fName().isBlank() || passenger.lName().isBlank();
    }

}
