package travelAgency.domain.passenger;

import java.time.LocalDate;

public class PassengerDtoBuilder {

    private String nationalCode;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private String city;
    private String address;
    private String zipcode;
    private String phoneNumber;

    public static PassengerDtoBuilder passenger() {
        return new PassengerDtoBuilder();
    }

    public PassengerDtoBuilder withNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
        return this;
    }

    public PassengerDtoBuilder firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public PassengerDtoBuilder lastName(String lastName){
        this.lastName = lastName;
        return this;
    }

    public PassengerDtoBuilder withBirthday(LocalDate date) {
        this.birthday = date;
        return this;
    }

    public PassengerDtoBuilder withAddress(String address) {
        this.address = address;
        return this;
    }

    public PassengerDtoBuilder ofCity(String city) {
        this.city = city;
        return this;
    }

    public PassengerDtoBuilder withZipcode(String zipcode) {
        this.zipcode = zipcode;
        return this;
    }

    public PassengerDtoBuilder withPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public PassengerDto build() {
        return new PassengerDto(nationalCode, firstName,lastName, birthday, city, address, zipcode, phoneNumber);
    }

}
