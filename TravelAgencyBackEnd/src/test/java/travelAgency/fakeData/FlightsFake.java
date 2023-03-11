package travelAgency.fakeData;

import travelAgency.domain.Flight;
import travelAgency.domain.FlightSchedule;
import travelAgency.domain.FlightInformation;
import travelAgency.domain.FlightLocation;
import travelAgency.domain.country.France;
import travelAgency.domain.country.Iran;

import java.time.LocalDate;
import java.util.List;

import static java.util.List.of;

public class FlightsFake {
    private final List<Flight> flights;
    private final LocalDate departure = LocalDate.of(2023, 3, 3);
    ;
    private final LocalDate arrival = departure.plusDays(3);
    private final FlightSchedule flightSchedule = new FlightSchedule(departure, arrival);

    {
        final FlightLocation flightTransit = new FlightLocation(Iran.TEHRAN, France.PARIS);

        flights = of(new Flight("0321", 145, new FlightInformation(flightTransit, flightSchedule)),
                new Flight("4256", 560, new FlightInformation(flightTransit, flightSchedule)),
                new Flight("0214", 450, new FlightInformation(flightTransit, flightSchedule)),
                new Flight("4578", 545, new FlightInformation(flightTransit, flightSchedule)));
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public Flight getFakeFlightFromIranToParis() {
        LocalDate departure = LocalDate.of(2023, 3, 7);
        final LocalDate arrival = departure.plusDays(3);
        return getFakeFlight("0321", new FlightLocation(Iran.TEHRAN, France.PARIS),
                new FlightSchedule(departure, arrival), 545);
    }

    private Flight getFakeFlight(String flightName, FlightLocation transfer, FlightSchedule flightSchedule, double price) {
        return new Flight(flightName, price,new FlightInformation(transfer, flightSchedule));
    }


}
