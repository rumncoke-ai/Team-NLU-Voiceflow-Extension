package com.transcriptanalyzer.transcript.User_Requests_Intents.Documents;

import org.springframework.data.mongodb.core.mapping.Document;


public interface API {
    String getApiKey();

    String getApiVersion();

    //Is there ar way to check if the apiKey or apiVersion is valid

}
