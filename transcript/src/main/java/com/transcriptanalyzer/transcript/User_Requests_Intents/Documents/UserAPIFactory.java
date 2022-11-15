package com.transcriptanalyzer.transcript.User_Requests_Intents.Documents;

public class UserAPIFactory implements APIFactory{

    @Override
    public API create(String apiKey, String apiVersion){
        return new UserAPI(apiKey, apiVersion);
    }
}
