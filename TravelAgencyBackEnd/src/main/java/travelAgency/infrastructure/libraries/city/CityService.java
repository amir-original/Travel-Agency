package travelAgency.infrastructure.libraries.city;

import travelAgency.model.flight.City;

import java.util.List;

public interface CityService {
    List<String> citiesList();
    City getCity(String city);

}
