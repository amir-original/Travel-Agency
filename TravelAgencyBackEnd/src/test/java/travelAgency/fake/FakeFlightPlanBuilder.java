package travelAgency.fake;

import travelAgency.domain.city.City;
import travelAgency.domain.flight.FlightLocation;
import travelAgency.domain.flight.FlightPlan;
import travelAgency.domain.flight.FlightSchedule;

import java.time.LocalDate;

import static travelAgency.domain.city.City.PARIS;
import static travelAgency.domain.city.City.TEHRAN;

public class FakeFlightPlanBuilder  {

    private City from = TEHRAN;
    private City to = PARIS;
    private LocalDate departure = LocalDate.now();
    private LocalDate arrival = LocalDate.now().plusDays(1);

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
        this.to = City.DELHI;
        return this;
    }

    public FlightPlan build(){
        return new FlightPlan(new FlightLocation(from,to), new FlightSchedule(departure, arrival));
    }
}
