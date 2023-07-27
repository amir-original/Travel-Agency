package travelAgency.application.use_case;

public final class FlightAvailability {

    private final FindReservationService bookingLists;

    public FlightAvailability(FindReservationService reservationList) {
        this.bookingLists = reservationList;
    }

}
