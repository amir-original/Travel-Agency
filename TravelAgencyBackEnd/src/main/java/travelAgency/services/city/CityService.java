package travelAgency.services.city;

import travelAgency.domain.city.City;

import java.util.List;

public interface CityService {

    List<String> citiesList();
    String[] citiesArray();

    City getCity(String city);

}
