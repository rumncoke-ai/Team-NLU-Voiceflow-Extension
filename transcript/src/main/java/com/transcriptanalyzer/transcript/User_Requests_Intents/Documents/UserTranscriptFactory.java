package com.transcriptanalyzer.transcript.User_Requests_Intents.Documents;

public class UserTranscriptFactory implements TranscriptFactory{

    @Override
    public Transcript create(String intent, String content){
        return new UserTranscript(intent, content);
    }
}
