package com.transcriptanalyzer.transcript.User_Requests_Intents.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.Properties;
import java.io.IOException;

import java.io.*;
import java.util.Properties;

public final class PropertiesWriter {
    private static FileReader reader = null;
    private static FileWriter writer = null;


    // make sure this user.dir call is clean and works on any machine. there's a good chance
    // its a bad idea. -martin, thurs nov 17, 3:50am
    private static final File file = new File(System.getProperty("user.dir") +
            "/src/main/resources/apiAccess.properties");

    private static final Properties PROPERTIES;

    /**
     * Default private constructor PropertiesReader.
     */
    private PropertiesWriter() {
    }


    static {
        //create a new Properties object and sets it to properties
        PROPERTIES = new Properties();

//        try {
//            reader = new FileReader(file);
//            writer = new FileWriter(file);
//            PROPERTIES.load(reader);
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
    }

    public static void setProperty(final String key, final String value) {
        PROPERTIES.setProperty(key, value);

        try {
            writer = new FileWriter(file);
            PROPERTIES.store(writer, "Properties Stored.");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
//public final class PropertiesWriter {
//
//    //Creates an instance of the logger
//    //A Logger object is used to log messages for a specific system or application component.
//    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesReader.class);
//
//    //Intializes a properties variable
//    private static final Properties PROPERTIES;
//
//
//    //Name of file that we want to access
//    private static final String PROP_FILE = "apiAccess.properties";
//
//    /**
//     * Default private constructor PropertiesReader.
//     */
//    private PropertiesWriter() {
//    }
//
//    static {
//        //create a new Properties object and sets it to properties
//        PROPERTIES = new Properties();
//
//        // .getSystemResource does the following according to documentation: Find a resource of the
//        // specified name from the search path used to load classes. This method locates the
//        // resource through the system class loader
//
//
//        final URL props = ClassLoader.getSystemResource(PROP_FILE);
//
//
//        try {
//            PROPERTIES.load(props.openStream());
//        } catch (IOException ex) {
//
//            if (LOGGER.isDebugEnabled()) {
//                LOGGER.debug(ex.getClass().getName() + "PropertiesReader method");
//            }
//        }
//    }

//    public static void setProperty(final String key, final String value) {
//
//        PROPERTIES.setProperty(key, value);
//    }
//}
