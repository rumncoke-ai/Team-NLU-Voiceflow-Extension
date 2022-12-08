package com.transcriptanalyzer.transcript.User_Requests_Intents.Service;

import com.google.gson.*;
import com.transcriptanalyzer.transcript.User_Requests_Intents.Documents.API;
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
     * Method getJSONContent_2.
     *
     * @return Return all transcript data from attached to the chatbot from the set apiKey and Version ID
     *
     * Data is cleaned and exibits both messages and intents specific format includes:
     *     Outermost layer: Stores overall result for all transcripts.
     *     Middle Layer: Each element is a full transcript.
     *     Inner layer: Each element is a turn.
     *     String: either a user intent or bot message in the format "message: " + the actual message (same for intents)."
     */

    public static ArrayList<ArrayList<ArrayList<String>>> getJSONContent_2(API api) throws IOException {

//      Define the url to call to access the chatbot API.

            String apiKey = api.getApiKey();
            String version = api.getApiVersion();

            String urlString = "https://api-dm-test.voiceflow.fr/exportraw/" + apiKey + "?versionID=" + version;

            URL url = new URL(urlString);


//      Return the result of the API call (i.e., all the stored transcripts) as one long string in JSON format.
            String jsonString = retrieveJsonString(url);

//      Parse the API return string to form a JsonArray
            JsonArray dataArr = new Gson().fromJson(jsonString, JsonArray.class);

//      Define the object which will contain all of the seperated turns
            ArrayList<ArrayList<ArrayList<String>>> finalParseResults = new ArrayList<>();

            flowIterator2(finalParseResults, dataArr);

            return finalMerge(finalParseResults);
    }



    /** Helper functions to getJSONContent*/
    private static String retrieveJsonString(URL url) throws IOException {

//      Access the Voiceflow API from the given url, read transcript results into one JSON formatted string

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

//          Isolate only turns which contain a user request or chatbot response/message
            turnReader(fullTranscriptAsJsonArray, turnsByKey);

//          Isolate the messages and intents as strings in separate ArrayLists from the pre-parsed turns.
            accessTurnData2(finalParseResults, turnsByKey);
        }
    }

    private static void accessTurnData2(ArrayList<ArrayList<ArrayList<String>>> finalParseResults, ArrayList<JsonObject> turnsByKey) {

//        Container for results of each parsed turn
        ArrayList<ArrayList<String>> overallParse = new ArrayList<>();
        for (JsonObject item : turnsByKey) {
            ArrayList<String> contains = new ArrayList<>();
            JsonObject turnContent = item.get("payload").getAsJsonObject().get("payload").getAsJsonObject();

//          If the turn is identified as one with user input ("query"), isolate the actual intent ("name") as a string
//          and return it contained in an ArrayList.
            if (turnContent.keySet().contains("query") || turnContent.keySet().contains("intent")) {
                JsonObject intentContainer = turnContent.get("intent").getAsJsonObject();
                String intent = intentContainer.get("name").toString();
                contains.add(intent);
            }

//          If the turn is identified to be a chatbot response ("message"), return the message as a string within an
//          ArrayList.
            if (turnContent.keySet().contains("message")) {
                String message = turnContent.get("message").toString();
                contains.add(message);
            }
//          Store result of the parsed turn
            overallParse.add(contains);
        }

//      Results of all turns being parsed.
        finalParseResults.add(overallParse);
    }

    private static ArrayList<ArrayList<ArrayList<String>>> finalMerge(ArrayList<ArrayList<ArrayList<String>>> finalParse) {

//      Final return, see meaning of each layer in getJsonContent above.
        ArrayList<ArrayList<ArrayList<String>>> mergedReturn = new ArrayList<>();

//      Access each transcript individually.
        for (ArrayList<ArrayList<String>> overallParse : finalParse) {
            int intentIndex = 1;
            ArrayList<ArrayList<String>> mergedResult = new ArrayList<>();

            while (intentIndex <= overallParse.size()) {


                ArrayList<String> turnToMerge = new ArrayList<>();
                StringBuilder currMessage = new StringBuilder();
                StringBuilder currIntent = new StringBuilder();

//              Check if we are at the end of a successful transcript
                if (intentIndex == overallParse.size()) {
                    currMessage.append(overallParse.get(intentIndex - 1).get(0));
                    currIntent.append("Null");
                } else {
                    currMessage.append(overallParse.get(intentIndex - 1).get(0));
                    currIntent.append(overallParse.get(intentIndex).get(0));
                }

                turnToMerge.add(String.valueOf(currMessage));

//              Isolate messages of end turns of successful transcripts
                if (!String.valueOf(currIntent).equals("Null")) {
                    turnToMerge.add(String.valueOf(currIntent));
                }

                mergedResult.add(turnToMerge);
                intentIndex = intentIndex + 2;
            }
            mergedReturn.add(mergedResult);
        }
        return mergedReturn;
    }

    private static void turnReader(JsonArray transcriptTop, ArrayList<JsonObject> turnsByKey) {
        // Find the turns within the flow which contain meaningful actions (i.e., not set-up or termination)
        for (JsonElement turnRaw : transcriptTop) {
            JsonObject turn = turnRaw.getAsJsonObject();
            if (turn.keySet().contains("type") && turn.get("type").getAsString().equals("request")) {
                turnsByKey.add(turn);
            } else if (turn.keySet().contains("type") && turn.get("type").getAsString().equals("text")) {
                turnsByKey.add(turn);
            }

        }
    }
}