package com.transcriptanalyzer.transcript.User_Requests_Intents.Documents;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;


public interface Transcript {
    String getId();
    String getIntent();
    String getContent();
}


