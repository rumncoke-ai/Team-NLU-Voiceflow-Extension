package com.transcriptanalyzer.transcript;

import com.transcriptanalyzer.transcript.User_Requests_Intents.Documents.API;
import com.transcriptanalyzer.transcript.User_Requests_Intents.Documents.BlockRequest;
import com.transcriptanalyzer.transcript.User_Requests_Intents.Service.TranscriptService;
import com.transcriptanalyzer.transcript.User_Requests_Intents.Service.createBlocksVoiceflow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class ApplicationTests {

	API api;
	BlockRequest blockRequest;

	@BeforeEach
	void createExampleData(){
		String apiKey = "apiKey";
		String versionID = "versionID";
		api = new API(apiKey, versionID);
		String email = "emailAddress@gmail.com";
		String password = "password1234";
		String diagramID = "diagramID";
		String intent1 = "intent1";
		String intent2 = "intent2";
		String intent3 = "intent3";
		blockRequest = new BlockRequest(email, password, diagramID, intent1, intent2, intent3);
	}

	// Tests getter methods for API
	@Test
	void getApiKey() {
		assertEquals(api.getApiKey(), "apiKey");
	}

	@Test
	void getApiVersion() {
		assertEquals(api.getApiVersion(), "versionID");
	}

	// Tests if getJSONcontent_2 throws error message if given a invalid block request information
	@Test
	public void testInvalidAPIInfo() {

		IOException thrown = assertThrows(
				IOException.class,
				() -> TranscriptService.getJSONContent_2(api));


		// assertions on the thrown exception
		assertEquals("Server returned HTTP response code: 503 for URL: " +
				"https://api-dm-test.voiceflow.fr/exportraw/apiKey?versionID=versionID", thrown.getMessage());
	}

	// Tests getter methods for BlockRequest
	@Test
	void getEmailAddress() {
		assertEquals(blockRequest.getEmailAddress(),"emailAddress@gmail.com");
	}

	@Test
	void getPassword() {
		assertEquals(blockRequest.getPassword(),"password1234");
	}

	@Test
	void getDiagramID() {
		assertEquals(blockRequest.getDiagramID(),"diagramID");
	}

	@Test
	void getIntent1() {
		assertEquals(blockRequest.getIntent1(),"intent1");
	}
	@Test
	void getIntent2() {
		assertEquals(blockRequest.getIntent2(),"intent2");
	}

	@Test
	void getIntent3() {
		assertEquals(blockRequest.getIntent3(),"intent3");
	}

	// Tests if createBlocksVoiceflow throws error message if given a invalid block request information
	@Test
	public void testInvalidBlockRequest() {

		IOException thrown = assertThrows(
				IOException.class,
				() -> createBlocksVoiceflow.add_block(blockRequest.getEmailAddress(),
						blockRequest.getPassword(),
						blockRequest.getDiagramID(),
						blockRequest.getIntent1(),
						blockRequest.getIntent2(),
						blockRequest.getIntent3()));


		// assertions on the thrown exception
		assertEquals("Server returned HTTP response code: 406 for URL: " +
				"https://api.voiceflow.com/session", thrown.getMessage());
	}


}
