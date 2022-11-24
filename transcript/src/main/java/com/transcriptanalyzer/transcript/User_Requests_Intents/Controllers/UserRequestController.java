package com.transcriptanalyzer.transcript.User_Requests_Intents.Controllers;

import com.transcriptanalyzer.transcript.User_Requests_Intents.Documents.API;
import com.transcriptanalyzer.transcript.User_Requests_Intents.Documents.UserAPI;
import com.transcriptanalyzer.transcript.User_Requests_Intents.Service.TranscriptService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface UserRequestController {

    /**
     * Question: For clean architecture the presenter should talk to the interface layer however our mappings are in the
     * implementation file, should we have mappings on out controller file
     */

    void storeAPIInfo(UserAPI api);

//    void storeAPIKey(String apiKey);
//
//    void storeAPIVersion(String apiVersion);

    ArrayList<ArrayList<ArrayList<String>>> getCleanedTranscript() throws Exception;

    ArrayList<String>  getThreeIntents() throws IOException;
//    }




}
