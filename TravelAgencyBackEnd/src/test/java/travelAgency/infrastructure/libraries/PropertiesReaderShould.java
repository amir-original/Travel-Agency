package travelAgency.infrastructure.libraries;

import org.junit.jupiter.api.Test;
import travelAgency.exceptions.InvalidPropertiesFileException;
import travelAgency.infrastructure.io.PropertiesReader;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;

public class PropertiesReaderShould {

    @Test
    void read_property_without_throw_any_exception() {
        final PropertiesReader propertiesReader = new PropertiesReader("db-config");

        assertThatNoException().isThrownBy(()->propertiesReader.getProperty("host"));
    }

    @Test
    void throw_InvalidPropertiesFileException_when_can_not_found_any_file() {
        assertThatExceptionOfType(InvalidPropertiesFileException.class)
                .isThrownBy(() -> new PropertiesReader("incorrect_file_name"));
    }
}
