package com.dev.exchange_rate.helper.file_reader;

import com.dev.exchange_rate.exceptions.JsonFileNotFoundException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.time.LocalDate;

public class JsonMyFileReader implements MyFileReader {

    private final Gson gson;

    public JsonMyFileReader() {
        gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter()).create();
    }

    public <T> T read(String fileName,Type type) {
        try {
            String resourcePath = getResourcePath(fileName);
            return readJsonFile(resourcePath,type);
        } catch (FileNotFoundException e) {
            throw new JsonFileNotFoundException(fileName + " not found!");
        }
    }

    private String getResourcePath(String fileName) {
        URL resource = getResource(fileName);
        return getPathIfResourceIsNotNull(resource);
    }

    private URL getResource(String fileName) {
        return getClass().getClassLoader().getResource(fileName);
    }

    private static String getPathIfResourceIsNotNull(URL resource) {
        if (resource == null) {
            throw new JsonFileNotFoundException();
        }
        return resource.getPath();
    }

    private <T> T readJsonFile(String path, Type type) throws FileNotFoundException {
        return gson.fromJson(new FileReader(path), type);
    }
}
