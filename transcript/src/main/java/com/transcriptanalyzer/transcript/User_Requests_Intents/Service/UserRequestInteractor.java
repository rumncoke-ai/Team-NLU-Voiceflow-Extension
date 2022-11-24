package com.transcriptanalyzer.transcript.User_Requests_Intents.Service;

import com.transcriptanalyzer.transcript.User_Requests_Intents.Documents.UserInfo;

import java.io.IOException;
import java.util.ArrayList;

public class UserRequestInteractor {
    /**
     * UserRequestInteractor class contains all important methods that may be needed for HTTP requests in the controller
     *
     * */


    // Creates Instances of the transcript service and user service class to access methods
    private TranscriptService transcriptService;
    private UserService userService;



    public void storeUserInfo(UserInfo user) {
        userService.storeUserInfo(user);
    }

    public ArrayList<ArrayList<ArrayList<String>>> getTranscriptData() throws IOException {
        return TranscriptService.getJSONContent();
    }




}
