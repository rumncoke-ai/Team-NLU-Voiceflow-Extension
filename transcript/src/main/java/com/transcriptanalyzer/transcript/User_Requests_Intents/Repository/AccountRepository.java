package com.transcriptanalyzer.transcript.User_Requests_Intents.Repository;

import com.transcriptanalyzer.transcript.User_Requests_Intents.Documents.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

// Extends the MongoRepository to utilize the database functions for Account objects
@Repository
public interface AccountRepository extends MongoRepository<Account, String> {
}
