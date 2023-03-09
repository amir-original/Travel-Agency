package travelAgency.repository;

import travelAgency.domain.BookingInformation;
import travelAgency.domain.Flight;

import java.util.List;

public interface TravelAgencyRepository {

    List<Flight> findFlights(Flight flight);

    void book(BookingInformation bookingInformation);
}
