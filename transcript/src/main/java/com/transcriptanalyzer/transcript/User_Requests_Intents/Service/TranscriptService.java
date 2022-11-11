package com.transcriptanalyzer.transcript.User_Requests_Intents.Service;

import com.transcriptanalyzer.transcript.User_Requests_Intents.Documents.Transcript;
import com.transcriptanalyzer.transcript.User_Requests_Intents.Repository.TranscriptRepository;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import java.io.IOException;
import java.net.*;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import java.io.BufferedReader;
import java.io.InputStreamReader;


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
        JSONParser parse = new JSONParser(jsonString.toString());

        //Expected 0 arguments but found 1, for parse(jsonString.toString()), rearranged
        // so JSONParser(jsonString.toString())
        //JSONArray dataArr = (JSONArray) parse.parse(jsonString.toString());
        JSONArray dataArr = (JSONArray) parse.parse();


        flowIterator(finalParseResults, dataArr);
        // Return the resulting information.
        return finalParseResults;
    }

    // Helper functions to getJSONContent

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

    private static void turnReader(JSONArray transcriptTop, ArrayList<JSONObject> turnsByKey) {
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