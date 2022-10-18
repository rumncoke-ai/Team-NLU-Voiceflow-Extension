package com.TranscriptAnalyzer.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Transcript")
public class Transcript {

    @Id
    private String id;


    private String content;
    private String intent;


    public Transcript(String id, String intent, String content) {
        super();
        this.id = id;
        this.intent = intent;
        this.content = content;
    }

    public Transcript(String intent, String content) {
        super();
        this.intent = intent;
        this.content = content;
    }


}
