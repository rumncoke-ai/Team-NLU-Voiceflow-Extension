package com.transcriptanalyzer.transcript.Documents;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Transcript {
    @Id
    private String id;
    // @Indexed(unique = true)
    private String intent;
    private String content;

    public Transcript(String intent, String content) {
        this.intent = intent;
        this.content = content;
    }
}
