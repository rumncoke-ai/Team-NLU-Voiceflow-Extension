package com.transcriptanalyzer.transcript.User_Requests_Intents.Service;

import com.transcriptanalyzer.transcript.User_Requests_Intents.Documents.API;
import com.transcriptanalyzer.transcript.User_Requests_Intents.Documents.Account;
import com.transcriptanalyzer.transcript.User_Requests_Intents.Documents.UserInfo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
@Service
public class UserRequestInteractor {
    /**
     * UserRequestInteractor class contains all important methods that may be needed for HTTP requests in the controller
     *
     * */


    // Creates Instances of the transcript service and user service class to access methods
    @Autowired
    private UserService userService;

    private Tree tree;

    public void storeUserInfo(UserInfo user) {
        userService.storeUserInfo(user);
    }

    public ArrayList<ArrayList<ArrayList<String>>> getTranscriptData() throws IOException {
        return TranscriptService.getJSONContent();
    }

    public ArrayList<List<String>> getBestIntents(API api) throws IOException {
        Tree intentTree = new Tree(TranscriptService.getJSONContent_2(api));
        return intentTree.getBestIntents();
    }

    public void deleteAll() {
        userService.deleteAll();
    }

    public List<API> getAPIList() {
        return userService.getAPIList();
    }


    public ArrayList<ArrayList<ArrayList<String>>> getTranscriptData_2(API api) throws IOException {
        return TranscriptService.getJSONContent_2(api);
    }
}
