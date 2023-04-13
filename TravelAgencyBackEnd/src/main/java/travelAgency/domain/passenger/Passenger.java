package travelAgency.domain.passenger;

import org.jetbrains.annotations.NotNull;
import travelAgency.domain.city.City;
import travelAgency.domain.exceptions.*;

import java.time.LocalDate;

public record Passenger(@NotNull String id, @NotNull String fName, @NotNull String lName,
                        @NotNull LocalDate birthday, @NotNull City city, @NotNull String address,
                        @NotNull String zipcode, @NotNull String phoneNumber) {

    public Passenger(@NotNull String id,
                     @NotNull String fName,
                     @NotNull String lName,
                     @NotNull LocalDate birthday,
                     @NotNull City city,
                     @NotNull String address,
                     @NotNull String zipcode,
                     @NotNull String phoneNumber) {
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

    public void validate() {
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
                "passengerId='" + id + '\'' +
                ", fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", birthday=" + birthday +
                ", city=" + city +
                ", withAddress='" + address + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    public boolean canMatchWith(String passengerFirstName, LocalDate PassengerBirthday) {
        return fName().equals(passengerFirstName) &&
                birthday().equals(PassengerBirthday);
    }
}
