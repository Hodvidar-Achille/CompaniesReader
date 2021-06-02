package com.hodvidar.cr.json.reader;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class JsonReader {

    private static JsonReader instance = null;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private JsonReader() {
    }

    public static synchronized JsonReader getInstance() {
        if (instance == null) {
            instance = new JsonReader();
        }
        return instance;
    }

    public JsonParser getParserFromFile(final String filePath) {
        try {
            final File jsonFile = new File(filePath);
            return new JsonFactory().createParser(jsonFile);
        } catch (IOException e) {
            logger.warn("An exception occurred", e);
        }
        return null;
    }

}
