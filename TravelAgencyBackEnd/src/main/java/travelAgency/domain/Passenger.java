package travelAgency.domain;

import travelAgency.domain.country.City;

import java.time.LocalDate;

public record Passenger(String id, String fName, String lName, LocalDate birthday,
                        City city, String address, String zipcode, String phoneNumber) {

}
