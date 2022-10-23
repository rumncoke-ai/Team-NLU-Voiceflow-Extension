package com.transcriptanalyzer.transcript.Controllers;

import com.transcriptanalyzer.transcript.Documents.Transcript;
import com.transcriptanalyzer.transcript.Service.TranscriptService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/transcripts")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000/")
public class TranscriptController {
    @Autowired
    private final TranscriptService transcriptService;
    @GetMapping("/getAll")
    public List<Transcript> fetchAllTranscripts() {
        return transcriptService.getAllTranscripts();
    }

    @GetMapping("/getById/{id}")
    public Optional<Transcript> fetchTranscriptById(@PathVariable String id) {
        return transcriptService.getTranscriptById(id);
    }

    @PostMapping("/create")
    public Transcript createTranscript(@RequestBody Transcript transcript) {
        return transcriptService.createTranscript(transcript);
    }

}
