package travelAgency.repository;

import travelAgency.domain.BookingInformation;
import travelAgency.domain.Flight;

import java.util.LinkedList;
import java.util.List;

public class TravelAgencyRepositoryImpl implements TravelAgencyRepository {

    @Override
    public List<Flight> findFlights(Flight flight) {
        return new LinkedList<>();
    }

    @Override
    public void book(BookingInformation bookingInformation) {

    }
}
