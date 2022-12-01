package com.transcriptanalyzer.transcript;

import com.transcriptanalyzer.transcript.User_Requests_Intents.Repository.TranscriptRepository;
import com.transcriptanalyzer.transcript.User_Requests_Intents.Service.PropertiesReader;
import com.transcriptanalyzer.transcript.User_Requests_Intents.Service.PropertiesWriter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TranscriptApplication {

	//Run this application to start up the Springboot application
	public static void main(String[] args) {
		SpringApplication.run(TranscriptApplication.class, args);
	}

}


