package travelAgency.domain.passenger;

import travelAgency.domain.vo.FullName;
import travelAgency.domain.vo.PassengerId;
import travelAgency.domain.vo.Phone;

import java.time.LocalDate;

public class PassengerBuilder {

    private PassengerId id;
    private FullName fullName;
    private LocalDate birthday;
    private String city;
    private String address;
    private String zipcode;
    private Phone phoneNumber;

    public static PassengerBuilder passenger() {
        return new PassengerBuilder();
    }

    public PassengerBuilder withId(PassengerId id) {
        this.id = id;
        return this;
    }

    public PassengerBuilder fullName(FullName fullName) {
        this.fullName = fullName;
        return this;
    }

    public PassengerBuilder withBirthday(LocalDate date) {
        this.birthday = date;
        return this;
    }

    public PassengerBuilder withAddress(String address) {
        this.address = address;
        return this;
    }

    public PassengerBuilder ofCity(String city) {
        this.city = city;
        return this;
    }

    public PassengerBuilder withZipcode(String zipcode) {
        this.zipcode = zipcode;
        return this;
    }

    public PassengerBuilder withPhoneNumber(Phone phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public Passenger build() {
        return new Passenger(id, fullName, birthday, city, address, zipcode, phoneNumber);
    }

}
