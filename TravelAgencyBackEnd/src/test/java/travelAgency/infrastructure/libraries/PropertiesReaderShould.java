package travelAgency.infrastructure.libraries;

import org.junit.jupiter.api.Test;
import travelAgency.infrastructure.io.InvalidPropertiesFileException;
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
    void not_be_constructed_when_file_name_is_invalid_or_does_not_exist() {
        assertThatExceptionOfType(InvalidPropertiesFileException.class)
                .isThrownBy(() -> new PropertiesReader("incorrect_file_name"));
    }
}
