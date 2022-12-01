package com.transcriptanalyzer.transcript.User_Requests_Intents.Documents;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class BlockRequest {
    private final String emailAddress;
    private final String password;
    private final String diagramID;
    private final String intent1;
    private final String intent2;
    private final String intent3;

    public BlockRequest(String emailAddress, String password, String diagramID,
                        String intent1, String intent2, String intent3) {
        this.emailAddress = emailAddress;
        this.password = password;
        this.diagramID = diagramID;
        this.intent1 = intent1;
        this.intent2 = intent2;
        this.intent3 = intent3;
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

    public String getIntent1() {
        return intent1;
    }

    public String getIntent2() {
        return intent2;
    }

    public String getIntent3() {
        return intent3;
    }
}

