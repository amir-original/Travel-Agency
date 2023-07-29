package travelAgency.infrastructure.mapper;

import org.jetbrains.annotations.NotNull;
import travelAgency.application.dto.FlightDto;
import travelAgency.application.dto.FlightPlanRequest;
import travelAgency.model.flight.City;
import travelAgency.model.flight.*;
import travelAgency.model.rate.Currency;
import travelAgency.model.flight.Money;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class FlightMapper {

    public Flight toEntity (@NotNull FlightDto flightDto){
        FlightPlan flightPlan = getFlightPlan(flightDto);
        Money money = getMoney(flightDto);
        FlightNumber flightNumber = FlightNumber.of(flightDto.flightNumber());
        FlightCapacity flightCapacity = FlightCapacity.of(flightDto.getTotalCapacity());
        return Flight.addWith(flightNumber, flightPlan, flightCapacity, money);
    }

    public FlightDto toViewDto(@NotNull Flight flight){
        return new FlightDto(flight.flightNumber(),
                flight.plan().from().toString(), flight.to().toString(),
                flight.departure().toString(),
                flight.arrival().toString(),
                flight.totalCapacity(),
                flight.price().amount(),
                flight.price().currency().name()
        );
    }

    @NotNull
    private Money getMoney(FlightDto flightDto) {
        final String upperCase = flightDto.getCurrency().toUpperCase();
        final Currency currency = Currency.valueOf(upperCase);
        return Money.of(flightDto.getPrice(),currency);
    }

    @NotNull
    private FlightPlan getFlightPlan(FlightDto flightDto) {
        FlightLocation flightLocation = getFlightLocation(flightDto);
        FlightSchedule flightSchedule = getFlightSchedule(flightDto);

        return FlightPlan.of(flightLocation,flightSchedule);
    }

    @NotNull
    private FlightSchedule getFlightSchedule(FlightDto flightDto) {
        final LocalDate departureDate = LocalDate.parse(flightDto.getDepartureDate());
        final LocalDate arrivalDate = LocalDate.parse(flightDto.getArrivalDate());

        return FlightSchedule.with(departureDate,arrivalDate);
    }

    @NotNull
    private FlightLocation getFlightLocation(FlightDto flightDto) {
        final City from = City.valueOf(flightDto.from());
        final City to = City.valueOf(flightDto.to());

        return FlightLocation.with(from,to);
    }

    public List<FlightDto> toViewDto(List<Flight> flights) {
        List<FlightDto> flightDtos = new LinkedList<>();
        flights.forEach(flight -> flightDtos.add(toViewDto(flight)));
        return flightDtos;
    }


    public FlightPlanRequest toView(FlightPlan flightPlan){
       return new FlightPlanRequest(flightPlan.from().name(),
                flightPlan.to().name(),
                flightPlan.departure(),
                flightPlan.arrival());
    }

    public FlightPlan toEntity(FlightPlanRequest flightPlanRequest) {
        City from = City.valueOf(flightPlanRequest.from());
        City to = City.valueOf(flightPlanRequest.to());
        FlightLocation flightLocation = FlightLocation.with(from, to);
        FlightSchedule flightSchedule = FlightSchedule.with(flightPlanRequest.departure(),
                flightPlanRequest.arrival());

        return FlightPlan.of(flightLocation,flightSchedule);
    }
}
