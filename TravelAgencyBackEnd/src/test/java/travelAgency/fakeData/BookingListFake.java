package travelAgency.fakeData;

import travelAgency.domain.*;

import java.time.LocalDate;
import java.util.List;

import static java.time.LocalDate.of;

public class BookingListFake {

    public Passenger getPassenger() {
        return new Passenger("fly78478", "Sara", "Baiati",
                of(1999, 4, 5),
                "Tehran","Iran,Tehran",
                "1145789", "989907994339");
    }

    public Flight getFlight() {
        LocalDate departure = LocalDate.of(2023, 3, 7);
        final LocalDate arrival = departure.plusDays(3);
        return flight("0321", departure, arrival, "Tehran", "Paris", 545);
    }

    public Flight getNotFoundFlight(){
        LocalDate departure = LocalDate.of(2024, 3, 7);
        final LocalDate arrival = departure.plusDays(2);
        return flight("4578", departure, arrival, "Iran", "USA", 123);
    }

    private Flight flight(String name, LocalDate departure, LocalDate arrival, String from, String to, double price) {
        return new Flight(name, new FlightTransit(from, to), new FlightSchedule(departure, arrival), price);
    }

    public List<BookingInformation> getFakeBookingTickets() {
        return List.of(getBookingTicket());
    }

    public BookingInformation getBookingTicket() {
        return new BookingInformation(getFlight(), getPassenger(), 2);
    }

    public BookingInformation getBookingInfoWithNullPassenger(){
        return new BookingInformation(getFlight(), null, 2);
    }

    public BookingInformation getBookingInfoWithNullFlight() {
        return new BookingInformation(null, getPassenger(), 2);
    }

    public BookingInformation getNotFoundTicket() {
        return new BookingInformation(getNotFoundFlight(), getPassenger(), 2);
    }

    public BookingInformation getBookingInfoWithZeroOrNullTicket() {
        return new BookingInformation(getFlight(), getPassenger(), 0);
    }
}
