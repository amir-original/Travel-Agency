package travelAgency.domain.passenger;

import org.jetbrains.annotations.NotNull;
import travelAgency.domain.exceptions.*;

import java.time.LocalDate;
import java.util.regex.Pattern;

public record Passenger(@NotNull String id, @NotNull String fName, @NotNull String lName,
                        @NotNull LocalDate birthday, @NotNull String city, @NotNull String address,
                        @NotNull String zipcode, @NotNull String phoneNumber) {

    public Passenger(@NotNull String id,
                     @NotNull String fName,
                     @NotNull String lName,
                     @NotNull LocalDate birthday,
                     @NotNull String city,
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
        validatePassengerInformation();
    }

    public void validatePassengerInformation() {
        if (isNameBlank(fName,lName))
            throw new PassengerNameException();
        if (zipcode.isBlank())
            throw new PassengerZipCodeException();
        if (address.isBlank())
            throw new PassengerAddressException();
        if (phoneNumber.isBlank())
            throw new PhoneNumberNotEmptyException();
        if (isNotValidPhoneNumberFormat())
            throw new InvalidPhoneNumberException();
    }

    private boolean isNotValidPhoneNumberFormat() {
        return !Pattern.matches("^((\\+98)|(0)|(98)9)\\d{9}$",phoneNumber);
    }

    private boolean isNameBlank(String fName,String lName) {
        return fName.isBlank() || lName.isBlank();
    }

    public boolean canMatchWith(String passengerFirstName, LocalDate PassengerBirthday) {
        return fName().equals(passengerFirstName) &&
                birthday().equals(PassengerBirthday);
    }
}
