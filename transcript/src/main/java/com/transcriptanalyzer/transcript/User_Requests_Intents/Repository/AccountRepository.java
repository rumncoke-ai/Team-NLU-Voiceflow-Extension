package com.transcriptanalyzer.transcript.User_Requests_Intents.Repository;

import com.transcriptanalyzer.transcript.User_Requests_Intents.Documents.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

// Extends the MongoRepository to utilize the database functions for Account objects
public interface AccountRepository extends MongoRepository<Account, String> {
}
