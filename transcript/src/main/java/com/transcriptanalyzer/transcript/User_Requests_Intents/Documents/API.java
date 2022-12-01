package com.transcriptanalyzer.transcript.User_Requests_Intents.Documents;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class API{

    //For now the apiKey can be the databased name
    private final String apiKey;

    private final String apiVersion;


    //There should be a database linked to every new apiKey

    public API(String apiKey, String apiVersion) {
        this.apiKey = apiKey;
        this.apiVersion = apiVersion;
    }


    public String getApiKey() {
        return apiKey;
    }


    public String getApiVersion() {
        return apiVersion;
    }

//    public void setApiKey(String apiKey) {
//        this.apiKey = apiKey;
//    }
//
//    public void setApiVersion(String apiVersion) {
//        this.apiVersion = apiVersion;
//    }

}
