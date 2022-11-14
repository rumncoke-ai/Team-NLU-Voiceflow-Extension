package com.transcriptanalyzer.transcript.User_Requests_Intents;

import com.transcriptanalyzer.transcript.UserAPIRequestModel;
import com.transcriptanalyzer.transcript.UserTreeMap;

public interface UserApiInputBoundary {
    //call Transcripts from the API Key data

    UserTreeMap create(UserAPIRequestModel requestModel);


}
