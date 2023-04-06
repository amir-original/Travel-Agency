package travelAgency.helper;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {

    private final String resourcePath;

    public PropertiesReader(String fileName) {
        this.resourcePath = fileName + ".properties";
    }

    public String getProperty(String key) {
        final Properties properties = new Properties();
        String value = "";
        try {
            InputStream configFile = loadProperties(properties);
            value = properties.getProperty(key);
            configFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }

    private InputStream loadProperties(Properties properties) throws IOException {
        String path = getClass().getClassLoader().getResource(resourcePath).getPath();
        InputStream configFile = new FileInputStream(path);
        properties.load(configFile);
        return configFile;
    }
}
