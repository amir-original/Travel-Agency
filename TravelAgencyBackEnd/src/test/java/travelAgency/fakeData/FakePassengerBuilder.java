package travelAgency.fakeData;

import travelAgency.domain.Passenger;
import travelAgency.domain.city.City;

import java.time.LocalDate;
import java.util.Random;

import static java.time.LocalDate.of;
import static travelAgency.domain.city.City.TEHRAN;

public class FakePassengerBuilder {

    private String id = "sa78478";
    private String fName = "Sara";
    private String lName = "Baiati";
    private LocalDate birthday = of(1999, 4, 5);
    private City city = TEHRAN;
    private String address = "Iran,TEHRAN";
    private String zipcode = "1145789";
    private String phoneNumber = "989907994339";

    public static FakePassengerBuilder passenger() {
        return new FakePassengerBuilder();
    }

    public FakePassengerBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public FakePassengerBuilder firstName(String firstName) {
        this.fName = firstName;
        return this;
    }

    public FakePassengerBuilder lastName(String lastName) {
        this.lName = lastName;
        return this;
    }

    public FakePassengerBuilder withBirthday(LocalDate date) {
        this.birthday = date;
        return this;
    }

    public FakePassengerBuilder address(String address) {
        this.address = address;
        return this;
    }

    public FakePassengerBuilder ofCity(City city) {
        this.city = city;
        return this;
    }

    public FakePassengerBuilder withZipcode(String zipcode) {
        this.zipcode = zipcode;
        return this;
    }

    public FakePassengerBuilder withPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public Passenger build() {
        return new Passenger(id, fName, lName, birthday, city, address, zipcode, phoneNumber);
    }

}
