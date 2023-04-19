package travelAgency.domain.passenger;

import travelAgency.domain.exceptions.*;

import java.util.regex.Pattern;

public class PassengerValidator {

    private final Passenger passenger;

    public PassengerValidator(Passenger passenger) {
        this.passenger = passenger;
    }

    public void validatePassengerInformation() {
        if (isNameBlank())
            throw new PassengerNameException();
        if (passenger.zipcode().isBlank())
            throw new PassengerZipCodeException();
        if (passenger.address().isBlank())
            throw new PassengerAddressException();
        if (passenger.phoneNumber().isBlank())
            throw new PhoneNumberNotEmptyException();
        if (isNotValidPhoneNumberFormat())
            throw new InvalidPhoneNumberException();
    }

    private boolean isNotValidPhoneNumberFormat() {
        return !Pattern.matches("^((\\+98)|(0)|(98)9)\\d{9}$", passenger.phoneNumber());
    }

    private boolean isNameBlank() {
        return passenger.fName().isBlank() || passenger.lName().isBlank();
    }

}
