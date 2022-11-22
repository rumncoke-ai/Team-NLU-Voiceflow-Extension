package com.transcriptanalyzer.transcript.User_Requests_Intents.Repository;

import com.transcriptanalyzer.transcript.User_Requests_Intents.Documents.API;
import com.transcriptanalyzer.transcript.User_Requests_Intents.Documents.Transcript;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

// Extends the MongoRepository to utilize the database functions for API objects
public interface ApiRepository extends MongoRepository<API, String> {

}
