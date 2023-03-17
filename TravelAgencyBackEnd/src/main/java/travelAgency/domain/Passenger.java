package travelAgency.domain;

import org.jetbrains.annotations.NotNull;
import travelAgency.domain.city.City;
import travelAgency.domain.exceptions.*;

import java.time.LocalDate;

public record Passenger(@NotNull String id, @NotNull String fName, @NotNull String lName, @NotNull LocalDate birthday,
                        @NotNull City city, @NotNull String address, @NotNull String zipcode,
                        @NotNull String phoneNumber) {

    public Passenger(@NotNull String id, @NotNull String fName, @NotNull String lName, @NotNull LocalDate birthday,
                     @NotNull City city, @NotNull String address,
                     @NotNull String zipcode, @NotNull String phoneNumber) {
        this.id = id;
        this.fName = fName;
        this.lName = lName;
        this.birthday = birthday;
        this.city = city;
        this.address = address;
        this.zipcode = zipcode;
        this.phoneNumber = phoneNumber;

        validate();
    }

    private void validate() {
        if (isNameBlank())
            throw new PassengerNameException();
        if (zipcode.isBlank())
            throw new PassengerZipCodeNotNullException();
        if (address.isBlank())
            throw new PassengerAddressNotNullException();
        if (phoneNumber.isBlank())
            throw new PassengerPhoneNumbersNotEmptyException();
        if (phoneNumber.length() != 12)
            throw new PhoneNumberLengthSizeException();
    }

    private boolean isNameBlank() {
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
