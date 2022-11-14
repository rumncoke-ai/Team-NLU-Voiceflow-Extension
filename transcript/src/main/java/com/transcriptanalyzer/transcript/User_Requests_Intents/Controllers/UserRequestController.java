package com.transcriptanalyzer.transcript.User_Requests_Intents.Controllers;

import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface UserRequestController {

    void storeAPIKey(String apiKey);

    void storeAPIVersion(String apiVersion);

    List<String> getCleanedTranscript() throws Exception;




}
