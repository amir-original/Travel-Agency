package travelAgency.domain.flight;

import org.jetbrains.annotations.NotNull;
import travelAgency.domain.booking.Reservation;
import travelAgency.domain.city.City;
import travelAgency.domain.exceptions.*;
import travelAgency.services.priceConverter.CurrencyConverterService;

import java.time.LocalDate;
import java.util.List;

public record Flight(@NotNull String flightNumber,
                     int totalCapacity,
                     double price,
                     @NotNull FlightPlan plan) {

    private static final int NO_AVAILABLE_SEATS = -1;
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
        if (isNegativePrice()) throw new FlightPriceException();
    }

    private boolean isNegativePrice() {
        return price <= 0;
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
        return getAvailableSeats(reservations) == NO_AVAILABLE_SEATS;
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
        return getAvailableSeatsAfterBooking(reservations, newTravelers) >= NO_AVAILABLE_SEATS;
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

    public String price(CurrencyConverterService currencyConverter) {
        return formatPrice(currencyConverter.convert(price));
    }

    private String formatPrice(double price) {
        return String.format("%,.2f", price);
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
}
