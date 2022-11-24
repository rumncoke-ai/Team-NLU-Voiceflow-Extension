package com.transcriptanalyzer.transcript.User_Requests_Intents.Service;

import com.google.gson.*;
import com.transcriptanalyzer.transcript.User_Requests_Intents.Documents.API;
import com.transcriptanalyzer.transcript.User_Requests_Intents.Repository.ApiRepository;
import com.transcriptanalyzer.transcript.User_Requests_Intents.Repository.TranscriptRepository;
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
// Return the parsed results of the given chatbot's transcripts.

//      Define the url to call to access the chatbot API.
        String apiKey = PropertiesReader.getProperty("api-key");
        String version = PropertiesReader.getProperty("api-version");
        String urlString = "https://api-dm-test.voiceflow.fr/exportraw/" + apiKey + "?versionID=" + version;

        URL url = new URL(urlString);


//      Return the result of the API call (i.e., all the stored transcripts) as one long string in JSON format.
        String jsonString = retrieveJsonString(url);

//      Parse the API return string to form a JsonArray
        JsonArray dataArr = new Gson().fromJson(jsonString, JsonArray.class);

//      Define the object which will contain all of the seperated turns
        ArrayList<ArrayList<ArrayList<String>>> finalParseResults = new ArrayList<>();

        flowIterator2(finalParseResults, dataArr);

//  Return the parsed transcripts in the following format:
//  ArrayList<ArrayList<ArrayList<String>>>
//  Where the layers represent:

//  Outermost Layer ArrayList<>: Outer container to store the overall results for each transcript.

//  Middle Layer ArrayList<>: Each element represents one full transcript that was parsed.

//  Inner layer ArrayList<>: Each element represents a pair of turns from the given transcript; the first string
//  represents a message from the chatbot, and the subsequent string represents the intent given by the user in
//  response. Note that if there is only one string, it is a termination message when the chatbot ends.

        return finalMerge(finalParseResults);
        //return finalParseResults;
    }


    // Helper functions to getJSONContent

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
            if (turnContent.keySet().contains("message")){
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
        for(ArrayList<ArrayList<String>> overallParse : finalParse) {
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
                }
                else {
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
            if (turn.keySet().contains("type") && turn.get("type").getAsString().equals("request")){
                turnsByKey.add(turn);
            } else if (turn.keySet().contains("type") && turn.get("type").getAsString().equals("text")) {
                turnsByKey.add(turn);
            }

        }

    }

    public void deleteALl() {

    }


//    public static ArrayList<String> getIntents() throws IOException {
//        ArrayList<String> list = new ArrayList<>();
//        //Intent 1
//        String intent_1 = getJSONContent().get(0).get(1).get(0).replaceAll("[:{\"}]","");
//        intent_1 = intent_1.substring(11);
//        String intent_2 = getJSONContent().get(0).get(3).get(0).replaceAll("[:{\"}]","");
//        intent_2 = intent_2.substring(11);
//        String intent_3 = getJSONContent().get(2).get(1).get(0).replaceAll("[:{\"}]","");
//        intent_3 = intent_3.substring(11);
//
//
//        list.add(intent_1);
//        list.add(intent_2);
//        list.add(intent_3);
//
//        return list;
//    }





//Stores the APIKey in the apiAccess.properties files
//    //Switched to static
//    public static void storeAPIKey(String apiKey) {
//    }
//
//
//    //Stores the APIVersion in the apiAccess.properties files
//    //Switched to static
//    public static void storeAPIVersion(String apiVersion) {
//
//    }
//    public static ArrayList<String> getJSONContent() throws Exception {
//        // A method which returns the content of each turn of the given transcript. The transcript is based on the API
//        // key which is stored in an external file.
//
//        // Create an object within which you can store JSON string information taken from the Voiceflow API.
//        StringBuilder jsonString = new StringBuilder();
//
//        // ArrayList object which will store result of transcript parsing and be returned by method.
//        ArrayList<String> finalParseResults = new ArrayList<>();
//
//        // Define the url to download the transcript from, based on the API key and version number.
//        String apiKey = PropertiesReader.getProperty("api-key");
//        String version = PropertiesReader.getProperty("api-version");
//        String urlToRead = "https://api-dm-test.voiceflow.fr/exportraw/" + apiKey + "?versionID=" + version;
//
//        // Create a URL object to use for connecting to the Voiceflow API.
//        URL url = new URL(urlToRead);
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//
//        // Set the connection to the appropriate method.
//        conn.setRequestMethod("GET");
//
//        // Read the information from the Voiceflow API into a string.
//        jsonGetter(jsonString, conn);
//
//        // Transform the JSON string into a JSONArray object for parsing and manipulation.
//        JSONParser parse = new JSONParser(jsonString.toString());
//
//        //Expected 0 arguments but found 1, for parse(jsonString.toString()), rearranged
//        // so JSONParser(jsonString.toString())
//        //JSONArray dataArr = (JSONArray) parse.parse(jsonString.toString());
//        JSONArray dataArr = (JSONArray) parse.parse();
//
//
//        flowIterator(finalParseResults, dataArr);
//        // Return the resulting information.
//        return finalParseResults;
//    }
//
//    // Helper functions to getJSONContent
//
//    private static void jsonGetter(StringBuilder jsonString, HttpURLConnection conn) throws IOException {
//        try (BufferedReader reader = new BufferedReader(
//                new InputStreamReader(conn.getInputStream()))) {
//            for (String line; (line = reader.readLine()) != null; ) {
//                jsonString.append(line);
//            }
//        }
//    }
//
//    private static void flowIterator(ArrayList<String> finalParseResults, JSONArray dataArr) {
//        // Iterate through each flow contained within the transcript array.
//        for (Object value : dataArr) {
//            JSONArray transcriptTop = (JSONArray) value;
//
//            // Store turns which have been found to be meaningful actions or responses
//            ArrayList<JSONObject> turnsByKey = new ArrayList<>();
//
//            turnReader(transcriptTop, turnsByKey);
//            accessTurnData(finalParseResults, turnsByKey);
//        }
//    }

//    private static void accessTurnData(ArrayList<String> finalParseResults, ArrayList<JSONObject> turnsByKey) {
//        for (JSONObject item : turnsByKey) {
//            ArrayList<String> contains = new ArrayList<>();
//
//            if (item.containsKey("message")) {
//                String message = item.get("message").toString();
//                contains.add("message: " + message);
//            }
//
//            if (item.containsKey("intent")) {
//                String intent = item.get("intent").toString();
//                contains.add("intent: " + intent);
//            }
//
//            finalParseResults.add(contains.toString());
//        }
//    }
//
//    private static void turnReader(JSONArray transcriptTop, ArrayList<JSONObject> turnsByKey) {
//        // Find the turns within the flow which contain meaningful actions (i.e., not set-up or termination)
//        for (Object o : transcriptTop) {
//            JSONObject turn = (JSONObject) o;
//            if (turn.containsKey("type") && turn.get("type").equals("request")) {
//                turnsByKey.add(turn);
//            } else if (turn.containsKey("type") && turn.get("type").equals("text")) {
//                turnsByKey.add(turn);
//            }
//        }



//    public static void countIntents(){
//        //Changes the intentTreeMap with the intents as keys
//        // and the number of times they occur as values
//
//        List<Transcript> transcripts = getAllTranscripts();
//
//        for (Transcript transcript: transcripts){
//            String key = transcript.getId();
//
//            if(intentTreeMap.containsKey(transcript.getId())){
//                intentTreeMap.put(key, intentTreeMap.get(key) + 1);
//            }
//            else{
//                intentTreeMap.put(key, 1);
//            }
//        }
//    }
//

//    public void countIntents(){
//        //First get the cleaned data from the Voiceflow API then
//        //count the number of the intents by parsing through each message
//    }

//    public static List<String> getBestIntent() {
//        //Sorts the intent treeMap then returns an arrayList containing the keys with the largest numbers
//        //What if there is a tie?
//
//        Map map = sortTranscriptMap();
//        ArrayList<String> intentList = new ArrayList<>();
//
//        String prompt_1 = (String) map.get(map.values().toArray()[map.size() - 1]);
//        String prompt_2 = (String) map.get(map.values().toArray()[map.size() - 2]);
//        String prompt_3 = (String) map.get(map.values().toArray()[map.size() - 3]);
//
//        intentList.add(prompt_1);
//        intentList.add(prompt_2);
//        intentList.add(prompt_3);
//
//        return intentList;
//    }

//    public Map sortTranscriptMap(){
//        // Sort HashMap by the public method
//        countIntents();
//
//        Map sortedMap = valueSort(intentTreeMap);
//
//        return sortedMap;
//
//    }

//    public static <K, V extends Comparable<V> > Map<K, V>
//    valueSort(final Map<K, V> map)
//    {
//        // Static Method with return type Map and
//        // extending comparator class which compares values
//        // associated with two keys
//        Comparator<K> valueComparator = new Comparator<K>() {
//
//            // return comparison results of values of
//            // two keys
//            public int compare(K k1, K k2) {
//                int comp = map.get(k1).compareTo(
//                        map.get(k2));
//                if (comp == 0)
//                    return 1;
//                else
//                    return comp;
//            }
//
//        };
//
//        // SortedMap created using the comparator
//        Map<K, V> sorted = new TreeMap<K, V>(valueComparator);
//
//        sorted.putAll(map);
//
//        return sorted;
//    }

//    @Autowired
//    private static TranscriptRepository transcriptRepository;
//
//    private static TreeMap<String, Integer> intentTreeMap;
//
//
//    public static List<Transcript> getAllTranscripts() {
//        return transcriptRepository.findAll();
//    }
//
//    public Optional<Transcript> getTranscriptById(String id) {
//        return transcriptRepository.findById(id);
//    }
//
//    public List<Transcript> getAllTranscriptsByIntent(String intent) {
//        return transcriptRepository.findTranscriptsByIntent(intent);
//    }

//    public Transcript createTranscript(Transcript transcript) {
//        return transcriptRepository.insert(transcript);
//    }
//
//    public Transcript updateTranscriptById(String id, Transcript transcript) {
//        Transcript verifiedTranscript = transcriptRepository.findById(id)
//                .orElseThrow(() -> new ResponseStatusException(
//                        HttpStatus.NOT_FOUND, "entity not found")); // whatever message we want
////        Transcript verifiedTranscript = oldTranscript.get();
//        verifiedTranscript.setIntent(transcript.getIntent());
//        verifiedTranscript.setContent(transcript.getContent());
//        return transcriptRepository.save(verifiedTranscript);
//    }

//    public void deleteAllTranscripts() {
//        transcriptRepository.deleteAll();
//    }
//
//    public void deleteTranscriptById(String id) {
//        transcriptRepository.deleteById(id);
//    }
//
//    public void deleteAllTranscriptsByIntent(String intent) {
//        transcriptRepository.deleteByIntent(intent);
//    }


//    public static void main(String[] args) throws Exception {
//        System.out.println(getJSONContent());
//    }


}
