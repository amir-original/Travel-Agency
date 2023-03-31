package travelAgency.fake;

import travelAgency.domain.booking.BookingInformation;
import travelAgency.domain.booking.FlightTicket;
import travelAgency.helper.UniqueIdGenerator;

import static travelAgency.fake.FakeBookingInformationBuilder.bookingInformation;

public class FakeFlightTicketBuilder {
    private String flightTicketNumber = UniqueIdGenerator.generate(8);
    private BookingInformation bookingInformation = bookingInformation().build();

    public static FakeFlightTicketBuilder flightTicket() {
        return new FakeFlightTicketBuilder();
    }

    public FakeFlightTicketBuilder withFlightTicketNumber(String ticketNumber){
        this.flightTicketNumber = ticketNumber;
        return this;
    }

    public FakeFlightTicketBuilder withBookingInfo(BookingInformation bookingInformation){
        this.bookingInformation = bookingInformation;
        return this;
    }


    public FlightTicket build() {
        return new FlightTicket(flightTicketNumber,bookingInformation);
    }

}
