package travelAgency.domain;

import java.time.LocalDate;

public record Passenger(String id, String fName, String lName, LocalDate birthday,
                        String city, String address, String zipcode, String phoneNumber) {

}
