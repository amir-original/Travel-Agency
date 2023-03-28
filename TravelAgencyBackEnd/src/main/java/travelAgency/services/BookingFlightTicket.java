package travelAgency.services;

import travelAgency.domain.booking.FlightTicket;
import travelAgency.domain.booking.FlightTicketInfo;
import travelAgency.repository.booking.BookingListRepositoryImpl;
import travelAgency.repository.db.mysq.MySQLDbConnection;
import travelAgency.repository.flight.FlightRepository;
import travelAgency.repository.passenger.PassengerRepository;
import travelAgency.services.booking.BookingFlightService;
import travelAgency.services.bookingList.BookingListService;
import travelAgency.services.bookingList.BookingListServiceImpl;

public class BookingFlightTicket {

    private final BookingFlightService ticketService;
    private final PassengerRepository passengerRepository;
    private final FlightRepository flightRepository;

    public BookingFlightTicket(BookingFlightService ticketService,
                               FlightRepository FlightRepository,
                               PassengerRepository passengerRepository) {
        this.ticketService = ticketService;
        this.passengerRepository = passengerRepository;
        this.flightRepository = FlightRepository;
    }

    public FlightTicket book(FlightTicketInfo flightTicketInfo) {
        check(flightTicketInfo);
        flightRepository.checkExistenceFlightWith(flightTicketInfo.flightNumber());
        // check availibilty
        passengerRepository.save(flightTicketInfo.passenger());
        return ticketService.book(flightTicketInfo);
    }

    private void check(FlightTicketInfo flightTicketInfo) {
        flightTicketInfo.check();
    }

}
