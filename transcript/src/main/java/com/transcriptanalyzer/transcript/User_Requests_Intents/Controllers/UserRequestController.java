package com.transcriptanalyzer.transcript.User_Requests_Intents.Controllers;

import com.transcriptanalyzer.transcript.User_Requests_Intents.Documents.API;
import com.transcriptanalyzer.transcript.User_Requests_Intents.Documents.BlockRequest;
import com.transcriptanalyzer.transcript.User_Requests_Intents.Service.UserRequestInteractor;
import com.transcriptanalyzer.transcript.User_Requests_Intents.Service.createBlocksVoiceflow;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@RestController
@RequestMapping("api/v1/transcripts")
@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "https://nluchatbotpromptanalyzer.netlify.app/"})

public class UserRequestController{
    @Autowired
    private final UserRequestInteractor interactor;

    // GET MAPPINGS
    @GetMapping() // Get mapping to avoid AWS pinging the base route and saying the deployment health is not okay
    public ResponseEntity getServiceName() {
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/threeIntents/api") // Returns the top three intents to be added to the front end
    public ArrayList<ArrayList<String>> getTreeThreeIntentsWithAPI
            (@RequestHeader("Authorization") String authHeader) throws IOException {


        System.out.println(authHeader);

        String encoded = authHeader.substring(6);

        System.out.println(encoded);

        byte[] decoded = Base64.getDecoder().decode(encoded);

        System.out.println(Arrays.toString(decoded));

        String decodedString = new String(decoded, StandardCharsets.UTF_8);

        System.out.println(decodedString);


        //assumes api key and version do not have semicolons in them
        String[] apiInfo = decodedString.split(":");

        API api = new API(apiInfo[0], apiInfo[1]);

        return interactor.getBestIntents(api);
    }

    // POST MAPPINGS

    @PostMapping("/createBlock")
    public void createVoiceflowBlock(@RequestBody BlockRequest blockRequest) throws Exception {
        String email = blockRequest.getEmailAddress();
        String password = blockRequest.getPassword();
        String diagramID = blockRequest.getDiagramID();
        String intent1 = blockRequest.getIntent1();
        String intent2 = blockRequest.getIntent2();
        String intent3 = blockRequest.getIntent3();
        System.out.println(intent1);
        createBlocksVoiceflow.add_block(email, password, diagramID, intent1, intent2, intent3);
    }
}