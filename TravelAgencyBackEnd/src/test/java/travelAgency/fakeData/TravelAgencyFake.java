package travelAgency.fakeData;

import travelAgency.domain.Flight;
import travelAgency.domain.FlightSchedule;
import travelAgency.domain.FlightTransit;

import java.time.LocalDate;
import java.util.List;

import static java.util.List.of;

public class TravelAgencyFake {
    private List<Flight> flights;

    public TravelAgencyFake() {
        init();
    }

    public void init() {
        LocalDate departure = LocalDate.of(2023, 3, 3);
        final LocalDate arrival = departure.plusDays(3);
        FlightSchedule flightSchedule = new FlightSchedule(departure, arrival);

        flights = of(new Flight("0321",new FlightTransit("Iran","Paris"), flightSchedule, 145),
                new Flight("4256",new FlightTransit("Iran","Paris"), flightSchedule, 560),
                new Flight("0214",new FlightTransit("Iran","Paris"), flightSchedule, 450),
                new Flight("4578", new FlightTransit("Iran","Paris"), flightSchedule, 478));
    }

    public Flight getFakeFlightFromIranToParis(){
        LocalDate departure = LocalDate.of(2023, 3, 7);
        final LocalDate arrival = departure.plusDays(3);
        return getFakeFlight("0321", new FlightTransit("Iran", "Paris"),
                new FlightSchedule(departure, arrival), 545);
    }

    private Flight getFakeFlight(String flightName, FlightTransit transfer, FlightSchedule flightSchedule, double price){
        return new Flight(flightName, transfer, flightSchedule, price);
    }

    public List<Flight> getFakeFlights() {
        return flights;
    }


}
