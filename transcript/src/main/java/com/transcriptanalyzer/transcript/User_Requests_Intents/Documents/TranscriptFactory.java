package com.transcriptanalyzer.transcript.User_Requests_Intents.Documents;

public interface TranscriptFactory {
    Transcript create(String intent, String content);
}
