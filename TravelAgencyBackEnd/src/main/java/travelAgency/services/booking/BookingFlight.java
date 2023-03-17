package travelAgency.services.booking;

import travelAgency.domain.FlightTicket;
import travelAgency.repository.booking.BookingFlightRepository;
import travelAgency.repository.flight.FindFlightRepository;
import travelAgency.repository.passenger.PassengerRepository;

public class BookingFlight {

    private final BookingFlightRepository bookingRepository;
    private final PassengerRepository passengerRepository;
    private final FindFlightRepository findFlightRepository;

    public BookingFlight(BookingFlightRepository bookingRepository,
                         FindFlightRepository findFlightRepository, PassengerRepository passengerRepository) {
        this.bookingRepository = bookingRepository;
        this.passengerRepository = passengerRepository;
        this.findFlightRepository = findFlightRepository;
    }

    public void book(FlightTicket flightTicket) {
        flightTicket.check();
        findFlightRepository.checkExistenceFlightWith(flightTicket.flightPlan());

        passengerRepository.save(flightTicket.passenger());
        bookingRepository.book(flightTicket);
    }

}
