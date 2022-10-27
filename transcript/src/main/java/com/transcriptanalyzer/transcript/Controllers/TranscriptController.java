package com.transcriptanalyzer.transcript.Controllers;

import com.transcriptanalyzer.transcript.Documents.Transcript;
import com.transcriptanalyzer.transcript.Service.TranscriptService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @PostMapping("/storeData")
    public void storeTranscriptData() throws Exception {
        // Get raw string of transcript contents per turn.
        ArrayList<String> rawTranscripts = transcriptService.getJSONContent();

        // Iterate through the turns contained in rawTranscripts and post them to the database.
        for (String transcriptContent: rawTranscripts) {
            Transcript currTranscript = new Transcript("", transcriptContent);
            transcriptService.createTranscript(currTranscript);
        }
    }

}
