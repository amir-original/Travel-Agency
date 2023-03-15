package travelAgency.fakeData;

import travelAgency.domain.FlightLocation;
import travelAgency.domain.FlightPlan;
import travelAgency.domain.FlightSchedule;
import travelAgency.domain.city.City;

import java.time.LocalDate;

import static java.time.LocalDate.of;
import static travelAgency.domain.city.City.PARIS;
import static travelAgency.domain.city.City.TEHRAN;

public class FakeFlightPlanBuilder  {

    private City from = TEHRAN;
    private City to = PARIS;
    private LocalDate departure = of(2023, 3, 3);
    private LocalDate arrival = departure.plusDays(3);

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
