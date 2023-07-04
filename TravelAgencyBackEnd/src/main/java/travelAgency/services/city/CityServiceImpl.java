package travelAgency.services.city;

import org.jetbrains.annotations.NotNull;
import travelAgency.domain.city.City;

import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

public final class CityServiceImpl implements CityService {

    private final City[] cities;

    public CityServiceImpl() {
        this.cities = City.values();
    }

    @Override
    public List<String> citiesList() {
        return getCitiesStream().toList();
    }

    @Override
    public String[] citiesArray() {
        return  getCitiesStream().toArray(String[]::new);
    }

    @NotNull
    private Stream<String> getCitiesStream() {
        return Stream.of(cities).map(Enum::name);
    }

    @Override
    public City getCity(String name) {
        return City.valueOf(name.toUpperCase());
    }

}
