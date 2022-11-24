package com.transcriptanalyzer.transcript.User_Requests_Intents.Controllers;

//import the Transcript Service class as something else to implement clean architecture (NOT POSSIBLE IN JAVA)
import com.transcriptanalyzer.transcript.User_Requests_Intents.Documents.API;
import com.transcriptanalyzer.transcript.User_Requests_Intents.Documents.UserAPI;
import com.transcriptanalyzer.transcript.User_Requests_Intents.Service.TranscriptService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("api/v1/transcripts")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")

public class UserRequestControllerImp implements UserRequestController {
    @Autowired
    private final TranscriptService transcriptService;
    
    @GetMapping() // Get mapping to avoid AWS pinging the base route and saying the deployment health is not okay
    public ResponseEntity getServiceName() {
        return new ResponseEntity(HttpStatus.OK);
    }

    @Override
    @PostMapping("/storeAPI")
    public void storeAPIInfo(@RequestBody UserAPI api) {
        //transcriptService.storeAPIInfo(api);
        transcriptService.storeAPIInfoProperties(api);
    }

    @Override
    @GetMapping("/cleanTranscript") // this will return a list
    public ArrayList<ArrayList<ArrayList<String>>>  getCleanedTranscript() throws IOException {
        return TranscriptService.getJSONContent();
    }

    @GetMapping("/cleanTranscript/level/1") // this will return a list
    public ArrayList<String> getCleanedTranscriptLevelDown_1() throws IOException {
        //Intent 1
        //return (TranscriptService.getJSONContent()).get(0).get(1);

        //Intent 2
        return (TranscriptService.getJSONContent()).get(0).get(3);

    }

    @GetMapping("/cleanTranscript/level/2") // this will return a list
    public ArrayList<String> getCleanedTranscriptLevelDown_2() throws IOException {
        //Intent 3
        return (TranscriptService.getJSONContent()).get(2).get(1);
    }

    @Override
    @GetMapping("/threeIntents") // this will return a list
    public ArrayList<String>  getThreeIntents() throws IOException {
        return TranscriptService.getIntents();
    }
}



//    @Override
//    @PostMapping("/storeAPI/Key")
//    public void storeAPIKey(@RequestBody String apiKey) {
//       transcriptService.storeAPIKey(apiKey);
//    }
//
//    @Override
//    @PostMapping("/storeAPI/Version")
//    public void storeAPIVersion(@RequestBody String apiVersion) {
//        transcriptService.storeAPIVersion(apiVersion);
//    }

//    @GetMapping("/getBestIntents") // this will return a list
//    public List<String> getBestIntent() {
//        return transcriptService.getBestIntent();
//    }

//    @PostMapping("/storeData")
//    public void storeTranscriptData() throws Exception {
//        // Get raw string of transcript contents per turn.
//        ArrayList<String> rawTranscripts = TranscriptService.getJSONContent();
//
//        // Iterate through the turns contained in rawTranscripts and post them to the database.
//        for (String transcriptContent : rawTranscripts) {
//            Transcript currTranscript = new Transcript("", transcriptContent);
//            transcriptService.createTranscript(currTranscript);
//        }
//    }
//    @GetMapping("/getAll")
//    public List<Transcript> fetchAllTranscripts() {
//        return transcriptService.getAllTranscripts();
//    }
//
//    @GetMapping("/getById/{id}") // this will return just one transcript because we will
//    // make ids unique
//    public Optional<Transcript> fetchTranscriptById(@PathVariable String id) {
//        return transcriptService.getTranscriptById(id);
//    }
//
//    @GetMapping("/getAllByIntent/{intent}") // this will return a list because
//    // there may be more than one transcript with the same intent
//    public List<Transcript> fetchAllTranscriptsByIntent(@PathVariable String intent) {
//        return transcriptService.getAllTranscriptsByIntent(intent);
//    }
//
//
//    @GetMapping("/getIntentMapping") // this will return a treemap
//    public Map getSortedMapping(){
//        return transcriptService.sortTranscriptMap();
//    }
//
//    @PostMapping("/create")
//    public Transcript createTranscript(@RequestBody Transcript transcript) {
//        return transcriptService.createTranscript(transcript);
//    }
//
//    @PutMapping("/updateById/{id}")
//    public Transcript updateTranscriptById(@PathVariable String id, @RequestBody Transcript transcript) {
//        return transcriptService.updateTranscriptById(id, transcript);
//    }
//
//    //After merging Molly's code to the github Charlie's code was erased
//
//
//    @DeleteMapping("/deleteAll")
//    public void deleteAllTranscripts() {
//        transcriptService.deleteAllTranscripts();
//    }
//
//    @DeleteMapping("/deleteById/{id}") // this will delete just one transcript since
//    // ids are unique
//    public void deleteTranscriptById(@PathVariable String id) {
//        transcriptService.deleteTranscriptById(id);
//    }
//
//    @DeleteMapping("/deleteAllByIntent/{intent}") // this may delete more than one id
//    // since intents are not necessarily unique // FIX THIS
//    public void deleteAllTranscriptsByIntent(@PathVariable String intent) {
//        transcriptService.deleteAllTranscriptsByIntent(intent);
//    }
//
//