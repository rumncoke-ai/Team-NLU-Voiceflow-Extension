package com.transcriptanalyzer.transcript.Controllers;

import com.transcriptanalyzer.transcript.Documents.Transcript;
import com.transcriptanalyzer.transcript.Service.TranscriptService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/transcripts")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class TranscriptController {
    @Autowired
    private final TranscriptService transcriptService;
    @GetMapping("/getAll")
    public List<Transcript> fetchAllTranscripts() {
        return transcriptService.getAllTranscripts();
    }

    @GetMapping("/getById/{id}") // this will return just one transcript because we will
    // make ids unique
    public Optional<Transcript> fetchTranscriptById(@PathVariable String id) {
        return transcriptService.getTranscriptById(id);
    }

    @GetMapping("/getAllByIntent/{intent}") // this will return a list because
    // there may be more than one transcript with the same intent
    public List<Transcript> fetchAllTranscriptsByIntent(@PathVariable String intent) {
        return transcriptService.getAllTranscriptsByIntent(intent);
    }

    @PostMapping("/create")
    public Transcript createTranscript(@RequestBody Transcript transcript) {
        return transcriptService.createTranscript(transcript);
    }

    @PostMapping("/storeData")
    public void storeTranscriptData() throws Exception {
        // Get raw string of transcript contents per turn.
        ArrayList<String> rawTranscripts = TranscriptService.getJSONContent();

        // Iterate through the turns contained in rawTranscripts and post them to the database.
        for (String transcriptContent: rawTranscripts) {
            Transcript currTranscript = new Transcript("", transcriptContent);
            transcriptService.createTranscript(currTranscript);
        }
    }

    @PutMapping("/updateById/{id}")
    public Transcript updateTranscriptById(@PathVariable String id, @RequestBody Transcript transcript) {
        return transcriptService.updateTranscriptById(id, transcript);
    }

    @DeleteMapping("/deleteAll")
    public void deleteAllTranscripts() {
        transcriptService.deleteAllTranscripts();
    }

    @DeleteMapping("/deleteById/{id}") // this will delete just one transcript since
    // ids are unique
    public void deleteTranscriptById(@PathVariable String id) {
        transcriptService.deleteTranscriptById(id);
    }

    @DeleteMapping("/deleteAllByIntent/{intent}") // this may delete more than one id
    // since intents are not necessarily unique // FIX THIS
    public void deleteAllTranscriptsByIntent(@PathVariable String intent) {
        transcriptService.deleteAllTranscriptsByIntent(intent);
    }

}
