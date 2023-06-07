package com.dev.exchange_rate.helper.file_reader;

import com.dev.exchange_rate.helper.InvalidPropertiesFileException;
import com.dev.exchange_rate.helper.MissingPropertyException;

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
        this.resourcePath = fileName;
        this.properties = new Properties();
    }

    public String getProperty(String key) {
        loadProperties();
        if (properties.containsKey(key)) {
            return properties.getProperty(key);
        }
        throw new IllegalArgumentException("Property with key '" + key + "' doesn't exist.");
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
