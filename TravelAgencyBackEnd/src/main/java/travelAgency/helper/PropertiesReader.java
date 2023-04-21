package travelAgency.helper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;
import java.util.Properties;

import static java.util.Objects.requireNonNull;

public class PropertiesReader {

    private final String resourcePath;
    private final Properties properties;

    public PropertiesReader(String fileName) {
        this.resourcePath = fileName + ".properties";
        properties = new Properties();
        loadProperties();
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    private void loadProperties() {
        try (InputStream configFile = new FileInputStream(getPath())) {
            properties.load(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getPath() {
        final URL url = getClass().getClassLoader().getResource(resourcePath);
        return requireNonNull(url).getPath();
    }
}
