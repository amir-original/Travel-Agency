package travelAgency.domain.flight;

import org.jetbrains.annotations.NotNull;
import travelAgency.domain.booking.Reservation;
import travelAgency.domain.city.City;
import travelAgency.domain.exceptions.*;
import travelAgency.services.priceConverter.CurrencyConverterService;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public record Flight(@NotNull String flightNumber,
                     int totalCapacity,
                     double price,
                     @NotNull FlightPlan plan) {

    private static final int FULLY_BOOKED = 0;
    public static final int EMPTY_BOOKED = 0;

    public Flight(@NotNull String flightNumber, int totalCapacity, double price, @NotNull FlightPlan plan) {
        this.flightNumber = flightNumber;
        this.totalCapacity = totalCapacity;
        this.price = price;
        this.plan = plan;
        validate();
    }

    public void validate() {
        if (flightNumber.isBlank() || flightNumber.length() < 3)
            throw new FlightNumberException();
        if (price <= EMPTY_BOOKED) throw new FlightPriceException();
    }

    public void checkExistenceFlight(List<Flight> flights) {
        flights.stream()
                .filter(flight -> flight.matches(flightNumber))
                .findFirst()
                .orElseThrow(FlightNotFoundException::new);
    }

    public void checkAvailability(List<Reservation> reservations, int newTravelers) {
        if (isSoldOutAllSeats(reservations))
            throw new FullyBookedException();
        if (!isAvailableSeatsFor(reservations, newTravelers))
            throw new NotEnoughCapacityException();
    }

    private boolean isSoldOutAllSeats(List<Reservation> reservations) {
        return getAvailableSeats(reservations) == FULLY_BOOKED;
    }

    public int getAvailableSeats(List<Reservation> reservations) {
        return totalCapacity - getBookedSeats(reservations);
    }

    public int getBookedSeats(List<Reservation> reservations) {
        return reservations.isEmpty() ? EMPTY_BOOKED :
                reservations.stream()
                        .filter(flightTicket -> flightTicket.canMatchWith(flightNumber()))
                        .mapToInt(Reservation::travelers)
                        .sum();
    }

    private boolean isAvailableSeatsFor(List<Reservation> reservations, int newTravelers) {
        return getAvailableSeatsAfterBooking(reservations, newTravelers) >= FULLY_BOOKED;
    }

    private int getAvailableSeatsAfterBooking(List<Reservation> reservations, int newTravelers) {
        return totalCapacity - getTotalBookingSeats(reservations, newTravelers);
    }

    private int getTotalBookingSeats(List<Reservation> reservations, int newTravelers) {
        return newTravelers + getBookedSeats(reservations);
    }

    public boolean matches(FlightPlan flightPlan) {
        return plan.equals(flightPlan);
    }

    public boolean matches(String flightNumber) {
        return flightNumber().equals(flightNumber);
    }

    public void validateSchedule() {
        plan.validateSchedule();
    }

    public double price(CurrencyConverterService currencyConverter) {
        return currencyConverter.convert(price);
    }

    public City to() {
        return plan.to();
    }

    public City from() {
        return plan.from();
    }

    public LocalDate departure() {
        return plan.departure();
    }

    public LocalDate arrival() {
        return plan.arrival();
    }

    public String getFlightInfo(List<Reservation> reservations) {
        return String.format("""
                        Flight:
                           Flight Number: %s
                           From: %s
                           To: %s
                           Price: %s Rial
                           Available Seats: %s
                           select flight
                        """,
                flightNumber(),
                from(),
                to(),
                NumberFormat.getInstance().format(price()),
                getAvailableSeats(reservations));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return Double.compare(flight.price, price) == EMPTY_BOOKED &&
                Objects.equals(flightNumber, flight.flightNumber) &&
                Objects.equals(plan, flight.plan);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flightNumber, price, plan);
    }

    @Override
    public String toString() {
        return "Flight{" +
                "name='" + flightNumber + '\'' +
                ", price=" + price +
                ", spec=" + plan +
                '}';
    }
}
