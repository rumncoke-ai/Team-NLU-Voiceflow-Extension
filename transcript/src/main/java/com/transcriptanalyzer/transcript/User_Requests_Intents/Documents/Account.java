package com.transcriptanalyzer.transcript.User_Requests_Intents.Documents;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Account {
    private String emailAddress;
    private String password;
    private String diagramID;

    public Account(String emailAddress, String password, String diagramID) {
        this.emailAddress = emailAddress;
        this.password = password;
        this.diagramID = diagramID;
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
