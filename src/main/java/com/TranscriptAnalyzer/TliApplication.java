package com.TranscriptAnalyzer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.Transcript.Analyzer.service,TranscriptService;
@SpringBootApplication
public class TliApplication {

	public static void main(String[] args) {
		SpringApplication.run(TliApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {

		// save transcipts
		createTranscript(new Transcript("Shopping", "I'm looking for the new rare beauty blush"));
//		repository.save(new Transcript("Shopping", "I'm looking for the new rare beauty blush"));
//		repository.save(new Transcript("Hotel", "Please book me a hotel"));
//		repository.save(new Transcript("Taxi", "Please order me a taxi"));


		// fetch all transcripts
		System.out.println("Transcripts found with findAll():");
		System.out.println("-------------------------------");
		for (Transcript transcript : repository.findAll()) {
			System.out.println(transcript);
		}
		System.out.println();

		// fetch an individual customer
		System.out.println("Transcript found with findByIntent('Hotel'):");
		System.out.println("--------------------------------");
		System.out.println(repository.findByIntent("Hotel"));
	}

}

}
