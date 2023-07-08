package travelAgency.use_case.fake;

import travelAgency.model.city.City;
import travelAgency.model.flight.FlightLocation;
import travelAgency.model.flight.FlightPlan;
import travelAgency.model.flight.FlightSchedule;

import java.time.LocalDate;

import static travelAgency.model.city.City.PARIS;
import static travelAgency.model.city.City.TEHRAN;

public class FakeFlightPlanBuilder  {

    public static final LocalDate NOW = LocalDate.now();
    private City from = TEHRAN;
    private City to = PARIS;
    private LocalDate departure = NOW;
    private LocalDate arrival = NOW.plusDays(1);

    public static FakeFlightPlanBuilder flightPlan(){
        return new FakeFlightPlanBuilder();
    }

    public FakeFlightPlanBuilder from(City city){
        this.from = city;
        return this;
    }

    public FakeFlightPlanBuilder to(City city){
        this.to = city;
        return this;
    }

    public FakeFlightPlanBuilder departureAt(LocalDate date){
        this.departure = date;
        return this;
    }

    public FakeFlightPlanBuilder arrivalAt(LocalDate date){
        this.arrival = date;
        return this;
    }

    public FakeFlightPlanBuilder withNotExistLocation() {
        this.to = City.KASHAN;
        return this;
    }

    public FlightPlan build(){
        return new FlightPlan(new FlightLocation(from,to), new FlightSchedule(departure, arrival));
    }
}
