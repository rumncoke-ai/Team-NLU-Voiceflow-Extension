package com.transcriptanalyzer.transcript.User_Requests_Intents.Documents;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class UserInfo {

    private String apiKey;
    private String apiVersion;
    private String emailAddress;
    private String password;
    private String diagramID;


    public UserInfo(String apiKey, String apiVersion, String emailAddress, String password, String diagramID) {
        this.apiKey = apiKey;
        this.apiVersion = apiVersion;
        this.emailAddress = emailAddress;
        this.password = password;
        this.diagramID = diagramID;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public String getDiagramID() {
        return diagramID;
    }


}
