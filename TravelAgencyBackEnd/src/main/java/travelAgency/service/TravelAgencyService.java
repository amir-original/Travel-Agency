package travelAgency.service;

import travelAgency.domain.BookingInformation;

public interface TravelAgencyService {

    //List<Flight> findFlights(Flight flight);

    void book(BookingInformation bookingInformation);

}
