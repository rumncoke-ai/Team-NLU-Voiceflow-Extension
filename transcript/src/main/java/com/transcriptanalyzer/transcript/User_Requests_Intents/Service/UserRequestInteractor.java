package com.transcriptanalyzer.transcript.User_Requests_Intents.Service;

import com.transcriptanalyzer.transcript.User_Requests_Intents.Documents.API;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;


/**
 * UserRequestInteractor class contains all important methods that may be needed for HTTP requests in the controller
 *
 * */
@AllArgsConstructor
@Service
public class UserRequestInteractor {

    // Creates Instances of tree class to access methods
    private Tree tree;



    /**
     * Method getBestIntents.
     *
     * @param api - API info; consisting of api key and version id
     * @return Return best intents from the transcript data linked to the API information
     *
     * The Data is returned in the following format:
     *         <<Option A1, Option A2, Option A3>,<Option B1, Option B2, Option B3>>
     *
     *          Where option A deal with a general analysis of the transcript data and
     *          option B finds most common occurences within the leaf nodes of the tree
     *          representation of the tree data
     */
    public ArrayList<ArrayList<String>> getBestIntents(API api) throws IOException {
        Tree intentTree = new Tree(TranscriptService.getJSONContent_2(api));
        return intentTree.getBestIntents();
    }

}
