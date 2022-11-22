package com.transcriptanalyzer.transcript.User_Requests_Intents.Service;

import com.google.gson.*;
import com.transcriptanalyzer.transcript.User_Requests_Intents.Documents.*;
import com.transcriptanalyzer.transcript.User_Requests_Intents.Repository.AccountRepository;
import com.transcriptanalyzer.transcript.User_Requests_Intents.Repository.ApiRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

import java.io.IOException;
import java.net.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;


@AllArgsConstructor
@Service

public class TranscriptService {


    /**
     * Method getJSONContent.
     *
     * @return Return all transcript data from attached to the chatbot from the set apiKey and Version ID
     *
     * Data is cleaned and exibits both messages and intents specific format includes:
     *     Outermost layer: Stores overall result for all transcripts.
     *     Middle Layer: Each element is a full transcript.
     *     Inner layer: Each element is a turn.
     *     String: either a user intent or bot message in the format "message: " + the actual message (same for intents)."
     */
    public static ArrayList<ArrayList<ArrayList<String>>> getJSONContent() throws IOException {
        String apiKey = PropertiesReader.getProperty("api-key");
        String version = PropertiesReader.getProperty("api-version");
        String urlString = "https://api-dm-test.voiceflow.fr/exportraw/" + apiKey + "?versionID=" + version;

        URL url = new URL(urlString);


        String jsonString = retrieveJsonString(url);

//        System.out.println(jsonString);
        JsonArray dataArr = new Gson().fromJson(jsonString, JsonArray.class);
//        System.out.println(dataArr);
//        System.out.println(dataArr.asList());
        ArrayList<ArrayList<ArrayList<String>>> finalParseResults = new ArrayList<>();

        flowIterator2(finalParseResults, dataArr);



        return finalParseResults;
    }


    // Helper functions to getJSONContent

    private static String retrieveJsonString(URL url) throws IOException {

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        StringBuilder jsonString = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(urlConnection.getInputStream()))) {
            for (String line; (line = reader.readLine()) != null; ) {
                jsonString.append(line);
            }
        }
        return jsonString.toString();
    }

    private static void flowIterator2(ArrayList<ArrayList<ArrayList<String>>> finalParseResults, JsonArray dataArr) {
        // Iterate through each flow contained within the transcript array.
        for (JsonElement fullTranscript : dataArr) {
            JsonArray fullTranscriptAsJsonArray = fullTranscript.getAsJsonArray();

            // Store turns which have been found to be meaningful actions or responses
            ArrayList<JsonObject> turnsByKey = new ArrayList<>();

            turnReader(fullTranscriptAsJsonArray, turnsByKey);
            accessTurnData2(finalParseResults, turnsByKey);
        }
    }

    private static void accessTurnData2(ArrayList<ArrayList<ArrayList<String>>> finalParseResults, ArrayList<JsonObject> turnsByKey) {
        ArrayList<ArrayList<String>> overallParse = new ArrayList<>();
        for (JsonObject item : turnsByKey) {
            ArrayList<String> contains = new ArrayList<>();
            JsonObject turnContent = item.get("payload").getAsJsonObject().get("payload").getAsJsonObject();
//            System.out.println(turnContent);

            if (turnContent.keySet().contains("query") || turnContent.keySet().contains("intent")) {
                String intent = turnContent.get("intent").toString();
                contains.add("intent: " + intent);
            }

            if (turnContent.keySet().contains("message")){
                String message = turnContent.get("message").toString();
                contains.add("message: " + message);
            }

            overallParse.add(contains);
        }
        finalParseResults.add(overallParse);
    }

    private static void turnReader(JsonArray transcriptTop, ArrayList<JsonObject> turnsByKey) {
        // Find the turns within the flow which contain meaningful actions (i.e., not set-up or termination)
        for (JsonElement turnRaw : transcriptTop) {
            JsonObject turn = turnRaw.getAsJsonObject();
//            System.out.println(turn.keySet());
            if (turn.keySet().contains("type") && turn.get("type").getAsString().equals("request")){
                turnsByKey.add(turn);
            } else if (turn.keySet().contains("type") && turn.get("type").getAsString().equals("text")) {
                turnsByKey.add(turn);
            }
//            System.out.println(turn.get("type"));
        }
//        System.out.println(turnsByKey);
    }


}
