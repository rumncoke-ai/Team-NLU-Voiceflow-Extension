package com.transcriptanalyzer.transcript.User_Requests_Intents.Repository;

import com.transcriptanalyzer.transcript.User_Requests_Intents.Documents.API;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

// Extends the MongoRepository to utilize the database functions for API objects
@Repository
public interface ApiRepository extends MongoRepository<API, String> {

}
