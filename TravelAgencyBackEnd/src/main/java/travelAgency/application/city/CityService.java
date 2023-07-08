package travelAgency.application.city;

import travelAgency.model.city.City;

import java.util.List;

public interface CityService {
    List<String> citiesList();
    String[] citiesArray();
    City getCity(String city);

}
