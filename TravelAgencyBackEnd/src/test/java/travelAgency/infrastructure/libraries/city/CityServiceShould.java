package travelAgency.infrastructure.libraries.city;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.model.flight.City;

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
    void convert_given_city_name_to_City_type_when_is_valid() {
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
    void not_return_anything_when_city_name_is_invalid() {

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(()-> cityService.getCity("lahijan"));
    }
}
