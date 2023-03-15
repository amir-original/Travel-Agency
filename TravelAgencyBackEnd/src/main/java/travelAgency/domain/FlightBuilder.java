package travelAgency.domain;

import travelAgency.domain.city.City;

import java.time.LocalDate;

public class FlightBuilder {

    private String serialNumber;
    private double price;
    private City from;
    private City to;
    private LocalDate departure;
    private LocalDate arrival;

    public FlightBuilder withSerialNumber(String serialNumber){
        this.serialNumber = serialNumber;
        return this;
    }

    public FlightBuilder from(City city){
        this.from = city;
        return this;
    }

    public FlightBuilder to(City city){
        this.to = city;
        return this;
    }

    public FlightBuilder departureAt(LocalDate date){
        this.departure = date;
        return this;
    }

    public FlightBuilder arrivalAt(LocalDate date){
        this.arrival = date;
        return this;
    }

    public FlightBuilder withPrice(double price){
        this.price = price;
        return this;
    }

    public Flight build(){
        return new Flight(serialNumber,price,
                new FlightPlan(new FlightLocation(from,to),new FlightSchedule(departure,arrival)));
    }
}
