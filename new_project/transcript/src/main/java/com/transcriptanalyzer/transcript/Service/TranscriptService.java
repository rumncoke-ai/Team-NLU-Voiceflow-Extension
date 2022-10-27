package com.transcriptanalyzer.transcript.Service;

import com.transcriptanalyzer.transcript.Documents.Transcript;
import com.transcriptanalyzer.transcript.Repository.TranscriptRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class TranscriptService {
    @Autowired
    private TranscriptRepository transcriptRepository;
    public List<Transcript> getAllTranscripts() {
        return transcriptRepository.findAll();
    }

    public Optional<Transcript> getTranscriptById(String id) {return transcriptRepository.findById(id);
    }

    public List<Transcript> getAllTranscriptsByIntent(String intent) {
        return transcriptRepository.findTranscriptsByIntent(intent);}

    public Transcript createTranscript(Transcript transcript) {
        return transcriptRepository.insert(transcript);
    }

    public Transcript updateTranscriptById(String id, Transcript transcript) {
        Transcript verifiedTranscript = transcriptRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "entity not found")); // whatever message we want
//        Transcript verifiedTranscript = oldTranscript.get();
        verifiedTranscript.setIntent(transcript.getIntent());
        verifiedTranscript.setContent(transcript.getContent());
        return transcriptRepository.save(verifiedTranscript);
    }

    public void deleteAllTranscripts() {transcriptRepository.deleteAll();}

    public void deleteTranscriptById(String id) {transcriptRepository.deleteById(id);
    }

    public void deleteAllTranscriptsByIntent(String intent) {transcriptRepository.deleteByIntent(intent);}
}


