package travelAgency.infrastructure.mapper;

import org.jetbrains.annotations.NotNull;
import travelAgency.application.dto.FlightDto;
import travelAgency.model.city.City;
import travelAgency.model.flight.*;
import travelAgency.model.rate.currency.Currency;
import travelAgency.model.rate.currency.Money;

import java.time.LocalDate;

public class FlightMapper {

    public Flight toEntity (@NotNull FlightDto flightDto){
        FlightPlan flightPlan = getFlightPlan(flightDto);
        Money money = getMoney(flightDto);
        FlightNumber flightNumber = FlightNumber.of(flightDto.getFlightNumber());
        FlightCapacity flightCapacity = FlightCapacity.of(flightDto.getTotalCapacity());
        return Flight.addWith(flightNumber, flightPlan, flightCapacity, money);
    }

    public FlightDto toViewDto(@NotNull Flight flight){
        return new FlightDto(flight.flightNumber(),
                flight.plan().from().toString(), flight.to().toString(),
                flight.departure().toString(),
                flight.arrival().toString(),
                flight.totalCapacity(), flight.price().amount(), flight.price().currency().value()
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
        final City from = City.valueOf(flightDto.getFrom());
        final City to = City.valueOf(flightDto.getTo());

        return FlightLocation.with(from,to);
    }
}
