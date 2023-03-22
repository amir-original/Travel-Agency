package travelAgency.services;

import travelAgency.domain.booking.FlightTicket;
import travelAgency.domain.booking.FlightTicketInfo;
import travelAgency.repository.flight.FindFlightRepository;
import travelAgency.repository.passenger.PassengerRepository;
import travelAgency.services.booking.BookingTicketService;

public class BookingFlightTicket {

    private final BookingTicketService ticketService;
    private final PassengerRepository passengerRepository;
    private final FindFlightRepository findFlightRepository;

    public BookingFlightTicket(BookingTicketService ticketService,
                               FindFlightRepository findFlightRepository,
                               PassengerRepository passengerRepository) {
        this.ticketService = ticketService;
        this.passengerRepository = passengerRepository;
        this.findFlightRepository = findFlightRepository;
    }

    public FlightTicket book(FlightTicketInfo flightTicketInfo) {
        check(flightTicketInfo);
        findFlightRepository.checkExistenceFlightWith(flightTicketInfo.flightNumber());
        passengerRepository.save(flightTicketInfo.passenger());
        final FlightTicket flightTicket = ticketService.book(flightTicketInfo);
        return flightTicket;
    }

    private void check(FlightTicketInfo flightTicketInfo) {
        flightTicketInfo.check();
    }

}
