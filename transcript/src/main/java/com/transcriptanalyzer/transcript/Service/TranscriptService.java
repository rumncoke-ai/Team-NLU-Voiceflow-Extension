package com.transcriptanalyzer.transcript.Service;

import com.transcriptanalyzer.transcript.Documents.Transcript;
import com.transcriptanalyzer.transcript.Repository.TranscriptRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class TranscriptService {
    @Autowired
    private final TranscriptRepository transcriptRepository;
    public List<Transcript> getAllTranscripts() {
        return transcriptRepository.findAll();
    }

    public Optional<Transcript> getTranscriptById(String id) {
        return transcriptRepository.findById(id);
    }

    public Transcript createTranscript(Transcript transcript) {
        return transcriptRepository.insert(transcript);
    }
}
