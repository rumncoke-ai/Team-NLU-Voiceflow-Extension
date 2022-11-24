package com.transcriptanalyzer.transcript.User_Requests_Intents.Repository;

import com.transcriptanalyzer.transcript.User_Requests_Intents.Documents.API;
import org.springframework.data.mongodb.repository.MongoRepository;

// Extends the MongoRepository to utilize the database functions for API objects
public interface ApiRepository extends MongoRepository<API, String> {

}
