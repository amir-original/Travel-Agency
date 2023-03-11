package travelAgency.fakeData;

import travelAgency.domain.*;
import travelAgency.domain.country.City;
import travelAgency.domain.country.France;
import travelAgency.domain.country.Iran;
import travelAgency.domain.country.UnitedStates;

import java.time.LocalDate;
import java.util.List;

import static java.time.LocalDate.of;

public class BookingListFake {

    public Passenger getPassenger() {
        return new Passenger("fly78478", "Sara", "Baiati",
                of(1999, 4, 5),
                Iran.TEHRAN,"Iran,TEHRAN",
                "1145789", "989907994339");
    }

    public Flight getFlight() {
        LocalDate departure = LocalDate.of(2023, 3, 3);
        final LocalDate arrival = departure.plusDays(3);
        return flight("0321", departure, arrival, Iran.TEHRAN, France.PARIS, 545);
    }

    public Flight getNotFoundFlight(){
        LocalDate departure = LocalDate.of(2024, 3, 7);
        final LocalDate arrival = departure.plusDays(2);
        return flight("4578", departure, arrival, Iran.TEHRAN, UnitedStates.LOS_ANGELES, 123);
    }

    private Flight flight(String name, LocalDate departure, LocalDate arrival, City from, City to, double price) {
        return new Flight(name, price,new FlightInformation(new FlightLocation(from, to), new FlightSchedule(departure, arrival)));
    }

    public List<BookingInformation> getFakeBookingTickets() {
        return List.of(getExistTicket());
    }

    public BookingInformation getExistTicket() {
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
