package travelAgency.domain.passenger;

import org.jetbrains.annotations.NotNull;
import travelAgency.domain.city.City;
import travelAgency.domain.exceptions.*;

import java.time.LocalDate;

public record Passenger(@NotNull String id, @NotNull String fName, @NotNull String lName,
                        @NotNull LocalDate birthday, @NotNull City city, @NotNull String address,
                        @NotNull String zipcode, @NotNull String phoneNumber) {

    public void check() {
        if (isNameBlank(fName,lName))
            throw new PassengerNameException();
        if (zipcode.isBlank())
            throw new PassengerZipCodeException();
        if (address.isBlank())
            throw new PassengerAddressException();
        if (phoneNumber.isBlank())
            throw new PassengerPhoneNumbersNotEmptyException();
        if (phoneNumber.length() != 12)
            throw new PhoneNumberLengthException();
    }

    private boolean isNameBlank(String fName,String lName) {
        return fName.isBlank() || lName.isBlank();
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "passenger_id='" + id + '\'' +
                ", fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", birthday=" + birthday +
                ", city=" + city +
                ", address='" + address + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
