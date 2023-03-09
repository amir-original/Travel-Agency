package travelAgency.service;

import travelAgency.domain.BookingInformation;
import travelAgency.repository.TravelAgencyRepository;

public class TravelAgencyServiceImpl implements TravelAgencyService {

    private final TravelAgencyRepository travelAgencyRepository;
    private final FlightService flightService;

    public TravelAgencyServiceImpl(TravelAgencyRepository travelAgencyRepository, FlightService flightService) {
        this.travelAgencyRepository = travelAgencyRepository;
        this.flightService = flightService;
    }

    public void book(BookingInformation bf) {
        // check information
        checkBookingInformation(bf);

        // find flight
        flightService.checkingTheExistenceThisFlight(bf.flight());

        // booking
        travelAgencyRepository.book(bf);
    }

    private void checkBookingInformation(BookingInformation bf) {
        bf.check();
    }

}
