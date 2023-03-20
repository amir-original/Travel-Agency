package travelAgency.fakeData;

import travelAgency.domain.FlightTicket;
import travelAgency.domain.FlightTicketInfo;
import travelAgency.helper.UniqueIdGenerator;

import static travelAgency.fakeData.FakeFlightTicketInfoBuilder.flightTicketInfo;

public class FakeFlightTicketBuilder {
    private String flightTicketNumber = UniqueIdGenerator.generate(8);
    private FlightTicketInfo flightTicketInfo = flightTicketInfo().build();

    public static FakeFlightTicketBuilder flightTicket() {
        return new FakeFlightTicketBuilder();
    }

    public FakeFlightTicketBuilder withFlightTicketNumber(String ticketNumber){
        this.flightTicketNumber = ticketNumber;
        return this;
    }

    public FakeFlightTicketBuilder withFlightTicketInfo(FlightTicketInfo flightTicketInfo){
        this.flightTicketInfo = flightTicketInfo;
        return this;
    }


    public FlightTicket build() {
        return new FlightTicket(flightTicketNumber,flightTicketInfo);
    }

}
