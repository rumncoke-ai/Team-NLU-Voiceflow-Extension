package com.transcriptanalyzer.transcript.User_Requests_Intents.Controllers;

import com.transcriptanalyzer.transcript.User_Requests_Intents.Documents.API;
import com.transcriptanalyzer.transcript.User_Requests_Intents.Documents.Account;
import com.transcriptanalyzer.transcript.User_Requests_Intents.Documents.BlockRequest;
import com.transcriptanalyzer.transcript.User_Requests_Intents.Documents.UserInfo;
import com.transcriptanalyzer.transcript.User_Requests_Intents.Service.UserRequestInteractor;
import com.transcriptanalyzer.transcript.User_Requests_Intents.Service.createBlocksVoiceflow;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("api/v1/transcripts")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
//@CrossOrigin(origins = {"http://localhost:3000", "https://nluchatbotpromptanalyzer.netlify.app/"})

public class UserRequestController{

    @Autowired
    private final UserRequestInteractor interactor;

    @GetMapping() // Get mapping to avoid AWS pinging the base route and saying the deployment health is not okay
    public ResponseEntity getServiceName() {
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/storeUserInfo") // Post mapping that stores user's api information and api version ID in the database
    public void storeUserInfo(@RequestBody UserInfo user) {
        interactor.storeUserInfo(user);
    }

    @PostMapping("/createBlock")
    public void createVoiceflowBlock(@RequestBody BlockRequest blockRequest) throws Exception {
        String email = blockRequest.getEmailAddress();
        String password = blockRequest.getPassword();
        String diagramID = blockRequest.getDiagramID();
        String intent1 = blockRequest.getIntent1();
        String intent2 = blockRequest.getIntent2();
        String intent3 = blockRequest.getIntent3();
        System.out.println(intent1);
        createBlocksVoiceflow.add_block(email, password, diagramID, intent1, intent2, intent3);
    }

    //REMOVE LATER
    @GetMapping("/cleanTranscript") // Returns an arraylist of all cleaned transcripts from voiceflow API
    public ArrayList<ArrayList<ArrayList<String>>>  getTranscriptData() throws IOException {
        return interactor.getTranscriptData();
    }

    @GetMapping("/cleanTranscript/api") // Returns an arraylist of all cleaned transcripts from voiceflow API
    public ArrayList<ArrayList<ArrayList<String>>>  getTranscriptData_2(@RequestBody API api) throws IOException {
        List<API> apiList = interactor.getAPIList();
        API UserApi = apiList.get(0);
        return interactor.getTranscriptData_2(UserApi);
    }

    @GetMapping("/threeIntents") // Returns the top three intents to be added to the front end
    public ArrayList<ArrayList<String>> getTreeThreeIntents() throws IOException {
        List<API> apiList = interactor.getAPIList();
        API api = apiList.get(0);
        ArrayList<ArrayList<String>> returnValue;
        returnValue = interactor.getBestIntents(api);
        interactor.deleteAll();
        return returnValue;
        //return interactor.getBestIntents(api);
    }

    @DeleteMapping("/deleteAll")
    public void deleteAllTranscripts() {
        interactor.deleteAll();
    }


    //REMOVE LATER
    @GetMapping("/apiFindAll") // Returns the top three intents to be added to the front end
    public List<API> getApiList(){
        return interactor.getAPIList();
    }

    @GetMapping("/api") // Returns the top three intents to be added to the front end
    public API getApi(){
        return interactor.getAPIList().get(0);
    }

}




//
//    @PostMapping("/storeAPI")
//    public void storeAPI(@RequestBody API api) {
//        interactor.storeAPI(api);
//
//    }
//    @PostMapping("/storeAccount")
//    public void storeAccount(@RequestBody Account account) {
//        interactor.storeAccount(account);
//    }
//@GetMapping("/cleanTranscript/level/1") // this will return a list
//    public ArrayList<String> getCleanedTranscriptLevelDown_1() throws IOException {
//        //Intent 1
//        //return (TranscriptService.getJSONContent()).get(0).get(1);
//
//        //Intent 2
//        return (TranscriptService.getJSONContent()).get(0).get(3);
//
//    }
//
//    @GetMapping("/cleanTranscript/level/2") // this will return a list
//    public ArrayList<String> getCleanedTranscriptLevelDown_2() throws IOException {
//        //Intent 3
//        return (TranscriptService.getJSONContent()).get(2).get(1);
//    }
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
