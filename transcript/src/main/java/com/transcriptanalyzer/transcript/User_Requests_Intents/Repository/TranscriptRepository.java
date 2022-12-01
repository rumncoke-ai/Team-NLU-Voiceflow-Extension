package com.transcriptanalyzer.transcript.User_Requests_Intents.Repository;

import com.transcriptanalyzer.transcript.User_Requests_Intents.Documents.Transcript;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


// Extends the MongoRepository to utilize the database functions for Transcript objects
@Repository
public interface TranscriptRepository extends MongoRepository<Transcript, String> {
}
