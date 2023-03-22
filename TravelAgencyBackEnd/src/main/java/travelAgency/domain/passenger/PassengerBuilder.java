package travelAgency.domain.passenger;

import travelAgency.domain.city.City;

import java.time.LocalDate;

public class PassengerBuilder {

    private String id ;
    private String fName;
    private String lName ;
    private LocalDate birthday;
    private City city;
    private String address;
    private String zipcode ;
    private String phoneNumber;

    public static PassengerBuilder passenger() {
        return new PassengerBuilder();
    }

    public PassengerBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public PassengerBuilder firstName(String firstName) {
        this.fName = firstName;
        return this;
    }

    public PassengerBuilder lastName(String lastName) {
        this.lName = lastName;
        return this;
    }

    public PassengerBuilder withBirthday(LocalDate date) {
        this.birthday = date;
        return this;
    }

    public PassengerBuilder address(String address) {
        this.address = address;
        return this;
    }

    public PassengerBuilder ofCity(City city) {
        this.city = city;
        return this;
    }

    public PassengerBuilder withZipcode(String zipcode) {
        this.zipcode = zipcode;
        return this;
    }

    public PassengerBuilder withPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public Passenger build() {
        return new Passenger(id, fName, lName, birthday, city, address, zipcode, phoneNumber);
    }

}
