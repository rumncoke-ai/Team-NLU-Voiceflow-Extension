package com.transcriptanalyzer.transcript.Service;

import com.transcriptanalyzer.transcript.Documents.Transcript;
import com.transcriptanalyzer.transcript.Repository.TranscriptRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.*;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class TranscriptService {
    @Autowired
    private final TranscriptRepository transcriptRepository;
    public List<Transcript> getAllTranscripts() {
        return transcriptRepository.findAll();
    }

    public Optional<Transcript> getTranscriptById(String id) {
        return transcriptRepository.findById(id);
    }

    public Transcript createTranscript(Transcript transcript) {
        return transcriptRepository.insert(transcript);
    }

    public static ArrayList<String> getJSONContent() throws Exception {
        // A method which returns the content of each turn of the given transcript. The transcript is based on the API
        // key which is stored in an external file.

        // Create an object within which you can store JSON string information taken from the Voiceflow API.
        StringBuilder jsonString = new StringBuilder();

        // ArrayList object which will store result of transcript parsing and be returned by method.
        ArrayList<String> finalParseResults = new ArrayList<>();

        // Define the url to download the transcript from, based on the API key and version number.
        String apiKey = PropertiesReader.getProperty("api-key");
        String version = PropertiesReader.getProperty("api-version");
        String urlToRead = "https://api-dm-test.voiceflow.fr/exportraw/" + apiKey + "?versionID=" + version;

        // Create a URL object to use for connecting to the Voiceflow API.
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        // Set the connection to the appropriate method.
        conn.setRequestMethod("GET");

        // Read the information from the Voiceflow API into a string.
        jsonGetter(jsonString, conn);

        // Transform the JSON string into a JSONArray object for parsing and manipulation.
        JSONParser parse = new JSONParser();
        JSONArray dataArr = (JSONArray) parse.parse(jsonString.toString());

        flowIterator(finalParseResults, dataArr);
        // Return the resulting information.
        return finalParseResults;
    }

    private static void jsonGetter(StringBuilder jsonString, HttpURLConnection conn) throws IOException {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream()))) {
            for (String line; (line = reader.readLine()) != null; ) {
                jsonString.append(line);
            }
        }
    }

    private static void flowIterator(ArrayList<String> finalParseResults, JSONArray dataArr) {
        // Iterate through each flow contained within the transcript array.
        for (Object value : dataArr) {
            JSONArray transcriptTop = (JSONArray) value;

            // Store turns which have been found to be meaningful actions or responses
            ArrayList<JSONObject> turnsByKey = new ArrayList<>();

            turnReader(transcriptTop, turnsByKey);
            accessTurnData(finalParseResults, turnsByKey);
        }
    }

    private static void accessTurnData(ArrayList<String> finalParseResults, ArrayList<JSONObject> turnsByKey) {
        for (JSONObject item : turnsByKey) {
            ArrayList<String> contains = new ArrayList<>();

            if (item.containsKey("message")) {
                String message = item.get("message").toString();
                contains.add("message: " + message);
            }

            if (item.containsKey("intent")) {
                String intent = item.get("intent").toString();
                contains.add("intent: " + intent);
            }

            finalParseResults.add(contains.toString());
        }
    }

    private static void turnReader (JSONArray transcriptTop, ArrayList < JSONObject > turnsByKey){
        // Find the turns within the flow which contain meaningful actions (i.e., not set-up or termination)
        for (Object o : transcriptTop) {
            JSONObject turn = (JSONObject) o;
            if (turn.containsKey("type") && turn.get("type").equals("request")) {
                turnsByKey.add(turn);
            } else if (turn.containsKey("type") && turn.get("type").equals("text")) {
                turnsByKey.add(turn);
            }
        }
    }
}
