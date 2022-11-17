package com.transcriptanalyzer.transcript.User_Requests_Intents;

import com.transcriptanalyzer.transcript.User_Requests_Intents.Documents.APIFactory;
import com.transcriptanalyzer.transcript.User_Requests_Intents.Documents.TranscriptFactory;

public class UserRequestInteractor implements UserApiInputBoundary{
    //This is where the main logic occurs
    // Once the user has uploaded their api-key and api-version we want to access all transcripts break them down into
    //individual turns and then upload them onto the database
    //During the above process we need to use gateway to update a static dictionary that contains all intents and the
    //number of occurences


    final UserRequestsIntentsDSGateway userDsGateway;

    final APIFactory apiFactory;

    final TranscriptFactory transcriptFactory;

    public UserRequestInteractor(UserRequestsIntentsDSGateway userDsGateway, APIFactory apiFactory,
                                  TranscriptFactory transcriptFactory) {
        this.userDsGateway =userDsGateway;
        //this.userPresenter = userRegisterPresenter;
        this.apiFactory = apiFactory;
        this.transcriptFactory = transcriptFactory;
    }

//    @Override
//    public UserTreeMap create(UserAPIRequestModel requestModel){
//
//    }

}
