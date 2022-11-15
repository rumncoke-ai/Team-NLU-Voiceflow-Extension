package com.transcriptanalyzer.transcript.User_Requests_Intents.Documents;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
//What is the purpose of the CompoundIndex
@CompoundIndex(def = "{'intent': 1, 'content': 1}", unique = true)
public class UserTranscript implements Transcript{

    //@Indexed(unique = true) don't need this because we used @CompoundIndex above
    @Id
    private String id;

    private String intent;
    private String content;

    public UserTranscript(String intent, String content) {
        this.intent = intent;
        this.content = content;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getIntent() {
        return intent;
    }

    @Override
    public String getContent() {
        return content;
    }


//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public void setIntent(String intent) {
//        this.intent = intent;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }

}
