package com.transcriptanalyzer.transcript.User_Requests_Intents.Service;//package com.transcriptanalyzer.transcript.User_Requests_Intents.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

/**
 * The class Properties reader. Used to access private API attributes.
 */
public final class PropertiesReader {


    //Creates an instance of the logger
    //A Logger object is used to log messages for a specific system or application component.
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesReader.class);

    //Intializes a properties variable
    private static final Properties PROPERTIES;
    //Name of file that we wish to access
    private static final String PROP_FILE = "apiAccess.properties";

    /**
     * Default private constructor PropertiesReader.
     */
    private PropertiesReader() {
    }


    static {
        //create a new Properties object and sets it to properties
        PROPERTIES = new Properties();


        // .getSystemResource does the following according to documentation: Find a resource of the
        // specified name from the search path used to load classes. This method locates the
        // resource through the system class loader


        final URL props = ClassLoader.getSystemResource(PROP_FILE);


        try {
            PROPERTIES.load(props.openStream());
        } catch (IOException ex) {

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(ex.getClass().getName() + "PropertiesReader method");
            }
        }
    }

    /**
     * Method getProperty.
     *
     * @param name String name file.
     * @return Return property
     */
    public static String getProperty(final String name) {

        return PROPERTIES.getProperty(name);
    }
}