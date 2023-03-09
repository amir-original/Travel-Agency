package travelAgency.service;

import travelAgency.domain.BookingInformation;
import travelAgency.domain.Flight;
import travelAgency.repository.TravelAgencyRepository;

import java.util.LinkedList;
import java.util.List;

public class TravelAgencyServiceImpl implements TravelAgencyService {

    private final List<Flight> flights = new LinkedList<>();
    private final TravelAgencyRepository repository;

    public TravelAgencyServiceImpl(TravelAgencyRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Flight> findFlights(Flight flight) {
        return repository.findFlights(flight);
    }

    public void book(BookingInformation bookingInformation) {
        // check information
        bookingInformation.check();

        // find flight
        findFlight(bookingInformation);

        // booking
        repository.book(bookingInformation);
    }

    private void findFlight(BookingInformation bf) {
        for (Flight flight : flights) {
            flight.isTheSame(bf.flight());
        }
    }
}
