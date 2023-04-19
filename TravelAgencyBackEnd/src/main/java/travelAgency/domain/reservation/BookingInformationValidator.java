package travelAgency.domain.reservation;

import travelAgency.domain.exceptions.InvalidNumberOfTicketsException;

public class BookingInformationValidator {

    private final BookingInformation bookingInformation;

    public BookingInformationValidator(BookingInformation bookingInformation) {
        this.bookingInformation = bookingInformation;
    }

    public void validate() {
        if (hasNoTickets())
            throw new InvalidNumberOfTicketsException();
    }

    private boolean hasNoTickets() {
        return this.bookingInformation.numberOfTickets() <= 0;
    }
}
