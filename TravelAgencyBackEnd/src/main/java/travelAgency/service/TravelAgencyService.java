package travelAgency.service;

import travelAgency.domain.BookingInformation;
import travelAgency.domain.Flight;

import java.util.List;

public interface TravelAgencyService {

    List<Flight> findFlights(Flight flight);

    void book(BookingInformation bookingInformation);

}
