package travelAgency.fakeData;

import travelAgency.domain.Flight;
import travelAgency.domain.FlightSchedule;
import travelAgency.domain.FlightPlan;
import travelAgency.domain.FlightLocation;
import travelAgency.domain.country.France;
import travelAgency.domain.country.Germany;
import travelAgency.domain.country.Iran;
import travelAgency.domain.country.UnitedStates;

import java.time.LocalDate;
import java.util.List;

import static java.time.LocalDate.of;
import static java.util.List.of;

public class FakeFlightData {
    private final List<Flight> flights;

    private final FlightSchedule baseSchedule =
            new FlightSchedule(of(2023, 3, 3),
                    of(2023, 3, 3).plusDays(3));

    private final FlightLocation tehranToParis = new FlightLocation(Iran.TEHRAN, France.PARIS);

    {
        final FlightLocation iranToKish = new FlightLocation(Iran.TEHRAN, Iran.KISH);
        final FlightLocation tehranToHamburg = new FlightLocation(Iran.TEHRAN, Germany.HAMBURG);
        flights = of(new Flight("0321", 145, new FlightPlan(tehranToParis, baseSchedule)),
                new Flight("4256", 560, new FlightPlan(iranToKish, baseSchedule)),
                new Flight("0214", 450, new FlightPlan(tehranToHamburg, baseSchedule)),
                new Flight("4578", 545, new FlightPlan(tehranToParis, baseSchedule)));
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public Flight getFakeFlightFromTehranToParis() {
        return flight("0321", tehranToParis, baseSchedule, 545);
    }

    public Flight getNotFoundFlight(){
        LocalDate departure = of(2024, 3, 7);
        final LocalDate arrival = departure.plusDays(2);
        final FlightLocation location = new FlightLocation(Iran.TEHRAN, UnitedStates.LOS_ANGELES);
        final FlightSchedule flightSchedule = new FlightSchedule(departure, arrival);
        return flight("4578", location, flightSchedule, 123);
    }


    private Flight flight(String flightName, FlightLocation location, FlightSchedule flightSchedule, double price) {
        return new Flight(flightName, price, new FlightPlan(location, flightSchedule));
    }

    public FlightPlan getFlightPlan(FlightLocation location) {
        final LocalDate departure = of(2023, 3, 3);
        final LocalDate arrival = of(2023, 3, 6);
        final FlightSchedule flightSchedule = new FlightSchedule(departure, arrival);
        return new FlightPlan(location, flightSchedule);
    }




}
