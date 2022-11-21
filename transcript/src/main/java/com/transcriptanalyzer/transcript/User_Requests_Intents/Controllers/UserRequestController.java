package com.transcriptanalyzer.transcript.User_Requests_Intents.Controllers;

//import the Transcript Service class as something else to implement clean architecture (NOT POSSIBLE IN JAVA)
import com.transcriptanalyzer.transcript.User_Requests_Intents.Documents.*;
import com.transcriptanalyzer.transcript.User_Requests_Intents.Service.TranscriptService;
import com.transcriptanalyzer.transcript.User_Requests_Intents.Service.UserRequestInteractor;
import com.transcriptanalyzer.transcript.User_Requests_Intents.Service.UserService;
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

public class UserRequestController {

    @Autowired
    private final UserRequestInteractor interactor;

    @PostMapping("/storeUserInfo") // Post mapping that stores user's api information and api version ID in the database
    public void storeUserInfo(@RequestBody UserInfo user) {
        interactor.storeUserInfo(user);
    }


    @GetMapping() // Get mapping to avoid AWS pinging the base route and saying the deployment health is not okay
    public ResponseEntity getServiceName() {
        return new ResponseEntity(HttpStatus.OK);
    }
    @GetMapping("/cleanTranscript") // Returns an arraylist of all cleaned transcripts from voiceflow API
    public ArrayList<ArrayList<ArrayList<String>>> getCleanedTranscript() throws IOException {
        return interactor.getTranscriptData();
    }



//    @PostMapping("/storeAPI") // Post mapping that stores user's api information and api version ID in the database
//    public void storeAPIInfo(@RequestBody API api) {
//        transcriptService.storeAPIInfo(api);
//    }
//
//    @PostMapping("/storeAccount") // Post mapping that stores user's account informaiton in the database
//    public void storeAccountInfo(@RequestBody Account account) {
//        transcriptService.storeAccountInfo(account);
//    }

}


