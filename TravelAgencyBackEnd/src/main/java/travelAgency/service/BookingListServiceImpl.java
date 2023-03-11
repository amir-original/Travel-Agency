package travelAgency.service;

import travelAgency.domain.BookingInformation;
import travelAgency.domain.exceptions.NotFoundAnyBookingFlightException;
import travelAgency.repository.BookingListRepository;

import java.time.LocalDate;
import java.util.List;

public class BookingListServiceImpl implements BookingListService {

    private List<BookingInformation> tickets;
    private final BookingListRepository bookingListRepository;

    public BookingListServiceImpl(BookingListRepository bookingListRepository) {
        this.bookingListRepository = bookingListRepository;
        loadAllTickets();
    }

    private void loadAllTickets() {
        tickets = getAllTickets();
    }

    @Override
    public List<BookingInformation> getAllTickets() {
        return bookingListRepository.getAllTickets();
    }

    public BookingInformation search(String flightName, String passengerFirstName, LocalDate PassengerBirthday) {
        return tickets.stream()
                .filter(booking -> isFindTicketBy(flightName, passengerFirstName, PassengerBirthday, booking))
                .findFirst()
                .orElseThrow(NotFoundAnyBookingFlightException::new);
    }

    private boolean isFindTicketBy(String flightName, String passengerFirstName,
                                   LocalDate PassengerBirthday, BookingInformation booking) {

        return booking.flight().getSerialNumber().equals(flightName) &&
                booking.passenger().fName().equals(passengerFirstName) &&
                booking.passenger().birthday().equals(PassengerBirthday);
    }

}
