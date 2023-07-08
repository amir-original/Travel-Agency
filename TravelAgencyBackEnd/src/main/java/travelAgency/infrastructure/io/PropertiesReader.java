package travelAgency.infrastructure.io;

import travelAgency.exceptions.InvalidPropertiesFileException;
import travelAgency.exceptions.MissingPropertyException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import static java.util.Objects.requireNonNull;

public class PropertiesReader {

    private final String resourcePath;
    private final Properties properties;

    public PropertiesReader(String fileName) {
        String validFileName = requireNonNull(fileName);
        this.resourcePath = validFileName + ".properties";
        properties = new Properties();
        loadProperties();
    }

    public static PropertiesReader of(String fileName){
        return new PropertiesReader(fileName);
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    private void loadProperties() {
        try (InputStream configFile = new FileInputStream(getPath())) {
            properties.load(configFile);
        } catch (IOException e) {
            throw new MissingPropertyException();
        }catch (NullPointerException e){
            throw new InvalidPropertiesFileException("The resource not found!,enter a correct file name!");
        }
    }

    private String getPath() {
        final URL url = getClass().getClassLoader().getResource(resourcePath);
        return requireNonNull(url).getPath();
    }
}
