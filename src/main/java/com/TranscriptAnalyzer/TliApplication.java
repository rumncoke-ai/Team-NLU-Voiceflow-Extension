package com.TranscriptAnalyzer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import com.TranscriptAnalyzer.service.TranscriptService;
import com.TranscriptAnalyzer.documents.Transcript;
import org.springframework.beans.factory.annotation.Autowired;

import com.TranscriptAnalyzer.repository.TranscriptRepository;


@SpringBootApplication
public class TliApplication implements CommandLineRunner {

	@Autowired
	private TranscriptRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(TliApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {

		// save transcipts
		Transcript transcript = new Transcript("Shopping", "I'm looking for the new rare beauty blush");
		repository.createTranscript(transcript);
//		repository.save(new Transcript("Shopping", "I'm looking for the new rare beauty blush"));
//		repository.save(new Transcript("Hotel", "Please book me a hotel"));
//		repository.save(new Transcript("Taxi", "Please order me a taxi"));
}

}
