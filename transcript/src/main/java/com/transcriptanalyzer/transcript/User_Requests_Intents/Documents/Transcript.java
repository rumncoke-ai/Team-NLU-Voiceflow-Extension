package com.transcriptanalyzer.transcript.User_Requests_Intents.Documents;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document

//@CompoundIndex(def = "{'intent': 1, 'content': 1}", unique = true)
public class Transcript{
    @Id
    private String id;

    private String intent;
    private String content;

    public Transcript(String intent, String content) {
        this.intent = intent;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public String getIntent() {
        return intent;
    }

    public String getContent() {
        return content;
    }


}
