package travelAgency.domain;

import travelAgency.domain.country.City;

public record FlightLocation(City from, City to) {
}
