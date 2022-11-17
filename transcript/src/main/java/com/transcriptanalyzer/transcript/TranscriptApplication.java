package com.transcriptanalyzer.transcript;

import com.transcriptanalyzer.transcript.User_Requests_Intents.Repository.TranscriptRepository;
import com.transcriptanalyzer.transcript.User_Requests_Intents.Service.PropertiesReader;
import com.transcriptanalyzer.transcript.User_Requests_Intents.Service.PropertiesWriter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TranscriptApplication {

	public static void main(String[] args) {
		SpringApplication.run(TranscriptApplication.class, args);
	}

//	@Bean2
//	CommandLineRunner runner(
//			TranscriptRepository repository, MongoTemplate mongoTemplate) {
//		return args -> {
//
//			String intent = "Hotel";
//			String content = "Please fine me a hotel!";
//			Transcript transcript = new Transcript(
//					intent,
//					content);
//			Transcript transcript1 = new Transcript("Makeup", "I would like to purchase a blush please.");
//
//			//usingMongoTemplateAndQuery(repository, mongoTemplate, intent, transcript);
//
//			repository.findTranscriptByIntent(intent)
//					.ifPresentOrElse(t -> {System.out.println(t + " already exists");
//
//					}, () -> {System.out.println("Inserting transcript " + transcript);
//						repository.insert(transcript);});
//
//		};
//	}
//
//	private static void usingMongoTemplateAndQuery(TranscriptRepository repository, MongoTemplate mongoTemplate, String intent, Transcript transcript) {
//		Query query = new Query();
//		query.addCriteria(Criteria.where("intent").is(intent));
//
//		List<Transcript> transcripts = mongoTemplate.find(query, Transcript.class);
//
//		if (transcripts.size() > 1) {
//			throw new IllegalStateException(
//					"found many transcripts with intent " + intent);
//		}
//
//		if (transcripts.isEmpty()) {
//			System.out.println("Inserting transcript " + transcript);
//			repository.insert(transcript);
//		}
//		else {
//			System.out.println(transcript + "already exists");
//		}
//	}
}


