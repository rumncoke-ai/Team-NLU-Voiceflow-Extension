package com.transcriptanalyzer.transcript.User_Requests_Intents.Service;

import com.google.gson.*;
import com.transcriptanalyzer.transcript.User_Requests_Intents.Documents.Transcript;
import com.transcriptanalyzer.transcript.User_Requests_Intents.Repository.TranscriptRepository;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.Reader;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.*;

import java.io.IOException;
import java.net.*;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import static java.lang.System.in;


@AllArgsConstructor
@Service

//Switching everything to non-static
public class TranscriptService {
    private TreeMap<String, Integer> intentTreeMap;

    //Stores the APIKey in the apiAccess.properties files
    //Switched to static
    public static void storeAPIKey(String apiKey) {

    }


    //Stores the APIVersion in the apiAccess.properties files
    //Switched to static
    public static void storeAPIVersion(String apiVersion) {

    }

    public static ArrayList<ArrayList<ArrayList<String>>> getJSONContent() throws IOException {
        String apiKey = PropertiesReader.getProperty("api-key");
        String version = PropertiesReader.getProperty("api-version");
        String urlString = "https://api-dm-test.voiceflow.fr/exportraw/" + apiKey + "?versionID=" + version;

        URL url = new URL(urlString);


        String jsonString = retrieveJsonString(url);

        System.out.println(jsonString);
        JsonArray dataArr = new Gson().fromJson(jsonString, JsonArray.class);
        System.out.println(dataArr);
        System.out.println(dataArr.asList());
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


    public static void main(String[] args) throws Exception {
        System.out.println(getJSONContent());
    }
}