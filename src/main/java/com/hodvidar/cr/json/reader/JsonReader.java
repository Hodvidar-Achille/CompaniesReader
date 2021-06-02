package com.hodvidar.cr.json.reader;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class JsonReader {

    private static JsonReader INSTANCE = null;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private JsonReader() {
    }

    public static synchronized JsonReader getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new JsonReader();
        }
        return INSTANCE;
    }

    public JsonParser getParserFromFile(final String filePath) {
        try {
            final File jsonFile = new File(filePath);
            JsonFactory jsonfactory = new JsonFactory();
            JsonParser jsonParser = jsonfactory.createParser(jsonFile);
            return jsonParser;
        } catch (IOException e) {
            logger.warn("An exception occurred", e);
        }
        return null;
    }

}
