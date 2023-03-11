package travelAgency.fakeData;

import travelAgency.domain.Flight;
import travelAgency.domain.FlightSchedule;
import travelAgency.domain.FlightTransit;
import travelAgency.domain.country.France;
import travelAgency.domain.country.Iran;

import java.time.LocalDate;
import java.util.List;

import static java.util.List.of;

public class FlightsFake {
    private final List<Flight> flights;
    private final LocalDate departure = LocalDate.of(2023, 3, 3);;
    private final LocalDate arrival = departure.plusDays(3);
    private final FlightSchedule flightSchedule = new FlightSchedule(departure, arrival);

    {
        flights = of(new Flight("0321",new FlightTransit(Iran.TEHRAN, France.PARIS), flightSchedule, 145),
                new Flight("4256",new FlightTransit(Iran.TEHRAN,France.PARIS), flightSchedule, 560),
                new Flight("0214",new FlightTransit(Iran.TEHRAN,France.PARIS), flightSchedule, 450),
                new Flight("4578", new FlightTransit(Iran.TEHRAN,France.PARIS), flightSchedule, 545));
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public Flight getFakeFlightFromIranToParis(){
        LocalDate departure = LocalDate.of(2023, 3, 7);
        final LocalDate arrival = departure.plusDays(3);
        return getFakeFlight("0321", new FlightTransit(Iran.TEHRAN, France.PARIS),
                new FlightSchedule(departure, arrival), 545);
    }

    private Flight getFakeFlight(String flightName, FlightTransit transfer, FlightSchedule flightSchedule, double price){
        return new Flight(flightName, transfer, flightSchedule, price);
    }


}
