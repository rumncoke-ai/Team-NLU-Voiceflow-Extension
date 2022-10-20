package com.transcriptanalyzer.transcript.Repository;

import com.transcriptanalyzer.transcript.Documents.Transcript;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TranscriptRepository extends MongoRepository<Transcript, String> {

    List<Transcript> findTranscriptsByIntent(String intent);

    //@Query("")
    //void test();
}
