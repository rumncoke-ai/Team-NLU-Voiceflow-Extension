package com.transcriptanalyzer.transcript.User_Requests_Intents.Controllers;

import com.transcriptanalyzer.transcript.User_Requests_Intents.Documents.API;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

public interface UserRequestController {

    /**
     * Question: For clean architecture the presenter should talk to the interface layer however our mappings are in the
     * implementation file, should we have mappings on out controller file
     */

    void storeAPIInfo(API api);

//    void storeAPIKey(String apiKey);
//
//    void storeAPIVersion(String apiVersion);

    ArrayList<ArrayList<ArrayList<String>>> getCleanedTranscript() throws Exception;




}
