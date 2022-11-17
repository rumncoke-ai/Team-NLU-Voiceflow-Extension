package com.transcriptanalyzer.transcript.User_Requests_Intents.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public final class PropertiesLineReader {
    private static final File file = new File("/Users/rumaisachowdhury/IdeaProjects/" +
            "Team-NLU-Voiceflow-Extension-controller-fix/transcript/src/main/resources/apiAccess.properties");

    /**
     * Default private constructor PropertiesLineReader.
     */
    private PropertiesLineReader() {
    }


    static {
        BufferedReader br = null;
    try {
        //create a new Properties object and sets it to properties
        br = new BufferedReader(new FileReader(file));
        String line = br.readLine();


        while (line != null) {

            line = br.readLine();
            System.out.println(line);
        }

    } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            try {
                assert br != null;
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
