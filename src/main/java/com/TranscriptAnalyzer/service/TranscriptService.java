package com.TranscriptAnalyzer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

import com.TranscriptAnalyzer.documents.Transcript;
import com.TranscriptAnalyzer.repository.TranscriptRepository;

//@Service

public class TranscriptService{

    @Autowired
    private TranscriptRepository repository;

    public Transcript createTranscript(Transcript transcript) {
        return repository.save(transcript);
    }

}
