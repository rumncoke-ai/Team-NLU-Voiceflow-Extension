package com.transcriptanalyzer.transcript.User_Requests_Intents.Documents;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class API{

    private final String apiKey;

    private final String apiVersion;


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


}
