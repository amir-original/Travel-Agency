package travelAgency.use_case.city;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.domain.city.City;
import travelAgency.services.city.CityService;
import travelAgency.services.city.CityServiceImpl;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class CityServiceShould {

    private CityService cityService;

    @BeforeEach
    void setUp() {
        cityService = new CityServiceImpl();
    }

    @Test
    void convert_string_city_to_City_type() {
        final City tehran = cityService.getCity("Tehran");

        assertThat(tehran).isEqualTo(City.TEHRAN);
    }

    @Test
    void get_cities_as_list() {
        final List<String> cities = cityService.citiesList();

        assertThat(cities.isEmpty()).isFalse();
        assertThat(cities.size()).isNotZero();
        assertThat(cities).isInstanceOf(List.class);
    }

    @Test
    void get_cities_as_array() {
        final String[] cities = cityService.citiesArray();

        assertThat(cities.length).isNotZero();
        assertThat(cities).isInstanceOf(String[].class);
    }

    @Test
    void throw_IllegalArgumentException_when_the_enter_name_is_invalid() {

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(()-> cityService.getCity("lahijan"));
    }
}
